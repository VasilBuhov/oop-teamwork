package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.TaskImpl;
import com.project.oop.task.management.models.TeamImpl;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class CreateNewTeamCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private final TaskManagementRepositoryImpl repository;
    private String name;

    public CreateNewTeamCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(MessageHelper.ENTER_TEAM_NAME_MESSAGE);
        boolean teamIsValid = false;
        while (!teamIsValid) {
            name = scanner.nextLine();
            teamIsValid = isTeamValid(parameters, teamIsValid);
        }


        return String.format(MessageHelper.TEAM_CREATED, name);
    }

    private boolean isTeamValid(List<String> parameters, boolean teamIsValid) {
        if (repository.getTeams().stream().noneMatch(team1 -> team1.getName().equals(name))) {
            repository.isItCancel(name, MessageHelper.INVALID_INPUT);
            try {
                TeamImpl.validateName(name);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Try again or enter 'cancel' to exit:");
                name = "";
            }
            if (!name.equals("")) {
                teamIsValid = true;
                parameters.add(name);
                ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
                Team team1 = repository.createNewTeam(name);
            }
        } else {
            System.out.println(MessageHelper.TEAM_ALREADY_EXIST);
        }
        return teamIsValid;
    }
}
