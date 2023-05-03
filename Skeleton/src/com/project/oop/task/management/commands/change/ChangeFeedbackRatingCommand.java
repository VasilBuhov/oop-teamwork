package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeFeedbackRatingCommand implements Command {
    public static final String ENTER_ID_MESSAGE =
            "Please enter a valid ID or 'cancel' if you want to exit:";
    public static final String FEEDBACK_NOT_FOUND_MESSAGE =
            "Feedback with id: %d is not found! Please enter a valid id or 'cancel if you want to exit:";
    public static final String ENTER_RATING_MESSAGE =
            "Please enter a new rating or 'cancel' if you want to exit:";
    public static final String PARSING_ERROR_MESSAGE =
            "Invalid input, must be a number! Please try again or enter 'cancel' if you want to exit:";
    public static final String CHANGED_RATING =
            "Rating to feedback with id: %d was changed to %d.";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";


    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private int id;
    private int newRating;
    private final TaskManagementRepository repository;

    public ChangeFeedbackRatingCommand(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
         Scanner scanner = new Scanner(System.in);

        System.out.println(ENTER_ID_MESSAGE);
        boolean isValidId = false;
        while (!isValidId) {
            String input = scanner.nextLine();
            if (input.equals("cancel")) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
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

        System.out.println(ENTER_RATING_MESSAGE);
        boolean isValidRating = false;
        while (!isValidRating) {
            String input = scanner.nextLine();
            if (input.equals("cancel")) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
            try {
                newRating = ParsingHelpers.tryParseInt(input, PARSING_ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Try again or enter 'cancel' to exit:");
            }
            if (newRating != 0) {
                isValidRating = true;
                parameters.add(newRating + "");
            }
        }

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        repository.changeFeedbackRating(id, newRating);

        return String.format(CHANGED_RATING, id, newRating);
    }

}
