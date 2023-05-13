package com.project.oop.task.management.core;

import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.*;
import com.project.oop.task.management.models.contracts.*;
import com.project.oop.task.management.models.enums.*;
import com.project.oop.task.management.utils.MessageHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManagementRepositoryImpl implements TaskManagementRepository {

    private int nextId;

    private final List<Task> tasks = new ArrayList<>();
    private final List<Task> assignedTasks = new ArrayList<>();
    private final List<Bug> bugs = new ArrayList<>();
    private final List<Feedback> feedbacks = new ArrayList<>();
    private final List<Story> stories = new ArrayList<>();
    private final List<Member> notMembers = new ArrayList<>();
    private final List<Member> allPeople = new ArrayList<>();
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
    public List<Member> getAllPeople() {
        return new ArrayList<>(allPeople);
    }
    @Override
    public List<Member> getNotMembers() {return new ArrayList<>(notMembers);
    }
    @Override
    public List<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }
    @Override
    public List<Task> getAssignedTasks() {
        return new ArrayList<>(assignedTasks);
    }
    @Override
    public List<Story> getStories() {
        return new ArrayList<>(stories);
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
        if (isPersonAlreadyCreated(name)){
            throw new IllegalArgumentException(String.format(MessageHelper.PERSON_EXISTS));
        }
        Member person = new MemberImpl(name);
        allPeople.add(person);
        notMembers.add(person);

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

        stories.add(story);
        tasks.add(story);
        assignedTasks.add(story);
        findMemberByName(assignee).addTask(story);

        return story;
    }

    public Bug createBug(String title, String description, Priority priority, Severity severity, String assignee) {
        Bug bug = new BugImpl(++nextId, title, description, priority, severity, assignee);

        bugs.add(bug);
        tasks.add(bug);
        assignedTasks.add(bug);
        findMemberByName(assignee).addTask(bug);

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
    @Override
    public void changeBugStatus(int id, String direction) {
        Bug bug = findBugById(id);
        if (direction.equals("revert")) {
            bug.revertStatus();
        } else if (direction.equals("advance")) {
            bug.advanceStatus();
        }
    }

    public void changeBugPriority(int id, Priority newPriority) {
        Bug bug = findBugById(id);
        bug.changePriority(newPriority);
    }

    @Override
    public void addNewPersonToTeam(String name, String team) {
        Member person = findPersonByName(name);
        members.add(person);
        findTeamByName(team).addMember(person);
        this.notMembers.remove(person);
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
    public Bug findBugById(int id) {
        return bugs.stream().filter(bug -> bug.getId()==id).findFirst().get();
    }

    @Override
    public void changeBugSeverity(int id, Severity severity) {
        Bug bug = findBugById(id);
        bug.changeSeverity(severity);
    }

    @Override
    public Member findMemberByName(String name) {
        return getMembers().stream().filter(member -> member.getName().equals(name)).collect(Collectors.toList()).get(0);
    }
    public Member findPersonByName(String name) {
        return allPeople.stream().filter(person -> person.getName().equals(name)).collect(Collectors.toList()).get(0);
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
    public void assignTask(int taskId, String name) {
        Task task = this.findTaskById(taskId);
        assignedTasks.add(task);
        Member member = this.findMemberByName(name);
        member.addTask(task);
    }
    public void unassignTask(int taskId, String name) {
        Task task = this.findTaskById(taskId);
        assignedTasks.remove(task);
        Member member = this.findMemberByName(name);
        member.removeTask(task);
    }
    public void addCommentToTask(int taskId, Comment comment) {
        Task task = this.findTaskById(taskId);
        task.addComment(comment);
    }

    @Override
    public boolean isTeamAlreadyCreated(String teamName) {
        return getTeams().stream().anyMatch(team -> team.getName().equals(teamName));
    }

    @Override
    public boolean isBoardAlreadyCreated(String teamName, String boardName) {
        Team teamToAddBoard = findTeamByName(teamName);
        return teamToAddBoard.getBoards().stream().anyMatch(board -> board.getName().equals(boardName));
    }

    @Override
    public boolean isTaskAlreadyCreated(int taskId) {
        return tasks.stream().anyMatch(task -> task.getId() == taskId);
    }

    @Override
    public boolean isPersonAlreadyCreated(String personName) {
        return getAllPeople().stream().anyMatch(member -> member.getName().equals(personName));
    }

    @Override
    public void checkForAssignee(String assigneeName) {
        if (!getMembers().stream().anyMatch(member -> member.getName().equals(assigneeName))) {
            throw new IllegalArgumentException("No assignee with this name");
        }
    }

    @Override
    public List<Bug> getBugsByAssignee(String assigneeName) {
        return getBugs()
                .stream()
                .filter(bug -> bug.getAssignee().equals(assigneeName))
                .collect(Collectors.toList());
    }

    @Override
    public void checkForBugStatus(String status) {
        if (!status.equalsIgnoreCase("Active")
                && (!status.equalsIgnoreCase("Fixed"))){
            throw new IllegalArgumentException("Status is not valid. Please choose between Active and Fixed or cancel if you want to exit:");
        }
    }

    @Override
    public List<Bug> getBugsByStatus(String status) {
        return getBugs()
                .stream()
                .filter(bug -> bug.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Bug> getBugsByStatusAndAssignee(String status, String assignee) {
        return getBugs()
                .stream()
                .filter(bug -> bug.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList())
                .stream()
                .filter(bug -> bug.getAssignee().equalsIgnoreCase(assignee))
                .collect(Collectors.toList());
    }

    @Override
    public void checkForFeedbackStatus(String status) {
        if (!status.equalsIgnoreCase("New")
                && (!status.equalsIgnoreCase("Unscheduled"))
                && (!status.equalsIgnoreCase("Scheduled"))
                && (!status.equalsIgnoreCase("Done"))){
            throw new IllegalArgumentException("Status is not valid. Please choose between New, Unscheduled, Scheduled and Done or cancel if you want to exit:");
        }
    }

    @Override
    public List<Feedback> getFeedbacksByStatus(String status) {
        return getFeedback()
                .stream()
                .filter(feedback -> feedback.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Story> getStoriesByAssignee(String assignee) {
        return getStories()
                .stream()
                .filter(story -> story.getAssignee().equals(assignee))
                .collect(Collectors.toList());
    }

    @Override
    public void checkForStoryStatus(String status) {
        if (!status.equalsIgnoreCase("NotDone")
                && (!status.equalsIgnoreCase("InProgress"))
                && (!status.equalsIgnoreCase("Done"))){
            throw new IllegalArgumentException("Status is not valid. Please choose between NotDone, InProgress and Done or cancel if you want to exit:");
        }
    }

    @Override
    public List<Story> getStoriesByStatus(String status) {
        return getStories()
                .stream()
                .filter(story -> story.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    @Override
    public List<Story> getStoriesByStatusAndAssignee(String status, String assignee) {
        return getStories()
                .stream()
                .filter(story -> story.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList())
                .stream()
                .filter(story -> story.getAssignee().equalsIgnoreCase(assignee))
                .collect(Collectors.toList());
    }

    @Override
    public void checkForTaskTitle(String title) {
        if (!getTasks().stream().anyMatch(task -> task.getTitle().equals(title))){
            throw new IllegalArgumentException(
                    "No task with this title. Please enter a new title or 'cancel' if you want to exit:");
        }
    }

    @Override
    public List<Task> getTasksByTitle(String title) {
        return getTasks()
                .stream()
                .filter(task -> task.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    @Override
    public void checkForStoryPriority(String priority) {
        if (!priority.equalsIgnoreCase("low")
                && (!priority.equalsIgnoreCase("medium"))
                && (!priority.equalsIgnoreCase("high"))){
            throw new IllegalArgumentException(MessageHelper.STORY_PRIORITY_NOT_VALID);
        }
    }

    @Override
    public void checkForTaskId(int id) {
        if (!tasks.stream().anyMatch(task -> task.getId() == id)) {
            throw new IllegalArgumentException(String.format(MessageHelper.TASK_NOT_FOUND_MESSAGE, id));
        }
    }

    @Override
    public void checkForStorySize(String size) {
        if (!size.equalsIgnoreCase("small")
                && (!size.equalsIgnoreCase("medium"))
                && (!size.equalsIgnoreCase("large"))){
            throw new IllegalArgumentException(MessageHelper.STORY_SIZE_NOT_VALID);
        }
    }

    @Override
    public void checkForTeam(String teamName) {
        if (!getTeams().stream().anyMatch(team -> team.getName().equals(teamName))) {
            throw new IllegalArgumentException("No team with this name in the Task management system");
        }
    }

    @Override
    public void checkForBoard(String teamName, String boardName) {
        if (!getTeams().stream().filter(team -> team.getName().equalsIgnoreCase(teamName)).collect(Collectors.toList()).get(0)
                .getBoards().stream().anyMatch(board -> board.getName().equalsIgnoreCase(boardName))) {
            throw new IllegalArgumentException("No board with this name found");
        }
    }

    @Override
    public boolean isItNotMember(String name) {
        return (getNotMembers().stream().anyMatch(member -> member.getName().equals(name)));
    }

    @Override
    public boolean isItMember(String name) {
        return (getMembers().stream().anyMatch(member -> member.getName().equals(name)));
    }

    @Override
    public boolean isItValidTaskID(int id) {
        return (getTasks().stream().anyMatch(task -> task.getId() == id));
    }


}
