package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewTeamCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ShowTeamActivityCommandTests {
    private Command command;
    private Command createTeam;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new ShowTeamActivityCommand(repository);
        this.createTeam = new CreateNewTeamCommand(repository);
    }

    @Test
    public void execute_Should_ShowTeamActivity_When_ArgumentsAreValid() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in);
        createTeam.execute(params);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in2);

        String sb = String.format("---------------------%n") +
                String.format("Team: %s%n", "Team1") +
                String.format("---------------------%n") + System.lineSeparator() +
                String.format("*********************%n") +
                String.format("Members activity:%n") +
                String.format("*********************%n") + System.lineSeparator() +
                String.format("*********************%n") +
                String.format("Boards activity:%n") +
                String.format("*********************%n") + System.lineSeparator() +
                String.format("*********************%n") +
                String.format("Tasks activity:%n") +
                String.format("*********************%n");

        //Act, Assert
        Assertions.assertEquals(sb, command.execute(params2));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamEqualsCancel() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in);
        createTeam.execute(params);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("cancel\n").getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params2));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamNotFound() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in);
        createTeam.execute(params);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Invalid\n").getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params2));
    }
}
