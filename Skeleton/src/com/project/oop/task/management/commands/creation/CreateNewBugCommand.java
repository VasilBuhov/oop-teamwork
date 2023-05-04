package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.TaskImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Severity;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CreateNewBugCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS=5;
    private String teamName;
    private String boardName;
    private String title;
    private String description;
    private  Severity severity;
    private String assignee;
    private final TaskManagementRepositoryImpl repository;
    private Priority priority;
    public static final String INVALID_INPUT = "Command is terminated. Please enter a new command:";
    public static final String NOT_A_MEMBER_MESSAGE =
            "You are not part of the team and cannot create a new Bug! " +
                    "Please enter a valid assignee or 'cancel' if you want to exit: ";
    public CreateNewBugCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        boolean allParamsValid = false;
        System.out.println("Please enter a team name for your Bug:");
        boolean teamIsValid = false;
        Team teamToAddBug = null;

        while (!teamIsValid) {
            teamName = scanner.nextLine();
            if (teamName.equals("cancel")){
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            if (repository.getTeams().stream().anyMatch(team -> team.getName().equals(teamName))){
                teamToAddBug = repository
                        .getTeams()
                        .stream()
                        .filter(team1 -> team1.getName().equals(teamName))
                        .collect(Collectors.toList())
                        .get(0);
                teamIsValid = true;
            }else {
                System.out.println("Team is not found. Please enter a valid team name or 'cancel' if you want to exit");
            }
        }
        System.out.println("Please enter the board name for your feedback:");
        boolean boardIsValid = false;
        Board boardToAddBug = null;

        while (!boardIsValid) {
            boardName = scanner.nextLine();
            if (boardName.equals("cancel")){
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            if (teamToAddBug.getBoards().stream().anyMatch(board -> board.getName().equals(boardName))){
                boardToAddBug = teamToAddBug
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
            System.out.println("Please enter Bug title or 'cancel' if you want to exit:");
            boolean titleIsValid = false;
            while (!titleIsValid) {
                title = scanner.nextLine();
                if (title.equals("cancel")) {
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
            System.out.println("Please enter Bug description or 'cancel' if you want to exit:");
            boolean descriptionIsValid = false;
            while (!descriptionIsValid) {

                description = scanner.nextLine();
                if (description.equals("cancel")) {
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
                    System.out.println(e.getMessage() +
                            String.format(" You can choose between: %s, %s or %s. Try again or enter 'cancel' to exit:",
                                    Priority.LOW, Priority.MEDIUM, Priority.HIGH));
                }
                if (priority != null) {
                    isValidPriority = true;
                    parameters.add(priority.toString());
                }
            }
            System.out.println("Please enter a valid severity: ");
            boolean isValidSeverity = false;
            while (!isValidSeverity) {
                String input = scanner.nextLine();
                if (input.equals("cancel")) {
                    throw new IllegalArgumentException(INVALID_INPUT);
                }
                try {
                    severity = ParsingHelpers.tryParseEnum(input, Severity.class);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage() +
                            String.format(" You can choose between: %s, %s or %s. Try again or enter 'cancel' to exit:",
                                    Severity.MINOR, Severity.MAJOR, Severity.CRITICAL));
                }
                if (severity != null) {
                    isValidSeverity = true;
                    parameters.add(severity.toString());
                }
            }
            System.out.println("Please enter your name, as assignee: ");
            boolean assigneeIsValid = false;
            while (!assigneeIsValid) {
                assignee = scanner.nextLine();
                if (repository.isAssigneeMemberOfTheTeam(assignee, teamName)) {
                    assigneeIsValid = true;
                    parameters.add(assignee);
                } else {
                    if (assignee.equals("cancel")) {
                        throw new IllegalArgumentException(INVALID_INPUT);
                    }
                    System.out.println(NOT_A_MEMBER_MESSAGE);
                }
            }
            allParamsValid=true;
        }
//        System.out.println("Please enter Bug title");
//        String title= scanner.nextLine();
//        parameters.add(title);
//        System.out.println("Please enter Bug description");
//
//        String description = scanner.nextLine();
//        parameters.add(description);
//        System.out.println("Please enter Bug priority");
//        Priority priority = ParsingHelpers.tryParseEnum(scanner.nextLine(), Priority.class);
//        parameters.add(priority.toString());
//        System.out.println("Please enter Bug severity");
//        Severity severity= ParsingHelpers.tryParseEnum(scanner.nextLine(),Severity.class);System.out.println("Please enter Bug assignee");
//        parameters.add(severity.toString());
//        System.out.println("Please enter Bug assignee");
//        String assignee= scanner.nextLine();
//        parameters.add(assignee);
//        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Bug createdBug = repository.createBug(title,
                description,
                priority,
                severity,
                assignee);
        boardToAddBug.addTask(createdBug);
        repository.findMemberByName(assignee, teamName).addTask(createdBug);
        return String.format("Bug with ID %d was created and added to board %s of team %s",
                createdBug.getId(),
                boardToAddBug.getName(),
                teamToAddBug.getName());
    }
}
