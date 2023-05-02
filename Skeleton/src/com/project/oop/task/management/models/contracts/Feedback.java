package com.project.oop.task.management.models.contracts;

public interface Feedback extends Task{

    int getRating();
    void changeRating(int rating);

    void revertStatus();

    void advanceStatus();
}
