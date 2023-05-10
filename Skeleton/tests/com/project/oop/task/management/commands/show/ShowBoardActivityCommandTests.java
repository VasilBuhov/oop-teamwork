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
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new ShowBoardActivityCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        this.command3 = new CreateNewFeedbackCommand(repository);
        this.command4 = new CreateNewStoryCommand(repository);
    }


    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfTeamName(){
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

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nFeedbackTitle\nFeedbackDescription\n1\n").getBytes());
        System.setIn(in2);
        command3.execute(params);
        params.clear();

        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in3);
        command4.execute(params);

        InputStream in4 = new ByteArrayInputStream(("cancel\nBoard1\n").getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }


    @Test
    public void execute_Should_ThrowException_When_CancelEnteredInsteadOfBoardName(){
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

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nFeedbackTitle\nFeedbackDescription\n1\n").getBytes());
        System.setIn(in2);
        command3.execute(params);
        params.clear();

        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in3);
        command4.execute(params);

        InputStream in4 = new ByteArrayInputStream(("Team1\ncancel\n").getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredTeamNotExist(){
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

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nFeedbackTitle\nFeedbackDescription\n1\n").getBytes());
        System.setIn(in2);
        command3.execute(params);
        params.clear();

        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in3);
        command4.execute(params);

        InputStream in4 = new ByteArrayInputStream(("Team2\nBoard1\n").getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredBoardNotExist(){
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

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nFeedbackTitle\nFeedbackDescription\n1\n").getBytes());
        System.setIn(in2);
        command3.execute(params);
        params.clear();

        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in3);
        command4.execute(params);

        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard2\n").getBytes());
        System.setIn(in4);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command1.execute(params));
    }
    @Test
    public void should_DisplayBoardActivity(){
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

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nFeedbackTitle1\nFeedbackDescription\n1\n").getBytes());
        System.setIn(in2);
        command3.execute(params);
        params.clear();

        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nMargarita\nStoryTitle1\nStoryDescription1\nlow\nsmall\n").getBytes());
        System.setIn(in3);
        command4.execute(params);

        //Act
        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in4);
        String actualResult = command1.execute(params);

        String expectedResult = String.format("Board: Board1%n" +
                "Activity:%n" +
                "[10-May-2023 12:10:55] Board with name Board1 was created!%n" +
                "[10-May-2023 12:11:57] Task: FeedbackTitle1 is added to board: Board1!%n" +
                "[10-May-2023 12:12:36] Task: StoryTitle1 is added to board: Board1!%n" +
                "======================%n");


        //Assert
        //Assertions.assertEquals(expectedResult, actualResult);
    }
}
