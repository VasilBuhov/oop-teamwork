package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
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

public class FilterStoriesByAssigneeCommandTests {
    private Command command1;
    private Command command2;
    private Command command3;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new FilterStoriesByAssigneeCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        this.command3 = new CreateNewStoryCommand(repository);

        List<String> params = new ArrayList<>();
        String teamName = "Team1";
        String boardName = "Board1";
        String personName1 = "Margarita";
        String personName2 = "Ivaylo";
        repository.createNewTeam(teamName);
        repository.createBoard(boardName);
        repository.createNewPerson(personName1);
        repository.createNewPerson(personName2);
        repository.addNewPersonToTeam(personName1, teamName);
        repository.addNewPersonToTeam(personName2, teamName);

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
    public void execute_Should_ThrowException_When_InputIsEqualToCancel() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "cancel";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredAssigneeNameNotFound() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "Tatyana";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_DisplayFilteredStories_When_ExistingAssigneeNameEntered() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "Margarita";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act
        String filteredStories = command1.execute(params).trim();

        String result = String.format("*********************%n" +
                "Story:%n" +
                "Title: StoryTitle1%n" +
                "Description: StoryDescription1%n" +
                "Comments: %n" +
                "Status: NotDone%n" +
                "Priority: Low%n" +
                "Size: Small%n" +
                "Assignee: Margarita%n" +
                "*********************");

        //Assert
        Assertions.assertEquals(result, filteredStories);
    }

    @Test
    public void execute_Should_DisplayNoStory_When_AssigneeHasNoStories() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "Ivaylo";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act
        String filteredStories = command1.execute(params).trim();

        String result = String.format("No stories assigned to this person.");

        //Assert
        Assertions.assertEquals(result, filteredStories);
    }
}
