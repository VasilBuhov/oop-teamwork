package com.project.oop.task.management.models.contracts;

import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.models.enums.StoryStatus;

public interface Story extends Task{
    Priority getPriority();

    Size getSize();

    String getAssignee();

    void changePriority(Priority newPriority);

    void changeSize(Size newSize);

    void changeStatus(StoryStatus newStatus);
}
