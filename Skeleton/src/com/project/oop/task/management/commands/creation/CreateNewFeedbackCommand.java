package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.TaskImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.utils.MessageHelper;
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
    private Team teamToAddFeedback;
    private Board boardToAddFeedback;

    private final TaskManagementRepository repository;

    public CreateNewFeedbackCommand(TaskManagementRepository taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        boolean allParamsValid = false;

        MessageHelper.printPromptMessage("team name");
        boolean teamIsValid = false;

        while (!teamIsValid) {
            teamName = scanner.nextLine();
            repository.isItCancel(teamName, MessageHelper.INVALID_INPUT);
            if (repository.isTeamAlreadyCreated(teamName)){
                teamToAddFeedback = repository.findTeamByName(teamName);
                teamIsValid = true;
            }else {
                System.out.println(MessageHelper.TEAM_IS_NOT_FOUNDED);
            }
        }

            MessageHelper.printPromptMessage("board name");
            boolean boardIsValid = false;

            while (!boardIsValid) {
                boardName = scanner.nextLine();
                repository.isItCancel(boardName, MessageHelper.INVALID_INPUT);
                if (repository.isBoardAlreadyCreated(teamName, boardName)){
                    boardToAddFeedback = repository.findBoardByName(boardName, teamName);
                    boardIsValid = true;
                }else {
                    System.out.println(MessageHelper.BOARD_IS_NOT_FOUNDED);
                }
            }

            while (!allParamsValid) {
                MessageHelper.printPromptMessage("feedback title");
                boolean titleIsValid = false;
                while (!titleIsValid) {
                    title = scanner.nextLine();
                    repository.isItCancel(title, MessageHelper.INVALID_INPUT);
                    try {
                        TaskImpl.validateTitle(title);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        title = "";
                    }
                    if (!title.equals("")) {
                        titleIsValid = true;
                    }
                }
                MessageHelper.printPromptMessage("feedback description");
                boolean descriptionIsValid = false;
                while (!descriptionIsValid) {

                    description = scanner.nextLine();
                    repository.isItCancel(description, MessageHelper.INVALID_INPUT);
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

                MessageHelper.printPromptMessage("feedback rating");
                boolean ratingIsValid = false;
                while (!ratingIsValid) {
                    String input = scanner.nextLine();
                    repository.isItCancel(input, MessageHelper.INVALID_INPUT);
                    try {
                        rating = ParsingHelpers.tryParseInt(input, MessageHelper.RATING_NOT_VALID);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                        rating = 0;
                    }
                    if (rating != 0) {
                        ratingIsValid = true;
                    }
                }
                allParamsValid = true;
            }

            Feedback createdFeedback = repository.createFeedback(title, description, rating);
            boardToAddFeedback.addTask(createdFeedback);

            return String.format(MessageHelper.FEEDBACK_CREATED,
                    createdFeedback.getId(), boardToAddFeedback.getName(), teamToAddFeedback.getName());
        }
    }


