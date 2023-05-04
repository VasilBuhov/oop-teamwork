package com.project.oop.task.management.core.contracts;

import com.project.oop.task.management.models.FeedbackImpl;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.contracts.*;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.models.enums.StoryStatus;

import java.util.ArrayList;
import java.util.List;

public interface TaskManagementRepository {
    List<Member> getMembers();
    List<Team> getTeams();
    List<Task> getTasks();
    List<Feedback> getFeedback();
    List<Bug> getBugs();
    Member createMember(String name);
    Team createNewTeam(String name);
    StoryImpl createNewStory(String title, String description, Priority priority, Size size, String assignee);
    Feedback createFeedback(String title, String description, int rating);
    void changeStoryPriority(int storyId, Priority newPriority);
    void changeStorySize(int storyId, Size newSize);
    void changeStoryStatus(int storyId, StoryStatus status);
    void changeFeedbackRating(int id, int newRating);
    void changeFeedbackStatus(int id, String direction);
    void addNewPersonToTeam(String name, String team);
    public Task findTaskById(int taskId);
    Task findFeedbackById(int taskId);
    Story findStoryById(int storyId);
    Team findTeamByName(String name);
    Member findMemberByName(String name, String teamName);
    Board findBoardByName(String boardName, String teamName);
    boolean isAssigneeMemberOfTheTeam(String assignee, String teamName);
    void showPersonActivity(String activity);
    void showTeamActivity(String activity);
    void isItCancel(String string, String errorMessage);
    Bug findBugById(int id);







}
