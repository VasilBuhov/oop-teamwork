package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.utils.MessageHelper;

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
        MessageHelper.printPromptMessage("team name the board belongs to:");
        boolean teamNameIsValid = false;

        while (!teamNameIsValid) {
            teamName = scanner.nextLine();
            repository.isItCancel(teamName, MessageHelper.INVALID_INPUT);
            try {
                repository.checkForTeam(teamName);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                teamName = "";
            }

            if (!teamName.equals("")) {
                teamNameIsValid = true;
            }
        }

        MessageHelper.printPromptMessage("board name");
        boolean boardNameIsValid = false;

        while (!boardNameIsValid) {
            boardName = scanner.nextLine();
            repository.isItCancel(boardName, MessageHelper.INVALID_INPUT);
            try {
                repository.checkForBoard(teamName, boardName);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                boardName = "";
            }

            if (!boardName.equals("")) {
                boardNameIsValid = true;
                board = repository.findBoardByName(boardName, teamName);

            }
        }
        return board.getActivity();
    }
}
