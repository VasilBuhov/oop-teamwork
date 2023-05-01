package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.enums.BugStatus;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Severity;
import com.project.oop.task.management.models.enums.StoryStatus;


public class BugImpl extends TaskImpl implements Bug {
    private static final BugStatus INITIAL_STATUS = BugStatus.ACTIVE;
    private static final BugStatus FINAL_STATUS = BugStatus.FIXED;
    private BugStatus status;
    private Priority priority;
    private Severity severity;
    private String assignee;


    public BugImpl(int id, String title, String description, Priority priority, Severity severity, String assignee) {
        super(id, title, description);
        this.status = BugStatus.ACTIVE;
        this.priority = priority;
        this.severity = severity;
        this.assignee = assignee;
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }
    @Override
    public Priority getPriority() {
        return this.priority;
    }

    private void setSeverity(Severity severity) {
        this.severity = severity;
    }
    @Override
    public Severity getSeverity() {
        return this.severity;
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
        return String.format("*********************%n" +
                "Bug: %n" +
                super.toString() +
                "Status: %s%n" +
                "Priority: %s%n" +
                "Severity: %s%n" +
                "Assignee: %s%n" +
                "*********************%n",
                status.toString(),
                priority.toString(),
                severity.toString(),
                assignee);
    }

    protected void revertStatus() {
        if (status != INITIAL_STATUS) {
            setStatus(BugStatus.values()[status.ordinal() - 1]);
        } else {
            logEvent(String.format("Can't revert, already at %s", getStatus()));
        }
    }

    protected void advanceStatus() {
        if (status != FINAL_STATUS) {
            setStatus(BugStatus.values()[status.ordinal() + 1]);
        } else {
            logEvent(String.format("Can't advance, already at %s", getStatus()));
        }
    }

    protected void setStatus(BugStatus status) {
        logEvent(String.format("Status changed from %s to %s", getStatus(), status));
        this.status = status;
    }
    @Override
    public String getStatus() {
        return this.status.toString();
    }

    @Override
    public String getAsString() {
        return viewInfo();
    }
}
