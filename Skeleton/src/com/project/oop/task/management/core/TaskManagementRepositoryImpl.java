package com.project.oop.task.management.core;

import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.*;
import com.project.oop.task.management.models.contracts.Feedback;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementRepositoryImpl implements TaskManagementRepository {
    private int nextId;
    private final List<StoryImpl> stories = new ArrayList<>();
    private final List<FeedbackImpl> feedback = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<TaskImpl> tasksWithAssignee = new ArrayList<>();

    public TaskManagementRepositoryImpl() {
        nextId = 0;
    }

    public StoryImpl createNewStory(String title, String description, Priority priority, Size size, String assignee) {
        StoryImpl story = new StoryImpl(++nextId, title, description, priority, size, assignee);
        this.stories.add(story);
        tasksWithAssignee.add(story);
        return story;
    }
    public Team createNewTeam(String name) {
        Team team = new TeamImpl(name);
        this.teams.add(team);
        return team;
    }

    @Override
    public void changeFeedbackRating(int id, int rating) {
        for (Feedback feedback : getFeedback()) {
            if (feedback.getId() == id) {
                feedback.changeRating(rating);
            }
        }
    }

    @Override
    public String changeFeedbackStatus(int id, String direction) {
        for (FeedbackImpl feedback : getFeedback()) {
            if (feedback.getId() == id) {
               if (direction.equals("revert")) {
                   feedback.revertStatus();
                   return feedback.getStatus();
               } else if (direction.equals("advance")) {
                   feedback.advanceStatus();
                   return feedback.getStatus();
               }
            }
        }
        return "Status not changed!";
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }
    @Override
    public List<StoryImpl> getStories() {
        return new ArrayList<>(stories);
    }
    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }
    @Override
    public List<TaskImpl> getTasksWithAssignee() {
        return new ArrayList<>(tasksWithAssignee);
    }

    @Override
    public List<FeedbackImpl> getFeedback() {
        return new ArrayList<>(feedback);
    }

}
