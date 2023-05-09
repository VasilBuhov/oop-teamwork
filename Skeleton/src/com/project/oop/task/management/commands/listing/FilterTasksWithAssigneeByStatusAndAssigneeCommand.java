package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.utils.ListingHelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FilterTasksWithAssigneeByStatusAndAssigneeCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;
    private String assigneeName;
    private String status;
    private List<Bug> filteredBugs = new ArrayList<>();
    private List<Story> filteredStories = new ArrayList<>();
    private final TaskManagementRepositoryImpl repository;

    public FilterTasksWithAssigneeByStatusAndAssigneeCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = new TaskManagementRepositoryImpl();
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("Please enter assignee name:");
        boolean nameIsValid = false;

        while (!nameIsValid) {
            assigneeName = scanner.nextLine();
            if (assigneeName.equals("cancel")) {
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try {
                if (!repository.getMembers().stream().anyMatch(member -> member.getName().equals(assigneeName))) {
                    throw new IllegalArgumentException("No assignee with this name");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                assigneeName = "";
            }
            if (!assigneeName.equals("")) {
                nameIsValid = true;
            }
        }
        System.out.println("Please enter status to filter by:");
        boolean statusIsValid = false;

        while (!statusIsValid) {
            status = scanner.nextLine();
            if (status.equals("cancel")) {
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try {
                if (!status.equalsIgnoreCase("NotDone")
                        && (!status.equalsIgnoreCase("InProgress"))
                        && (!status.equalsIgnoreCase("Done"))) {
                    throw new IllegalArgumentException("Status is not valid. Please choose between NotDone, InProgress and Done or cancel if you want to exit:");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                status = "";
            }
            if (!status.equals("")) {
                statusIsValid = true;
            }
        }

        filteredBugs
                = repository.getBugs().stream().filter(bug -> bug.getAssignee().
                equals(assigneeName) && bug.getStatus().equals(status)).collect(Collectors.toList());
        filteredStories
                = repository.getStories().stream().filter(story -> story.getAssignee().
                equals(assigneeName) && story.getStatus().equals(status)).collect(Collectors.toList());
        if (filteredBugs
                .size() == 0 && filteredStories.size() == 0) {
            return String.format("No task assigned to this person.");
        }
        return ListingHelpers.bugsToString(filteredBugs) + ListingHelpers.storiesToString(filteredStories);
    }
}
