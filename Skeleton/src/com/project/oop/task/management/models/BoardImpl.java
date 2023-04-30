package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.EventLog;

import java.util.List;

public class BoardImpl {
    public static final int NAME_MIN_LENGTH = 5;
    public static final int NAME_MAX_LENGTH = 15;
    private String name;
    private List<TaskImpl> tasks;
    private EventLog eventLog;


}
