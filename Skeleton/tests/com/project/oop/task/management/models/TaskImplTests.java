package com.project.oop.task.management.models;


import com.project.oop.task.management.models.contracts.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskImplTests {

    @Test
    public void getHistory_Should_ReturnCopyOfTheCollection() {
        // Arrange
        Task task = initializeTestTask();

        // Act
        task.getHistory().clear();

        // Assert
        assertEquals(1, task.getHistory().size());
    }

    @Test
    public void addComment_Should_AddCommentToTheCollection() {
        // Arrange
        Task task = initializeTestTask();
        CommentImpl comment = initializeTestComment();

        //Act
        task.addComment(comment);

        // Assert
        assertEquals(1, task.getComments().size());
    }

    @Test
    public void removeComment_Should_RemoveCommentFromTheCollection() {
        // Arrange
        Task task = initializeTestTask();
        CommentImpl comment = initializeTestComment();

        //Act
        task.addComment(comment);
        task.removeComment(comment);

        // Assert
        assertEquals(0, task.getComments().size());
    }

    @Test
    public void removerComment_Should_ThrowException_When_CommentIsMissing() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () ->
                initializeTestTask().removeComment(initializeTestComment()));
    }


    public static Task initializeTestTask() {
        return new FeedbackImpl(
                1,
                "valid title",
                "valid description",
                10);
    }

    public static CommentImpl initializeTestComment() {
        return new CommentImpl(
                "This is test.",
                "Vasil");
    }
}
