package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Comment;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskImpl implements Task{
    public static final int DESCRIPTION_MIN_LENGTH = 10;
    public static final int DESCRIPTION_MAX_LENGTH = 500;
    public static final int TITLE_MIN_LENGTH = 10;
    public static final int TITLE_MAX_LENGTH = 50;
    private int id;
    private String title;
    private String description;
    private final List<String> comments;
    private final List<String> history;

    public TaskImpl(int id, String title, String description) {
        setId(id);
        setTitle(title);
        setDescription(description);
        comments = new ArrayList<>();
        history = new ArrayList<>();
    }

    private void setId(int id) {
        this.id = id;
    }
    @Override
    public int getId() {
        return this.id;
    }

    public static void validateTitle(String title) {
        ValidationHelper.ValidateStringLength(title, TITLE_MIN_LENGTH, TITLE_MAX_LENGTH);
    }

    private void setTitle(String title) {
        validateTitle(title);
        this.title = title;
    }
    @Override
    public String getTitle() {
        return this.title;
    }
    public static void validateDescription(String description) {
        ValidationHelper.ValidateStringLength(description, DESCRIPTION_MIN_LENGTH, DESCRIPTION_MAX_LENGTH);
    }

    private void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }
    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

    @Override
    public List<String> getComments(){
        return new ArrayList<>(comments);
    };
    @Override
    public void addComment(Comment comment){
        comments.add(comment.toString());
    }

    @Override
    public void removeComment(Comment comment){
        if (comments.contains(comment.toString())) {
            comments.remove(comment.toString());
        } else {
            throw new IllegalArgumentException("Comment is not exist!");
        }
    }
    public void logEvent(EventLogImpl event) {
        history.add(event.toString());
    }

    public String viewInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Title: %s%n" +
                "Description: %s%n" +
                "Comments: %n", title, description));

        for (String comment : comments) {
          sb.append(comment);
        }
        return sb.toString();
    }
    public String getActivity() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Task: %s%n", getTitle()));
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
    public abstract void revertStatus();

    public abstract void advanceStatus();
}
