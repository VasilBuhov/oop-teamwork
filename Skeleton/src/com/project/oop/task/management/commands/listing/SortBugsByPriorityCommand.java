package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.enums.Priority;

import java.util.List;
import java.util.stream.Collectors;

public class SortBugsByPriorityCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;

    private final TaskManagementRepositoryImpl repository;

    public SortBugsByPriorityCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = new TaskManagementRepositoryImpl();
    }

    @Override
    public String execute(List<String> parameters) {
        String a = repository.getBugs().stream()
                .filter(bug -> bug.getPriority().equals(Priority.HIGH))
                .map(bug -> bug.getPriority() + " - " + bug.getTitle())
                .collect(Collectors.toList()).toString();
        String b = repository.getBugs().stream()
                .filter(bug -> bug.getPriority().equals(Priority.MEDIUM))
                .map(bug -> bug.getPriority() + " - " + bug.getTitle())
                .collect(Collectors.toList()).toString();
        String c = repository.getBugs().stream()
                .filter(bug -> bug.getPriority().equals(Priority.LOW))
                .map(bug -> bug.getPriority() + " - " + bug.getTitle())
                .collect(Collectors.toList()).toString();
        return "Bugs with high priority: " + a +
                "\n" + "Bugs with medium priority: " + b +
                "\n" + "Bugs with low priority: " + c;

    }
}
