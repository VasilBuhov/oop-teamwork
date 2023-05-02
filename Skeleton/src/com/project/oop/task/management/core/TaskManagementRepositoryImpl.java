package com.project.oop.task.management.core;

import com.project.oop.task.management.core.contracts.TaskManagementRepository;
import com.project.oop.task.management.models.FeedbackImpl;
import com.project.oop.task.management.models.MemberImpl;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.contracts.*;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;
import com.project.oop.task.management.models.enums.StoryStatus;

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

    @Override
    public List<Member> getMembers() {
        return new ArrayList<>(members);
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
    public Task findTaskById(int taskId) {
        return tasks.stream().filter(task -> task.getId() == taskId).collect(Collectors.toList()).get(0);
    }


}
