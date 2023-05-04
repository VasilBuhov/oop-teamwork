package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewTeamCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.TeamImpl;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.utils.ValidationHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class CreateNewTeamCommandTests {

    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String ENTER_TEAM_NAME_MESSAGE =
            "Please enter the name of your new team or 'cancel' to exit:";
    public static final String TEAM_ALREADY_EXIST =
            "Team with this name already exist. Please enter a valid team name or 'cancel' if you want to exit:";
    public static final String TEAM_CREATED =
            "Team with name %s was created!";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";
    Command createNewTeamCommand;
    TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void beforeEach() {
        repository = new TaskManagementRepositoryImpl();
        createNewTeamCommand= new CreateNewTeamCommand(repository);
    }

    @Test
    public void should_ThrowException_When_ArgumentCountDifferentThanExpected() {
        String name = "valid";
        String teamIsValid = "false";
        List<String> parameters = new ArrayList<>();
        if (repository.getTeams().stream().noneMatch(team1 -> team1.getName().equals("valid"))) {
            repository.isItCancel(name, INVALID_INPUT);
            try {
                TeamImpl.validateName(name);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Try again or enter 'cancel' to exit:");
                name = "";
            }
            if (!name.equals("")) {
                teamIsValid = "true";
                parameters.add(name);
                ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
                Team team1 = repository.createNewTeam(name);
            }
        } else {
            System.out.println(TEAM_ALREADY_EXIST);
        }
        Assertions.assertEquals("true", teamIsValid);
    }

}
