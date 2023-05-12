package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.utils.ListingHelpers;
import com.project.oop.task.management.utils.MessageHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FilterFeedbackByStatusCommand implements Command {
    private List<Feedback> filteredFeedbacks = new ArrayList<>();
    private String status;
    private String result;
    private final TaskManagementRepositoryImpl repository;

    public FilterFeedbackByStatusCommand(TaskManagementRepositoryImpl taskManagementRepository) {
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
                repository.checkForFeedbackStatus(status);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                status = "";
            }

            if (!status.equals("")) {
                statusIsValid = true;
                filteredFeedbacks = repository.getFeedbacksByStatus(status);
                if (filteredFeedbacks.size() == 0) {
                    return MessageHelper.NO_FEEDBACK_WITH_THIS_STATUS;
                }
            }
        }

        return ListingHelpers.feedbacksToString(filteredFeedbacks);
    }
}
