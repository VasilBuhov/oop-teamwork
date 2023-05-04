package com.project.oop.task.management.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentImplTests {

    @Test
    public void construct_Should_CreateCategory_When_NameIsValid() {
        CommentImpl comment = initializeTestComment();
        Assertions.assertNotNull(comment);
    }

    @Test
    public void constructor_Should_InitializeContent_When_ArgumentsAreValid() {
        CommentImpl comment = initializeTestComment();
        Assertions.assertNotNull(comment.getContent());
    }

    @Test
    public void constructor_Should_InitializeAuthor_When_ArgumentsAreValid() {
        CommentImpl comment = initializeTestComment();
        Assertions.assertNotNull(comment.getAuthor());
    }

    @Test
    public void toString_Should_ReturnResultFromViewInfo() {
        //Arrange
        CommentImpl comment = initializeTestComment();

        //Act
        String string = String.format("***********%n") +
                String.format("Comment: " + comment.getContent() + "%n") +
                String.format("Author: %s%n", comment.getAuthor()) +
                String.format("***********%n");

        //Assert
        assertEquals(string, comment.toString());
    }

    public static CommentImpl initializeTestComment() {
        return new CommentImpl(
                "This is test.",
                "Vasil");
    }
}
