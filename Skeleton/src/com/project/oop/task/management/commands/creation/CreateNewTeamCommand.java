package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.TeamImpl;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class CreateNewTeamCommand implements Command {
    public static final String TEAM_CREATED = "Team with name %s was created!";
    public static final String NAME_ALREADY_EXIST = "Team with name %s already exist!";
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final TaskManagementRepositoryImpl repository;
    private String name;

    public CreateNewTeamCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }
    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a valid name to your new team: ");
        name = scanner.nextLine();
        parameters.add(name);

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        if (repository.getTeams().stream().anyMatch(team -> team.getName().equals(name))) {
            throw new IllegalArgumentException(String.format(NAME_ALREADY_EXIST, name));
        }

        Team team1 = repository.createNewTeam(name);
        return String.format(TEAM_CREATED, name);
    }
}
