package com.project.oop.task.management.utils;

public class ValidationHelper {

    public static final String INVALID_NUMBER_MESSAGE = "Number must be between %d and %d.%n";
    public static final String INVALID_STRING_LENGTH_MESSAGE = "Input must be between %d and %d symbols.%n";

    public static void ValidateStringLength(String str, int min, int max) {
        if (str.length() < min || str.length() > max) {
            throw new IllegalArgumentException(String.format(INVALID_STRING_LENGTH_MESSAGE, min, max));
        }
    }

    public static void ValidateIntRange(int number, int min, int max) {
        if (number < min && number > max) {
            throw new IllegalArgumentException(String.format(INVALID_NUMBER_MESSAGE, min, max));
        }
    }
}
