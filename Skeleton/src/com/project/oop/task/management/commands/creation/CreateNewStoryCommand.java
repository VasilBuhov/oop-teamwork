package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.TaskImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class CreateNewStoryCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 7;
    private final TaskManagementRepository repository;
    private String team;
    private String title;
    private String description;
    private Priority priority;
    private Size size;
    private String assignee;
    private String targetBoard;

    public CreateNewStoryCommand(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(MessageHelper.ENTER_TEAM_NAME_MESSAGE);
        boolean teamIsValid = false;
        while (!teamIsValid) {
            team = scanner.nextLine();
            if (repository.getTeams().stream().anyMatch(team1 -> team1.getName().equals(team))) {
                teamIsValid = true;
                parameters.add(team);
            } else {
                repository.isItCancel(team, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.TEAM_IS_NOT_FOUNDED);
            }
        }

        Board board1 = null;
        System.out.println(MessageHelper.ENTER_BOARD_NAME_MESSAGE);
        boolean boardIsValid = false;
        while (!boardIsValid) {
            targetBoard = scanner.nextLine();
            if (repository
                    .findTeamByName(team)
                    .getBoards()
                    .stream()
                    .anyMatch(board -> board.getName().equals(targetBoard))) {
                boardIsValid = true;
                board1 = repository.findBoardByName(targetBoard, team);
                parameters.add(targetBoard);
            } else {
                repository.isItCancel(targetBoard, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.BOARD_IS_NOT_FOUNDED);
            }
        }

        System.out.println(MessageHelper.ENTER_ASSIGNEE_MESSAGE);
        boolean assigneeIsValid = false;
        while (!assigneeIsValid) {
            assignee = scanner.nextLine();
            if (repository.isAssigneeMemberOfTheTeam(assignee, team)) {
                assigneeIsValid = true;
                parameters.add(assignee);
            } else {
                repository.isItCancel(assignee, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.CANNOT_ASSIGN_STORY);
            }
        }

        System.out.println(MessageHelper.ENTER_TITLE_MESSAGE);
        boolean isValidTitle = false;
        while (!isValidTitle) {
            title = scanner.nextLine();
            repository.isItCancel(title, MessageHelper.INVALID_INPUT);
            try {
                TaskImpl.validateTitle(title);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Try again or enter 'cancel' to exit:");
                title = "";
            }
            if (!title.isBlank()) {
                isValidTitle = true;
                parameters.add(title);
            }
        }


        System.out.println(MessageHelper.ENTER_DESCRIPTION_MESSAGE);
        boolean isValidDescription = false;
        while (!isValidDescription) {
            description = scanner.nextLine();
            repository.isItCancel(description, MessageHelper.INVALID_INPUT);
            try {
                TaskImpl.validateDescription(description);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Try again or enter 'cancel' to exit:");
                description = "";
            }
            if (!description.isBlank()) {
                isValidDescription = true;
                parameters.add(description);
            }
        }

        System.out.println(MessageHelper.ENTER_PRIORITY_MESSAGE);
        boolean isValidPriority = false;
        while (!isValidPriority) {
            String input = scanner.nextLine();
            repository.isItCancel(input, MessageHelper.INVALID_INPUT);
            try {
                priority = ParsingHelpers.tryParseEnum(input, Priority.class);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() +
                        String.format(" You can choose between: %s, %s or %s. Try again or enter 'cancel' to exit:",
                                Priority.LOW, Priority.MEDIUM, Priority.HIGH));
            }
            if (priority != null) {
                isValidPriority = true;
                parameters.add(priority.toString());
            }
        }

        System.out.println(MessageHelper.ENTER_SIZE_MESSAGE);
        boolean isValidSize = false;
        while (!isValidSize) {
            String input = scanner.nextLine();
            repository.isItCancel(input, MessageHelper.INVALID_INPUT);
            try {
                size = ParsingHelpers.tryParseEnum(input, Size.class);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() +
                        String.format(" You can choose between: %s, %s or %s. Try again or enter 'cancel' to exit:",
                                Size.SMALL, Size.MEDIUM, Size.LARGE));
            }
            if (size != null) {
                isValidSize = true;
                parameters.add(size.toString());
            }
        }

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        StoryImpl story = repository.createNewStory(title, description, priority, size, assignee);
        board1.addTask(story);
        return String.format(MessageHelper.STORY_CREATED, story.getId(), story.getTitle());
    }
}
