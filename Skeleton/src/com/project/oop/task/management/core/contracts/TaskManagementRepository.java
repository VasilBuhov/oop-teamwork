package com.project.oop.task.management.core.contracts;

import com.project.oop.task.management.models.FeedbackImpl;
import com.project.oop.task.management.models.StoryImpl;
import com.project.oop.task.management.models.TaskImpl;
import com.project.oop.task.management.models.contracts.Member;
import com.project.oop.task.management.models.contracts.Team;
import com.project.oop.task.management.models.enums.Priority;
import com.project.oop.task.management.models.enums.Size;

import java.util.List;

public interface TaskManagementRepository {

    StoryImpl createNewStory(String title, String description, Priority priority, Size size, String assignee);

    Team createNewTeam(String name);

    void changeFeedbackRating(int id, int rating);
    String changeFeedbackStatus(int id, String direction);

    List<Team> getTeams();
    List<Member> getMembers();
    List<StoryImpl> getStories();
    List<TaskImpl> getTasksWithAssignee();
    List<FeedbackImpl> getFeedback();
}
