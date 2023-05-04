package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.enums.BugStatus;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Severity;


public class BugImpl extends TaskImpl implements Bug {
    private static final BugStatus INITIAL_STATUS = BugStatus.ACTIVE;
    private static final BugStatus FINAL_STATUS = BugStatus.FIXED;
    public static final String BUG_CREATED_MESSAGE = "Bug with title: %s was created.";
    public static final String CHANGED_PRIORITY_MESSAGE = "Bug's priority changed from %s to %s";
    public static final String CHANGED_SEVERITY_MESSAGE = "Bug's severity changed from %s to %s";
    public static final String CHANGED_STATUS_MESSAGE = "Bug's status changed from %s to %s";
    public static final String CANNOT_REVERT_MESSAGE = "Bug's status cannot be reverted, already at %s";
    public static final String CANNOT_ADVANCE_MESSAGE = "Bug's status cannot advanced, already at %s";

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
        logEvent(new EventLogImpl(String.format(BUG_CREATED_MESSAGE, title)));
     }

    @Override
    public void changePriority(Priority priority) {
        logEvent(new EventLogImpl(String.format(CHANGED_PRIORITY_MESSAGE, getPriority(), priority)));
        this.priority = priority;
    }

    private void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public Priority getPriority() {
        return this.priority;
    }

    @Override
    public void changeSeverity(Severity severity) {
        logEvent(new EventLogImpl(String.format(CHANGED_SEVERITY_MESSAGE, getSeverity(), severity)));
        this.severity = severity;
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

    protected void setStatus(BugStatus status) {
        logEvent(new EventLogImpl(String.format(CHANGED_STATUS_MESSAGE, getStatus(), status)));
        this.status = status;
    }

    @Override
    public String getStatus() {
        return this.status.toString();
    }

    public void revertStatus() {
        if (status != INITIAL_STATUS) {
            setStatus(BugStatus.values()[status.ordinal() - 1]);
        } else {
            logEvent(new EventLogImpl(String.format(CANNOT_REVERT_MESSAGE, getStatus())));
        }
    }

    public void advanceStatus() {
        if (status != FINAL_STATUS) {
            setStatus(BugStatus.values()[status.ordinal() + 1]);
        } else {
            logEvent(new EventLogImpl(String.format(CANNOT_ADVANCE_MESSAGE, getStatus())));
        }
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

    @Override
    public String getAsString() {
        return viewInfo();
    }

}
