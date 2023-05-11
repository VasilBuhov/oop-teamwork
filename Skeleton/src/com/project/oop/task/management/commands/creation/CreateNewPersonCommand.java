package com.project.oop.task.management.commands.creation;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.MemberImpl;
import com.project.oop.task.management.models.TaskImpl;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.utils.MessageHelper;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class CreateNewPersonCommand implements Command {
    private String name;
    private final TaskManagementRepository repository;
    public CreateNewPersonCommand(TaskManagementRepository taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute( List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        MessageHelper.printPromptMessage("person name");
        boolean nameIsValid = false;
        while (!nameIsValid) {
            name = scanner.nextLine();
            repository.isItCancel(name, MessageHelper.INVALID_INPUT);
            try {
                MemberImpl.validateName(name);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                name = "";
            }

            if (!name.equals("")) {
                try{
                    repository.createNewPerson(name);
                }catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
                    name = "";
                }
            }
            if (!name.equals("")){
                nameIsValid = true;
            }
        }

        return String.format(MessageHelper.PERSON_CREATED, name);
    }
}
