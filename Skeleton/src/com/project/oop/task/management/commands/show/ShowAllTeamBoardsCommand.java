package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Task;

import java.util.List;
import java.util.stream.Collectors;

public class ShowAllTeamBoardsCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;

    private final TaskManagementRepositoryImpl repository;
    public ShowAllTeamBoardsCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }
//      teams.stream()
//             .flatMap(team -> team.getBoards().stream()
//              .map(board -> team.getName() + ": " + board.getName()))
//             .forEach(System.out::println);
    @Override
    public String execute(List<String> parameters) {

        String result =repository.getTeams().stream()
                .flatMap(team -> team.getBoards().stream()
                        .map(board -> team.getName() + ": " +
                                board.getName()+
                                board.getTasks() .
                                        stream().
                                        map(task -> task.getActivity())))


                .collect(Collectors.joining("\n"));
        return "The team boards are: \n" +result;

    }
}
