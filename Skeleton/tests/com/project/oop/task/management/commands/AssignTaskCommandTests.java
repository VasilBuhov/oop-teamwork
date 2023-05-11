package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.*;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AssignTaskCommandTests {
    private Command command;
    private Command createFeedback;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new AssignTaskCommand(repository);
        this.createFeedback = new CreateNewFeedbackCommand(repository);
        repository.createNewTeam("Team1");
        repository.findTeamByName("Team1").addBoard(repository.createBoard("Board1"));
        repository.createNewPerson("Valid");
        repository.addNewPersonToTeam("Valid", "Team1");
    }

    @Test
    public void execute_Should_AssignTask_When_AllParametersValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nA.ValidTitle\nValidDescription\n1\n").getBytes());
        System.setIn(in);
        createFeedback.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("1\nValid\n").getBytes());
        System.setIn(in1);
        command.execute(params1);

        //Act, Assert
        Assertions.assertEquals(1, repository.getAssignedTasks().size());
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidID() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nA.ValidTitle\nValidDescription\n1\n").getBytes());
        System.setIn(in);
        createFeedback.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("2\nValid\n").getBytes());
        System.setIn(in1);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params1));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidAssignee() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream
                (("Team1\nBoard1\nA.ValidTitle\nValidDescription\n1\n").getBytes());
        System.setIn(in);
        createFeedback.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("1\nInvalid\n").getBytes());
        System.setIn(in1);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params1));
    }
}
