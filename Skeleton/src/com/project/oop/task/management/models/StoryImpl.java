package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.models.enums.StoryStatus;


public class StoryImpl extends TaskImpl implements Story {

    private static final StoryStatus INITIAL_STATUS = StoryStatus.NOT_DONE;
    private static final StoryStatus FINAL_STATUS = StoryStatus.DONE;
    public static final String STORY_CREATED_MESSAGE = "Story with title: %s was created.";
    public static final String CHANGED_PRIORITY_MESSAGE = "Story's priority changed from %s to %s";
    public static final String CHANGED_SIZE_MESSAGE = "Story's size changed from %s to %s";
    public static final String CHANGED_STATUS_MESSAGE = "Story's status changed from %s to %s";
    public static final String CANNOT_REVERT_MESSAGE = "Story's status cannot be reverted, already at %s";
    public static final String CANNOT_ADVANCE_MESSAGE = "Story's status cannot advanced, already at %s";
    private StoryStatus status;
    private Priority priority;
    private Size size;
    private String assignee;

    public StoryImpl(int id, String title, String description, Priority priority, Size size, String assignee) {
        super(id, title, description);
        this.status = StoryStatus.NOT_DONE;
        setPriority(priority);
        setSize(size);
        setAssignee(assignee);
        logEvent(new EventLogImpl(String.format(STORY_CREATED_MESSAGE, title)));
    }

    @Override
    public void changePriority(Priority newPriority) {
        logEvent(new EventLogImpl(String.format(CHANGED_PRIORITY_MESSAGE, getPriority(), newPriority)));
        setPriority(newPriority);
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public void changeSize(Size newSize) {
        logEvent(new EventLogImpl(String.format(CHANGED_SIZE_MESSAGE, getSize(), newSize)));
        setSize(newSize);
    }

    private void setSize(Size size) {
        this.size = size;
    }

    @Override
    public Size getSize() {
        return this.size;
    }


    private void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public String getAssignee() {
        return this.assignee;
    }

    @Override
    public void changeStatus(StoryStatus newStatus) {
        logEvent(new EventLogImpl(String.format(CHANGED_STATUS_MESSAGE, getStatus(), newStatus)));
        setStatus(newStatus);
    }

    private void setStatus(StoryStatus status) {
        logEvent(new EventLogImpl(String.format(CHANGED_STATUS_MESSAGE, getStatus(), status)));
        this.status = status;
    }

    @Override
    public String getStatus() {
        return this.status.toString();
    }

    public void revertStatus() {
        if (status != INITIAL_STATUS) {
            setStatus(StoryStatus.values()[status.ordinal() - 1]);
        } else {
            logEvent(new EventLogImpl(String.format(CANNOT_REVERT_MESSAGE, getStatus())));
        }
    }

    public void advanceStatus() {
        if (status != FINAL_STATUS) {
            setStatus(StoryStatus.values()[status.ordinal() + 1]);
        } else {
            logEvent(new EventLogImpl(String.format(CANNOT_ADVANCE_MESSAGE, getStatus())));
        }
    }

    @Override
    public String getAsString() {
        return viewInfo();
    }

    @Override
    public String viewInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("*********************%n" +
                        "Story:%n"));
        sb.append(super.viewInfo());
        sb.append(String.format("Status: %s%n" +
                        "Priority: %s%n" +
                        "Size: %s%n" +
                        "Assignee: %s%n" +
                        "*********************%n",
                getStatus(),
                priority.toString(),
                size.toString(),
                assignee));

        return sb.toString();
    }
}
