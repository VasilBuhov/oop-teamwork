package com.project.oop.task.management.commands.listing;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.models.enums.Severity;

import java.util.List;
import java.util.stream.Collectors;

public class SortBugsBySeverityCommand implements Command {
    public static int EXPECTED_NUMBER_OF_ARGUMENTS;

    private final TaskManagementRepositoryImpl repository;
    public SortBugsBySeverityCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = new TaskManagementRepositoryImpl();
    }

    @Override
    public String execute(List<String> parameters) {
        String a = repository.getBugs().stream()
                .filter(bug -> bug.getSeverity().equals(Severity.CRITICAL))
                .map(bug -> bug.getSeverity() + " - " + bug.getTitle())
                .collect(Collectors.toList()).toString();
        String b = repository.getBugs().stream()
                .filter(bug -> bug.getSeverity().equals(Severity.MAJOR))
                .map(bug -> bug.getPriority() + " - " + bug.getTitle())
                .collect(Collectors.toList()).toString();
        String c = repository.getBugs().stream()
                .filter(bug -> bug.getSeverity().equals(Severity.MINOR))
                .map(bug -> bug.getSeverity() + " - " + bug.getTitle())
                .collect(Collectors.toList()).toString();
        return  "Bugs with critical severity: "+ a +
                "\n"+"Bugs with major severity: " + b +
                "\n"+"Bugs with minor severity: " + c;
    }
}
