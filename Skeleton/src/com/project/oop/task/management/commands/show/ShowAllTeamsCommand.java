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
        }
        int index = 1;
        stringBuilder.append(String.format("======================%n"));
        for (Team team : repository.getTeams()) {
            stringBuilder.append(String.format("%d. Team name: %s%n", index, team.getName()));
            stringBuilder.append(String.format("Team members: %n"));
            if (team.getMembers().size() == 0){
                stringBuilder.append(String.format("No members added to this team%n"));
            }else {
                for (Member member : team.getMembers()) {
                    stringBuilder.append(String.format("%s%n", member.getName()));
                }
            }
            stringBuilder.append(String.format("Team boards: %n"));
            if (team.getBoards().size() == 0){
                stringBuilder.append(String.format("No boards created for this team%n"));
            }else {
                for (Board board : team.getBoards()) {
                    stringBuilder.append(String.format("%s%n", board.getName()));
                }
            }
            stringBuilder.append(String.format("======================%n"));
            index ++;
        }

        return stringBuilder.toString().trim();
    }
}
