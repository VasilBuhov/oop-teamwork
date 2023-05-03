package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.TaskImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CreateNewFeedbackCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    private int id;
    private String title;
    private String description;
    private int rating;
    private String teamName;
    private String boardName;

    private final TaskManagementRepository repository;

    public CreateNewFeedbackCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        boolean allParamsValid = false;
        //String title;
        //String description;
        //int rating;


        System.out.println("Please enter a team name for your feedback:");
        boolean teamIsValid = false;
        Team teamToAddFeedback = null;

        while (!teamIsValid) {
            teamName = scanner.nextLine();
            if (teamName.equals("cancel")){
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            if (repository.getTeams().stream().anyMatch(team -> team.getName().equals(teamName))){
                teamToAddFeedback = repository
                        .getTeams()
                        .stream()
                        .filter(team -> team.getName().equals(teamName))
                        .collect(Collectors.toList())
                        .get(0);
                teamIsValid = true;
            }else {
                System.out.println("Team is not found. Please enter a valid team name or 'cancel' if you want to exit");
            }
        }

            System.out.println("Please enter the board name for your feedback:");
            boolean boardIsValid = false;
            Board boardToAddFeedback = null;

            while (!boardIsValid) {
                boardName = scanner.nextLine();
                if (boardName.equals("cancel")){
                    throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
                }
                if (teamToAddFeedback.getBoards().stream().anyMatch(board -> board.getName().equals(boardName))){
                    boardToAddFeedback = teamToAddFeedback
                            .getBoards()
                            .stream()
                            .filter(board -> board.getName().equals(boardName))
                            .collect(Collectors.toList())
                            .get(0);
                    boardIsValid = true;
                }else {
                    System.out.println("Board is not found. Please enter a valid board name:");
                }

            }


            while (!allParamsValid) {
                System.out.println("Please enter feedback title or 'cancel' if you want to exit:");
                boolean titleIsValid = false;
                while (!titleIsValid) {
                    title = scanner.nextLine();
                    if (title.equals("cancel")){
                        throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
                    }
                    try {
                        TaskImpl.validateTitle(title);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        title = "";
                    }
                    if (!title.equals("")) {
                        titleIsValid = true;
                        parameters.add(title);
                    }
                }
                System.out.println("Please enter feedback description or 'cancel' if you want to exit:");
                boolean descriptionIsValid = false;
                while (!descriptionIsValid) {

                    description = scanner.nextLine();
                    if (description.equals("cancel")){
                        throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
                    }
                    try {
                        TaskImpl.validateDescription(description);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        description = "";
                    }
                    if (!description.equals("")) {
                        descriptionIsValid = true;
                        parameters.add(description);
                    }
                }

                System.out.println("Please enter feedback rating or 'cancel' if you want to exit:");
                boolean ratingIsValid = false;
                while (!ratingIsValid) {
                    String input = scanner.nextLine();
                    if (input.equals("cancel")){
                        throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
                    }
                    try {
                        rating = ParsingHelpers.tryParseInt(input, "Rating is not valid");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        rating = 0;
                    }
                    if (rating != 0) {
                        ratingIsValid = true;
                        parameters.add(rating + "");
                    }
                }
                allParamsValid = true;
            }

            title = parameters.get(0);
            description = parameters.get(1);
            rating = Integer.parseInt(parameters.get(2));


            Feedback createdFeedback = repository.createFeedback(title, description, rating);
            boardToAddFeedback.addTask(createdFeedback);

            return String.format("Feedback with ID %d was created and added to board %s of team %s.",
                    createdFeedback.getId(), boardToAddFeedback.getName(), teamToAddFeedback.getName());
        }

    }


