package com.clinica_hipocrates.common.util;

public class InputFormatter {

    private InputFormatter() {}

    public static String normalizeName(String input) {
        if (input == null || input.isBlank()) return null;

        input = input.trim().toLowerCase();
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }

    public static String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    public static String removeExtraSpaces(String input) {
        return input == null ? null : input.trim().replaceAll("\\s+", " ");
    }
}