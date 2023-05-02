package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChangeStoryPriorityCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private Priority priority;


    private final TaskManagementRepository repository;
    public ChangeStoryPriorityCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(  List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter story ID:");
        int storyId = ParsingHelpers.tryParseInt(scanner.nextLine(), "ID is not valid");

        System.out.println("Please enter the new priority of the story:");
        String newPriority = scanner.nextLine();
        parameters.add(newPriority);

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        priority = Priority.valueOf(newPriority);

        repository.changeStoryPriority(storyId, priority);

        return String.format("Story priority was changed to %s", newPriority);
    }
}
