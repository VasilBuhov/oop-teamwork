package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Story;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortStoriesByTitleCommand implements Command {
    private final TaskManagementRepositoryImpl repository;
    public SortStoriesByTitleCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Story> sortedByTitle = repository.getStories().stream()
                .sorted(Comparator.comparing(Story::getTitle)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (Story story : sortedByTitle) {
            stringBuilder.append(story.getAsString());
        }
        if (sortedByTitle.isEmpty()) {
            return "No story created yet.";
        }
        return stringBuilder.toString();
    }
}
