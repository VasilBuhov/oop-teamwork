package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewStoryCommand;
import com.project.oop.task.management.commands.listing.FilterStoriesByStatusCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ChangeStoryStatusCommandTests {
    private Command command1;
    private Command command2;
    private Command command3;
    private Command command4;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new ChangeStoryStatusCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        this.command3 = new CreateNewStoryCommand(repository);
        this.command4 = new FilterStoriesByStatusCommand(repository);

    }

    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfId(){
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

        InputStream in3 = new ByteArrayInputStream(("cancel\nDone\n").getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfStatus(){
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

        InputStream in3 = new ByteArrayInputStream(("1\ncancel\n").getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredIdNotExist(){
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

        InputStream in3 = new ByteArrayInputStream(("2\nDone\n").getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }


    @Test
    public void execute_Should_ThrowException_When_EnteredNewStatusNotExist(){
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

        InputStream in3 = new ByteArrayInputStream(("1\nactive\n").getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ChangeStatus_When_CorrectIdAndStatusEntered(){
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

        InputStream in3 = new ByteArrayInputStream(("1\nDone\n").getBytes());
        System.setIn(in3);
        command1.execute(params);


        InputStream in4 = new ByteArrayInputStream(("Done").getBytes());
        System.setIn(in4);
        String storyDetails = command4.execute(params);
        String expectedResult = String.format("*********************%n" +
                "Story:%n" +
                "Title: StoryTitle1%n" +
                "Description: StoryDescription1%n" +
                "Comments: %n" +
                "Status: Done%n" +
                "Priority: Low%n" +
                "Size: Small%n" +
                "Assignee: Margarita%n" +
                "*********************");

        //Act, Assert
        Assertions.assertEquals(expectedResult, storyDetails);
    }
}
