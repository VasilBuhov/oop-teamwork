package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.commands.creation.CreateNewBoardCommand;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShowAllTeamsCommandTests {

    private TaskManagementRepositoryImpl repository;
    private Command command1;
    private Command command2;
    private Command command3;

    @BeforeEach
    public void before() {
        this.repository = new TaskManagementRepositoryImpl();
        this.command1 = new ShowAllTeamsCommand(repository);
        this.command2 = new CreateNewBoardCommand(repository);
    }

    @Test
    public void should_ThrowException_WhenNoTeamsCreatedInSystem() {
        //Arrange
        List<String> params = new ArrayList<>();

        //Act, Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> command1.execute(params));
    }

    @Test
    public void should_DisplayTeamInfo() {

        //Arrange
        repository.createNewTeam("Team1");
        repository.createNewTeam("Team2");
        repository.createNewTeam("Team3");
        repository.createNewPerson("Margarita");
        repository.createNewPerson("Ivaylo");
        repository.createNewPerson("George");
        repository.createNewPerson("Monika");
        repository.addNewPersonToTeam("Margarita", "Team1");
        repository.addNewPersonToTeam("Ivaylo", "Team1");
        repository.addNewPersonToTeam("George", "Team2");
        repository.addNewPersonToTeam("Monika", "Team2");

        List<String> params = new ArrayList<>();
        InputStream in1 = new ByteArrayInputStream(("Team1\nBoard1\n").getBytes());
        System.setIn(in1);
        command2.execute(params);
        params.remove(0);

        InputStream in2 = new ByteArrayInputStream(("Team2\nBoard2\n").getBytes());
        System.setIn(in2);
        command2.execute(params);
        params.remove(0);

        String expectedResult = String.format("======================%n" +
                "Team: Team1%n" +
                "---------------------%n" +
                "MEMBERS:%n" +
                "1. ======================%n" +
                "Member: Margarita%n" +
                "Tasks:%n" +
                "Margarita still does not have any task%n" +
                "======================%n" +
                "1. ======================%n" +
                "Member: Ivaylo%n" +
                "Tasks:%n" +
                "Ivaylo still does not have any task%n" +
                "======================%n" +
                "---------------------%n" +
                "BOARDS:%n" +
                "1. *********************%n" +
                "Board: Board1%n" +
                "*********************%n" +
                "============================================%n" +
                "Team: Team2%n" +
                "---------------------%n" +
                "MEMBERS:%n" +
                "1. ======================%n" +
                "Member: George%n" +
                "Tasks:%n" +
                "George still does not have any task%n" +
                "======================%n" +
                "1. ======================%n" +
                "Member: Monika%n" +
                "Tasks:%n" +
                "Monika still does not have any task%n" +
                "======================%n" +
                "---------------------%n" +
                "BOARDS:%n" +
                "1. *********************%n" +
                "Board: Board2%n" +
                "*********************%n" +
                "============================================%n" +
                "Team: Team3%n" +
                "---------------------%n" +
                "MEMBERS:%n" +
                "There are no members in this team.%n" +
                "---------------------%n" +
                "BOARDS:%n" +
                "There are no boards in this team.%n" +
                "======================"
        );

        //Act
        String actualResult = command1.execute(params);

        //Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
