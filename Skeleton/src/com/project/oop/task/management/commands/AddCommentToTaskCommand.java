package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Comment;
import com.project.oop.task.management.models.contracts.Task;

import java.util.List;

public class AddCommentToTaskCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private Task task;
    private Comment comment;

    private final TaskManagementRepositoryImpl repository;
    public AddCommentToTaskCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        return null;
    }
}
