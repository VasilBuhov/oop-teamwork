package com.project.oop.task.management.models;

import com.project.oop.task.management.models.enums.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BugImplTests {
    @Test
    public void changePriority_Should_ChangeThePriority_When_Used() {
        // Arrange
        BugImpl bug = initializeTestBug();

        //Act
        bug.changePriority(Priority.HIGH);

        //Assert
        assertEquals(Priority.HIGH, bug.getPriority());
    }

    @Test
    public void changeSeverity_Should_ChangeTheSeverity_When_Used() {
        // Arrange
        BugImpl bug = initializeTestBug();

        //Act
        bug.changeSeverity(Severity.MAJOR);

        //Assert
        assertEquals(Severity.MAJOR, bug.getSeverity());
    }

    @Test
    public void revertStatus_Should_Revert_When_Possible() {
        // Arrange
        BugImpl bug = initializeTestBug();

        //Act
        bug.revertStatus();

        //Assert
        assertEquals(BugStatus.ACTIVE.toString(), bug.getStatus());
    }

    @Test
    public void advanceStatus_Should_Advance_When_Possible() {
        // Arrange
        BugImpl bug = initializeTestBug();

        //Act
        bug.advanceStatus();

        //Assert
        assertEquals(BugStatus.FIXED.toString(), bug.getStatus());
    }


    @Test
    public void getAssignee_Should_ReturnAssignee_When_BugIsCreated() {
        // Arrange
        BugImpl bug = initializeTestBug();

        // Assert
        assertEquals("Nikol", bug.getAssignee());
    }

    @Test
    public void getStatus_Should_ReturnStatus_When_BugIsCreated() {
        // Arrange
        BugImpl bug = initializeTestBug();

        // Assert
        assertEquals(BugStatus.ACTIVE.toString(), bug.getStatus());
    }

    @Test
    public void viewInfo_Should_ReturnFormattedString() {
        // Arrange
        BugImpl bug = initializeTestBug();

        //Act
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("*********************%n" +
                "Bug:%n" +
                "Title: %s%n" +
                "Description: %s%n" +
                "Comments: %n", bug.getTitle(), bug.getDescription()));
        for (String comment : bug.getComments()) {
            sb.append(comment);
        }
        sb.append(String.format("Status: %s%n" +
                        "Priority: %s%n" +
                        "Severity: %s%n" +
                        "Assignee: %s%n" +
                        "*********************%n",
                bug.getStatus(),
                bug.getPriority().toString(),
                bug.getSeverity().toString(),
                bug.getAssignee()));

        // Assert
        assertEquals(sb.toString(), bug.viewInfo());
    }

    @Test
    public void getAsAString_Should_ReturnResultFromViewInfo() {
        //Arrange
        BugImpl bug = initializeTestBug();

        //Assert
        assertEquals(bug.viewInfo(), bug.getAsString());
    }

    public static BugImpl initializeTestBug() {
        return new BugImpl(
                1,
                "valid title",
                "valid description",
                Priority.LOW,
                Severity.CRITICAL,
                "Nikol");
    }
}
