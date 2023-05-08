package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.commands.creation.CreateNewFeedbackCommand;
import com.project.oop.task.management.commands.creation.CreateNewPersonCommand;
import com.project.oop.task.management.commands.creation.CreateNewTeamCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;

public class UnassignTaskCommandTests {
    private Command command;
    private Command createTeam;
    private Command createTask;
    private Command createBoard;
    private Command createPerson;
    private Command addPersonToTeam;
    private TaskManagementRepositoryImpl repository;

    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.createTeam = new CreateNewTeamCommand(repository);
        this.command = new AssignTaskCommand(repository);
        this.createBoard = new CreateNewBoardCommand(repository);
        this.createPerson = new CreateNewPersonCommand(repository);
        this.addPersonToTeam = new AddPersonToTeamCommand(repository);
        this.createTask = new CreateNewFeedbackCommand(repository);
    }
}
