package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.models.enums.Priority;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortStoriesByPriorityCommand implements Command {

    private final TaskManagementRepositoryImpl repository;
    public SortStoriesByPriorityCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Story> sortedByPriority = repository.getStories().stream()
                .sorted(Comparator.comparing(Story::getPriority)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (Story story : sortedByPriority) {
            if (story.getPriority().equals(Priority.HIGH))
            stringBuilder.append(story.getAsString());
        }
        for (Story story : sortedByPriority) {
            if (story.getPriority().equals(Priority.MEDIUM))
                stringBuilder.append(story.getAsString());
        }
        for (Story story : sortedByPriority) {
            if (story.getPriority().equals(Priority.LOW))
                stringBuilder.append(story.getAsString());
        }
        if (sortedByPriority.isEmpty()) {
            return "No story created yet.";
        }
        return stringBuilder.toString();
    }
}
