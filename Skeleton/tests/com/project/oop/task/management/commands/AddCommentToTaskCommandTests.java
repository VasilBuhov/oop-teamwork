package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewStoryCommand;
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
    private Command createTask;
    private TaskManagementRepositoryImpl repository;
    private List<String> params;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new AddCommentToTaskCommand(repository);
        repository.createNewTeam("Team1");
        repository.findTeamByName("Team1").addBoard(repository.createBoard("Board1"));
        repository.createNewPerson("Valid");
        repository.addNewPersonToTeam("Valid", "Team1");
        this.createTask = new CreateNewStoryCommand(repository);
        params = new ArrayList<>();
    }

    @Test
    public void execute_Should_AddComment_When_AllParametersValid() {
        //Arrange
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);
        createTask.execute(params);

        InputStream in1 = new ByteArrayInputStream(("1\nValid\nTest\n").getBytes());
        System.setIn(in1);
        command.execute(params);

        //Act, Assert
        Assertions.assertEquals(1, repository.getTasks().get(0).getComments().size());
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredIDNotExit() {
        //Arrange
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);
        createTask.execute(params);

        InputStream in1 = new ByteArrayInputStream(("2\nValid\nTest\n").getBytes());
        System.setIn(in1);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_IDEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);
        createTask.execute(params);

        InputStream in1 = new ByteArrayInputStream(("cancel\nValid\nTest\n").getBytes());
        System.setIn(in1);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_AuthorNotExist() {
        //Arrange
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);
        createTask.execute(params);

        InputStream in1 = new ByteArrayInputStream(("1\nNotValid\nTest\n").getBytes());
        System.setIn(in1);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_CommentEqualsCancel() {
        //Arrange
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in);
        createTask.execute(params);

        InputStream in1 = new ByteArrayInputStream(("1\nValid\ncancel\n").getBytes());
        System.setIn(in1);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }


}
