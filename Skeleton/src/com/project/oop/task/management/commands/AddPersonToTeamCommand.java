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

        MessageHelper.printPromptMessage("person name");
        boolean nameIsValid = false;
        while (!nameIsValid) {
            personName = scanner.nextLine();
            if (repository.isItNotMember(personName)) {
                nameIsValid = true;
                parameters.add(personName);
            } else {
                repository.isItCancel(personName, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.PERSON_IS_NOT_FOUND_MESSAGE);
            }
        }

        MessageHelper.printPromptMessage("team name");
        boolean teamIsValid = false;
        while (!teamIsValid) {
            teamName = scanner.nextLine();
            if (repository.isTeamAlreadyCreated(teamName)) {
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
