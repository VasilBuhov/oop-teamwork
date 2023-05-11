package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewFeedbackCommand;
import com.project.oop.task.management.commands.creation.CreateNewTeamCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.MemberImpl;
import com.project.oop.task.management.models.enums.FeedbackStatus;
import com.project.oop.task.management.utils.MessageHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ChangeFeedbackStatusCommandTests {
    private Command command;
    private Command createTeam;
    private Command createBoard;
    private Command createFeedback;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new ChangeFeedbackStatusCommand(repository);
        this.createBoard = new CreateNewBoardCommand(repository);
        this.createTeam = new CreateNewTeamCommand(repository);
        this.createFeedback = new CreateNewFeedbackCommand(repository);
    }

    @Test
    public void execute_Should_AdvanceTheStatus_When_AllParametersValid(){
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
        InputStream in4 = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in4);
        command.execute(params4);

        String newStatus = FeedbackStatus.UNSCHEDULED.toString();

        //Act, Assert
        Assertions.assertEquals(newStatus, repository.getFeedback().get(0).getStatus());
    }

    @Test
    public void execute_Should_RevertTheStatus_When_AllParametersValid(){
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
        InputStream in4 = new ByteArrayInputStream(("1\nrevert\n").getBytes());
        System.setIn(in4);
        command.execute(params4);


        //Act, Assert
        Assertions.assertEquals(FeedbackStatus.NEW.toString(), repository.getFeedback().get(0).getStatus());
    }

    @Test
    public void execute_Should_ThrowException_When_CannotChangeTheStatus(){
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
        InputStream in4 = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in4);
        command.execute(params4);
        InputStream in5 = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in5);
        command.execute(params4);
        InputStream in6 = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in6);
        command.execute(params4);
        InputStream in7 = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in7);
        command.execute(params4);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params4));
    }

    @Test
    public void execute_Should_ThrowException_When_IdDoNotExist(){
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
        InputStream in4 = new ByteArrayInputStream(("2\nadvance\n").getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params4));
    }

    @Test
    public void execute_Should_ThrowException_When_DirectionEqualsCancel() {
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

}
