package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.models.contracts.Team;

import java.util.ArrayList;
import java.util.Collection;
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

        List<Collection<Board>> teamCollections = repository.getTeams().stream()
                .map(Team::getBoards)
                .collect(Collectors.toList());


        List<Board> allBoards = teamCollections.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());


        StringBuilder result = new StringBuilder();
        allBoards.forEach(board -> {
            result.append(String.format("%n%s:%n", board.getName()));
            List<Task> tasks = board.getTasks();
            if (tasks.isEmpty()) {
                result.append("\tNo tasks found");
            } else {
                tasks.stream()
                        .map(Task::getTitle)
                        .forEach(title -> result.append(String.format("\tTitle- %s%n", title)));
            }
        });

        return result.toString();
    }
}