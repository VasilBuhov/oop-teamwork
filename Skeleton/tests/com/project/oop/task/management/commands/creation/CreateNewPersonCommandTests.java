package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewPersonCommand;
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
import java.util.Scanner;

public class CreateNewPersonCommandTests {
    private Command command;
    private TaskManagementRepository repository;

    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new CreateNewPersonCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_NameLengthNotValid(){
        List<String> params = new ArrayList<>();
        String name = "T";

        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_InputIsEqualToCancel(){
        List<String> params = new ArrayList<>();
        String name = "cancel";

        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_ThrowException_When_NameAlreadyExists(){
        Member person = new MemberImpl("IvanIvanov");
        repository.createNewPerson(person.getName());
        List<String> params = new ArrayList<>();
        params.add(person.getName());
        String name = "IvanIvanov";
        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);

        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params));
    }

    @Test
    public void execute_Should_CreatePerson_When_NameIsValid(){
        List<String> params = new ArrayList<>();
        String name = "Margarita";
        InputStream in = new ByteArrayInputStream(name.getBytes());
        System.setIn(in);
        command.execute(params);

        Assertions.assertEquals(1, repository.getPeople().size());
    }
}
