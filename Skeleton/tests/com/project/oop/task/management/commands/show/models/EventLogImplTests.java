package com.project.oop.task.management.commands.show.models;

import com.project.oop.task.management.models.EventLogImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EventLogImplTests {

    @Test
    public void should_ThrowException_When_ArgumentsAreNotValid() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () ->
                new EventLogImpl(""));
    }

    @Test
    public void getDescription_Should_ReturnDescription_When_EventLogIsCreated() {
        // Arrange
        EventLogImpl eventLog = new EventLogImpl("test");

        // Act, Assert
        assertEquals("test", eventLog.getDescription());
    }
}
