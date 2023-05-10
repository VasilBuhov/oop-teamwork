package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.models.enums.Size;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortStoriesBySizeCommand implements Command {
    private final TaskManagementRepositoryImpl repository;
    public SortStoriesBySizeCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Story> sortedBySize = repository.getStories().stream()
                .sorted(Comparator.comparing(Story::getSize)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (Story story : sortedBySize) {
            if (story.getSize().equals(Size.SMALL)) {
                stringBuilder.append(story.getAsString());
            }
        }
        for (Story story : sortedBySize) {
            if (story.getSize().equals(Size.MEDIUM)) {
                stringBuilder.append(story.getAsString());
            }
        }
        for (Story story : sortedBySize) {
            if (story.getSize().equals(Size.LARGE)) {
                stringBuilder.append(story.getAsString());
            }
        }
        if (sortedBySize.isEmpty()) {
            return "No story created yet.";
        }
        return stringBuilder.toString();
    }
}
