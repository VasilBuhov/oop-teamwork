package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateNewFeedbackCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 3;

    private int id;
    private String title;
    private String description;
    private int rating;

    private final TaskManagementRepository repository;
    public CreateNewFeedbackCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter feedback title:");
        String title = scanner.nextLine();
        parameters.add(title);
        System.out.println("Please enter feedback description:");
        String description = scanner.nextLine();
        parameters.add(description);
        System.out.println("Please enter feedback rating:");
        int rating = ParsingHelpers.tryParseInt(scanner.nextLine(), "Rating is not valid");
        parameters.add(rating + "");

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        Feedback createdFeedback = repository.createFeedback(title, description, rating);

        return String.format("Feedback with ID %d was created.", createdFeedback.getId());
    }

    //private void parseParameters(List<String> parameters){
    //    title = parameters.get(0);
    //    description = parameters.get(1);
    //    rating = ParsingHelpers.tryParseInt(parameters.get(2), "Rating is not valid");
    //}
}
