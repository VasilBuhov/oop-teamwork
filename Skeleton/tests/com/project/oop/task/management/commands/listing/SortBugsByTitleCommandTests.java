package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBugCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.BoardImpl;
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
    private Command createBug;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new SortBugsByTitleCommand(repository);
        this.createBug = new CreateNewBugCommand(repository);
        repository.createNewTeam("Team1");
        repository.findTeamByName("Team1").addBoard(new BoardImpl("Board1"));
        repository.createNewPerson("Valid");
        repository.addNewPersonToTeam("Valid", "Team1");
    }

    @Test
    public void execute_Should_DisplayAllBugs_SortedByTitle_OrderedAlphabetically() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in4 = new ByteArrayInputStream
                (("Team1\nBoard1\nA.ValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in4);
        createBug.execute(params);

        InputStream in5 = new ByteArrayInputStream
                (("Team1\nBoard1\nB.ValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
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
