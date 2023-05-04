package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Severity;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeBugSeverityCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;
    public static final String ENTER_ID_MESSAGE =
            "Please enter a valid ID or 'cancel' if you want to exit:";
    private int id;
    private Severity severity;
    private final TaskManagementRepositoryImpl repository;
    public ChangeBugSeverityCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

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
        boolean severityIsValid = false;

        System.out.println("Please enter the new severity of the id:");
        while (!severityIsValid){
            String newSeverity = scanner.nextLine();
            try {
                if (!newSeverity.equalsIgnoreCase("critical")
                        && (!newSeverity.equalsIgnoreCase("major"))
                        && (!newSeverity.equalsIgnoreCase("minor"))){
                    throw new IllegalArgumentException("Severity is not valid. Please choose between Critical, Major and Minor  or cancel if you want to exit:");
                }
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                newSeverity = "";
            }

            if (!newSeverity.equals("")){
                severityIsValid = true;
                severity= Severity.valueOf(newSeverity.toUpperCase());
            }
        }

        repository.changeBugSeverity(id, severity);
        return String.format("Bug severity was changed to %s", severity.name());

    }
}
