package com.project.oop.task.management.utils;

import com.project.oop.task.management.models.contracts.Printable;

import java.util.ArrayList;
import java.util.List;

public class ListingHelpers {
    public static final String JOIN_DELIMITER = "*********************";
    public static <T extends Printable> String elementsToString(List<T> elements) {
        List<String> result = new ArrayList<>();
        for (T element : elements) {
            result.add(element.toString());
        }
        return String.join(JOIN_DELIMITER + System.lineSeparator(), result).trim();
    }


}
