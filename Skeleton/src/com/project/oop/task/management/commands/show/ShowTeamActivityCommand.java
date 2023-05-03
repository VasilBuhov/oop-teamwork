package com.project.oop.task.management.commands.show;

import com.project.oop.task.management.commands.contracts.Command;
import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.TeamImpl;
import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.List;
import java.util.Scanner;

public class ShowTeamActivityCommand implements Command {
    public static final String TEAM_NOT_FOUNDED = "No such team founded in application!";
    public static int EXPECTED_NUMBER_OF_ARGUMENTS = 1;
    private String name;
    private final TaskManagementRepository repository;
    public ShowTeamActivityCommand(TaskManagementRepository repository) {
        this.repository = repository;
    }

    @Override
    public String execute(List<String> parameters) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the name of a team, which activity you would like to see: ");
        name = scanner.nextLine();
        parameters.add(name);

        ValidationHelper.validateArgumentsCount(parameters, EXPECTED_NUMBER_OF_ARGUMENTS);

       for (Team team : repository.getTeams()) {
           StringBuilder sb = new StringBuilder();
           if (team.getName().equals(name)) {
               sb.append(String.format("Members activity:%n"));
               for (Member member : team.getMembers()) {
                  sb.append(member.getActivity());
               }
               sb.append(String.format("Boards activity:%n"));
               for (Board board : team.getBoards()) {
                   sb.append(board.getActivity());
               }
           }
           repository.showTeamActivity(sb.toString());
       }
       throw new IllegalArgumentException(TEAM_NOT_FOUNDED);
    }
}
