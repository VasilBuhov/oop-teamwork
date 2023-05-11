package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.models.enums.StoryStatus;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeStoryStatusCommand implements Command{

    private StoryStatus status;
    private int storyId;

    private final TaskManagementRepository repository;
    public ChangeStoryStatusCommand(TaskManagementRepositoryImpl taskManagementRepository) {
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
        boolean statusIsValid = false;
        System.out.println("Please enter the new status of the story:");
        while (!statusIsValid){
            String newStatus = scanner.nextLine();
            try {
                if (!newStatus.equalsIgnoreCase("Done")
                        && (!newStatus.equalsIgnoreCase("Not_Done"))
                        && (!newStatus.equalsIgnoreCase("In_Progress"))){
                    throw new IllegalArgumentException(MessageHelper.STORY_STATUS_NOT_VALID);
                }
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                newStatus = "";
            }

            if (!newStatus.equals("")){
                statusIsValid = true;
                status =StoryStatus.valueOf(newStatus.toUpperCase());
            }
        }

        repository.changeStoryStatus(storyId, status);

        return String.format(MessageHelper.STORY_STATUS_CHANGED, status.name());
    }
}
