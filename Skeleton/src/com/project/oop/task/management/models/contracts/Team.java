package com.project.oop.task.management.models.contracts;

import java.util.List;

public interface Team {

    String getName();
    List<Member> getMembers();
    List<Board> getBoards();
}
