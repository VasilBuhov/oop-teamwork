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

public class FilterStoriesByAssigneeCommand implements Command {
    private List<Story> filteredStories = new ArrayList<>();
    private String assigneeName;

    private final TaskManagementRepositoryImpl repository;
    public FilterStoriesByAssigneeCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
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
                filteredStories = repository.getStories().stream().filter(story -> story.getAssignee().equals(assigneeName)).collect(Collectors.toList());
                if (filteredStories.size() == 0) {
                    return String.format("No stories assigned to this person.");
                }
            }
        }

        return ListingHelpers.storiesToString(filteredStories);


    }
}
