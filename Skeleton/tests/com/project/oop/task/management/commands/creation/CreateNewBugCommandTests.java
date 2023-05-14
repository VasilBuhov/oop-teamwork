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

public class CreateNewBugCommandTests {
    private Command command;
    private TaskManagementRepositoryImpl repository;
    private List<String> params;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new CreateNewBugCommand(repository);
        repository.createNewTeam("Team1");
        repository.findTeamByName("Team1").addBoard(repository.createBoard("Board1"));
        repository.createNewPerson("Valid");
        repository.addNewPersonToTeam("Valid", "Team1");
        params = new ArrayList<>();
    }

    @Test
    public void execute_Should_ThrowException_When_TitleLengthNotValid() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\nShort\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_CreateNewBug_When_AllParametersValid() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in);
        command.execute(params);

        //Act, Assert
        Assertions.assertEquals(1, repository.getBugs().size());
    }

    @Test
    public void execute_Should_ThrowException_When_AssigneeIsNotValid() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\nHigh\nMinor\nNikol\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_PriorityNotValid() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\nInvalid\nMinor\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_SeverityNotValid() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\nHigh\nInvalid\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredTeamNameNotExit() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team2\nBoard1\nValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredBoardNameNotExit() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard2\nValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_BoardEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\ncancel\nValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("cancel\nBoard1\nValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_TitleEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\ncancel\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_DescriptionEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\ncancel\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_AssigneeEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\nHigh\nMinor\ncancel\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_PriorityEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\ncancel\nMinor\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_SeverityEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\nHigh\ncancel\nValid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }
}
