package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.FeedbackImpl;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.models.enums.FeedbackStatus;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ChangeFeedbackStatusCommand implements Command {
    public static final String ENTER_ID_MESSAGE =
            "Please enter a valid ID or 'cancel' if you want to exit:";
    public static final String FEEDBACK_NOT_FOUND_MESSAGE = "Feedback with id: %d is not found! " +
            "Please enter a valid id or 'cancel' if you want to exit:";
    public static final String ENTER_DIRECTION_MESSAGE =
            "Please enter a valid direction (advance or revert) or 'cancel' if you want to exit:";
    public static final String INVALID_DIRECTION_MESSAGE =
            "Invalid direction! Please enter a valid direction (advance or revert) or 'cancel' if you want to exit:";
    public static final String PARSING_ERROR_MESSAGE =
            "Invalid input, must be a number! Please try again or enter 'cancel' if you want to exit:";
    public static final String CHANGED_STATUS =
            "Status of feedback with id: %d was changed from %s to %s.";
    public static final String STATUS_IS_NOT_CHANGED =
            "Status remains the same - %s!";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";


    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private int id;
    private String direction;

    private final TaskManagementRepository repository;

    public ChangeFeedbackStatusCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ENTER_ID_MESSAGE);
        boolean isValidId = false;
        while (!isValidId) {
            String input = scanner.nextLine();
            repository.isItCancel(input, INVALID_INPUT);
            try {
                id = ParsingHelpers.tryParseInt(input, PARSING_ERROR_MESSAGE);
                if (repository.getFeedback().stream().anyMatch(feedback -> feedback.getId() == id)) {
                    isValidId = true;
                    parameters.add(String.valueOf(id));
                } else {
                    System.out.printf((FEEDBACK_NOT_FOUND_MESSAGE) + "%n", id);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(ENTER_DIRECTION_MESSAGE);
        boolean isValidDirection = false;
        Task feedback = repository.findFeedbackById(id);
        String oldStatus = "";
        while (!isValidDirection) {
            direction = scanner.nextLine();
            repository.isItCancel(direction, INVALID_INPUT);
            if (direction.equals("advance") || direction.equals("revert")) {
                isValidDirection = true;
                oldStatus = feedback.getStatus();

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
        if (oldStatus.equalsIgnoreCase(feedback.getStatus())) {
            return String.format(STATUS_IS_NOT_CHANGED, feedback.getStatus());
        }
        return String.format(CHANGED_STATUS, id, oldStatus, feedback.getStatus());
    }
}
