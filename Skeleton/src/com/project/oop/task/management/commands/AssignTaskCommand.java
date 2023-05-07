package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ParsingHelpers;

import java.util.List;
import java.util.Scanner;

public class AssignTaskCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String ENTER_ID_MESSAGE =
            "Please enter a valid ID or 'cancel' if you want to exit:";
    public static final String TASK_NOT_FOUND_MESSAGE = "Task with id: %d is not found! " +
            "Please enter a valid id or 'cancel' if you want to exit:";
    public static final String ENTER_PERSON_NAME_MESSAGE =
            "Please the name of the person or 'cancel' if you want to exit:";
    public static final String MEMBER_IS_NOT_FOUND_MESSAGE =
            "Person with this name is not found! " +
                    "Please enter a valid assignee or 'cancel' if you want to exit: ";
    public static final String PARSING_ERROR_MESSAGE =
            "Invalid input, must be a number! Please try again or enter 'cancel' if you want to exit:";
    public static final String TASK_ALREADY_ASSIGNED =
            "Task with id: %d is already assigned. Try with another task ID or enter 'cancel' if you want to exit:";
    public static final String TASK_ASSIGNED_MESSAGE =
            "Task with id: %d and title: %s is assign to %s.";

    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";
    private int id;
    private String name;

    private final TaskManagementRepositoryImpl repository;

    public AssignTaskCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(MessageHelper.ENTER_TASK_ID_MESSAGE);
        boolean isValidId = false;
        while (!isValidId) {
            String input = scanner.nextLine();
            repository.isItCancel(input, MessageHelper.INVALID_INPUT);
            try {
                id = ParsingHelpers.tryParseInt(input, MessageHelper.PARSING_ERROR_MESSAGE);
                if (repository.getTasks().stream().anyMatch(task -> task.getId() == id)) {
                    if (repository.getAssignedTasks().contains(repository.findTaskById(id))) {
                        System.out.printf((MessageHelper.TASK_ALREADY_ASSIGNED) + "%n", id);
                    } else {
                        isValidId = true;
                        parameters.add(String.valueOf(id));
                    }
                } else {
                    System.out.printf((MessageHelper.TASK_NOT_FOUND_MESSAGE) + "%n", id);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(MessageHelper.ENTER_PERSON_NAME_MESSAGE);
        boolean nameIsValid = false;
        while (!nameIsValid) {
            name = scanner.nextLine();
            if (repository.getMembers().stream().anyMatch(member -> member.getName().equals(name))) {
                nameIsValid = true;
                parameters.add(name);
            } else {
                repository.isItCancel(name, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.PERSON_IS_NOT_FOUND_MESSAGE);
            }
        }
        repository.assignTask(id, name);

        return String.format(MessageHelper.TASK_ASSIGNED_MESSAGE, id, repository.findTaskById(id).getTitle(), name);
    }
}
