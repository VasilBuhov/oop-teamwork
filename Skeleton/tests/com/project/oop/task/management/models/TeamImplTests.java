package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TeamImplTests {

    @Test
    public void should_ThrowException_When_NameIsShorter() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () ->
                new TeamImpl("test"));
    }

    @Test
    public void should_ThrowException_When_NameIsLonger() {
        // Arrange, Act, Assert
        assertThrows(IllegalArgumentException.class, () ->
                new TeamImpl("nikolnikolnikolnikol"));
    }


    @Test
    public void constructor_Should_CreateNewTeam_When_ParametersAreCorrect() {
        //Arrange
        TeamImpl user = initializeTestTeam();

        //Act
        assertEquals("valid", initializeTestTeam().getName());
    }

    @Test
    public void getTasks_Should_ReturnCopyOfTheCollection() {
        // Arrange
        TeamImpl team = initializeTestTeam();
        BoardImpl board = initializeTestBoard();

        // Act
        team.getBoards().add(board);

        // Assert
        assertEquals(0, team.getBoards().size());
    }

    @Test
    public void getMembers_Should_ReturnCopyOfTheCollection() {
        // Arrange
        TeamImpl team = initializeTestTeam();
        MemberImpl member = initializeTestMember();

        // Act
        team.getMembers().add(member);

        // Assert
        assertEquals(0, team.getMembers().size());
    }

    @Test
    public void addBoards_Should_AddBoardToTheCollection() {
        // Arrange
        TeamImpl team = initializeTestTeam();
        BoardImpl board = initializeTestBoard();

        //Act
        team.addBoard(board);

        // Assert
        assertEquals(1, team.getBoards().size());
    }

    @Test
    public void removeBoard_Should_RemoveBoardFromTheCollection() {
        // Arrange
        TeamImpl team = initializeTestTeam();
        BoardImpl board = initializeTestBoard();

        //Act
        team.addBoard(board);
        team.removeBoard(board);

        // Assert
        assertEquals(0, team.getBoards().size());
    }

    @Test
    public void addMember_Should_AddMemberToTheCollection() {
        // Arrange
        TeamImpl team = initializeTestTeam();
        BoardImpl board = initializeTestBoard();

        //Act
        team.addBoard(board);

        // Assert
        assertEquals(1, team.getBoards().size());
    }

    @Test
    public void removeMember_Should_RemoveMemberFromTheCollection() {
        // Arrange
        TeamImpl team = initializeTestTeam();
        MemberImpl member = initializeTestMember();

        //Act
        team.addMember(member);
        team.removeMember(member);

        // Assert
        assertEquals(0, team.getMembers().size());
    }

    @Test
    public void getAsString_Should_ReturnFormattedString() {
        // Arrange
        TeamImpl team = initializeTestTeam();
        BoardImpl board = initializeTestBoard();
        MemberImpl member = initializeTestMember();

        //Act
        team.addBoard(board);
        team.addMember(member);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("======================%n"));
        sb.append(String.format("Team: %s%n", team.getName()));
        sb.append(String.format("---------------------%n"));
        sb.append(String.format("MEMBERS:%n"));
        if (!team.getMembers().isEmpty()) {
            int counter = 1;
            for (Member member1 : team.getMembers()) {
                sb.append(counter).append(". ").append(member.getAsString());
            }
        } else {
            sb.append(String.format("There are no members in this team.%n"));
        }
        sb.append(String.format("---------------------%n"));
        sb.append(String.format("BOARDS:%n"));
        if (!team.getBoards().isEmpty()) {
            int counter = 1;
            for (Board board1 : team.getBoards()) {
                sb.append(counter).append(". ").append(board.getAsString());
            }
        } else {
            sb.append(String.format("There are no boards in this team.%n"));
        }
        sb.append("======================");

        // Assert
        assertEquals(sb.toString(), team.getAsString());
    }

    @Test
    public void getActivity_Should_ReturnFormattedString() {
        //Arrange
        TeamImpl team = initializeTestTeam();
        BoardImpl board = initializeTestBoard();
        MemberImpl member = initializeTestMember();
        Task task = initializeTestStory();

        //Act
        team.addBoard(board);
        team.addMember(member);
        team.getBoards().get(0).addTask(task);

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("---------------------%n"));
        sb.append(String.format("Team: %s%n", team.getName()));
        sb.append(String.format("---------------------%n")).append(System.lineSeparator());
        sb.append(String.format("*********************%n"));
        sb.append(String.format("Members activity:%n"));
        for (Member member1 : team.getMembers()) {
            sb.append(member.getActivity());
        }
        sb.append(String.format("*********************%n")).append(System.lineSeparator());

        sb.append(String.format("*********************%n"));
        sb.append(String.format("Boards activity:%n"));
        for (Board board1 : team.getBoards()) {
            sb.append(board.getActivity());

        }
        sb.append(String.format("*********************%n")).append(System.lineSeparator());

        sb.append(String.format("*********************%n"));
        sb.append(String.format("Tasks activity:%n"));
        for (Board board1 : team.getBoards()) {
            for (Task task1 : board.getTasks()) {
                sb.append(task.getActivity());
            }
        } sb.append(String.format("*********************%n"));

        // Assert
        assertEquals(sb.toString(), team.getActivity());
    }

    public static TeamImpl initializeTestTeam() {
        return new TeamImpl("valid");
    }
    public static BoardImpl initializeTestBoard() {
        return new BoardImpl("valid");
    }
    public static MemberImpl initializeTestMember() {
        return new MemberImpl("valid");
    }
    public static Task initializeTestStory() {
        return new StoryImpl(
                1,
                "valid title",
                "valid description",
                Priority.LOW,
                Size.LARGE,
                "Nikol");
    }
}
