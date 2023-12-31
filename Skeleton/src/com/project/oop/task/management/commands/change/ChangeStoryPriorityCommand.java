package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.utils.MessageHelper;
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
        MessageHelper.printPromptMessage("story ID");
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
                    repository.checkForTaskId(storyId);
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
        MessageHelper.printPromptMessage("new priority");
        while (!priorityIsValid){
            String newPriority = scanner.nextLine();
            repository.isItCancel(newPriority, MessageHelper.INVALID_INPUT);
            try {
                repository.checkForStoryPriority(newPriority);
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

        return String.format(MessageHelper.STORY_PRIORITY_CHANGED, priority.name());
    }

}
