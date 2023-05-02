package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.FeedbackImpl;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class CreateNewStoryCommand implements Command{
    public static final String STORY_CREATED = "Story with id: %d and title: %s was created.";
    public static final String NOT_A_MEMBER_MESSAGE = "You are not part of the team and cannot create a new story!";
    public static final String BOARD_IS_NOT_FOUNDED = "This board is not founded in your team!";
    public static final String TEAM_IS_NOT_FOUNDED = "Team is not founded.";
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 5;
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
        team = scanner.nextLine();
        if (repository.getTeams().stream().anyMatch(team1 -> team1.getName().equals(team))) {
        System.out.println("Please enter your name, as assignee: ");
        assignee = scanner.nextLine();
        if (isValidAssignee(assignee)) {
            parameters.add(assignee);
            System.out.println("Please enter a valid title: ");
            title = scanner.nextLine();
            StoryImpl.validateTitle(title);
            parameters.add(title);
            System.out.println("Please enter a valid description: ");
            description = scanner.nextLine();
            StoryImpl.validateDescription(description);
            parameters.add(description);
            System.out.println("Please enter a valid priority: ");
            priority = ParsingHelpers.tryParseEnum(scanner.nextLine(), Priority.class);
            parameters.add(priority.toString());
            System.out.println("Please enter a valid size: ");
            size = ParsingHelpers.tryParseEnum(scanner.nextLine(), Size.class);
            parameters.add(size.toString());
            System.out.println("Please int which board you would like to add this story: ");
            targetBoard = scanner.nextLine();
            if (repository.getTeams().stream().anyMatch(team1 -> team1.getBoards().stream().anyMatch(board -> board.getName().equals(targetBoard)))) {
                parameters.add(targetBoard);

                ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

                StoryImpl story = repository.createNewStory(title, description, priority, size, assignee);

                for (Team team1 : repository.getTeams()) {
                    if (team1.getName().equals(team)) {
                        for (Board board : team1.getBoards()) {
                            if (board.getName().equals(targetBoard)) {
                                board.addTask(story);
                            }
                        }
                    }
                }
                return String.format(STORY_CREATED, story.getId(), story.getTitle());
            }
            throw new IllegalArgumentException(BOARD_IS_NOT_FOUNDED);
        }
            throw new IllegalArgumentException(NOT_A_MEMBER_MESSAGE);
        }
        throw new IllegalArgumentException(TEAM_IS_NOT_FOUNDED);
    }

    private boolean isValidAssignee(String assignee) {
        for (Team team1 : repository.getTeams()) {
            if (team1.getName().equals(team)) {
                for (Member member : team1.getMembers()) {
                    if (member.getName().equals(assignee)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
