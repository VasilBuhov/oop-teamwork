package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Member;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ShowAllTeamMembersCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;
    private String teamName;
    public static final String ENTER_TEAM_NAME_MESSAGE =
            "Please enter your team name or 'cancel' if you want to exit:";
    public static final String TEAM_IS_NOT_FOUNDED =
            "There is no team with this name. " +
                    "Please enter a valid team name or 'cancel' if you want to exit:";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";
    private final TaskManagementRepositoryImpl repository;
    public ShowAllTeamMembersCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ENTER_TEAM_NAME_MESSAGE);
        boolean teamIsValid = false;
        while (!teamIsValid) {
            teamName = scanner.nextLine();
            if (repository.getTeams().stream().anyMatch(team1 -> team1.getName().equals(teamName))) {
                teamIsValid = true;
                parameters.add(teamName);
            } else {
                repository.isItCancel(teamName, INVALID_INPUT);
                System.out.println(TEAM_IS_NOT_FOUNDED);
            }
        }
        return repository.
                findTeamByName(teamName).
                getMembers().stream().map(name->name.getName()).collect(Collectors.toList()).toString();
    }
}
