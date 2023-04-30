package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.enums.FeedbackStatus;

public class FeedbackImpl extends TaskImpl implements Feedback {
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

    @Override
    public String getStatus() {
        return this.status.toString();
    }

    public void setStatus(FeedbackStatus status) {
        this.status = status;
    }
}
