package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.utils.MessageHelper;

import java.util.List;
import java.util.Scanner;

public class ShowPersonActivityCommand implements Command {

    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private String personName;
    private final TaskManagementRepository repository;

    public ShowPersonActivityCommand(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(MessageHelper.ENTER_PERSON_NAME_MESSAGE);
        boolean nameIsValid = false;
        while (!nameIsValid) {
            personName = scanner.nextLine();
            if (repository.getAllPeople().stream().anyMatch(person -> person.getName().equals(personName))) {
                nameIsValid = true;
            } else {
                repository.isItCancel(personName, MessageHelper.INVALID_INPUT);
                System.out.println(MessageHelper.PERSON_IS_NOT_FOUND_MESSAGE);
            }
        }
        return repository.findPersonByName(personName).getActivity();
    }
}
