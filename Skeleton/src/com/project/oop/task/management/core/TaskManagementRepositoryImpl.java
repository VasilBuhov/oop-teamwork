package com.project.oop.task.management.core;

import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.*;
import com.project.oop.task.management.models.contracts.*;
import com.project.oop.task.management.models.enums.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManagementRepositoryImpl implements TaskManagementRepository {

    private int nextId;

    private final List<Task> tasks = new ArrayList<>();

    private final List<Bug> bugs = new ArrayList<>();
    private final List<Feedback> feedbacks = new ArrayList<>();
    private final List<Story> stories = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public Feedback createFeedback(String title, String description, int rating) {
        Feedback feedback = new FeedbackImpl(++nextId, title, description, rating);
        this.tasks.add(feedback);
        this.feedbacks.add(feedback);

        return feedback;
    }

    @Override
    public Member createMember(String name) {
        Member member = new MemberImpl(name);
        members.add(member);

        return member;
    }

    @Override
    public void changeStoryPriority(int storyId, Priority newPriority) {
        Story story = findStoryById(storyId);
        story.changePriority(newPriority);

    }

    @Override
    public void changeStorySize(int storyId, Size newSize) {
        Story story = findStoryById(storyId);
        story.changeSize(newSize);
    }

    @Override
    public void changeStoryStatus(int storyId, StoryStatus status) {
        Story story = findStoryById(storyId);
        story.changeStatus(status);

    }

    @Override
    public Story findStoryById(int storyId) {
        return stories.stream().filter(story -> story.getId() == storyId).collect(Collectors.toList()).get(0);
    }

    @Override
    public StoryImpl createNewStory(String title, String description, Priority priority, Size size, String assignee) {
        StoryImpl story = new StoryImpl(++nextId, title, description, priority, size, assignee);
        stories.add(story);
        return story;
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
        for (Feedback feedback : getFeedback()) {
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
    public List<Feedback> getFeedback() {
        return new ArrayList<>(feedbacks);
    }

    public Team createNewTeam(String name) {
        Team team = new TeamImpl(name);
        this.teams.add(team);
        return team;

    }

    @Override
    public Task findTaskById(int taskId) {
        return tasks.stream().filter(task -> task.getId() == taskId).collect(Collectors.toList()).get(0);
    }


    public Bug createBug(String title, String description, Priority priority, Severity severity, String assignee) {
        Bug bug = new BugImpl(++nextId,title,description,priority,severity,assignee);
        bugs.add(bug);
        return bug;
    }

    public Board createBoard(String name) {
        Board board = new BoardImpl(name);
        return board;
    }
}
