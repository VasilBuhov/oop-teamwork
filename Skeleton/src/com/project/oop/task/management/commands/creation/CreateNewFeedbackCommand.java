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

    private final TaskManagementRepository repository;

    public CreateNewFeedbackCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        boolean allParamsValid = false;
        String title;
        String description;
        int rating;


        System.out.println("Please enter a team name for your feedback:");
        boolean teamIsValid = false;
        Team teamToAddFeedback = null;
        String teamName;
        while (!teamIsValid) {
            teamName = scanner.nextLine();
            for (Team team : repository.getTeams()) {
                if (team.getName().equals(teamName)) {
                    teamIsValid = true;
                    teamName = team.getName();
                    teamToAddFeedback = repository.findTeamByName(teamName);
                } else {
                    System.out.println("There is no team with this name. Please enter a valid team name:");
                }
            }
        }





            System.out.println("Please enter the board name for your feedback:");
            boolean boardIsValid = false;
            String boardName;
            Board boardToAddFeedback = null;
            while (!boardIsValid) {
                boardName = scanner.nextLine();
                for (Board board : teamToAddFeedback.getBoards()) {
                    if (board.getName().equals(boardName)) {
                        boardIsValid = true;
                        boardToAddFeedback = board;
                    } else {
                        System.out.println("There is no board with this name. Please enter a valid board name:");
                    }
                }
            }


            while (!allParamsValid) {
                System.out.println("Please enter feedback title:");
                boolean titleIsValid = false;
                while (!titleIsValid) {
                    title = scanner.nextLine();
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
                System.out.println("Please enter feedback description:");
                boolean descriptionIsValid = false;
                while (!descriptionIsValid) {

                    description = scanner.nextLine();
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

                System.out.println("Please enter feedback rating:");
                boolean ratingIsValid = false;
                while (!ratingIsValid) {
                    try {
                        rating = ParsingHelpers.tryParseInt(scanner.nextLine(), "Rating is not valid");
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


