package com.project.oop.task.management.commands;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class AddPersonToTeamCommand implements Command {
    public static final String MEMBER_ADDED_MESSAGE = "Member with name: %s was added to team: %s";
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 2;
    private String personName;
    private String teamName;

    private final TaskManagementRepository repository;
    public AddPersonToTeamCommand(TaskManagementRepository taskManagementRepository) {
        this.repository = new TaskManagementRepositoryImpl();
    }

    @Override
    public String execute(List<String> parameters) {
         Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter a person name: ");
        personName = scanner.nextLine();
        parameters.add(personName);
        System.out.println("Please enter a team name: ");
        teamName = scanner.nextLine();
        parameters.add(teamName);

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        repository.addNewPersonToTeam(personName, teamName);

        return String.format(MEMBER_ADDED_MESSAGE, personName, teamName);
    }
}
