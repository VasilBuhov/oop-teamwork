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
import java.util.stream.Collectors;

public class ChangeStoryPriorityCommand implements Command {


    private Priority priority;
    private int storyId;


    private final TaskManagementRepository repository;
    public ChangeStoryPriorityCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(  List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        boolean idIsValid = false;
        System.out.println("Please enter story ID or 'cancel' if you want to exit:");
        while (!idIsValid){
            String input = scanner.nextLine();
            if (input.equals("cancel")){
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try{
                storyId = ParsingHelpers.tryParseInt(input,
                        "ID is not valid. Please enter a number or 'cancel' if you want to exit:");
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            if (storyId != 0){
                try{
                    if (!repository.getTasks().stream().anyMatch(task -> task.getId() == storyId)) {
                        throw new IllegalArgumentException("Story with this ID not found. Please enter another ID or 'cancel' if you want to exit:");
                    }
                }catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    storyId = 0;
                }
            }

            if (storyId != 0){
                idIsValid = true;
            }

        }
        boolean priorityIsValid = false;
        System.out.println("Please enter the new priority of the story:");
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

        repository.changeStoryPriority(storyId, priority);

        return String.format("Story priority was changed to %s", priority.name());
    }

}
