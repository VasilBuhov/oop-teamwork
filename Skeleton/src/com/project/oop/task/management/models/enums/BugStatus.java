package com.project.oop.task.management.models.enums;

public enum BugStatus {
    ACTIVE, FIXED;

    @Override
    public String toString() {
        switch (this) {
            case FIXED:
                return "Fixed";
            case ACTIVE:
                return "Active";
            default:
                return "Unknown";
        }
    }
}
