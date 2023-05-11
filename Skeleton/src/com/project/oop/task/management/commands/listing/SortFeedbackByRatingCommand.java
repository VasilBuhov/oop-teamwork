package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Feedback;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortFeedbackByRatingCommand implements Command {
    private final TaskManagementRepositoryImpl repository;
    public SortFeedbackByRatingCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        List<Feedback> sortedByRating = repository.getFeedback().stream()
                .sorted(Comparator.comparing(Feedback::getRating)).collect(Collectors.toList());
        StringBuilder stringBuilder = new StringBuilder();
        for (Feedback feedback : sortedByRating) {
            stringBuilder.append(feedback.getAsString());
        }
        if (sortedByRating.isEmpty()) {
            return "No feedback created yet.";
        }
        return stringBuilder.toString();
    }
}
