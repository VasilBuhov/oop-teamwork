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


public class CreateNewStoryCommandTests {
    private Command command;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new CreateNewStoryCommand(repository);
        repository.createNewTeam("Team1");
        repository.findTeamByName("Team1").addBoard(repository.createBoard("Board1"));
        repository.createNewPerson("Valid");
        repository.addNewPersonToTeam("Valid", "Team1");
    }

    @Test
    public void execute_Should_ThrowException_When_TitleLengthNotValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nShort\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_CreateNewStory_When_AllParametersValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);
        command.execute(params);

        //Act, Assert
        Assertions.assertEquals(1, repository.getStories().size());
    }

    @Test
    public void execute_Should_ThrowException_When_AssigneeIsNotValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nNikol\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_PriorityNotValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nInvalid\nLarge\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_SizeNotValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nInvalid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredTeamNameNotExit() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team2\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredBoardNameNotExit() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard2\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }
}
