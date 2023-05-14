package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.MemberImpl;
import com.project.oop.task.management.models.contracts.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CreateNewPersonCommandTests {
    private Command command;
    private TaskManagementRepository repository;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new CreateNewPersonCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_NameLengthNotValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        String name = "T";

        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_InputIsEqualToCancel() {
        //Arrange
        List<String> params = new ArrayList<>();
        String name = "cancel";

        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_NameAlreadyExists() {
        //Arrange
        String name = "IvanIvanov";
        Member person = new MemberImpl(name);
        repository.createNewPerson(person.getName());
        List<String> params = new ArrayList<>();
        params.add(person.getName());
        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_CreatePerson_When_NameIsValid() {
        //Arrange
        List<String> params = new ArrayList<>();
        String name = "Margarita";
        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        //Act
        command.execute(params);

        //Assert
        Assertions.assertEquals(1, repository.getAllPeople().size());
    }
}
