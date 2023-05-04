package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoardImplTests {

    @Test
    public void should_ThrowException_When_NameIsShorter() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () ->
                new BoardImpl("test"));
    }

    @Test
    public void should_ThrowException_When_NameIsLonger() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () ->
                new BoardImpl("nikolnikolnikolnikol"));
    }


    @Test
    public void constructor_Should_CreateNewBoard_When_ParametersAreCorrect() {
        BoardImpl board = initializeTestBoard();

        assertEquals("valid", board.getName());
    }

    @Test
    public void getTasks_Should_ReturnCopyOfTheCollection() {
        // Arrange
        BoardImpl board = initializeTestBoard();
        Task task = initializeTestStory();

        // Act
       board.addTask(task);
       board.getTasks().clear();

        // Assert
        assertEquals(1, board.getTasks().size());
    }

    @Test
    public void getHistory_Should_ReturnCopyOfTheCollection() {
        // Arrange
        BoardImpl board = initializeTestBoard();

        // Act
        board.getHistory().clear();

        // Assert
        assertEquals(1, board.getHistory().size());
    }

    @Test
    public void removeTask_Should_RemoveTaskFromTheCollection() {
        // Arrange
        BoardImpl board = initializeTestBoard();
        Task task = initializeTestStory();

        board.addTask(task);
        board.removeTask(task);

        // Assert
        assertEquals(0, board.getTasks().size());
    }

    public static BoardImpl initializeTestBoard() {
        return new BoardImpl("valid");
    }
    public static Task initializeTestStory() {
        return new StoryImpl(1, "nikolnikol", "nikolnikolnikol", Priority.LOW, Size.LARGE, "Nikol");
    }
}
