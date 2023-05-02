package com.project.oop.task.management.models.contracts;

import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;

public interface Story extends Task{
    Priority getPriority();

    Size getSize();

    String getAssignee();

}
