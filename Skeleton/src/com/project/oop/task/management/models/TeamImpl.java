package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Task;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;

public class TeamImpl implements Team {
    public static final int NAME_MIN_LENGTH = 5;
    public static final int NAME_MAX_LENGTH = 15;
    private String name;
    private List<Member> members;
    private List<Board> boards;

    public TeamImpl(String name) {
        setName(name);
    }

    private void setName(String name) {
        ValidationHelper.ValidateStringLength(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("*********************%n"));
        sb.append(String.format("Team: %s%n", getName()));
        sb.append(String.format("Members:%n"));
        for (Member member : members) {
            sb.append(member.getAsString());
        }
        sb.append(String.format("Boards:%n"));
        for (Board board : boards) {
            sb.append(board.getAsString());
        }
        sb.append(String.format("*********************%n"));
        return sb.toString();
    }
}
