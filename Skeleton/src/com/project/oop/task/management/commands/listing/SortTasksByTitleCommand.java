package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.contracts.Task;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortTasksByTitleCommand implements Command {
    private final TaskManagementRepositoryImpl repository;
    public SortTasksByTitleCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Task> sortedByTitle = repository.getTasks().stream()
                .sorted(Comparator.comparing(Task::getTitle)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : sortedByTitle) {
            stringBuilder.append(task.getAsString());
        }
        if (sortedByTitle.isEmpty()) {
            return "No task created yet.";
        }
        return stringBuilder.toString();
    }
}
