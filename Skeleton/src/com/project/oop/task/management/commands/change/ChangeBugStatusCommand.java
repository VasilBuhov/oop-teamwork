package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.models.enums.BugStatus;
import com.project.oop.task.management.models.enums.FeedbackStatus;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.utils.ParsingHelpers;
import jdk.jshell.Snippet;

import java.util.List;
import java.util.Scanner;

public class ChangeBugStatusCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;
    private String direction;
    public static final String ENTER_ID_MESSAGE =
            "Please enter a valid ID or 'cancel' if you want to exit:";
    private int id;
    public static final String INVALID_DIRECTION_MESSAGE =
            "Invalid direction! Please enter a valid direction (advance or revert) or 'cancel' if you want to exit:";
    public static final String STATUS_IS_NOT_CHANGED =
            "Status remains the same - %s!";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";
    public static final String CHANGED_STATUS =
            "Status of feedback with id: %d was changed from %s to %s.";
    private final TaskManagementRepositoryImpl repository;

    public ChangeBugStatusCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = new TaskManagementRepositoryImpl();
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ENTER_ID_MESSAGE);
        boolean idIsValid = false;
        System.out.println("Please enter bug ID or 'cancel' if you want to exit:");
        while (!idIsValid) {
            String input = scanner.nextLine();
            if (input.equals("cancel")) {
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try {
                id = ParsingHelpers.tryParseInt(input,
                        "ID is not valid. Please enter a number or 'cancel' if you want to exit:");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            if (id != 0) {
                try {
                    if (!repository.getBugs().stream().anyMatch(task -> task.getId() == id)) {
                        throw new IllegalArgumentException("ID with this ID not found. Please enter another ID or 'cancel' if you want to exit:");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    id = 0;
                }
            }
            if (id != 0) {
                idIsValid = true;
            }

        }

        System.out.println("Please enter the new status of the id:");
        boolean isValidDirection = false;
        Task bug = repository.findFeedbackById(id);
        String oldStatus = "";
        while (!isValidDirection) {
            direction = scanner.nextLine();
            repository.isItCancel(direction, INVALID_INPUT);
            if (direction.equals("advance") || direction.equals("revert")) {
                isValidDirection = true;
                oldStatus = bug.getStatus();

                if (direction.equals("advance")) {
                    try {
                        repository.changeFeedbackStatus(id, direction);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (direction.equals("revert")) {
                    try {
                        repository.changeFeedbackStatus(id, direction);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } else {
                System.out.println(INVALID_DIRECTION_MESSAGE);
            }
        }
        if (oldStatus.equalsIgnoreCase(bug.getStatus())) {
            return String.format(STATUS_IS_NOT_CHANGED, bug.getStatus());
        }
        return String.format(CHANGED_STATUS, id, oldStatus, bug.getStatus());
    }
}
