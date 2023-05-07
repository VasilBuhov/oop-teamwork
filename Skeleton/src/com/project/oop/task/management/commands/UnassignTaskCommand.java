package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ParsingHelpers;

import java.util.List;
import java.util.Scanner;

public class UnassignTaskCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private int id;
    private String name;

    private final TaskManagementRepositoryImpl repository;

    public UnassignTaskCommand(TaskManagementRepositoryImpl repository) {
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
                    isValidId = true;
                    parameters.add(String.valueOf(id));
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
        repository.unassignTask(id, name);

        return String.format(MessageHelper.TASK_UNASSIGNED_MESSAGE, id, repository.findTaskById(id).getTitle(), name);
    }
}
