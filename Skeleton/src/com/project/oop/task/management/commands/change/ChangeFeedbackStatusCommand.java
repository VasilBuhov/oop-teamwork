package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ParsingHelpers;

import java.util.List;
import java.util.Scanner;

public class ChangeFeedbackStatusCommand implements Command {
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

        MessageHelper.printPromptMessage("feedback ID");
        boolean isValidId = false;
        while (!isValidId) {
            String input = scanner.nextLine();
            repository.isItCancel(input, MessageHelper.INVALID_INPUT);
            try {
                id = ParsingHelpers.tryParseInt(input, MessageHelper.PARSING_ERROR_MESSAGE);
                if (repository.getFeedback().stream().anyMatch(feedback -> feedback.getId() == id)) {
                    isValidId = true;
                } else {
                    System.out.printf((MessageHelper.FEEDBACK_NOT_FOUND_MESSAGE) + "%n", id);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(MessageHelper.ENTER_DIRECTION_MESSAGE);
        boolean isValidDirection = false;
        Task feedback = repository.findFeedbackById(id);
        String oldStatus = "";
        while (!isValidDirection) {
            direction = scanner.nextLine();
            repository.isItCancel(direction, MessageHelper.INVALID_INPUT);
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
                System.out.println(MessageHelper.INVALID_DIRECTION_MESSAGE);
            }
        }
        if (oldStatus.equalsIgnoreCase(feedback.getStatus())) {
            return String.format(MessageHelper.STATUS_IS_NOT_CHANGED, feedback.getStatus());
        }
        return String.format(MessageHelper.CHANGED_STATUS, id, oldStatus, feedback.getStatus());
    }
}
