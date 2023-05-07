package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddPersonToTeamCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private String personName;
    private String teamName;

    private final TaskManagementRepository repository;

    public AddPersonToTeamCommand(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(MessageHelper.ENTER_PERSON_NAME_MESSAGE);
        boolean nameIsValid = false;
        while (!nameIsValid) {
            personName = scanner.nextLine();
            if (repository.getPeople().stream().anyMatch(member -> member.getName().equals(personName))) {
                nameIsValid = true;
                parameters.add(personName);
            } else {
                repository.isItCancel(personName, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.PERSON_IS_NOT_FOUND_MESSAGE);
            }
        }

        System.out.println(MessageHelper.ENTER_TEAM_NAME_MESSAGE);
        boolean teamIsValid = false;
        while (!teamIsValid) {
            teamName = scanner.nextLine();
            if (repository.getTeams().stream().anyMatch(team1 -> team1.getName().equals(teamName))) {
                teamIsValid = true;
                parameters.add(teamName);
            } else {
                repository.isItCancel(teamName, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.TEAM_IS_NOT_FOUNDED);
            }
        }

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        repository.addNewPersonToTeam(personName, teamName);

        return String.format(MessageHelper.MEMBER_ADDED_MESSAGE, personName, teamName);
    }
}
