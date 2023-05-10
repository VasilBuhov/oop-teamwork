package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.contracts.Feedback;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortBugsByTitleCommand implements Command {
    private final TaskManagementRepositoryImpl repository;
    public SortBugsByTitleCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Bug> sortedByTitle = repository.getBugs().stream()
                .sorted(Comparator.comparing(Bug::getTitle)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (Bug bug : sortedByTitle) {
            stringBuilder.append(bug.getAsString());
        }
        if (sortedByTitle.isEmpty()) {
            return "No bugs created yet.";
        }
        return stringBuilder.toString();
    }
}
