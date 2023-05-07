package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Board;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ShowBoardActivityCommand implements Command {
    private String teamName;
    private String boardName;
    private Board board;

    private final TaskManagementRepositoryImpl repository;
    public ShowBoardActivityCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the team name the board belongs to:");
        boolean teamNameIsValid = false;

        while (!teamNameIsValid) {
            teamName = scanner.nextLine();
            if (teamName.equals("cancel")) {
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try {
                if (!repository.getTeams().stream().anyMatch(team -> team.getName().equals(teamName))) {
                    throw new IllegalArgumentException("No team with this name in the Task management system");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                teamName = "";
            }

            if (!teamName.equals("")) {
                teamNameIsValid = true;
            }
        }

        System.out.println("Please enter board name:");
        boolean boardNameIsValid = false;

        while (!boardNameIsValid) {
            boardName = scanner.nextLine();
            if (boardName.equals("cancel")) {
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try {
                if (!repository.getTeams().stream().filter(team -> team.getName().equalsIgnoreCase(teamName)).collect(Collectors.toList()).get(0)
                        .getBoards().stream().anyMatch(board -> board.getName().equalsIgnoreCase(boardName))) {
                    throw new IllegalArgumentException("No board with this name found");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                boardName = "";
            }

            if (!boardName.equals("")) {
                boardNameIsValid = true;
                board = repository.getTeams().stream().filter(team -> team.getName().equalsIgnoreCase(teamName)).collect(Collectors.toList()).get(0)
                        .getBoards().stream().filter(board -> board.getName().equalsIgnoreCase(boardName)).collect(Collectors.toList()).get(0);

            }
        }




        return board.getActivity();
    }
}
