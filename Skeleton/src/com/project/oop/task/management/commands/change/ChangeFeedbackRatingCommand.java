package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeFeedbackRatingCommand implements Command {
    public static final String CHANGED_RATING = "Rating to feedback with id: %d was changed to %d.";
    public static final String FEEDBACK_NOT_FOUND_MESSAGE = "Feedback with id: %d is not found!";
    public static final String INVALID_INPUT = "Command is terminated. Please enter a new command:";

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


        System.out.println("Please enter a valid id or 'cancel' if you want to exit: ");
        boolean isValidId = false;
        while (!isValidId) {
            String input = scanner.nextLine();
            if (input.equals("cancel")) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
            try {
                id = ParsingHelpers.tryParseInt(input, "Invalid ID, must be a number!");
                if (repository.getFeedback().stream().anyMatch(feedback -> feedback.getId() == id)) {
                    isValidId = true;
                    parameters.add(String.valueOf(id));
                } else {
                    System.out.println(FEEDBACK_NOT_FOUND_MESSAGE);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Please enter a new rating or 'cancel' if you want to exit: ");
        boolean isValidRating = false;
        while (!isValidRating) {
            String input = scanner.nextLine();
            if (input.equals("cancel")) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
            try {
                newRating = ParsingHelpers.tryParseInt(input, "Invalid rating, must be a number!");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            if (newRating != 0) {
                isValidRating = true;
                parameters.add(newRating + "");
            }
        }

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        repository.changeFeedbackRating(id, newRating);

        return String.format(CHANGED_RATING, id, newRating);

//        System.out.println("Please enter a valid id: ");
//        id = ParsingHelpers.tryParseInt(scanner.nextLine(), "Invalid input, id must be a number!");
//        if (repository.getFeedback().stream().anyMatch(feedback -> feedback.getId() == id)) {
//            parameters.add(String.valueOf(id));
//            System.out.println("Please enter a new rating: ");
//            newRating = ParsingHelpers.tryParseInt(scanner.nextLine(), "Invalid input, rating must be a number!");
//            parameters.add(String.valueOf(newRating));
//
//            ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
//
//            repository.changeFeedbackRating(id, newRating);
//
//            return String.format(CHANGED_RATING, id, newRating);
//        } else {
//            throw new IllegalArgumentException(String.format(FEEDBACK_NOT_FOUND_MESSAGE, id));
//        }
    }

}
