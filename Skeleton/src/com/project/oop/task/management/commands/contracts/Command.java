package com.project.oop.task.management.commands.contracts;

import java.util.List;

public interface Command {
    String execute(List<String> parameters);
}
