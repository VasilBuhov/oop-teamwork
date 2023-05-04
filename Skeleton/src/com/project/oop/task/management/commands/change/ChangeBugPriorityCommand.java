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
    public static final String ENTER_ID_MESSAGE =
            "Please enter a valid ID or 'cancel' if you want to exit:";


    public static int EXPECTED_NUMBER_OF_ARGUMENTS=2;

    private Priority priority;

    private int id;
    private final TaskManagementRepositoryImpl repository;

    public ChangeBugPriorityCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ENTER_ID_MESSAGE);
        boolean idIsValid = false;
        System.out.println("Please enter bug ID or 'cancel' if you want to exit:");
        while (!idIsValid){
            String input = scanner.nextLine();
            if (input.equals("cancel")){
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try{
                id = ParsingHelpers.tryParseInt(input,
                        "ID is not valid. Please enter a number or 'cancel' if you want to exit:");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            if (id != 0){
                try{
                    if (!repository.getBugs().stream().anyMatch(task -> task.getId() == id)) {
                        throw new IllegalArgumentException("ID with this ID not found. Please enter another ID or 'cancel' if you want to exit:");
                    }
                }catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    id = 0;
                }
            }
            if (id != 0){
                idIsValid = true;
            }
        }
        boolean priorityIsValid = false;
        System.out.println("Please enter the new priority of the id:");
        while (!priorityIsValid){
            String newPriority = scanner.nextLine();
            try {
                if (!newPriority.equalsIgnoreCase("low")
                        && (!newPriority.equalsIgnoreCase("medium"))
                        && (!newPriority.equalsIgnoreCase("high"))){
                    throw new IllegalArgumentException("Priority is not valid. Please choose between Low, Medium and High or cancel if you want to exit:");
                }
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                newPriority = "";
            }

            if (!newPriority.equals("")){
                priorityIsValid = true;
                priority = Priority.valueOf(newPriority.toUpperCase());
            }
        }

//        System.out.println("Please enter a valid priority: ");
//        priority = ParsingHelpers.tryParseEnum(scanner.nextLine(), Priority.class);
//        parameters.add(String.valueOf(priority));
//        System.out.println("Please enter a new priority: ");
//        newPriority = ParsingHelpers.tryParseEnum(scanner.nextLine(), Priority.class);
//        parameters.add(String.valueOf(newPriority));

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        repository.changeBugPriority(id, priority);
        return String.format("Bug priority was changed to %s", priority.name());
    }
}
