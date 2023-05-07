package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.utils.ListingHelpers;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FilterStoriesByStatusAndAssigneeCommand implements Command {
    private List<Story> filteredStories;
    private String status;
    private String assigneeName;


    private final TaskManagementRepositoryImpl repository;
    public FilterStoriesByStatusAndAssigneeCommand(TaskManagementRepositoryImpl taskManagementRepository) {
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
                if (!status.equalsIgnoreCase("NotDone")
                        && (!status.equalsIgnoreCase("InProgress"))
                        && (!status.equalsIgnoreCase("Done"))){
                    throw new IllegalArgumentException("Status is not valid. Please choose between NotDone, InProgress and Done or cancel if you want to exit:");
                }
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                status = "";
            }

            if (!status.equals("")) {
                statusIsValid = true;
                filteredStories = repository.getStories().stream().filter(story -> story.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
                if (filteredStories.size() == 0) {
                    return String.format("No stories with this status");
                }
            }
        }

        System.out.println("Please enter assignee name:");
        boolean nameIsValid = false;

        while (!nameIsValid) {
            assigneeName = scanner.nextLine();
            if (assigneeName.equals("cancel")) {
                throw new IllegalArgumentException("Command is terminated. Please enter a new command:");
            }
            try {
                if (!repository.getMembers().stream().anyMatch(member -> member.getName().equals(assigneeName))) {
                    throw new IllegalArgumentException("No assignee with this name. Please enter another assignee name or 'cancel' if you want to exit:");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                assigneeName = "";
            }

            if (!assigneeName.equals("")) {
                nameIsValid = true;
                filteredStories = repository.getStories().stream().filter(story -> story.getAssignee().equals(assigneeName)).collect(Collectors.toList());
                if (filteredStories.size() == 0) {
                    return String.format("No stories assigned to this person.");
                }
            }
        }

        filteredStories = repository
                .getStories()
                .stream()
                .filter(story -> story.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList())
                .stream()
                .filter(story -> story.getAssignee().equalsIgnoreCase(assigneeName))
                .collect(Collectors.toList());

        return ListingHelpers.storiesToString(filteredStories);
    }
}
