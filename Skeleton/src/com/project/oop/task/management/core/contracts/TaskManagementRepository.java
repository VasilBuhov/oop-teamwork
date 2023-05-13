package com.project.oop.task.management.core.contracts;

import com.project.oop.task.management.models.FeedbackImpl;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.contracts.*;
import com.project.oop.task.management.models.enums.*;

import java.util.ArrayList;
import java.util.List;

public interface TaskManagementRepository {
    List<Member> getMembers();
    List<Member> getNotMembers();
    List<Member> getAllPeople();
    List<Team> getTeams();
    List<Task> getTasks();
    List<Task> getAssignedTasks();
    List<Story> getStories();
    List<Feedback> getFeedback();
    List<Bug> getBugs();
    Member createNewPerson(String name);
    Team createNewTeam(String name);
    StoryImpl createNewStory(String title, String description, Priority priority, Size size, String assignee);
    Feedback createFeedback(String title, String description, int rating);
    void changeStoryPriority(int storyId, Priority newPriority);
    void changeStorySize(int storyId, Size newSize);
    void changeStoryStatus(int storyId, StoryStatus status);
    void changeFeedbackRating(int id, int newRating);
    void changeFeedbackStatus(int id, String direction);
    void addNewPersonToTeam(String name, String team);
    void addCommentToTask(int taskId, Comment comment);
    void assignTask(int taskId, String name);
    void unassignTask(int taskId, String name);
    public Task findTaskById(int taskId);
    Task findFeedbackById(int taskId);
    Story findStoryById(int storyId);
    Team findTeamByName(String name);
    Member findMemberByName(String name);
    Member findPersonByName(String name);
    Board findBoardByName(String boardName, String teamName);
    boolean isAssigneeMemberOfTheTeam(String assignee, String teamName);
    void isItCancel(String string, String errorMessage);
    Bug findBugById(int id);
    void changeBugSeverity(int id, Severity severity);
    void changeBugStatus(int id, String direction);
    boolean isTeamAlreadyCreated(String teamName);
    boolean isBoardAlreadyCreated(String teamName, String boardName);
    boolean isTaskAlreadyCreated(int taskId);

    boolean isPersonAlreadyCreated(String personName);

    public void checkForAssignee(String assigneeName);
    public void checkForBugStatus(String status);
    List<Bug> getBugsByAssignee(String assigneeName);
    List<Bug> getBugsByStatus(String status);
    List<Bug> getBugsByStatusAndAssignee(String status, String assignee);
    List<Feedback> getFeedbacksByStatus(String status);
    List<Story> getStoriesByAssignee(String assignee);
    List<Story> getStoriesByStatus(String status);
    List<Story> getStoriesByStatusAndAssignee(String status, String assignee);
    List<Task> getTasksByTitle(String title);
    public void checkForFeedbackStatus(String status);
    public void checkForStoryStatus(String status);
    public void checkForStoryPriority(String priority);
    public void checkForTaskTitle(String title);
    public void checkForTaskId(int id);
    public void checkForStorySize(String size);
    public void checkForTeam(String teamName);
    public void checkForBoard(String teamName, String boardName);

    boolean isItNotMember(String name);
    boolean isItMember(String name);
    boolean isItValidTaskID(int id);
}

