package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.models.enums.StoryStatus;


public class StoryImpl extends TaskImpl implements Story {

    private static final StoryStatus INITIAL_STATUS = StoryStatus.NOT_DONE;
    private static final StoryStatus FINAL_STATUS = StoryStatus.DONE;
    private StoryStatus status;
    private Priority priority;
    private Size size;
    private String assignee;

    public StoryImpl(int id, String title, String description, Priority priority, Size size, String assignee) {
        super(id, title, description);
        this.status = StoryStatus.IN_PROGRESS;
        setPriority(priority);
        setSize(size);
        setAssignee(assignee);
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }
    @Override
    public Priority getPriority() {
        return this.priority;
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
    public String viewInfo() {
        return String.format("***********%n" +
                "Story:%n" +
                super.toString() +
                "Status: %s%n" +
                "Priority: %s%n" +
                "Size: %d%n" +
                "Assignee: %s%n" +
                "***********%n", status.toString(), priority, size, assignee);
    }
    protected void revertStatus() {
        if (status != INITIAL_STATUS) {
            setStatus(StoryStatus.values()[status.ordinal() - 1]);
        } else {
            logEvent(String.format("Can't revert, already at %s", getStatus()));
        }
    }

    protected void advanceStatus() {
        if (status != FINAL_STATUS) {
            setStatus(StoryStatus.values()[status.ordinal() + 1]);
        } else {
            logEvent(String.format("Can't advance, already at %s", getStatus()));
        }
    }

    protected void setStatus(StoryStatus status) {
        logEvent(String.format("Status changed from %s to %s", getStatus(), status));
        this.status = status;
    }

    @Override
    public String getStatus() {
        return this.status.toString();
    }

}
