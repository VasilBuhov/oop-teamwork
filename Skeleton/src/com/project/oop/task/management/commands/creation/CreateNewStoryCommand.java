package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.TaskImpl;
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
            "You are not part of the team and cannot create a new story! Please enter a valid assignee:";
    public static final String BOARD_IS_NOT_FOUNDED =
            "This board is not founded in your team! Please enter a valid board name:";
    public static final String TEAM_IS_NOT_FOUNDED =
            "There is no team with this name. Please enter a valid team name:";
    public static final String INVALID_INPUT = "Invalid input! Enter a new command, please:";


    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 7;
    private final TaskManagementRepository repository;
    private String team;
    private String title;
    private String description;
    private Priority priority;
    private Size size;
    private String assignee;
    private String targetBoard;

    public CreateNewStoryCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }
    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your team: ");
        boolean teamIsValid = false;
        while (!teamIsValid) {
            team = scanner.nextLine();
            if (repository.getTeams().stream().anyMatch(team1 -> team1.getName().equals(team))) {
                teamIsValid = true;
                parameters.add(team);
            } else {
                if (team.equals("cancel")) {
                    throw new IllegalArgumentException(INVALID_INPUT);
                }
                System.out.println(TEAM_IS_NOT_FOUNDED);
            }
        }

        System.out.println("Please enter in which board you would like to add this story: ");
        boolean boardIsValid = false;
        while (!boardIsValid) {
            targetBoard = scanner.nextLine();
            if (repository
                    .findTeamByName(team)
                    .getBoards()
                    .stream()
                    .anyMatch(board -> board.getName().equals(targetBoard))) {
                boardIsValid = true;
                parameters.add(targetBoard);
            } else {
                if (targetBoard.equals("cancel")) {
                    throw new IllegalArgumentException(INVALID_INPUT);
                }
                System.out.println(BOARD_IS_NOT_FOUNDED);
            }
        }

        System.out.println("Please enter your name, as assignee: ");
        boolean assigneeIsValid = false;
        while (!assigneeIsValid) {
            assignee = scanner.nextLine();
            if (repository.isAssigneeMemberOfTheTeam(assignee, team)) {
                assigneeIsValid = true;
                parameters.add(assignee);
            } else {
                if (assignee.equals("cancel")) {
                    throw new IllegalArgumentException(INVALID_INPUT);
                }
                System.out.println(NOT_A_MEMBER_MESSAGE);
            }
        }

        System.out.println("Please enter a valid title: ");
        boolean isValidTitle = false;
        while (!isValidTitle) {
            title = scanner.nextLine();
            if (title.equals("cancel")) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
            try {
                TaskImpl.validateTitle(title);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                title = "";
            }
            if (!title.isBlank()) {
                isValidTitle = true;
                parameters.add(title);
            }
        }


        System.out.println("Please enter a valid description: ");
        boolean isValidDescription = false;
        while (!isValidDescription) {
            description = scanner.nextLine();
            if (description.equals("cancel")) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
            try {
                TaskImpl.validateDescription(description);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                description = "";
            }
            if (!description.isBlank()) {
                isValidDescription = true;
                parameters.add(description);
            }
        }

        System.out.println("Please enter a valid priority: ");
        boolean isValidPriority = false;
        while (!isValidPriority) {
            String input = scanner.nextLine();
            if (input.equals("cancel")) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
            try {
                priority = ParsingHelpers.tryParseEnum(input, Priority.class);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            if (priority != null) {
                isValidPriority = true;
                parameters.add(priority.toString());
            }
        }

        System.out.println("Please enter a valid size: ");
        boolean isValidSize = false;
        while (!isValidSize) {
            String input = scanner.nextLine();
            if (input.equals("cancel")) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
            try {
                size = ParsingHelpers.tryParseEnum(input, Size.class);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            if (size != null) {
                isValidSize = true;
                parameters.add(size.toString());
            }
        }

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        StoryImpl story = repository.createNewStory(title, description, priority, size, assignee);

                repository.findMemberByName(assignee, team).addTask(story);
                repository.findBoardByName(targetBoard, team).addTask(story);

                return String.format(STORY_CREATED, story.getId(), story.getTitle());
    }
}
