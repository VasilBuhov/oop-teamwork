package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeStorySizeCommand implements Command{

    private Size size;
    private int storyId;


    private final TaskManagementRepository repository;
    public ChangeStorySizeCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

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
        boolean sizeIsValid = false;
        System.out.println("Please enter the new size of the story:");
        while (!sizeIsValid){
            String newSize = scanner.nextLine();
            try {
                if (!newSize.equalsIgnoreCase("small")
                        && (!newSize.equalsIgnoreCase("medium"))
                        && (!newSize.equalsIgnoreCase("large"))){
                    throw new IllegalArgumentException("Size is not valid. Please choose between SMALL, MEDIUM and LARGE or cancel if you want to exit:");
                }
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                newSize = "";
            }

            if (!newSize.equals("")){
                sizeIsValid = true;
                size = Size.valueOf(newSize.toUpperCase());
            }
        }

        repository.changeStorySize(storyId, size);

        return String.format("Story size was changed to %s", size.name());
    }
}
