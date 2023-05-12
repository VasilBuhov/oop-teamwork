package com.project.oop.task.management.commands.change;

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

public class ChangeFeedbackRatingCommandTests {
    private Command command;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new ChangeFeedbackRatingCommand(repository);
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createFeedback("ValidTitle", "ValidDescription", 1);
    }

    @Test
    public void execute_Should_ChangeTheStatus_When_AllParametersValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream(("1\n2\n").getBytes());
        System.setIn(in);
        command.execute(params);

        //Act, Assert
        Assertions.assertEquals(2, repository.getFeedback().get(0).getRating());
    }

    @Test
    public void execute_Should_ThrowException_When_IdDoNotExist() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream(("2\n2\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidNewRating() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream(("1\nInvalid\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_InvalidID() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in = new ByteArrayInputStream(("Invalid\n2\n").getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }
}
