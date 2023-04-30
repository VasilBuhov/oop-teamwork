package com.project.oop.task.management.models.contracts;

import java.util.List;

public interface Loggable {
    List<String> getHistory();
    void logEvent(String event);


}
