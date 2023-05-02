package com.project.oop.task.management.commands.change;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.TaskManagementRepositoryImpl;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.utils.ParsingHelpers;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ChangeStorySizeCommand implements Command{
    public static final int EXPECTED_NUMBER_OF_ARGUMENTS = 2;

    private final TaskManagementRepository repository;
    public ChangeStorySizeCommand(TaskManagementRepositoryImpl taskManagementRepository) {
        this.repository = taskManagementRepository;
    }

    @Override
    public String execute(List<String> parameters) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter story ID:");
        int storyId = ParsingHelpers.tryParseInt(scanner.nextLine(), "ID is not valid");

        System.out.println("Please enter the new size of the story:");
        String newSize = scanner.nextLine();
        parameters.add(newSize);

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

        Size size = Size.valueOf(newSize);

        repository.changeStorySize(storyId, size);

        return String.format("Story size was changed to %s", newSize);
    }
}
