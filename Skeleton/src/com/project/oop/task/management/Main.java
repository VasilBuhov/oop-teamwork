package com.project.oop.task.management;

import com.project.oop.task.management.core.TaskManagementEngineImpl;

public class Main {
    public static void main(String[] args) {

        TaskManagementEngineImpl engine = new TaskManagementEngineImpl();
        engine.start();

    }
}
