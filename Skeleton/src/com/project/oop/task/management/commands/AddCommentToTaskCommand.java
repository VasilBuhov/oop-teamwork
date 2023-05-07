package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.CommentImpl;
import com.project.oop.task.management.models.contracts.Comment;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ParsingHelpers;

import java.util.List;
import java.util.Scanner;

public class AddCommentToTaskCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
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
            author = scanner.nextLine();
            if (repository.getMembers().stream().anyMatch(member -> member.getName().equals(author))) {
                nameIsValid = true;
                parameters.add(author);
            } else {
                repository.isItCancel(author, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.PERSON_IS_NOT_FOUND_MESSAGE);
            }
        }

        System.out.println(MessageHelper.ENTER_COMMENT_MESSAGE);
        comment = scanner.nextLine();
        if (comment.equalsIgnoreCase("cancel")) {
            repository.isItCancel(author, MessageHelper.INVALID_INPUT);
        } else {
            parameters.add(comment);
        }

        Comment comment1 = new CommentImpl(comment, author);
        repository.addCommentToTask(id, comment1);

        return String.format(MessageHelper.COMMENT_ADDED_MESSAGE, comment1.toString(), id);
    }
}
