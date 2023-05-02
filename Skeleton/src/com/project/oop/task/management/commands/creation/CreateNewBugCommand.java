package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Severity;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class CreateNewBugCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS=5;

    private final TaskManagementRepositoryImpl repository;

    public CreateNewBugCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter Bug title");
        String title= scanner.nextLine();
        parameters.add(title);
        System.out.println("Please enter Bug description");

        String description = scanner.nextLine();
        parameters.add(description);
        System.out.println("Please enter Bug priority");
        Priority priority = ParsingHelpers.tryParseEnum(scanner.nextLine(), Priority.class);
        parameters.add(priority.toString());
        System.out.println("Please enter Bug severity");
        Severity severity= ParsingHelpers.tryParseEnum(scanner.nextLine(),Severity.class);System.out.println("Please enter Bug assignee");
        parameters.add(severity.toString());
        System.out.println("Please enter Bug assignee");
        String assignee= scanner.nextLine();
        parameters.add(assignee);
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        Bug createdBug = repository.createBug(title,description,priority,severity,assignee);
        return String.format("Bug with ID %d was created", createdBug.getId());
    }
}
