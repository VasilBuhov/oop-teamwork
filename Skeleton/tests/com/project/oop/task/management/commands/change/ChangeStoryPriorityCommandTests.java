package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewStoryCommand;
import com.project.oop.task.management.commands.listing.FilterStoriesByStatusCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ChangeStoryPriorityCommandTests {
    private Command command1;
    private Command command2;
    private Command command3;
    private Command command4;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new ChangeStoryPriorityCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        this.command3 = new CreateNewStoryCommand(repository);
        this.command4 = new FilterStoriesByStatusCommand(repository);

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

        String inputDataStory = "Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n";
        InputStream in2 = new ByteArrayInputStream((inputDataStory).getBytes());
        System.setIn(in2);
        command3.execute(params);
    }

    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfId(){
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "cancel\nhigh\n";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfPriority(){
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "1\ncancel\n";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredIdNotExist(){
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "2\nhigh\n";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }


    @Test
    public void execute_Should_ThrowException_When_EnteredNewPriorityNotExist(){
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "1\nlarge\n";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ChangePriority_When_CorrectIdAndPriorityEntered(){
        //Arrange
        List<String> params = new ArrayList<>();


        String commandInput = "1\nhigh\n";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);
        command1.execute(params);


        String inputForFilterCommand = "notDone";
        InputStream in4 = new ByteArrayInputStream((inputForFilterCommand).getBytes());
        System.setIn(in4);
        String storyDetails = command4.execute(params);
        String expectedResult = String.format("*********************%n" +
                "Story:%n" +
                "Title: StoryTitle1%n" +
                "Description: StoryDescription1%n" +
                "Comments: %n" +
                "Status: NotDone%n" +
                "Priority: High%n" +
                "Size: Small%n" +
                "Assignee: Margarita%n" +
                "*********************");

        //Act, Assert
        Assertions.assertEquals(expectedResult, storyDetails);
    }
}
