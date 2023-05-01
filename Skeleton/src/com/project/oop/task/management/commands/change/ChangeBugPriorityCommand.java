package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementEngineImpl;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;

import java.util.List;

public class ChangeBugPriorityCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS;

    private final TaskManagementRepositoryImpl repository;

    public ChangeBugPriorityCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = new TaskManagementRepositoryImpl();
    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
