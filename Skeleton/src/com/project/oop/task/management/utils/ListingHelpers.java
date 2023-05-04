package com.project.oop.task.management.utils;

import com.project.oop.task.management.models.contracts.*;

import java.util.ArrayList;
import java.util.List;

public class ListingHelpers {
    public static final String JOIN_DELIMITER = "*********************";

    public static String bugsToString(List<Bug> bugs){
        return elementsToString(bugs);
    }

    public static String storiesToString(List<Story> stories){
        return elementsToString(stories);
    }

    public static String feedbacksToString(List<Feedback> feedbacks){
        return elementsToString(feedbacks);
    }

    public static String tasksToString(List<Task> tasks){
        return elementsToString(tasks);
    }
    public static <T extends Printable> String elementsToString(List<T> elements) {
        List<String> result = new ArrayList<>();
        for (T element : elements) {
            result.add(element.getAsString());
        }
        return String.join(JOIN_DELIMITER + System.lineSeparator(), result).trim();
    }


}
