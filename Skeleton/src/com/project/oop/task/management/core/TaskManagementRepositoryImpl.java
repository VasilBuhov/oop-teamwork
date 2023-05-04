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
    private final List<Member> people = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();

    public TaskManagementRepositoryImpl() {
        nextId = 0;
    }
    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
    }
    @Override
    public List<Member> getPeople() {
        return new ArrayList<>(people);
    }

    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public List<Feedback> getFeedback() {
        return new ArrayList<>(feedbacks);
    }

    @Override
    public List<Bug> getBugs() {
        return new ArrayList<>(bugs);
    }

    @Override
    public Member createNewPerson(String name) {
        Member person = new MemberImpl(name);
        people.add(person);

        return person;
    }
    @Override
    public Team createNewTeam(String name) {
        Team team = new TeamImpl(name);
        teams.add(team);

        return team;
    }

    public Board createBoard(String name) {
        Board board = new BoardImpl(name);

        return board;
    }

    @Override
    public StoryImpl createNewStory(String title, String description, Priority priority, Size size, String assignee) {
        StoryImpl story = new StoryImpl(++nextId, title, description, priority, size, assignee);
        findPersonByName(assignee).addTask(story);
        stories.add(story);
        tasks.add(story);

        return story;
    }

    public Bug createBug(String title, String description, Priority priority, Severity severity, String assignee) {
        Bug bug = new BugImpl(++nextId, title, description, priority, severity, assignee);
        findPersonByName(assignee).addTask(bug);
        bugs.add(bug);
        tasks.add(bug);

        return bug;
    }

    @Override
    public Feedback createFeedback(String title, String description, int rating) {
        Feedback feedback = new FeedbackImpl(++nextId, title, description, rating);
        this.tasks.add(feedback);
        this.feedbacks.add(feedback);

        return feedback;
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
    public void changeFeedbackRating(int id, int rating) {
        Feedback feedback = findFeedbackById(id);
        feedback.changeRating(rating);
    }

    @Override
    public void changeFeedbackStatus(int id, String direction) {
        Feedback feedback = findFeedbackById(id);
        if (direction.equals("revert")) {
            feedback.revertStatus();
        } else if (direction.equals("advance")) {
            feedback.advanceStatus();
        }
    }

    public void changeBugPriority(Priority priority, Priority newPriority) {
        for (Bug bugs : getBugs()) {
            if (bugs.getPriority().equals(priority)) {
                bugs.changePriority(newPriority);
            }
        }
    }

    @Override
    public void addNewPersonToTeam(String name, String team) {
        Member person = findPersonByName(name);
        members.add(person);
        findTeamByName(team).addMember(person);
        this.people.remove(person);
    }

    @Override
    public Task findTaskById(int taskId) {
        return tasks.stream().filter(task -> task.getId() == taskId).collect(Collectors.toList()).get(0);
    }

    @Override
    public Feedback findFeedbackById(int taskId) {
        return feedbacks.stream().filter(feedback -> feedback.getId() == taskId).collect(Collectors.toList()).get(0);
    }

    @Override
    public Story findStoryById(int storyId) {
        return stories.stream().filter(story -> story.getId() == storyId).collect(Collectors.toList()).get(0);
    }

    public Team findTeamByName(String name) {
        return teams.stream()
                .filter(team -> team.getName().equals(name))
                .findFirst().get();
    }
    @Override
    public Board findBoardByName(String boardName, String teamName) {
        Team team = findTeamByName(teamName);

       return team.getBoards()
               .stream()
               .filter(board1 -> board1.getName().equals(boardName))
               .collect(Collectors.toList()).get(0);
    }
    public Bug findBugByTitle(String title) {
        return bugs.stream().filter(bug -> bug.getTitle().equals(title)).findFirst().get();
    }

    @Override
    public Member findMemberByName(String name, String teamName) {
        Team team = findTeamByName(teamName);
       return team.getMembers().stream()
                .filter(member -> member.getName().equals(name)).collect(Collectors.toList()).get(0);
    }
    public Member findPersonByName(String name) {
        return people.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList()).get(0);
    }
    @Override
    public boolean isAssigneeMemberOfTheTeam(String assignee, String teamName) {
        Team team = findTeamByName(teamName);
        return team.getMembers().stream().anyMatch(member -> member.getName().equals(assignee));
    }
    @Override
    public void isItCancel(String string, String errorMessage) {
        if (string.equalsIgnoreCase("cancel")) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
