package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewPersonCommand;
import com.project.oop.task.management.commands.creation.CreateNewStoryCommand;
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

public class AddCommentToTaskCommandTests {
    private Command command;
    private Command createTeam;
    private Command createTask;
    private Command createBoard;
    private Command createPerson;
    private Command addPersonToTeam;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.createTeam = new CreateNewTeamCommand(repository);
        this.command = new AddCommentToTaskCommand(repository);
        this.createBoard = new CreateNewBoardCommand(repository);
        this.createPerson = new CreateNewPersonCommand(repository);
        this.addPersonToTeam = new AddPersonToTeamCommand(repository);
        this.createTask = new CreateNewStoryCommand(repository);
    }

    @Test
    public void execute_Should_AddComment_When_AllParametersValid(){
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Valid\nTeam1\n").getBytes());
        System.setIn(in3);
        addPersonToTeam.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in4);
        createTask.execute(params4);

        List<String> params5 = new ArrayList<>();
        InputStream in5 = new ByteArrayInputStream(("1\nValid\nTest\n").getBytes());
        System.setIn(in5);
        command.execute(params5);

        //Act, Assert
        Assertions.assertEquals(1, repository.getTasks().get(0).getComments().size());
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredIDNotExit(){
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Valid\nTeam1\n").getBytes());
        System.setIn(in3);
        addPersonToTeam.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in4);
        createTask.execute(params4);

        List<String> params5 = new ArrayList<>();
        InputStream in5 = new ByteArrayInputStream(("2\nValid\nTest\n").getBytes());
        System.setIn(in5);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params5));
    }

    @Test
    public void execute_Should_ThrowException_When_IDEqualsCancel() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Valid\nTeam1\n").getBytes());
        System.setIn(in3);
        addPersonToTeam.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in4);
        createTask.execute(params4);

        List<String> params5 = new ArrayList<>();
        InputStream in5 = new ByteArrayInputStream(("cancel\nValid\nTest\n").getBytes());
        System.setIn(in5);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params5));
    }

    @Test
    public void execute_Should_ThrowException_When_AuthorNotExist() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Valid\nTeam1\n").getBytes());
        System.setIn(in3);
        addPersonToTeam.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in4);
        createTask.execute(params4);

        List<String> params5 = new ArrayList<>();
        InputStream in5 = new ByteArrayInputStream(("1\nNotValid\nTest\n").getBytes());
        System.setIn(in5);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params5));
    }

    @Test
    public void execute_Should_ThrowException_When_CommentEqualsCancel() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Valid\nTeam1\n").getBytes());
        System.setIn(in3);
        addPersonToTeam.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in4);
        createTask.execute(params4);

        List<String> params5 = new ArrayList<>();
        InputStream in5 = new ByteArrayInputStream(("1\nValid\ncancel\n").getBytes());
        System.setIn(in5);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params5));
    }


}
