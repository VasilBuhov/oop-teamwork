package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.BoardImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.utils.ValidationHelper;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class CreateNewBoardCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    public static final String INVALID_INPUT = "Invalid input! Enter a new command, please:";
    public static final String TEAM_IS_NOT_FOUNDED =
            "There is no team with this name. Please enter a valid team name:";
    public static final String INVALID_BOARD_NAME = "Invalid Board name! Please enter a valid board name or cancel";
    String teamName = "";
    String name = "";
    private final TaskManagementRepositoryImpl repository;

    public CreateNewBoardCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    // boolean teamIsValid = false;
    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your team name or cancel if you want to exit");
        boolean teamIsValid = false;
        while (!teamIsValid) {
            String team = scanner.nextLine();
            teamName = team;
            if (repository.getTeams().
                    stream().
                    anyMatch(team1 -> team1.getName().equals(teamName))) {
                teamIsValid = true;
                break;
            } else {
                if (team.equals("cancel")) {
                    throw new IllegalArgumentException(INVALID_INPUT);
                }
            }
            System.out.println(TEAM_IS_NOT_FOUNDED);
        }
        System.out.println("Please enter board name or 'cancel' if you want to exit");
        boolean validName = false;
        while (!validName) {
            name = scanner.nextLine();
            try {
                ValidationHelper.ValidateStringLength(name,
                        BoardImpl.NAME_MIN_LENGTH,
                        BoardImpl.NAME_MAX_LENGTH);
            } catch (IllegalArgumentException e) {
                System.out.println(INVALID_BOARD_NAME);
                name = "";
            }
            if (name.equals("cancel")) {
                throw new IllegalArgumentException(INVALID_INPUT);
            }
            if (!name.equals("")) {
                validName = true;
                break;
            }
        }
        parameters.add(name);
        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        Board createdBoard = repository.createBoard(name);
        repository.findTeamByName(teamName).addBoard(createdBoard);
        return String.format("Board with name %s was created", createdBoard.getName());
    }
}



