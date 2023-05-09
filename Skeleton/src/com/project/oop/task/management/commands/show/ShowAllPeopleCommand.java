package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ShowAllPeopleCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;

    private final TaskManagementRepositoryImpl repository;
    public ShowAllPeopleCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters){
        return  repository.getPeople().
                stream().
                map(name->name.getName().
                        toString()).
                collect(Collectors.toList()).
                toString();

    }
}
