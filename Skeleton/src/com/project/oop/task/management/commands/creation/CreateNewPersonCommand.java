package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.TaskImpl;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class CreateNewPersonCommand implements Command {
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private String name;

    private final TaskManagementRepository repository;
    public CreateNewPersonCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute( List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter person name:");
        String personName = "";
        boolean nameIsValid = false;
        while (!nameIsValid) {
            personName = scanner.nextLine();
            try {
                TaskImpl.validateTitle(personName);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                personName = "";
            }
            if (!personName.equals("")) {
                nameIsValid = true;
                parameters.add(personName);
            }
        }

        name = parameters.get(0);
        if (repository.getMembers().stream().anyMatch(member -> member.getName().equals(name))){
            throw new IllegalArgumentException("A person with the same name already exists.");
        }
        Member createdMember = repository.createMember(name);

        return String.format("Person %s was created.", name);
    }
}
