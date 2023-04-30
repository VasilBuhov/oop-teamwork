package com.project.oop.task.management.core.contracts;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;

public interface CommandFactory {
    Command createCommandFromCommandName(String commandTypeAsString, TaskManagementRepositoryImpl repository);
}
