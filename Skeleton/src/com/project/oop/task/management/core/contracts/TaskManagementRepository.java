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
    Feedback createFeedback(String title, String description, int rating);

    Member createMember(String name);

    public List<Member> getMembers();
    public List<Team> getTeams();

    public void changeStoryPriority(int storyId, Priority newPriority);
    public void changeStorySize(int storyId, Size newSize);

    public void changeStoryStatus(int storyId, StoryStatus status);

    Member addNewPersonToTeam(String name, String team);

    public Task findTaskById(int taskId);
    Task findFeedbackById(int taskId);

    public Story findStoryById(int storyId);

    StoryImpl createNewStory(String title, String description, Priority priority, Size size, String assignee);

    void changeFeedbackRating(int id, int newRating);

    String changeFeedbackStatus(int id, String direction);

    public List<Feedback> getFeedback();

    Team createNewTeam(String name);

    List<Bug> getBugs();
    Member findMemberByName(String name, String teamName);
    Team findTeamByName(String name);
}
