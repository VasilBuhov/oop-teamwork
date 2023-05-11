package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.AddPersonToTeamCommand;
import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.*;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.enums.StoryStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SortStoriesByPriorityCommandTests {
    private TaskManagementRepositoryImpl repository;
    private Command command;
    private Command createTeam;
    private Command createStory;
    private Command createBoard;
    private Command createPerson;
    private Command addPersonToTeam;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new SortStoriesByPriorityCommand(repository);
        this.createTeam = new CreateNewTeamCommand(repository);
        this.createStory = new CreateNewStoryCommand(repository);
        this.createBoard = new CreateNewBoardCommand(repository);
        this.createPerson = new CreateNewPersonCommand(repository);
        this.addPersonToTeam = new AddPersonToTeamCommand(repository);
    }

    @Test
    public void execute_Should_DisplayAllStories_SortedByPriority() {
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

        List<String> params4 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in4);
        createStory.execute(params4);

        List<String> params5 = new ArrayList<>();
        InputStream in5 = new ByteArrayInputStream(("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nLow\nLarge\n").getBytes());
        System.setIn(in5);
        createStory.execute(params5);

        List<String> params6 = new ArrayList<>();
        InputStream in6 = new ByteArrayInputStream(("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nMedium\nLarge\n").getBytes());
        System.setIn(in6);
        createStory.execute(params6);

        String sb = String.format("*********************%n" +
                "Story:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "ValidTitle", "ValidDescription") +
                String.format("Status: %s%n" +
                                "Priority: %s%n" +
                                "Size: %s%n" +
                                "Assignee: %s%n" +
                                "*********************%n",
                        StoryStatus.NOT_DONE,
                        "High",
                        "Large",
                        "Valid") +
                String.format("*********************%n" +
                        "Story:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "ValidTitle", "ValidDescription") +
                String.format("Status: %s%n" +
                                "Priority: %s%n" +
                                "Size: %s%n" +
                                "Assignee: %s%n" +
                                "*********************%n",
                        StoryStatus.NOT_DONE,
                        "Medium",
                        "Large",
                        "Valid") +
                String.format("*********************%n" +
                        "Story:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "ValidTitle", "ValidDescription") +
                String.format("Status: %s%n" +
                                "Priority: %s%n" +
                                "Size: %s%n" +
                                "Assignee: %s%n" +
                                "*********************%n",
                        StoryStatus.NOT_DONE,
                        "Low",
                        "Large",
                        "Valid");


        //Act, Assert
        Assertions.assertEquals(sb, command.execute(params));
    }

    @Test
    public void execute_DisplayNoStoriesMessage_WhenListIsEmpty() {
        //Arrange
        List<String> params = new ArrayList<>();
        String expected = "No story created yet.";

        //Act, Assert
        Assertions.assertEquals(expected, command.execute(params));
    }

}
