package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.TeamImpl;
import com.project.oop.task.management.models.contracts.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class CreateNewTeamCommandTests {
    private Command command;
    private TaskManagementRepositoryImpl repository;
    private List<String> params;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new CreateNewTeamCommand(repository);
        params = new ArrayList<>();
    }

    @Test
    public void execute_Should_ThrowException_When_NameLengthNotValid() {
        String name = "Test";
        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_InputIsEqualToCancel() {
        String name = "cancel";
        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_NameAlreadyExists() {
        Team team = new TeamImpl("IntelliNinjas");
        repository.createNewTeam(team.getName());
        params.add(team.getName());

        String name = "IntelliNinjas";
        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_CreatePerson_When_NameIsValid() {
        String teamName = "IntelliNinjas";
        InputStream in = new ByteArrayInputStream(teamName.getBytes());
        System.setIn(in);
        command.execute(params);

        Assertions.assertEquals(1, repository.getTeams().size());
    }

}
