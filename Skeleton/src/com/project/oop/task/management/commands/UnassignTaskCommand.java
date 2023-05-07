package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.utils.ParsingHelpers;

import java.util.List;
import java.util.Scanner;

public class UnassignTaskCommand implements Command {
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
    public static final String TASK_UNASSIGNED_MESSAGE = "Task with id: %d and title: %s is unassigned from %s.";

    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";

    private int id;
    private String name;

    private final TaskManagementRepositoryImpl repository;
    public UnassignTaskCommand(TaskManagementRepositoryImpl repository) {
        this.repository = repository;
    }


    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ENTER_ID_MESSAGE);
        boolean isValidId = false;
        while (!isValidId) {
            String input = scanner.nextLine();
            repository.isItCancel(input, INVALID_INPUT);
            try {
                id = ParsingHelpers.tryParseInt(input, PARSING_ERROR_MESSAGE);
                if (repository.getTasks().stream().anyMatch(task -> task.getId() == id)) {
                    isValidId = true;
                    parameters.add(String.valueOf(id));
                } else {
                    System.out.printf((TASK_NOT_FOUND_MESSAGE) + "%n", id);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println(ENTER_PERSON_NAME_MESSAGE);
        boolean nameIsValid = false;
        while (!nameIsValid) {
            name = scanner.nextLine();
            if (repository.getMembers().stream().anyMatch(member -> member.getName().equals(name))) {
                nameIsValid = true;
                parameters.add(name);
            } else {
                repository.isItCancel(name, INVALID_INPUT);
                System.out.println(MEMBER_IS_NOT_FOUND_MESSAGE);
            }
        }
        repository.unassignTask(id, name);

        return String.format(TASK_UNASSIGNED_MESSAGE, id, repository.findTaskById(id).getTitle(), name);
    }
}
