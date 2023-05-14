package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.enums.FeedbackStatus;

public class FeedbackImpl extends TaskImpl implements Feedback {
    private static final FeedbackStatus INITIAL_STATUS = FeedbackStatus.NEW;
    private static final FeedbackStatus FINAL_STATUS = FeedbackStatus.DONE;
    public static final String FEEDBACK_CREATED_MESSAGE = "Feedback with title: %s was created.";
    public static final String CHANGED_RATING_MESSAGE = "Feedback's rating changed from %d to %d";
    public static final String CANNOT_REVERT_MESSAGE = "Feedback's status cannot be reverted, already at %s";
    public static final String CANNOT_ADVANCE_MESSAGE = "Feedback's status cannot advanced, already at %s";
    public static final String CHANGED_STATUS_MESSAGE = "Feedback's status changed from %s to %s";
    private FeedbackStatus status;
    private int rating;

    public FeedbackImpl(int id, String title, String description, int rating) {
        super(id, title, description);
        this.status = FeedbackStatus.NEW;
        this.rating = rating;
        logEvent(new EventLogImpl(String.format(FEEDBACK_CREATED_MESSAGE, title)));
    }

    @Override
    public void changeRating(int rating) {
        logEvent(new EventLogImpl(String.format(CHANGED_RATING_MESSAGE, getRating(), rating)));
        this.rating = rating;
    }

    @Override
    public int getRating() {
        return this.rating;
    }

    protected void setStatus(FeedbackStatus status) {
        logEvent(new EventLogImpl(String.format(CHANGED_STATUS_MESSAGE, getStatus(), status)));
        this.status = status;
    }

    @Override
    public String getStatus() {
        return this.status.toString();
    }

    public void revertStatus() {
        if (status != INITIAL_STATUS) {
            setStatus(FeedbackStatus.values()[status.ordinal() - 1]);
        } else {
            logEvent(new EventLogImpl(String.format(CANNOT_REVERT_MESSAGE, getStatus())));
            throw new IllegalArgumentException(String.format(CANNOT_REVERT_MESSAGE, getStatus()));
        }
    }

    public void advanceStatus() {
        if (status != FINAL_STATUS) {
            setStatus(FeedbackStatus.values()[status.ordinal() + 1]);
        } else {
            logEvent(new EventLogImpl(String.format(CANNOT_ADVANCE_MESSAGE, getStatus())));
            throw new IllegalArgumentException(String.format(CANNOT_ADVANCE_MESSAGE, getStatus()));
        }
    }

    @Override
    public String viewInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Feedback:%n"));
        sb.append(super.viewInfo());
        sb.append(String.format("Status: %s%n" +
                        "Rating: %d%n" +
                        "*********************%n",
                getStatus(),
                rating));
        return sb.toString();
    }

    @Override
    public String getAsString() {
        return viewInfo();
    }

}
