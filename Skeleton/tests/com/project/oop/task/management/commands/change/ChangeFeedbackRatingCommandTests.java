package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewFeedbackCommand;
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

public class ChangeFeedbackRatingCommandTests {
    private Command command;
    private Command createTeam;
    private Command createBoard;
    private Command createFeedback;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new ChangeFeedbackRatingCommand(repository);
        this.createBoard = new CreateNewBoardCommand(repository);
        this.createTeam = new CreateNewTeamCommand(repository);
        this.createFeedback = new CreateNewFeedbackCommand(repository);
    }

    @Test
    public void execute_Should_ChangeTheStatus_When_AllParametersValid() {
        //Arrange
        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\n1\n").getBytes());
        System.setIn(in3);
        createFeedback.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("1\n2\n").getBytes());
        System.setIn(in4);
        command.execute(params4);

        //Act, Assert
        Assertions.assertEquals(2, repository.getFeedback().get(0).getRating());
    }

    @Test
    public void execute_Should_ThrowException_When_IdDoNotExist() {
        //Arrange
        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\n1\n").getBytes());
        System.setIn(in3);
        createFeedback.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("2\n2\n").getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params4));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidNewRating() {
        //Arrange
        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\n1\n").getBytes());
        System.setIn(in3);
        createFeedback.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("1\nInvalid\n").getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params4));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidID() {
        //Arrange
        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nValidTitle\nValidDescription\n1\n").getBytes());
        System.setIn(in3);
        createFeedback.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("Invalid\n2\n").getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params4));
    }
}
