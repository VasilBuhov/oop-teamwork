package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.utils.ListingHelpers;

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

        System.out.println("Please enter a title to filter by:");
        boolean titleIsValid = false;

        while (!titleIsValid) {
            title = scanner.nextLine();
            if (title.equals("cancel")) {
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try {
                if (!repository.getTasks().stream().anyMatch(task -> task.getTitle().equals(title))) {
                    throw new IllegalArgumentException("No task with this title");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                title = "";
            }

            if (!title.equals("")) {
                titleIsValid = true;
                filteredTasks = repository
                        .getTasks()
                        .stream()
                        .filter(task -> task.getTitle().equals(title))
                        .collect(Collectors.toList());

                if (filteredTasks.size() == 0) {
                    return String.format("No tasks with this title.");
                }
            }
        }

        return ListingHelpers.tasksToString(filteredTasks);
    }
}
