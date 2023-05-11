package com.project.oop.task.management.models;

import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.models.enums.StoryStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoryImplTests {
    @Test
    public void changePriority_Should_ChangeThePriority_When_Used() {
        // Arrange
        StoryImpl story = initializeTestStory();

        //Act
        story.changePriority(Priority.HIGH);

        //Assert
        assertEquals(Priority.HIGH, story.getPriority());
    }

    @Test
    public void changeSize_Should_ChangeTheSize_When_Used() {
        // Arrange
        StoryImpl story = initializeTestStory();

        //Act
        story.changeSize(Size.SMALL);

        //Assert
        assertEquals(Size.SMALL, story.getSize());
    }

    @Test
    public void changeStatus_Should_ChangeTheStatus_When_Used() {
        // Arrange
        StoryImpl story = initializeTestStory();

        //Act
        story.changeStatus(StoryStatus.DONE);

        //Assert
        assertEquals(StoryStatus.DONE.toString(), story.getStatus());
    }

    @Test
    public void revertStatus_Should_Revert_When_Possible() {
        // Arrange
        StoryImpl story = initializeTestStory();

        //Act
        story.revertStatus();

        //Assert
        assertEquals(StoryStatus.NOT_DONE.toString(), story.getStatus());
    }

    @Test
    public void advanceStatus_Should_Advance_When_Possible() {
        // Arrange
        StoryImpl story = initializeTestStory();

        //Act
        story.advanceStatus();

        //Assert
        assertEquals(StoryStatus.IN_PROGRESS.toString(), story.getStatus());
    }

    @Test
    public void getSize_Should_ReturnSize_When_StoryIsCreated() {
        // Arrange
        StoryImpl story = initializeTestStory();

        // Assert
        assertEquals(Size.LARGE, story.getSize());
    }

    @Test
    public void getAssignee_Should_ReturnAssignee_When_StoryIsCreated() {
        // Arrange
        StoryImpl story = initializeTestStory();

        // Assert
        assertEquals("Nikol", story.getAssignee());
    }

    @Test
    public void getStatus_Should_ReturnStatus_When_StoryIsCreated() {
        // Arrange
        StoryImpl story = initializeTestStory();

        // Assert
        assertEquals(StoryStatus.NOT_DONE.toString(), story.getStatus());
    }

    @Test
    public void viewInfo_Should_ReturnFormattedString() {
        // Arrange
        StoryImpl story = initializeTestStory();

        //Act
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("*********************%n" +
                "Story:%n" +
                "Title: %s%n" +
                "Description: %s%n" +
                "Comments: %n", story.getTitle(), story.getDescription()));
        for (String comment : story.getComments()) {
            sb.append(comment);
        }
        sb.append(String.format("Status: %s%n" +
                        "Priority: %s%n" +
                        "Size: %s%n" +
                        "Assignee: %s%n" +
                        "*********************%n",
                story.getStatus(),
                story.getPriority().toString(),
                story.getSize().toString(),
                story.getAssignee()));

        // Assert
        assertEquals(sb.toString(), story.viewInfo());
    }

    @Test
    public void getAsString_Should_ReturnResultFromViewInfo() {
        //Arrange
        StoryImpl story = initializeTestStory();

        //Assert
        assertEquals(story.viewInfo(), story.getAsString());
    }

    public static StoryImpl initializeTestStory() {
        return new StoryImpl(
                1,
                "valid title",
                "valid description",
                Priority.LOW,
                Size.LARGE,
                "Nikol");
    }
}
