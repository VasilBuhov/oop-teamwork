package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.CommentImpl;
import com.project.oop.task.management.models.contracts.Comment;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.utils.ParsingHelpers;

import java.util.List;
import java.util.Scanner;

public class AddCommentToTaskCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    public static final String ENTER_ID_MESSAGE =
            "Please enter a valid ID or 'cancel' if you want to exit:";
    public static final String TASK_NOT_FOUND_MESSAGE = "Task with id: %d is not found! " +
            "Please enter a valid id or 'cancel' if you want to exit:";
    public static final String ENTER_PERSON_NAME_MESSAGE =
            "Please enter your name or 'cancel' if you want to exit:";
    public static final String MEMBER_IS_NOT_FOUND_MESSAGE =
            "Person with this name is not found! " +
                    "Please enter a valid assignee or 'cancel' if you want to exit: ";
    public static final String ENTER_COMMENT_MESSAGE =
            "Please enter the comment you would like to add or 'cancel' if you want to exit:";
    public static final String PARSING_ERROR_MESSAGE =
            "Invalid input, must be a number! Please try again or enter 'cancel' if you want to exit:";
    public static final String COMMENT_ADDED_MESSAGE = "%sWas added to task with ID: %d";

    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";

    private int id;
    private String comment;
    private String author;

    private final TaskManagementRepositoryImpl repository;

    public AddCommentToTaskCommand(TaskManagementRepositoryImpl repository) {
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
            author = scanner.nextLine();
            if (repository.getMembers().stream().anyMatch(member -> member.getName().equals(author))) {
                nameIsValid = true;
                parameters.add(author);
            } else {
                repository.isItCancel(author, INVALID_INPUT);
                System.out.println(MEMBER_IS_NOT_FOUND_MESSAGE);
            }
        }

        System.out.println(ENTER_COMMENT_MESSAGE);
        comment = scanner.nextLine();
        if (comment.equalsIgnoreCase("cancel")) {
            repository.isItCancel(author, INVALID_INPUT);
        } else {
            parameters.add(comment);
        }

        Comment comment1 = new CommentImpl(comment, author);
        repository.addCommentToTask(id, comment1);

        return String.format(COMMENT_ADDED_MESSAGE, comment1.toString(), id);
    }
}
