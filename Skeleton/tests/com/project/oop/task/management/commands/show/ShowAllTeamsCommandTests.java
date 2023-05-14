package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewFeedbackCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShowAllTeamsCommandTests {

    private TaskManagementRepositoryImpl repository;
    private Command command1;
    private Command command2;
    private Command command3;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new ShowAllTeamsCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
        this.command3 = new CreateNewFeedbackCommand(repository);
    }

    @Test
    public void should_ThrowException_WhenNoTeamsCreatedInSystem() {
        //Arrange
        List<String> params = new ArrayList<>();

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void should_DisplayTeamInfo() {

        //Arrange
        String teamName = "Team1";
        String boardName = "Board1";
        String personName = "Margarita";
        repository.createNewTeam(teamName);
        repository.createNewPerson(personName);
        repository.addNewPersonToTeam(personName, teamName);

        List<String> params = new ArrayList<>();
        String inputDataBoard = "Team1\nBoard1\n";
        InputStream in1 = new ByteArrayInputStream((inputDataBoard).getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.clear();

        String inputDataFeedback = "Team1\nBoard1\nShortTitle\nLongFeedbackDescription1\n1\n";
        InputStream in2 = new ByteArrayInputStream((inputDataFeedback).getBytes());
        System.setIn(in2);
        command3.execute(params);

        String expectedResult = String.format("======================%n" +
                "Team: Team1%n" +
                "MEMBERS:%n" +
                "1. Member: Margarita%n" +
                "Tasks:%n" +
                "Margarita still does not have any task%n" +
                "---------------------%n" +
                "BOARDS:%n" +
                "1. Board: Board1%n" +
                "### Feedback:%n" +
                "Title: ShortTitle%n" +
                "Description: LongFeedbackDescription1%n" +
                "Comments: %n" +
                "Status: New%n" +
                "Rating: 1%n" +
                "*********************%n" +
                "*********************%n" +
                "======================"
        );

        //Act
        String actualResult = command1.execute(params);

        //Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
