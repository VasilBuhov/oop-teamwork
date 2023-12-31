package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.utils.MessageHelper;

import java.util.List;
import java.util.Scanner;

public class ShowTeamActivityCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private String name;
    private final TaskManagementRepository repository;

    public ShowTeamActivityCommand(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(MessageHelper.ENTER_TEAM_NAME_MESSAGE);
        boolean teamIsValid = false;
        while (!teamIsValid) {
            name = scanner.nextLine();
            if (repository.getTeams().stream().anyMatch(team1 -> team1.getName().equals(name))) {
                teamIsValid = true;
            } else {
                repository.isItCancel(name, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.TEAM_IS_NOT_FOUNDED);
            }
        }
        return repository.findTeamByName(name).getActivity();
    }

}
