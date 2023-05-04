package com.project.oop.task.management.utils;

import java.util.Arrays;
import java.util.List;

public class TestUtilities {

    public static List<String> getList(int wantedSize) {
        return Arrays.asList(new String[wantedSize]);
    }

    public static String getString(int wantedSize) {
        return "x".repeat(wantedSize);
    }

}
