package com.project.oop.task.management.models.contracts;

import com.project.oop.task.management.models.EventLogImpl;

import java.util.List;

public interface Loggable {
    List<String> getHistory();
    void logEvent(EventLogImpl event);


}
