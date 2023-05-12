package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.utils.ListingHelpers;
import com.project.oop.task.management.utils.MessageHelper;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FilterTasksByTitleCommand implements Command {
    private List<Task> filteredTasks;
    private String title;
    private final TaskManagementRepositoryImpl repository;

    public FilterTasksByTitleCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        MessageHelper.printPromptMessage("title to filter by");
        boolean titleIsValid = false;

        while (!titleIsValid) {
            title = scanner.nextLine();
            repository.isItCancel(title, MessageHelper.INVALID_INPUT);

            try {
                repository.checkForTaskTitle(title);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                title = "";
            }

            if (!title.equals("")){
                titleIsValid = true;
                filteredTasks = repository.getTasksByTitle(title);
            }
        }

        return ListingHelpers.tasksToString(filteredTasks);
    }
}
