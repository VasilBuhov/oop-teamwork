package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.utils.MessageHelper;
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
            repository.isItCancel(input, MessageHelper.INVALID_INPUT);
            try{
                storyId = ParsingHelpers.tryParseInt(input, MessageHelper.PARSING_ERROR_MESSAGE);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }

            if (storyId != 0){
                try{
                    if (!repository.isTaskAlreadyCreated(storyId)) {
                        throw new IllegalArgumentException(String.format(MessageHelper.TASK_NOT_FOUND_MESSAGE, storyId));
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
            repository.isItCancel(newSize, MessageHelper.INVALID_INPUT);
            try {
                if (!newSize.equalsIgnoreCase("small")
                        && (!newSize.equalsIgnoreCase("medium"))
                        && (!newSize.equalsIgnoreCase("large"))){
                    throw new IllegalArgumentException(MessageHelper.STORY_SIZE_NOT_VALID);
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

        return String.format(MessageHelper.STORY_SIZE_CHANGED, size.name());
    }
}
