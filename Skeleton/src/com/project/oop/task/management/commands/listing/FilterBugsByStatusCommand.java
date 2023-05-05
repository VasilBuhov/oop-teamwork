package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.utils.ListingHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FilterBugsByStatusCommand implements Command {
    private List<Bug> filteredBugs = new ArrayList<>();
    private String status;

    private final TaskManagementRepositoryImpl repository;
    public FilterBugsByStatusCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter status to filter by:");
        boolean statusIsValid = false;

        while (!statusIsValid) {
            status = scanner.nextLine();
            if (status.equals("cancel")) {
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try {
                if (!status.equalsIgnoreCase("Active")
                        && (!status.equalsIgnoreCase("Fixed"))){
                    throw new IllegalArgumentException("Status is not valid. Please choose between Active and Fixed or cancel if you want to exit:");
                }
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                status = "";
            }

            if (!status.equals("")) {
                statusIsValid = true;
                filteredBugs = repository.getBugs().stream().filter(bug -> bug.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
                if (filteredBugs.size() == 0) {
                    return String.format("No bugs with this status");
                }
            }
        }

        return ListingHelpers.bugsToString(filteredBugs);
    }
}
