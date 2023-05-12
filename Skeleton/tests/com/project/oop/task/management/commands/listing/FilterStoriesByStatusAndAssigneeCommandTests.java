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

public class FilterStoriesByStatusAndAssigneeCommandTests {
    private Command command1;
    private Command command2;
    private Command command3;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new FilterStoriesByStatusAndAssigneeCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        this.command3 = new CreateNewStoryCommand(repository);

    }

    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfStatus() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.remove(0);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in2);
        command3.execute(params);

        InputStream in3 = new ByteArrayInputStream(("cancel\nMargarita\n").getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }


    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfAssignee() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.remove(0);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in2);
        command3.execute(params);

        InputStream in3 = new ByteArrayInputStream(("NotDone\ncancel\n").getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredStatusNotValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.remove(0);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in2);

        command3.execute(params);

        InputStream in3 = new ByteArrayInputStream(("New\nMargarita").getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }


    @Test
    public void execute_Should_ThrowException_When_EnteredAssigneeNotExist() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.remove(0);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in2);

        command3.execute(params);

        InputStream in3 = new ByteArrayInputStream(("NotDone\nTatyana").getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_DisplayFilteredStories_When_ValidStatusAndAssigneeEntered() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.remove(0);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in2);
        command3.execute(params);

        InputStream in3 = new ByteArrayInputStream(("notDone\nMargarita\n").getBytes());
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
    public void execute_Should_DisplayNoStories_When_NoStoriesWithEnteredStatusExist() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.remove(0);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in2);
        command3.execute(params);


        InputStream in3 = new ByteArrayInputStream(("InProgress\nMargarita").getBytes());
        System.setIn(in3);

        //Act
        String filteredStories = command1.execute(params).trim();

        String result = String.format("No stories with this status");

        //Assert
        Assertions.assertEquals(result, filteredStories);
    }

    @Test
    public void execute_Should_DisplayNoStories_When_NoStoriesWithEnteredAssigneeExist() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        repository.createNewPerson("Tatyana");
        repository.addNewPersonToTeam("Tatyana", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.remove(0);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in2);
        command3.execute(params);


        InputStream in3 = new ByteArrayInputStream(("NotDone\nTatyana").getBytes());
        System.setIn(in3);

        //Act
        String filteredStories = command1.execute(params).trim();

        String result = String.format("No stories assigned to this person.");

        //Assert
        Assertions.assertEquals(result, filteredStories);
    }
}
