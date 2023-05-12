package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.contracts.Story;
import com.project.oop.task.management.utils.ListingHelpers;
import com.project.oop.task.management.utils.MessageHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FilterStoriesByStatusCommand implements Command {

    private List<Story> filteredStories = new ArrayList<>();
    private String status;

    private final TaskManagementRepositoryImpl repository;
    public FilterStoriesByStatusCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        MessageHelper.printPromptMessage("status to filter by");
        boolean statusIsValid = false;

        while (!statusIsValid) {
            status = scanner.nextLine();
            repository.isItCancel(status, MessageHelper.INVALID_INPUT);
            try {
               repository.checkForStoryStatus(status);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                status = "";
            }

            if (!status.equals("")) {
                statusIsValid = true;
                filteredStories = repository.getStoriesByStatus(status);
                if (filteredStories.size() == 0) {
                    return MessageHelper.NO_STORY_WITH_THIS_STATUS;
                }
            }
        }

        return ListingHelpers.storiesToString(filteredStories);
    }
}
