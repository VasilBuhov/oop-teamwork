package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ShowPersonActivityCommand implements Command {
    public static final String ENTER_PERSON_NAME_MESSAGE =
            "Please enter the name of the person or 'cancel' if you want to exit:";
    public static final String MEMBER_IS_NOT_FOUND_MESSAGE =
            "Person with this name is not found! " +
                    "Please enter a valid assignee or 'cancel' if you want to exit: ";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private String personName;
    private final TaskManagementRepository repository;
    public ShowPersonActivityCommand(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
         Scanner scanner = new Scanner(System.in);

        System.out.println(ENTER_PERSON_NAME_MESSAGE);
        boolean nameIsValid = false;
        while (!nameIsValid) {
            personName = scanner.nextLine();
            if (repository.getMembers().stream().anyMatch(member -> member.getName().equals(personName))) {
                nameIsValid = true;
                parameters.add(personName);
            } else {
                repository.isItCancel(personName, INVALID_INPUT);
                System.out.println(MEMBER_IS_NOT_FOUND_MESSAGE);
            }
        }

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);
        return repository.findMemberByName(personName).getActivity();
    }
}
