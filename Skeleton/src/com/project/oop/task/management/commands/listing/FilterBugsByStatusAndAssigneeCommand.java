package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.contracts.Bug;
import com.project.oop.task.management.utils.ListingHelpers;
import com.project.oop.task.management.utils.MessageHelper;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FilterBugsByStatusAndAssigneeCommand implements Command {

    private List<Bug> filteredBugs;
    private String status;
    private String assigneeName;

    private final TaskManagementRepositoryImpl repository;
    public FilterBugsByStatusAndAssigneeCommand(TaskManagementRepositoryImpl taskManagementRepository) {
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
                repository.checkForBugStatus(status);
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
                status = "";
            }

            if (!status.equals("")) {
                statusIsValid = true;
                filteredBugs = repository.getBugsByStatus(status);
                if (filteredBugs.size() == 0){
                    return MessageHelper.NO_BUGS_WITH_THIS_STATUS;
                }
            }
        }

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
                filteredBugs = repository.getBugsByStatusAndAssignee(status, assigneeName);
                if (filteredBugs.size() == 0) {
                    return MessageHelper.NO_BUGS_ASSIGNED_TO_ASSIGNEE;
                }
            }
        }

        return ListingHelpers.bugsToString(filteredBugs);
    }
}
