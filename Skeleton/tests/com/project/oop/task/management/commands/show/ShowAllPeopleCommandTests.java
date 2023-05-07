package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewTeamCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ShowAllPeopleCommandTests {
    private Command command;
    private TaskManagementRepositoryImpl repository;


    @BeforeEach
    public void before(){
        this.repository = new TaskManagementRepositoryImpl();
        this.command = new CreateNewTeamCommand(repository);
    }

    @Test
    public void should_ShowCategory_When_ArgumentsAreValid() {
        // Arrange

        // Act, Assert
    }


}
