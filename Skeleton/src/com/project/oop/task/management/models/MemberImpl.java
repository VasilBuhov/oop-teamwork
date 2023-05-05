package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;

public class MemberImpl implements Member {
    public static final int NAME_MIN_LENGTH = 5;
    public static final int NAME_MAX_LENGTH = 15;
    public static final String MEMBER_CREATED =
            "Member with name %s was created!";
    public static final String TASK_ERROR_MESSAGE =
            "No such task founded!";
    private static final String TASK_ADDED_TO_MEMBER_MESSAGE =
            "Task with title: %s was added %s's tasks!";
    private static final String TASK_REMOVED_FROM_MEMBER_MESSAGE =
            "Task with title: %s is removed from %s's tasks!";
    private String name;
    private final List<Task> tasks;
    private final List<String> history;

    public MemberImpl(String name) {
        setName(name);
        tasks = new ArrayList<>();
        history = new ArrayList<>();
        logEvent(new EventLogImpl(String.format(MEMBER_CREATED, name)));
    }
    private void setName(String name) {
        validateName(name);
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
        logEvent(new EventLogImpl(String.format(TASK_ADDED_TO_MEMBER_MESSAGE, task.getTitle(), name)));
    }

    @Override
    public void removeTask(Task task) {
        if (tasks.contains(task)) {
            tasks.remove(task);
            logEvent(new EventLogImpl(String.format(TASK_REMOVED_FROM_MEMBER_MESSAGE, task.getTitle(), name)));
        } else {
            throw new IllegalArgumentException(TASK_ERROR_MESSAGE);
        }
    }

    @Override
    public void logEvent(EventLogImpl event) {
        this.history.add(event.toString());
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("======================%n"));
        sb.append(String.format("Member: %s%n", getName()));
        sb.append(String.format("Tasks:%n"));
        if (tasks.isEmpty()) {
            sb.append(String.format("%s still does not have any task%n", name));
        } else {
            int counter = 1;
            for (Task task : tasks) {
                sb.append(counter).append(". ").append(task.viewInfo());
            }
        }
        sb.append(String.format("======================%n"));
        return sb.toString();
    }
    public String getActivity() {
        StringBuilder sb = new StringBuilder();
//        sb.append(String.format("======================%n"));
        sb.append(String.format("Member: %s%n", getName()));
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

    public static void validateName(String name){
        ValidationHelper.ValidateStringLength(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }
}
