package com.project.oop.task.management.utils;

import java.util.SplittableRandom;

public class MessageHelper {
    public static final String ENTER_TASK_ID_MESSAGE =
            "Please enter a valid Task ID or 'cancel' if you want to exit:";
    public static final String TASK_NOT_FOUND_MESSAGE = "Task with id: %d is not found! " +
            "Please enter a valid ID or 'cancel' if you want to exit:";
    public static final String TASK_ALREADY_ASSIGNED =
            "Task with id: %d is already assigned. Try with another task ID or enter 'cancel' if you want to exit:";
    public static final String TASK_UNASSIGNED_MESSAGE =
            "Task with id: %d and title: %s is unassigned from %s.";

    public static final String ENTER_PERSON_NAME_MESSAGE =
            "Please enter person name or 'cancel' if you want to exit:";
    public static final String PERSON_IS_NOT_FOUND_MESSAGE =
            "Person with this name is not found! " +
                    "Please enter a valid assignee or 'cancel' if you want to exit: ";


    public static final String ENTER_TEAM_NAME_MESSAGE =
            "Please enter team name or 'cancel' if you want to exit:";
    public static final String TEAM_ALREADY_EXIST =
            "Team with this name already exist. Please enter a valid team name or 'cancel' if you want to exit:";
    public static final String TEAM_IS_NOT_FOUNDED =
            "There is no team with this name. " +
                    "Please enter a valid team name or 'cancel' if you want to exit:";
    public static final String TEAM_CREATED =
            "Team with name %s was created!";
    public static final String MEMBER_ADDED_MESSAGE =
            "Member with name: %s was added to team: %s!";


    public static final String ENTER_COMMENT_MESSAGE =
            "Please enter the comment you would like to add or 'cancel' if you want to exit:";
    public static final String COMMENT_ADDED_MESSAGE =
            "%sWas added to task with ID: %d";

    public static final String ENTER_BOARD_NAME_MESSAGE =
            "Please enter a board where you would like to add this story or 'cancel' if you want to exit:";
    public static final String BOARD_IS_NOT_FOUNDED =
            "This board is not founded in your team! " +
                    "Please enter a valid board name or 'cancel' if you want to exit:";

    public static final String CANNOT_ASSIGN_STORY =
            "You are not part of the team and cannot create a new story! " +
                    "Please enter a valid assignee or 'cancel' if you want to exit: ";
    public static final String ENTER_ASSIGNEE_MESSAGE =
            "Please enter your name, as assignee or 'cancel' if you want to exit:";
    public static final String STORY_CREATED =
            "Story with id: %d and title: %s was created.";

    public static final String CANNOT_ASSIGN_BUG =
            "You are not part of the team and cannot create a new bug! " +
                    "Please enter a valid assignee or 'cancel' if you want to exit: ";
    public static final String BUG_CREATED =
            "Bug with id: %d and title: %s was created.";

    public static final String ENTER_TITLE_MESSAGE =
            "Please enter a valid title or 'cancel' if you want to exit:";
    public static final String ENTER_DESCRIPTION_MESSAGE =
            "Please enter a valid description or 'cancel' if you want to exit:";
    public static final String ENTER_PRIORITY_MESSAGE =
            "Please enter a valid priority or 'cancel' if you want to exit:";
    public static final String ENTER_SEVERITY_MESSAGE =
            "Please enter a valid priority or 'cancel' if you want to exit:";
    public static final String ENTER_SIZE_MESSAGE =
            "Please enter a valid size or 'cancel' if you want to exit:";
    public static final String TASK_ASSIGNED_MESSAGE =
            "Task with id: %d and title: %s is assign to %s.";

    public static final String PARSING_ERROR_MESSAGE =
            "Invalid input, must be a number! Please try again or enter 'cancel' if you want to exit:";
    public static final String INVALID_INPUT =
            "Command is terminated. Please enter a new command:";
    public static final String FEEDBACK_NOT_FOUND_MESSAGE =
            "Feedback with id: %d is not found! Please enter a valid id or 'cancel if you want to exit:";
    public static final String ENTER_RATING_MESSAGE =
            "Please enter a new rating or 'cancel' if you want to exit:";
    public static final String CHANGED_RATING =
            "Rating to feedback with id: %d was changed to %d.";
    public static final String ENTER_DIRECTION_MESSAGE =
            "Please enter a valid direction (advance or revert) or 'cancel' if you want to exit:";
    public static final String INVALID_DIRECTION_MESSAGE =
            "Invalid direction! Please enter a valid direction (advance or revert) or 'cancel' if you want to exit:";
    public static final String CHANGED_STATUS =
            "Status of feedback with id: %d was changed from %s to %s.";
    public static final String STATUS_IS_NOT_CHANGED =
            "Status remains the same - %s!";


    public static final String RATING_NOT_VALID = "Rating is not valid. Please enter a number:";

    public static final String STORY_PRIORITY_NOT_VALID =
            "Priority is not valid. Please choose between Low, Medium and High or cancel if you want to exit:";
    public static final String STORY_SIZE_NOT_VALID =
            "Size is not valid. Please choose between SMALL, MEDIUM and LARGE or cancel if you want to exit:";

    public static final String STORY_STATUS_NOT_VALID = "Status is not valid. Please choose between Not_Done, In_Progress and Done or cancel if you want to exit:";
    public static final String STORY_PRIORITY_CHANGED = "Story priority was changed to %s";
    public static final String STORY_SIZE_CHANGED = "Story size was changed to %s";

    public static final String STORY_STATUS_CHANGED = "Story status was changed to %s";

    public static final String PERSON_CREATED = "Person %s was created.";
    public static final String PERSON_EXISTS =
            "A person with the same name already exists. Please enter another name or 'cancel' if you want to exit:";
    public static final String FEEDBACK_CREATED = "Feedback with ID %d was created and added to board %s of team %s.";
    public static final String PROMPT_MESSAGE = "Please enter %s or `cancel` if you want to exit";
    public static void printPromptMessage(String type){
        System.out.println(String.format(PROMPT_MESSAGE, type));
    }

    public static final String NO_BUGS_ASSIGNED_TO_ASSIGNEE = "No bugs assigned to this person.";
    public static final String NO_BUGS_WITH_THIS_STATUS = "No bugs with this status";
    public static final String NO_FEEDBACK_WITH_THIS_STATUS = "No feedbacks with this status";
    public static final String NO_STORY_WITH_THIS_STATUS = "No stories with this status";
    public static final String NO_STORY_ASSIGNED_TO_ASSIGNEE = "No stories assigned to this person.";
    public static final String NO_FEEDBACK_ASSIGNED_TO_ASSIGNEE = "No stories assigned to this person.";


}
