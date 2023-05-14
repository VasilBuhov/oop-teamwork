package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Team;

import java.util.List;

public class ShowAllTeamsCommand implements Command {


    private final TaskManagementRepositoryImpl repository;
    public ShowAllTeamsCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        StringBuilder stringBuilder = new StringBuilder();
        if (repository.getTeams().size() == 0){
            throw new IllegalArgumentException("There are no teams to display.");
        }else {
            stringBuilder.append(String.format("======================%n"));
            for (Team team : repository.getTeams()) {
                stringBuilder.append(team.getAsString()).append('\n');
            }
        }


        return stringBuilder.toString().trim();
    }
}
