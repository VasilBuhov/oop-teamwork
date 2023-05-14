package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewFeedbackCommand;
import com.project.oop.task.management.commands.creation.CreateNewStoryCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ShowBoardActivityCommandTests {
    private TaskManagementRepositoryImpl repository;
    private Command command1;
    private Command command2;
    private Command command3;

    private Command command4;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new ShowBoardActivityCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        this.command3 = new CreateNewFeedbackCommand(repository);
        this.command4 = new CreateNewStoryCommand(repository);

        List<String> params = new ArrayList<>();
        String teamName = "Team1";
        String boardName = "Board1";
        String personName = "Margarita";
        repository.createNewTeam(teamName);
        repository.createBoard(boardName);
        repository.createNewPerson(personName);
        repository.addNewPersonToTeam(personName, teamName);

        String inputDataBoard = "Team1\nBoard1\n";
        InputStream in1 = new ByteArrayInputStream((inputDataBoard).getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.remove(0);

        String inputDataFeedback = "Team1\nBoard1\nFeedbackTitle\nFeedbackDescription\n1\n";
        InputStream in2 = new ByteArrayInputStream((inputDataFeedback).getBytes());
        System.setIn(in2);
        command3.execute(params);
        params.clear();

        String inputDataStory= "Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n";
        InputStream in3 = new ByteArrayInputStream((inputDataStory).getBytes());
        System.setIn(in3);
        command4.execute(params);
    }


    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfTeamName() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "cancel\nBoard1\n";
        InputStream in4 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }


    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfBoardName() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "Team1\ncancel\n";
        InputStream in4 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredTeamNotExist() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "Team2\nBoard1\n";
        InputStream in4 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredBoardNotExist() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "Team1\nBoard2\n";
        InputStream in4 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }

    @Test
    public void should_DisplayBoardActivity() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "Team1\nBoard1\n";
        InputStream in4 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in4);
        //Act
        String actualResult = command1.execute(params);

        LocalDateTime currentLocalDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss");
        String formattedDateTime = currentLocalDateTime.format(dateTimeFormatter);
        String expectedResult = String.format("Board: Board1%n" +
                "Activity:%n" +
                "[%s] Board with name Board1 was created!%n" +
                "[%s] Task: FeedbackTitle is added to board: Board1!%n" +
                "[%s] Task: StoryTitle1 is added to board: Board1!%n" +
                "======================%n", formattedDateTime, formattedDateTime, formattedDateTime);


        //Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
