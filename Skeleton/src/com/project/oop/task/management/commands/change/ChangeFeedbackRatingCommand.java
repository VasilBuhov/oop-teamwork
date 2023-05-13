package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeFeedbackRatingCommand implements Command {
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

        MessageHelper.printPromptMessage("feedback ID");
        boolean isValidId = false;
        while (!isValidId) {
            String input = scanner.nextLine();
            repository.isItCancel(input, MessageHelper.INVALID_INPUT);
            try {
                id = ParsingHelpers.tryParseInt(input, MessageHelper.PARSING_ERROR_MESSAGE);
                if (repository.getFeedback().stream().anyMatch(feedback -> feedback.getId() == id)) {
                    isValidId = true;
                    parameters.add(String.valueOf(id));
                } else {
                    System.out.printf((MessageHelper.FEEDBACK_NOT_FOUND_MESSAGE) + "%n", id);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        MessageHelper.printPromptMessage("new rating");
        boolean isValidRating = false;
        while (!isValidRating) {
            String input = scanner.nextLine();
            repository.isItCancel(input, MessageHelper.INVALID_INPUT);
            try {
                newRating = ParsingHelpers.tryParseInt(input, MessageHelper.PARSING_ERROR_MESSAGE);
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

        return String.format(MessageHelper.CHANGED_RATING, id, newRating);
    }

}
