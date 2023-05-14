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

public class CreateNewFeedbackCommandTests {
    private Command command;
    private Command command2;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new CreateNewFeedbackCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        List<String> params = new ArrayList<>();
        String teamName = "Team1";
        String boardName = "Board1";
        repository.createNewTeam(teamName);
        repository.createBoard(boardName);
        String inputDataBoard = "Team1\nBoard1\n";
        InputStream in1 = new ByteArrayInputStream((inputDataBoard).getBytes());
        System.setIn(in1);
        command2.execute(params);
    }

    @Test
    public void execute_Should_ThrowException_When_TitleLengthNotValid() {
        //Arrange
        List<String> params = new ArrayList<>();

        String inputDataFeedback = "Team1\nBoard1\nShortTit\nLongFeedbackDescription1\n1\n";
        InputStream in2 = new ByteArrayInputStream((inputDataFeedback).getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_DescriptionLengthNotValid() {
        //Arrange
        List<String> params = new ArrayList<>();

        String inputDataFeedback = "Team1\nBoard1\nLongFeedbackTitle\nShortDesc\n1\n";
        InputStream in2 = new ByteArrayInputStream((inputDataFeedback).getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_RatingNotNumber() {
        //Arrange
        List<String> params = new ArrayList<>();

        String inputDataFeedback = "Team1\nBoard1\nLongFeedbackTitle\nLongFeedbackDescription\nabc\n";
        InputStream in2 = new ByteArrayInputStream((inputDataFeedback).getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_CreateNewFeedback_When_AllParametersValid() {
        //Arrange
        List<String> params = new ArrayList<>();

        String inputDataFeedback = "Team1\nBoard1\nLongFeedbackTitle1\nLongFeedbackDescription1\n1\n";
        InputStream in2 = new ByteArrayInputStream((inputDataFeedback).getBytes());
        System.setIn(in2);
        command.execute(params);

        //Act, Assert
        Assertions.assertEquals(1, repository.getFeedback().size());
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredTeamNameNotExit() {
        //Arrange
        List<String> params = new ArrayList<>();

        String inputDataFeedback = "Team2\nBoard1\nLongFeedbackTitle1\nLongFeedbackDescription1\n1\n";
        InputStream in2 = new ByteArrayInputStream((inputDataFeedback).getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredBoardNameNotExit() {
        //Arrange
        List<String> params = new ArrayList<>();

        String inputDataFeedback = "Team1\nBoard2\nLongFeedbackTitle1\nLongFeedbackDescription1\n1\n";
        InputStream in2 = new ByteArrayInputStream((inputDataFeedback).getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }
}
