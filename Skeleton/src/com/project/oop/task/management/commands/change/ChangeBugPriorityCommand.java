package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementEngineImpl;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeBugPriorityCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS=2;
    public static final String CHANGED_PRIORITY = "Priority with priority: %s was changed to %s.";
    private Priority priority;
    private Priority newPriority;
    private final TaskManagementRepositoryImpl repository;

    public ChangeBugPriorityCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a valid priority: ");
        priority = ParsingHelpers.tryParseEnum(scanner.nextLine(), Priority.class);
        parameters.add(String.valueOf(priority));
        System.out.println("Please enter a new priority: ");
        newPriority = ParsingHelpers.tryParseEnum(scanner.nextLine(), Priority.class);
        parameters.add(String.valueOf(newPriority));

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        repository.changeBugPriority(priority, newPriority);

        return String.format(CHANGED_PRIORITY, priority, newPriority);
    }
}
