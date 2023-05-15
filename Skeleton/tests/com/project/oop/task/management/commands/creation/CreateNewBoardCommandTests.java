package com.project.oop.task.management.commands.creation;

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

public class CreateNewBoardCommandTests {
    private Command command;
    private TaskManagementRepositoryImpl repository;
    private List<String> params;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new CreateNewBoardCommand(repository);
        repository.createNewTeam("Team1");
        params = new ArrayList<>();
    }

    @Test
    public void execute_Should_ThrowException_When_NameLengthNotValid() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nB\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_NameEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\ncancel\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("cancel\nBoard1\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamNotExist() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team2\nBoard1\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_CreateNewBug_When_AllParametersValid() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in);
        command.execute(params);

        //Act, Assert
        Assertions.assertEquals(1, repository.findTeamByName("Team1").getBoards().size());
    }
}
