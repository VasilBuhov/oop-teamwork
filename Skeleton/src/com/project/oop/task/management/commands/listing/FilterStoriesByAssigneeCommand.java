package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.utils.ListingHelpers;
import com.project.oop.task.management.utils.MessageHelper;

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

        MessageHelper.printPromptMessage("assignee name");
        boolean nameIsValid = false;

        while (!nameIsValid) {
            assigneeName = scanner.nextLine();
            repository.isItCancel(assigneeName, MessageHelper.INVALID_INPUT);
            try {
                repository.checkForAssignee(assigneeName);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                assigneeName = "";
            }

            if (!assigneeName.equals("")) {
                nameIsValid = true;
                filteredStories = repository.getStoriesByAssignee(assigneeName);
                if (filteredStories.size() == 0) {
                    return MessageHelper.NO_STORY_ASSIGNED_TO_ASSIGNEE;
                }
            }
        }

        return ListingHelpers.storiesToString(filteredStories);


    }
}
