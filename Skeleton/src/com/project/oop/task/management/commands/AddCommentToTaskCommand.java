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

        MessageHelper.printPromptMessage("task ID");
        boolean isValidId = false;
        while (!isValidId) {
            String input = scanner.nextLine();
            repository.isItCancel(input, MessageHelper.INVALID_INPUT);
            try {
                id = ParsingHelpers.tryParseInt(input, MessageHelper.PARSING_ERROR_MESSAGE);
                if (repository.isItValidTaskID(id)) {
                    isValidId = true;
                } else {
                    System.out.printf((MessageHelper.TASK_NOT_FOUND_MESSAGE) + "%n", id);
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        MessageHelper.printPromptMessage("author");
        boolean nameIsValid = false;
        while (!nameIsValid) {
            author = scanner.nextLine();
            repository.isItCancel(author, MessageHelper.INVALID_INPUT);
            if (repository.isItMember(author)) {
                nameIsValid = true;
            } else {
                System.out.println(MessageHelper.PERSON_IS_NOT_FOUND_MESSAGE);
            }
        }

        MessageHelper.printPromptMessage("comment");
        comment = scanner.nextLine();
        repository.isItCancel(comment, MessageHelper.INVALID_INPUT);

        Comment comment1 = new CommentImpl(comment, author);
        repository.addCommentToTask(id, comment1);

        return String.format(MessageHelper.COMMENT_ADDED_MESSAGE, comment1.toString(), id);
    }


}
