package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewPersonCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class CreateNewStoryCommandTests {
    private Command command;
    private TaskManagementRepository repository;

    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new CreateNewPersonCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_InputIsEqualToCancel(){
        List<String> params = new ArrayList<>();
        String name = "cancel";

        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }
}
