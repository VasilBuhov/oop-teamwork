package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewPersonCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ShowPersonActivityCommandTests {
    private Command command;
    private Command createPerson;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new ShowPersonActivityCommand(repository);
        this.createPerson = new CreateNewPersonCommand(repository);
    }

    @Test
    public void execute_Should_ShowPersonActivity_When_ArgumentsAreValid() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in2);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Member: %s%n", "Valid"));
        sb.append(String.format("Activity:%n"));
        for (String event : repository.findPersonByName("Valid").getHistory()) {
            sb.append(event).append(System.lineSeparator());
        }
        sb.append(String.format("======================%n"));

        //Act, Assert
        Assertions.assertEquals(sb.toString(), command.execute(params2));
    }

    @Test
    public void execute_Should_ThrowException_When_NameEqualsCancel() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("cancel\n").getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params2));
    }

    @Test
    public void execute_Should_ThrowException_When_NameIsNotFound() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Invalid\n").getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params2));
    }
}
