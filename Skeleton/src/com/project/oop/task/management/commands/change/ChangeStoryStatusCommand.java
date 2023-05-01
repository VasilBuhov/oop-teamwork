package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;

import java.util.List;

public class ChangeStoryStatusCommand implements Command{
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;

    private final TaskManagementRepositoryImpl repository;
    public ChangeStoryStatusCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = new TaskManagementRepositoryImpl();
    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
