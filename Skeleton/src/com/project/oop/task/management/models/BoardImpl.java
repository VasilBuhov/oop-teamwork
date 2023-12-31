package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;

public class BoardImpl implements Board {
    public static final int NAME_MIN_LENGTH = 5;
    public static final int NAME_MAX_LENGTH = 15;
    public static final String BOARD_CREATED = "Board with name %s was created!";
    public static final String TASK_ERROR_MESSAGE = "No such task founded!";
    private static final String TASK_ADDED_TO_BOARD_MESSAGE = "Task: %s is added to board: %s!";
    private static final String TASK_REMOVED_FROM_BOARD_MESSAGE = "Task: %s is removed from board: %s!";
    private String name;
    private final List<Task> tasks;
    private final List<String> history;

    public BoardImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        history = new ArrayList<>();
        logEvent(new EventLogImpl(String.format(BOARD_CREATED, name)));
    }

    private void setName(String name) {
        ValidationHelper.ValidateStringLength(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        this.name = name;
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
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }


    @Override
    public void addTask(Task task) {
        tasks.add(task);
        logEvent(new EventLogImpl(String.format(TASK_ADDED_TO_BOARD_MESSAGE, task.getTitle(), name)));
    }
    @Override
    public void removeTask(Task task) {
        if (tasks.contains(task)) {
            tasks.remove(task);
            logEvent(new EventLogImpl(String.format(TASK_REMOVED_FROM_BOARD_MESSAGE, task.getTitle(), name)));
        } else {
            throw new IllegalArgumentException(TASK_ERROR_MESSAGE);
        }
    }

    public void logEvent(EventLogImpl event) {
        history.add(event.toString());
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Board: %s%n", getName()));
        if (tasks.size() == 0){
            sb.append(String.format("No tasks created in this board.%n"));
        }else {
            for (Task task : tasks) {
                sb.append("### ").append(task.viewInfo());
            }
        }

        sb.append(String.format("*********************%n"));
        return sb.toString();
    }
    public String getActivity() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Board: %s%n", getName()));
        sb.append(String.format("Activity:%n"));
        if (history.isEmpty()) {
            sb.append(String.format("No activity registered!%n"));
        } else {
            for (String event : history) {
                sb.append(event).append(System.lineSeparator());
            }
        }
        sb.append(String.format("======================%n"));
        return sb.toString();
    }
}
