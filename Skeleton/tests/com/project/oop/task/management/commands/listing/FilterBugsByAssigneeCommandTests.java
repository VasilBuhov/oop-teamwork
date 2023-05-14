package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewBugCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FilterBugsByAssigneeCommandTests {
    private Command command1;
    private Command command2;
    private Command command3;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new FilterBugsByAssigneeCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        this.command3 = new CreateNewBugCommand(repository);

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

        String inputDataBug = "Team1\nBoard1\nLongBugTitle1\nLongBugDescription1\nlow\nminor\nMargarita\n";
        InputStream in2 = new ByteArrayInputStream((inputDataBug).getBytes());
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
    public void execute_Should_DisplayFilteredBugs_When_ExistingAssigneeNameEntered() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "Margarita";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act
        String filteredBugs = command1.execute(params).trim();

        String result = String.format("*********************%n" +
                "Bug:%n" +
                "Title: LongBugTitle1%n" +
                "Description: LongBugDescription1%n" +
                "Comments: %n" +
                "Status: Active%n" +
                "Priority: Low%n" +
                "Severity: Minor%n" +
                "Assignee: Margarita%n" +
                "*********************");

        //Assert
        Assertions.assertEquals(result, filteredBugs);
    }

    @Test
    public void execute_Should_DisplayNoBug_When_AssigneeHasNoBugs() {
        //Arrange
        List<String> params = new ArrayList<>();

        String commandInput = "Ivaylo";
        InputStream in3 = new ByteArrayInputStream((commandInput).getBytes());
        System.setIn(in3);

        //Act
        String filteredBugs = command1.execute(params).trim();

        String result = String.format("No bugs assigned to this person.");

        //Assert
        Assertions.assertEquals(result, filteredBugs);
    }
}
