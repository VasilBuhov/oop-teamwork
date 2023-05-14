package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.AddPersonToTeamCommand;
import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.*;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.BoardImpl;
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
    private Command createStory;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new SortStoriesByPriorityCommand(repository);
        this.createStory = new CreateNewStoryCommand(repository);
        repository.createNewTeam("Team1");
        repository.findTeamByName("Team1").addBoard(new BoardImpl("Board1"));
        repository.createNewPerson("Valid");
        repository.addNewPersonToTeam("Valid", "Team1");
    }

    @Test
    public void execute_Should_DisplayAllStories_SortedByPriority() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in4);
        createStory.execute(params);

        InputStream in5 = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nLow\nLarge\n").getBytes());
        System.setIn(in5);
        createStory.execute(params);

        InputStream in6 = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nValidTitle\nValidDescription\nMedium\nLarge\n").getBytes());
        System.setIn(in6);
        createStory.execute(params);

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
