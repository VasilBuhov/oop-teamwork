package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ListTasksCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;

    private final TaskManagementRepositoryImpl repository;
    public ListTasksCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        return repository.getTasks().stream()
                .map(bug -> bug.getId() + " - " + bug.getTitle())
                .collect(Collectors.toList())
                .toString();
    }

}
