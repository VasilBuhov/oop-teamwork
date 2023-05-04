package com.project.oop.task.management.models.contracts;

import java.util.List;

public interface Member extends Loggable, Printable{
    String getName();
    List<Task> getTasks();
    void addTask(Task task);
    void removeTask(Task task);
}
