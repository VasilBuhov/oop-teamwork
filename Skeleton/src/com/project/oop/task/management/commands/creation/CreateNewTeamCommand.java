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
    public static final String TEAM_ALREADY_EXIST =
            "Team with this name already exist. Please enter a valid team name:";
    public static final String INVALID_INPUT = "Invalid input! Enter a new command, please:";
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final TaskManagementRepositoryImpl repository;
    private String name;

    public CreateNewTeamCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }
    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the name of yor new team or 'cancel' if you want to exit: ");
        boolean teamIsValid = false;
        while (!teamIsValid) {
            name = scanner.nextLine();
            if (repository.getTeams().stream().noneMatch(team1 -> team1.getName().equals(name))) {
                if (name.equals("cancel")) {
                    throw new IllegalArgumentException(INVALID_INPUT);
                }
                teamIsValid = true;
                parameters.add(name);
            } else {
                System.out.println(TEAM_ALREADY_EXIST);
            }
        }

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        Team team1 = repository.createNewTeam(name);
        return String.format(TEAM_CREATED, name);
    }
}
