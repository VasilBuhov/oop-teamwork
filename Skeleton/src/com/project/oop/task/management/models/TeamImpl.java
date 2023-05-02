package com.project.oop.task.management.models;

import com.project.oop.task.management.models.contracts.Board;
import com.project.oop.task.management.models.contracts.Member;
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
        members = new ArrayList<>();
        boards = new ArrayList<>();
        setName(name);
    }

    private void setName(String name) {
        ValidationHelper.ValidateStringLength(name, NAME_MIN_LENGTH, NAME_MAX_LENGTH);
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<Board> getBoards() {
        return new ArrayList<>(boards);
    }

    @Override
    public void addBoard(Board board) {
        boards.add(board);
    }

    @Override
    public void removeBoard(Board board) {
        if (boards.contains(board)) {
            boards.remove(board);
        }
    }

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public void addMember(Member member) {
        members.add(member);
    }

    @Override
    public void removeMember(Member member) {
        if (members.contains(member)) {
            members.remove(member);
        }
    }

    @Override
    public String getAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("======================%n"));
        sb.append(String.format("Team: %s%n", getName()));
        sb.append(String.format("---------------------%n"));
        sb.append(String.format("MEMBERS:%n"));
        if (!members.isEmpty()) {
            int counter = 1;
            for (Member member : members) {
                sb.append(counter).append(". ").append(member.getAsString());
            }
        } else {
            sb.append(String.format("There are no members in this team.%n"));
        }
        sb.append(String.format("---------------------%n"));
        sb.append(String.format("BOARDS:%n"));
        if (!boards.isEmpty()) {
            int counter = 1;
            for (Board board : boards) {
                sb.append(counter).append(". ").append(board.getAsString());
            }
        } else {
            sb.append(String.format("There are no boards in this team.%n"));
        }
        sb.append("======================");
        return sb.toString();
    }
}
