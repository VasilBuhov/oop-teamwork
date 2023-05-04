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

public class FilterFeedbackByStatusCommand implements Command {
    private List<Feedback> filteredFeedbacks = new ArrayList<>();
    private String status;

    private final TaskManagementRepositoryImpl repository;
    public FilterFeedbackByStatusCommand(TaskManagementRepositoryImpl taskManagementRepository) {
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
                if (!status.equalsIgnoreCase("New")
                        && (!status.equalsIgnoreCase("Unscheduled"))
                        && (!status.equalsIgnoreCase("Scheduled"))
                        && (!status.equalsIgnoreCase("Done"))){
                    throw new IllegalArgumentException("Status is not valid. Please choose between New, Unscheduled, Scheduled and Done or cancel if you want to exit:");
                }
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                status = "";
            }

            if (!status.equals("")) {
                filteredFeedbacks = repository.getFeedback().stream().filter(feedback -> feedback.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
                if (filteredFeedbacks.size() == 0) {
                    return String.format("No feedbacks with this status");
                }
                System.out.println(ListingHelpers.feedbacksToString(filteredFeedbacks));
            }
        }

        return ListingHelpers.feedbacksToString(filteredFeedbacks);
    }
}
