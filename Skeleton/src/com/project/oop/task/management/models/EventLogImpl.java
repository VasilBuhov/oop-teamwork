package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.EventLog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventLogImpl {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
    private final String description;
    private final LocalDateTime timestamp;

    public EventLogImpl (String description) {
        if (description.isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return String.format("[%s] %s", timestamp.format(formatter), description);
    }

}
