package com.project.oop.task.management.models.contracts;

import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Severity;

public interface Bug extends Task{
    Priority getPriority();

    Severity getSeverity();

    String getAssignee();

    void changePriority(Priority priority);
    void changeSeverity(Severity severity);
}
