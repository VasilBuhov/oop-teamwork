package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class CreateNewBoardCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private final TaskManagementRepositoryImpl repository;

    public CreateNewBoardCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your team name");
        String teamName = scanner.nextLine();

        if (repository.getTeams().stream().anyMatch(team -> team.getName().contains(teamName))) {
            System.out.println("Please enter board name ");
            String name = scanner.nextLine();
            parameters.add(name);
            ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
            Board createdBoard = repository.createBoard(name);
            repository.findTeamByName(teamName).addBoard(createdBoard);
            return String.format("Board with name %s was created", createdBoard.getName());
        }
         return   "The team does not exist";

    }
}


