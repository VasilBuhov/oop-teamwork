package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ShowPersonActivityCommand implements Command {
    public static final String NO_SUCH_MEMBER_FOUNDED = "No such member founded in application!";
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;

    private final TaskManagementRepository repository;
    public ShowPersonActivityCommand(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
         Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the name of a person, which activity you would like to see: ");
        String name = scanner.nextLine();
        parameters.add(name);

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        for (Member member : repository.getMembers()) {
            if(member.getName().equals(name)) {
               return member.getAsString();
            }
        }
        throw new IllegalArgumentException(NO_SUCH_MEMBER_FOUNDED);
    }
}
