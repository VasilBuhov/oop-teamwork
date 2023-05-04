package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.TeamImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ShowTeamActivityCommand implements Command {
    public static final String ENTER_TEAM_NAME_MESSAGE =
            "Please enter your team name or 'cancel' if you want to exit:";
    public static final String TEAM_IS_NOT_FOUNDED =
            "There is no team with this name. " +
                    "Please enter a valid team name or 'cancel' if you want to exit:";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private String name;
    private final TaskManagementRepository repository;
    public ShowTeamActivityCommand(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ENTER_TEAM_NAME_MESSAGE);
        boolean teamIsValid = false;
        while (!teamIsValid) {
            name = scanner.nextLine();
            if (repository.getTeams().stream().anyMatch(team1 -> team1.getName().equals(name))) {
                teamIsValid = true;
                parameters.add(name);
            } else {
                repository.isItCancel(name, INVALID_INPUT);
                System.out.println(TEAM_IS_NOT_FOUNDED);
            }
        }

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
       return repository.findTeamByName(name).getActivity();
    }

}
