package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemberImplTests {

    @Test
    public void getTasks_Should_ReturnCopyOfTheCollection() {
        // Arrange
        MemberImpl member = initializeTestMember();
        Task story = initializeTestStory();

        // Act
       member.addTask(story);
       member.getTasks().clear();

        // Assert
        assertEquals(1, member.getTasks().size());
    }

    @Test
    public void getHistory_Should_ReturnCopyOfTheCollection() {
        // Arrange
        MemberImpl member = initializeTestMember();

        // Act
        member.getHistory().clear();

        // Assert
        assertEquals(1, member.getHistory().size());
    }

    @Test
    public void removeTask_Should_RemoveTaskFromTheCollection() {
        //Arrange
        MemberImpl member = initializeTestMember();
        Task task = initializeTestStory();

        //Act
        member.addTask(task);
        member.removeTask(task);

        //Assert
        assertEquals(0, member.getTasks().size());
    }

    @Test
    public void removerComment_Should_ThrowException_When_TaskIsMissing() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () ->
                initializeTestMember().removeTask(initializeTestStory()));
    }

    public static Task initializeTestStory() {
        return new StoryImpl(
                1,
                "valid title",
                "valid description",
                Priority.LOW,
                Size.LARGE,
                "Nikol");
    }
    public static MemberImpl initializeTestMember() {
        return new MemberImpl("valid");
    }
}
