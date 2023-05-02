package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.enums.FeedbackStatus;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeFeedbackStatusCommand implements Command {
    public static final String CHANGED_STATUS = "Status of feedback with id: %d was changed from %s to %s.";
    public static final String INVALID_INPUT_MESSAGE = "Invalid input, must be a number!";
    public static final String INVALID_DIRECTION_MESSAGE = "Invalid direction!";
    public static final String CANNOT_REVERT_MESSAGE = "You cannot revert the status anymore. It's already at Status: New.%n";
    public static final String CANNOT_ADVANCE_MESSAGE = "You cannot advance the status anymore. It's already at Status: Done.%n";
    public static final String FEEDBACK_NOT_FOUND_MESSAGE = "Feedback with id: %d is not found!";
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

        System.out.println("Please enter a valid id: ");
        id = ParsingHelpers.tryParseInt(scanner.nextLine(), INVALID_INPUT_MESSAGE);
        if (repository.getFeedback().stream().anyMatch(feedback -> feedback.getId() == id)) {
            parameters.add(String.valueOf(id));
            System.out.println("Please enter a valid direction (advance or revert): ");
            direction = scanner.nextLine();
            if (direction.equals("advance") || direction.equals("revert")) {
                parameters.add(direction);
                ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
                String oldStatus = "";
                for (Feedback feedback : repository.getFeedback()) {
                    if (feedback.getId() == id) {
                        oldStatus = feedback.getStatus();
                    }
                }
                String newStatus = repository.changeFeedbackStatus(id, direction);

                if (newStatus.equals(oldStatus) && newStatus.equals(FeedbackStatus.NEW.toString())) {
                    return String.format(CANNOT_REVERT_MESSAGE);
                } else if (newStatus.equals(oldStatus) && newStatus.equals(FeedbackStatus.DONE.toString())) {
                    return String.format(CANNOT_ADVANCE_MESSAGE);
                } else {
                    return String.format(CHANGED_STATUS, id, oldStatus, newStatus);
                }
            } else {
                throw new IllegalArgumentException(INVALID_DIRECTION_MESSAGE);
            }
        } else {
            throw new IllegalArgumentException(String.format(FEEDBACK_NOT_FOUND_MESSAGE, id));
        }
    }

}
