package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
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
    private TaskManagementRepositoryImpl repository;
    private List<String> params;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new ShowTeamActivityCommand(repository);
        repository.createNewTeam("Team1");
        params = new ArrayList<>();
    }

    @Test
    public void execute_Should_ShowTeamActivity_When_ArgumentsAreValid() {
        //Arrange
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
        Assertions.assertEquals(sb, command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamEqualsCancel() {
        //Arrange
        InputStream in2 = new ByteArrayInputStream(("cancel\n").getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamNotFound() {
        //Arrange
        InputStream in2 = new ByteArrayInputStream(("Invalid\n").getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }
}
