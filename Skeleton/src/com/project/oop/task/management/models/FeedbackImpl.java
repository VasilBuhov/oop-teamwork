package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.enums.BugStatus;
import com.project.oop.task.management.models.enums.FeedbackStatus;
import com.project.oop.task.management.models.enums.StoryStatus;

public class FeedbackImpl extends TaskImpl implements Feedback {
    private static final FeedbackStatus INITIAL_STATUS = FeedbackStatus.NEW;
    private static final FeedbackStatus FINAL_STATUS = FeedbackStatus.DONE;
    private FeedbackStatus status;
    private int rating;

    public FeedbackImpl(int id, String title, String description, int rating) {
        super(id, title, description);
        this.status = FeedbackStatus.NEW;
        this.rating = rating;
        logEvent(String.format("Feedback created: %s", viewInfo()));
    }

    private void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int getRating() {
        return this.rating;
    }

    @Override
    public String viewInfo() {
        return String.format("***********%n" +
                        "Feedback: %n" +
                        super.toString() +
                        "Status: %s%n" +
                        "Rating: %s%n" +
                        "***********%n", status.toString(), rating);
    }

    protected void revertStatus() {
        if (status != INITIAL_STATUS) {
            setStatus(FeedbackStatus.values()[status.ordinal() - 1]);
        } else {
            logEvent(String.format("Can't revert, already at %s", getStatus()));
        }
    }

    protected void advanceStatus() {
        if (status != FINAL_STATUS) {
            setStatus(FeedbackStatus.values()[status.ordinal() + 1]);
        } else {
            logEvent(String.format("Can't advance, already at %s", getStatus()));
        }
    }

    protected void setStatus(FeedbackStatus status) {
        logEvent(String.format("Status changed from %s to %s", getStatus(), status));
        this.status = status;
    }

    @Override
    public String getStatus() {
        return this.status.toString();
    }

}
