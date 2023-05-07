package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.TaskImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class CreateNewStoryCommand implements Command{
    public static final String STORY_CREATED =
            "Story with id: %d and title: %s was created.";
    public static final String NOT_A_MEMBER_MESSAGE =
            "You are not part of the team and cannot create a new story! " +
                    "Please enter a valid assignee or 'cancel' if you want to exit: ";
    public static final String BOARD_IS_NOT_FOUNDED =
            "This board is not founded in your team! " +
                    "Please enter a valid board name or 'cancel' if you want to exit:";
    public static final String TEAM_IS_NOT_FOUNDED =
            "There is no team with this name. " +
                    "Please enter a valid team name or 'cancel' if you want to exit:";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";
    public static final String ENTER_TEAM_NAME_MESSAGE =
            "Please enter your team name or 'cancel' if you want to exit:";
    public static final String ENTER_BOARD_NAME_MESSAGE =
            "Please enter a board where you would like to add this story or 'cancel' if you want to exit:";
    public static final String ENTER_ASSIGNEE_MESSAGE =
            "Please enter your name, as assignee or 'cancel' if you want to exit:";
    public static final String ENTER_TITLE_MESSAGE =
            "Please enter a valid title or 'cancel' if you want to exit:";
    public static final String ENTER_DESCRIPTION_MESSAGE =
            "Please enter a valid description or 'cancel' if you want to exit:";
    public static final String ENTER_PRIORITY_MESSAGE =
            "Please enter a valid priority or 'cancel' if you want to exit:";
    public static final String ENTER_SIZE_MESSAGE =
            "Please enter a valid size or 'cancel' if you want to exit:";


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

        System.out.println(ENTER_TEAM_NAME_MESSAGE);
        boolean teamIsValid = false;
        while (!teamIsValid) {
            team = scanner.nextLine();
            if (repository.getTeams().stream().anyMatch(team1 -> team1.getName().equals(team))) {
                teamIsValid = true;
                parameters.add(team);
            } else {
               repository.isItCancel(team, INVALID_INPUT);
               System.out.println(TEAM_IS_NOT_FOUNDED);
            }
        }

        Board board1 = null;
        System.out.println(ENTER_BOARD_NAME_MESSAGE);
        boolean boardIsValid = false;
        while (!boardIsValid) {
            targetBoard = scanner.nextLine();
            if (repository
                    .findTeamByName(team)
                    .getBoards()
                    .stream()
                    .anyMatch(board -> board.getName().equals(targetBoard))) {
                boardIsValid = true;
                board1 = repository.findBoardByName(targetBoard,team);
                parameters.add(targetBoard);
            } else {
                repository.isItCancel(targetBoard, INVALID_INPUT);
                System.out.println(BOARD_IS_NOT_FOUNDED);
            }
        }

        System.out.println(ENTER_ASSIGNEE_MESSAGE);
        boolean assigneeIsValid = false;
        while (!assigneeIsValid) {
            assignee = scanner.nextLine();
            if (repository.isAssigneeMemberOfTheTeam(assignee, team)) {
                assigneeIsValid = true;
                parameters.add(assignee);
            } else {
                repository.isItCancel(assignee, INVALID_INPUT);
                System.out.println(NOT_A_MEMBER_MESSAGE);
            }
        }

        System.out.println(ENTER_TITLE_MESSAGE);
        boolean isValidTitle = false;
        while (!isValidTitle) {
            title = scanner.nextLine();
            repository.isItCancel(title, INVALID_INPUT);
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


        System.out.println(ENTER_DESCRIPTION_MESSAGE);
        boolean isValidDescription = false;
        while (!isValidDescription) {
            description = scanner.nextLine();
            repository.isItCancel(description, INVALID_INPUT);
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

        System.out.println(ENTER_PRIORITY_MESSAGE);
        boolean isValidPriority = false;
        while (!isValidPriority) {
            String input = scanner.nextLine();
            repository.isItCancel(input, INVALID_INPUT);
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

        System.out.println(ENTER_SIZE_MESSAGE);
        boolean isValidSize = false;
        while (!isValidSize) {
            String input = scanner.nextLine();
            repository.isItCancel(input, INVALID_INPUT);
            try {
                size = ParsingHelpers.tryParseEnum(input, Size.class);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()+
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
        return String.format(STORY_CREATED, story.getId(), story.getTitle());
    }
}
