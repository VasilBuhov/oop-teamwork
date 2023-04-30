package com.project.oop.task.management.models.enums;

public enum StoryStatus {
    NOT_DONE, IN_PROGRESS, DONE;

    @Override
    public String toString() {
        switch (this) {
            case NOT_DONE:
                return "NotDone";
            case IN_PROGRESS:
                return "InProgress";
            case DONE:
                return "Done";
            default:
                return "Unknown";
        }
    }
}
