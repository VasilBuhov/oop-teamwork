package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;

public class MemberImpl implements Member {
    public static final int NAME_MIN_LENGTH = 5;
    public static final int NAME_MAX_LENGTH = 15;
    public static final String MEMBER_CREATED = "Member with name %s was created!";
    public static final String TASK_ERROR_MESSAGE = "No such task founded!";
    private static final String TASK_ADDED_TO_BOARD_MESSAGE = "Task: %s is added to board: %s!";
    private static final String TASK_REMOVED_FROM_BOARD_MESSAGE = "Task: %s is removed from board: %s!";
    private String name;
    private final List<Task> tasks;
    private final List<String> history;

    public MemberImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        history = new ArrayList<>();
        logEvent(new EventLogImpl(String.format(MEMBER_CREATED, name)).toString());
    }
    private void setName(String name) {
        ValidationHelper.ValidateStringLength(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        this.name = name;
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
        logEvent(new EventLogImpl(String.format(TASK_ADDED_TO_BOARD_MESSAGE, task.getTitle(), name)).toString());
    }

    @Override
    public void removeTask(Task task) {
        if (tasks.contains(task)) {
            tasks.remove(task);
            logEvent(new EventLogImpl(String.format(TASK_REMOVED_FROM_BOARD_MESSAGE, task.getTitle(), name)).toString());
        } else {
            throw new IllegalArgumentException(TASK_ERROR_MESSAGE);
        }
    }

    @Override
    public void logEvent(String event) {
        this.history.add(new EventLogImpl(event).toString());
    }
}
