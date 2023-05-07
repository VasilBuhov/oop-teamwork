package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AddPersonToTeamCommand implements Command {

    public static final String ENTER_PERSON_NAME_MESSAGE =
            "Please enter the name of the person or 'cancel' if you want to exit:";
    public static final String MEMBER_IS_NOT_FOUND_MESSAGE =
            "Person with this name is not found! " +
                    "Please enter a valid name or 'cancel' if you want to exit: ";
    public static final String ENTER_TEAM_NAME_MESSAGE =
            "Please enter team name or 'cancel' if you want to exit:";
    public static final String TEAM_IS_NOT_FOUNDED =
            "There is no team with this name. " +
                    "Please enter a valid team name or 'cancel' if you want to exit:";
    public static final String MEMBER_ADDED_MESSAGE =
            "Member with name: %s was added to team: %s!";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";

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

        System.out.println(ENTER_PERSON_NAME_MESSAGE);
        boolean nameIsValid = false;
        while (!nameIsValid) {
            personName = scanner.nextLine();
            if (repository.getPeople().stream().anyMatch(member -> member.getName().equals(personName))) {
                nameIsValid = true;
                parameters.add(personName);
            } else {
                repository.isItCancel(personName, INVALID_INPUT);
                System.out.println(MEMBER_IS_NOT_FOUND_MESSAGE);
            }
        }

        System.out.println(ENTER_TEAM_NAME_MESSAGE);
        boolean teamIsValid = false;
        while (!teamIsValid) {
            teamName = scanner.nextLine();
            if (repository.getTeams().stream().anyMatch(team1 -> team1.getName().equals(teamName))) {
                teamIsValid = true;
                parameters.add(teamName);
            } else {
                repository.isItCancel(teamName, INVALID_INPUT);
                System.out.println(TEAM_IS_NOT_FOUNDED);
            }
        }

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        repository.addNewPersonToTeam(personName, teamName);

        return String.format(MEMBER_ADDED_MESSAGE, personName, teamName);
    }
}
