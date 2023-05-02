package com.project.oop.task.management.models.contracts;

import java.util.List;

public interface Board extends Loggable, Printable{
    String getName();
    List<Task> getTasks();
    List<String> getHistory();
    void addTask(Task task);
    void removeTask(Task task);
    String getActivity();
}
