package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeFeedbackStatusCommand implements Command {
    public static final String CHANGED_STATUS = "Status of feedback with id: %d was changed to %s.";
    public static final String INVALID_INPUT_MESSAGE = "Invalid input, must be a number!";
    public static final String INVALID_DIRECTION_MESSAGE = "Invalid direction!";
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
        parameters.add(String.valueOf(id));
        System.out.println("Please enter a valid direction (advance or revert): ");
        direction = scanner.nextLine();
        if (direction.equals("advance") || direction.equals("revert")) {
            parameters.add(direction);
            ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
            String newStatus = repository.changeFeedbackStatus(id, direction);
            return String.format(CHANGED_STATUS, id, newStatus);
        } else {
            throw new IllegalArgumentException(INVALID_DIRECTION_MESSAGE);
        }
    }

}
