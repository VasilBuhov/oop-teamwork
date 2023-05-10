package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewFeedbackCommand;
import com.project.oop.task.management.commands.creation.CreateNewTeamCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.enums.FeedbackStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SortFeedbackByTitleCommandTests {
    private Command command;
    private Command createFeedback;
    private Command createBoard;
    private Command createTeam;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new SortFeedbackByTitleCommand(repository);
        this.createBoard = new CreateNewBoardCommand(repository);
        this.createTeam = new CreateNewTeamCommand(repository);
        this.createFeedback = new CreateNewFeedbackCommand(repository);
    }

    @Test
    public void execute_Should_DisplayAllFeedback_SortedByRating() {
        //Arrange
        List<String> params = new ArrayList<>();

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in2);
        createBoard.execute(params2);

        List<String> params3 = new ArrayList<>();
        InputStream in4 = new ByteArrayInputStream(("Team1\nBoard1\nA.ValidTitle\nValidDescription\n1\n").getBytes());
        System.setIn(in4);
        createFeedback.execute(params3);

        List<String> params4 = new ArrayList<>();
        InputStream in5 = new ByteArrayInputStream(("Team1\nBoard1\nB.ValidTitle\nValidDescription\n2\n").getBytes());
        System.setIn(in5);
        createFeedback.execute(params4);

        String sb = String.format("*********************%n" +
                "Feedback:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "A.ValidTitle", "ValidDescription") +
                String.format("Status: %s%n" +
                                "Rating: %d%n" +
                                "*********************%n",
                        FeedbackStatus.NEW,
                        1) +
                String.format("*********************%n" +
                        "Feedback:%n") +
                String.format("Title: %s%n" +
                        "Description: %s%n" +
                        "Comments: %n", "B.ValidTitle", "ValidDescription") +
                String.format("Status: %s%n" +
                                "Rating: %d%n" +
                                "*********************%n",
                        FeedbackStatus.NEW,
                        2);

        //Act, Assert
        Assertions.assertEquals(sb, command.execute(params));
    }

    @Test
    public void execute_DisplayNoFeedbackMessage_WhenListIsEmpty() {
        //Arrange
        List<String> params = new ArrayList<>();
        String expected = "No feedback created yet.";

        //Act, Assert
        Assertions.assertEquals(expected, command.execute(params));
    }
}
