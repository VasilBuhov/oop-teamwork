package com.project.oop.task.management.commands.show.models;

import com.project.oop.task.management.models.FeedbackImpl;
import com.project.oop.task.management.models.enums.FeedbackStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FeedbackImplTests {

    @Test
    public void changeRating_Should_ChangeTheRating_When_Used() {
        // Arrange
        FeedbackImpl feedback = initializeTestFeedback();

        //Act
        feedback.changeRating(111);

        //Assert
        assertEquals(111, feedback.getRating());
    }

    @Test
    public void should_ThrowException_When_RevertIsNotPossible() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () ->
                initializeTestFeedback().revertStatus());
    }

    @Test
    public void should_ThrowException_When_AdvanceIsNotPossible() {
        // Arrange
        FeedbackImpl feedback = initializeTestFeedback();
        feedback.advanceStatus();
        feedback.advanceStatus();
        feedback.advanceStatus();
        //Act, Assert
        assertThrows(IllegalArgumentException.class, feedback::advanceStatus);
    }

    @Test
    public void revertStatus_Should_Revert_When_Possible() {
        // Arrange
        FeedbackImpl feedback = initializeTestFeedback();

        //Act
        feedback.advanceStatus();
        feedback.revertStatus();

        //Assert
        assertEquals(FeedbackStatus.NEW.toString(), feedback.getStatus());
    }

    @Test
    public void advanceStatus_Should_Advance_When_Possible() {
        // Arrange
        FeedbackImpl feedback = initializeTestFeedback();

        //Act
        feedback.advanceStatus();

        //Assert
        assertEquals(FeedbackStatus.UNSCHEDULED.toString(), feedback.getStatus());
    }


    @Test
    public void getStatus_Should_ReturnStatus_When_FeedbackIsCreated() {
        // Arrange
        FeedbackImpl feedback = initializeTestFeedback();

        // Assert
        assertEquals(FeedbackStatus.NEW.toString(), feedback.getStatus());
    }

    @Test
    public void viewInfo_Should_ReturnFormattedString() {
        // Arrange
        FeedbackImpl feedback = initializeTestFeedback();

        //Act
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Feedback:%n" +
                "Title: %s%n" +
                "Description: %s%n" +
                "Comments: %n", feedback.getTitle(), feedback.getDescription()));
        for (String comment : feedback.getComments()) {
            sb.append(comment);
        }
        sb.append(String.format("Status: %s%n" +
                        "Rating: %d%n" +
                        "*********************%n",
                feedback.getStatus(),
                feedback.getRating()));

        // Assert
        assertEquals(sb.toString(), feedback.viewInfo());
    }

    @Test
    public void getAsAString_Should_ReturnResultFromViewInfo() {
        //Arrange
        FeedbackImpl feedback = initializeTestFeedback();

        //Assert
        assertEquals(feedback.viewInfo(), feedback.getAsString());
    }

    public static FeedbackImpl initializeTestFeedback() {
        return new FeedbackImpl(
                1,
                "valid title",
                "valid description",
                10);
    }
}
