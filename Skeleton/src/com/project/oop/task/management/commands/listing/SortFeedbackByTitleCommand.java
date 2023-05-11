package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Feedback;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortFeedbackByTitleCommand implements Command {
    private final TaskManagementRepositoryImpl repository;
    public SortFeedbackByTitleCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Feedback> sortedByTitle = repository.getFeedback().stream()
                .sorted(Comparator.comparing(Feedback::getTitle)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (Feedback feedback : sortedByTitle) {
            stringBuilder.append(feedback.getAsString());
        }
        if (sortedByTitle.isEmpty()) {
            return "No feedback created yet.";
        }
        return stringBuilder.toString();
    }
}
