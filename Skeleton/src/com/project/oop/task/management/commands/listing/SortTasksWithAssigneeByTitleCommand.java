package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.models.contracts.Task;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortTasksWithAssigneeByTitleCommand implements Command {
    private final TaskManagementRepositoryImpl repository;
    public SortTasksWithAssigneeByTitleCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Task> sortedByTitle = repository.getAssignedTasks().stream()
                .sorted(Comparator.comparing(Task::getTitle)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : sortedByTitle) {
            stringBuilder.append(task.getAsString());
        }
        return stringBuilder.toString();
    }
}
