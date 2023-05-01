package com.project.oop.task.management.models.contracts;

import java.util.List;

public interface Team extends Printable{

    String getName();
    List<Member> getMembers();
    List<Board> getBoards();
}
