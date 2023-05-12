package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.enums.FeedbackStatus;
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
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new ChangeFeedbackStatusCommand(repository);
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createFeedback("ValidTitle", "ValidDescription", 1);
    }

    @Test
    public void execute_Should_AdvanceTheStatus_When_AllParametersValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in);
        command.execute(params);

        String newStatus = FeedbackStatus.UNSCHEDULED.toString();

        //Act, Assert
        Assertions.assertEquals(newStatus, repository.getFeedback().get(0).getStatus());
    }

    @Test
    public void execute_Should_RevertTheStatus_When_AllParametersValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream(("1\nrevert\n").getBytes());
        System.setIn(in);
        command.execute(params);


        //Act, Assert
        Assertions.assertEquals(FeedbackStatus.NEW.toString(), repository.getFeedback().get(0).getStatus());
    }

    @Test
    public void execute_Should_ThrowException_When_CannotChangeTheStatus() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in4);
        command.execute(params);
        InputStream in5 = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in5);
        command.execute(params);
        InputStream in6 = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in6);
        command.execute(params);
        InputStream in7 = new ByteArrayInputStream(("1\nadvance\n").getBytes());
        System.setIn(in7);
        command.execute(params);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_IdDoNotExist() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream(("2\nadvance\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_DirectionEqualsCancel() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream(("1\nInvalid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

}
