package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.AddPersonToTeamCommand;
import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewBugCommand;
import com.project.oop.task.management.commands.creation.CreateNewPersonCommand;
import com.project.oop.task.management.commands.creation.CreateNewTeamCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.enums.BugStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class SortBugsByTitleCommandTests {
    private TaskManagementRepositoryImpl repository;
    private Command command;
    private Command createTeam;
    private Command createBug;
    private Command createBoard;
    private Command createPerson;
    private Command addPersonToTeam;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new SortBugsByTitleCommand(repository);
        this.createTeam = new CreateNewTeamCommand(repository);
        this.createBug = new CreateNewBugCommand(repository);
        this.createBoard = new CreateNewBoardCommand(repository);
        this.createPerson = new CreateNewPersonCommand(repository);
        this.addPersonToTeam = new AddPersonToTeamCommand(repository);
    }

    @Test
    public void execute_Should_DisplayAllBugs_SortedByTitle_OrderedAlphabetically() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in3 = new ByteArrayInputStream(("Valid\nTeam1\n").getBytes());
        System.setIn(in3);
        addPersonToTeam.execute(params3);

        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard1\nA.ValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in4);
        createBug.execute(params);

        InputStream in5 = new ByteArrayInputStream(("Team1\nBoard1\nB.ValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in5);
        createBug.execute(params);

        String sb = String.format("*********************%n" +
                "Bug:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "A.ValidTitle", "ValidDescription") +
                String.format("Status: %s%n" +
                                "Priority: %s%n" +
                                "Severity: %s%n" +
                                "Assignee: %s%n" +
                                "*********************%n",
                        BugStatus.ACTIVE,
                        "High",
                        "Minor",
                        "Valid") +
                String.format("*********************%n" +
                        "Bug:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "B.ValidTitle", "ValidDescription") +
                String.format("Status: %s%n" +
                                "Priority: %s%n" +
                                "Severity: %s%n" +
                                "Assignee: %s%n" +
                                "*********************%n",
                        BugStatus.ACTIVE,
                        "High",
                        "Minor",
                        "Valid");


        //Act, Assert
        Assertions.assertEquals(sb, command.execute(params));
    }

    @Test
    public void execute_DisplayNoBugsMessage_WhenListIsEmpty() {
        //Arrange
        List<String> params = new ArrayList<>();
        String expected = "No bugs created yet.";

        //Act, Assert
        Assertions.assertEquals(expected, command.execute(params));
    }
}
