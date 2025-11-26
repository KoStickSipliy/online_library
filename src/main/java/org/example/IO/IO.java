package org.example.IO;

import java.util.Scanner;

public class IO {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    public static Long readLongSafe(String message) {
        System.out.println(message);
        String s = scanner.nextLine();
        if (s == null || s.isBlank()) return null;
        try {
            return Long.parseLong(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer readIntSafe(String message) {
        System.out.println(message);
        String s = scanner.nextLine();
        if (s == null || s.isBlank()) return null;
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void printEmptyLine() {
        System.out.println();
    }

    public static void printError(String message) {
        System.err.println(message);
    }
}
