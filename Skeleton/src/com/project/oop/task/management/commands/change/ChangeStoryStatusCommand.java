package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.models.enums.StoryStatus;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeStoryStatusCommand implements Command{
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private final TaskManagementRepository repository;
    public ChangeStoryStatusCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter story ID:");
        int storyId = ParsingHelpers.tryParseInt(scanner.nextLine(), "ID is not valid");

        System.out.println("Please enter the new status of the story:");
        String newStatus = scanner.nextLine();
        parameters.add(newStatus);

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        StoryStatus status = StoryStatus.valueOf(newStatus);

        repository.changeStoryStatus(storyId, status);

        return String.format("Story size was changed to %s", newStatus);
    }
}
