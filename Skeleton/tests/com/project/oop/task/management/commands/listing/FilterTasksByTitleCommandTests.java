package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewBugCommand;
import com.project.oop.task.management.commands.creation.CreateNewFeedbackCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FilterTasksByTitleCommandTests {
    private Command command1;
    private Command command2;
    private Command command3;
    private Command command4;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new FilterTasksByTitleCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        this.command3 = new CreateNewBugCommand(repository);
        this.command4 = new CreateNewFeedbackCommand(repository);

    }

    @Test
    public void execute_Should_ThrowException_When_InputIsEqualToCancel() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nLongBugTitle1\nLongBugDescription1\nlow\nminor\nMargarita\n").getBytes());
        System.setIn(in2);
        command3.execute(params);

        InputStream in3 = new ByteArrayInputStream(("cancel").getBytes());
        System.setIn(in3);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_EnteredTitleNotFound() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nLongBugTitle1\nLongBugDescription1\nlow\nminor\nMargarita\n").getBytes());
        System.setIn(in2);
        command3.execute(params);

        InputStream in3 = new ByteArrayInputStream(("NewTaskTitle").getBytes());
        System.setIn(in3);

        //Act
        String foundTasks = command1.execute(params).trim();

        String result = String.format("No task with this title");

        //Assert
        Assertions.assertEquals(result, foundTasks);
    }

    @Test
    public void execute_Should_DisplayFilteredTasks_When_TasksWithEnteredTitleEntered() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nTaskTitle1\nLongBugDescription1\nlow\nminor\nMargarita\n").getBytes());
        System.setIn(in2);
        command3.execute(params);


        InputStream in3 = new ByteArrayInputStream(("Team1\nBoard1\nTaskTitle1\nFeedbackDescription\n1\n").getBytes());
        System.setIn(in3);
        command4.execute(params).trim();

        InputStream in4 = new ByteArrayInputStream(("TaskTitle1").getBytes());
        System.setIn(in4);

        //Act
        String filteredBugs = command1.execute(params).trim();

        String result = String.format("*********************%n" +
                "Bug:%n" +
                "Title: TaskTitle1%n" +
                "Description: LongBugDescription1%n" +
                "Comments: %n" +
                "Status: Active%n" +
                "Priority: Low%n" +
                "Severity: Minor%n" +
                "Assignee: Margarita%n" +
                "*********************%n" +
                "*********************%n" +
                "*********************%n" +
                "Feedback:%n" +
                "Title: TaskTitle1%n" +
                "Description: FeedbackDescription%n" +
                "Comments: %n" +
                "Status: New%n" +
                "Rating: 1%n" +
                "*********************");

        //Assert
        Assertions.assertEquals(result, filteredBugs);
    }

    @Test
    public void execute_Should_DisplayNoTask_When_NoTasksWithEnteredTitleExist() {
        //Arrange
        List<String> params = new ArrayList<>();
        repository.createNewTeam("Team1");
        repository.createBoard("Board1");
        repository.createNewPerson("Margarita");
        repository.addNewPersonToTeam("Margarita", "Team1");
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);

        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\nLongBugTitle1\nLongBugDescription1\nlow\nminor\nMargarita\n").getBytes());
        System.setIn(in2);
        command3.execute(params);

        InputStream in3 = new ByteArrayInputStream(("NewBugTitle").getBytes());
        System.setIn(in3);


        //Act
        String filteredTasks = command1.execute(params).trim();

        String result = String.format("No task with this title");

        //Assert
        Assertions.assertEquals(result, filteredTasks);
    }
}
