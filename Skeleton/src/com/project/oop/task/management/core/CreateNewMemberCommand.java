package com.project.oop.task.management.core;

import com.project.oop.task.management.commands.contracts.Command;

import java.util.List;

public class CreateNewMemberCommand implements Command {
    public CreateNewMemberCommand(TaskManagementRepositoryImpl taskManagementRepository) {
    }

    @Override
    public String execute(List<String> parameters) {
        return null;
    }
}
