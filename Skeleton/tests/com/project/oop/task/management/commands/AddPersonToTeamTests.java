package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBugCommand;
import com.project.oop.task.management.commands.creation.CreateNewPersonCommand;
import com.project.oop.task.management.commands.creation.CreateNewTeamCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AddPersonToTeamTests {
    private Command command;
    private Command createTeam;
    private Command createPerson;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.createTeam = new CreateNewTeamCommand(repository);
        this.command = new CreateNewBugCommand(repository);
        this.createPerson = new CreateNewPersonCommand(repository);
    }

    @Test
    public void execute_Should_ThrowException_When_NameEqualsCancel() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("cancel\nTeam1").getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params2));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamEqualsCancel() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Valid\ncancel").getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command.execute(params2));
    }

    @Test
    public void execute_Should_ThrowException_When_TeamIsNotFound() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("Valid\nTeam2").getBytes());
        System.setIn(in2);


        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params2));
    }

    @Test
    public void execute_Should_ThrowException_When_NameIsNotFound() {
        //Arrange
        List<String> params = new ArrayList<>();

        InputStream in = new ByteArrayInputStream(("Valid\n").getBytes());
        System.setIn(in);
        createPerson.execute(params);

        List<String> params1 = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\n").getBytes());
        System.setIn(in1);
        createTeam.execute(params1);

        List<String> params2 = new ArrayList<>();
        InputStream in2 = new ByteArrayInputStream(("InValid\nTeam1").getBytes());
        System.setIn(in2);

        //Act, Assert
        Assertions.assertThrows(NoSuchElementException.class, () -> command.execute(params2));
    }

}
