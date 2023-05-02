package com.project.oop.task.management.core;
import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.contracts.CommandFactory;
import com.project.oop.task.management.core.contracts.Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TaskManagementEngineImpl implements Engine {
    private static final String TERMINATION_COMMAND = "Exit";
    private static final String EMPTY_COMMAND = "Command cannot be empty.";

    private final TaskManagementRepositoryImpl repository;
    private final CommandFactory commandFactory;

    public TaskManagementEngineImpl() {
        repository = new TaskManagementRepositoryImpl();
        commandFactory = new CommandFactoryImpl();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String inputLine = scanner.nextLine();
                if (inputLine.isBlank()) {
                    System.out.println(EMPTY_COMMAND);
                    continue;
                }
                if (inputLine.equalsIgnoreCase(TERMINATION_COMMAND)) {
                    break;
                }
                processCommand(inputLine);
            } catch (Exception ex) {
                if (ex.getMessage() != null && !ex.getMessage().isEmpty()) {
                    System.out.println(ex.getMessage());
                } else {
                    System.out.println(ex.toString());
                }
            }
        }
    }

    private void processCommand(String inputLine) {
        String commandName = extractCommandName(inputLine);
        Command command = commandFactory.createCommandFromCommandName(commandName, repository);
        List<String> parameters = new ArrayList<>();
        String commandResult = command.execute(parameters);
        System.out.println(commandResult);
    }

    private String extractCommandName(String inputLine) {
        return inputLine.split(" ")[0];
    }

//    private List<String> extractCommandParameters(String inputLine) {
//        String[] commandParts = inputLine.split(" ");
//        ArrayList<String> parameters = new ArrayList<>();
//        for (int i = 1; i < commandParts.length; i++) {
//            parameters.add(commandParts[i]);
//        }
//        return parameters;
//    }

}
