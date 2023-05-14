package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.*;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.BoardImpl;
import com.project.oop.task.management.models.enums.BugStatus;
import com.project.oop.task.management.models.enums.FeedbackStatus;
import com.project.oop.task.management.models.enums.StoryStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SortTasksByTitleCommandTests {
    private TaskManagementRepositoryImpl repository;
    private Command command;
    private Command createBug;
    private Command createStory;
    private Command createFeedback;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new SortTasksByTitleCommand(repository);
        this.createBug = new CreateNewBugCommand(repository);
        this.createFeedback = new CreateNewFeedbackCommand(repository);
        this.createStory = new CreateNewStoryCommand(repository);
        repository.createNewTeam("Team1");
        repository.findTeamByName("Team1").addBoard(new BoardImpl("Board1"));
        repository.createNewPerson("Valid");
        repository.addNewPersonToTeam("Valid", "Team1");
    }

    @Test
    public void execute_Should_DisplayAllTasks_SortedByTitle_OrderedAlphabetically() {
        //Arrange
        List<String> params = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream
                (("Team1\nBoard1\nC.ValidTitle\nValidDescription\nHigh\nMinor\nValid\n").getBytes());
        System.setIn(in4);
        createBug.execute(params);

        InputStream in5 = new ByteArrayInputStream
                (("Team1\nBoard1\nValid\nA.ValidTitle\nValidDescription\nHigh\nLarge\n").getBytes());
        System.setIn(in5);
        createStory.execute(params);

        InputStream in6 = new ByteArrayInputStream
                (("Team1\nBoard1\nB.ValidTitle\nValidDescription\n1\n").getBytes());
        System.setIn(in6);
        createFeedback.execute(params);

        String sb = String.format("*********************%n" +
                "Story:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "A.ValidTitle", "ValidDescription") +
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
                        "Feedback:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "B.ValidTitle", "ValidDescription") +
                String.format("Status: %s%n" +
                                "Rating: %d%n" +
                                "*********************%n",
                        FeedbackStatus.NEW,
                        1) +
                String.format("*********************%n" +
                        "Bug:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "C.ValidTitle", "ValidDescription") +
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
    public void execute_DisplayNoTaskMessage_WhenListIsEmpty() {
        //Arrange
        List<String> params = new ArrayList<>();
        String expected = "No task created yet.";

        //Act, Assert
        Assertions.assertEquals(expected, command.execute(params));
    }
}
