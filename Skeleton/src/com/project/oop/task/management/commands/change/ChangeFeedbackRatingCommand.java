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

        System.out.println("Please enter a valid id: ");
        id = ParsingHelpers.tryParseInt(scanner.nextLine(), "Invalid input, id must be a number!");
        parameters.add(String.valueOf(id));
        System.out.println("Please enter a new rating: ");
        newRating = ParsingHelpers.tryParseInt(scanner.nextLine(), "Invalid input, rating must be a number!");
        parameters.add(String.valueOf(newRating));

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        repository.changeFeedbackRating(id, newRating);

        return String.format(CHANGED_RATING, id, newRating);
    }

}
