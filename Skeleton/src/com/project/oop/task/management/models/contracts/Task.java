package com.project.oop.task.management.models.contracts;

public interface Task extends Commentable, Loggable, Printable{

    int getId();
    String getTitle();
    String getDescription();
    String getStatus();
    void addComment(Comment comment);
    void removeComment(Comment comment);
    String viewInfo();
}
