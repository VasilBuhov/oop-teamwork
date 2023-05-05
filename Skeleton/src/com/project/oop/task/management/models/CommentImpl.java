package com.project.oop.task.management.models;
import com.project.oop.task.management.models.contracts.Comment;

public class CommentImpl implements Comment{
    private String content;
    private String author;

    public CommentImpl(String content, String author) {
        setContent(content);
        setAuthor(author);
    }

    private void setAuthor(String author) {
        this.author = author;
    }
    @Override
    public String getAuthor() {
        return this.author;
    }
    private void setContent(String content) {
        this.content = content;
    }
    @Override
    public String getContent() {
        return this.content;
    }

    public String toString() {
        return  String.format("***********%n") +
                String.format("Comment: " + getContent() + "%n") +
                String.format("Author: %s%n", getAuthor()) +
                String.format("***********%n");
    }
}
