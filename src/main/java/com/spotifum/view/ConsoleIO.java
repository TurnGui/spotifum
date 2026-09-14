package com.spotifum.view;

import java.util.Scanner;

/** Thin wrapper around {@link System#in}/{@link System#out} shared by every view. */
public final class ConsoleIO {
    private static final Scanner SCANNER = new Scanner(System.in);

    private ConsoleIO() {
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void print(String message) {
        System.out.print(message);
    }

    public static void printError(String message) {
        System.err.println(message);
    }

    public static String readLine(String prompt) {
        print(prompt);
        return SCANNER.nextLine();
    }

    public static int readInt(String prompt) {
        while (true) {
            String text = readLine(prompt);
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                printError("'" + text + "' is not a valid number.");
            }
        }
    }

    /** Reads a menu choice in the inclusive range {@code [0, maxOption]}. */
    public static int readOption(int maxOption) {
        while (true) {
            String text = readLine("Select one of the options above: ");
            try {
                int choice = Integer.parseInt(text);
                if (choice >= 0 && choice <= maxOption) {
                    return choice;
                }
                printError("'" + text + "' is not a valid option.");
            } catch (NumberFormatException e) {
                printError("'" + text + "' is not a valid option.");
            }
        }
    }

    public static String readEmail(String prompt) {
        String emailRegex = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,}$";
        String email = readLine(prompt);
        while (!email.equals("0") && !email.matches(emailRegex)) {
            email = readLine("Please enter a valid email (or 0 to go back): ");
        }
        return email;
    }

    public static void waitForEnter() {
        readLine("\nPress enter to continue...");
    }

    public static void printHeader() {
        println("  _____   ____    ____   _____   _    _____ ");
        println(" / ____| |    |  / __ \\ |_   _| | |  |  ___|");
        println("| (___   |    | | |  | |  | |   | |  | |_   ");
        println(" \\___ \\  | ___| | |  | |  | |   | |  |  _|  ");
        println(" ____) | | |    | |__| |  | |   | |  | | ");
        println("|_____/  |_|     \\____/   |_|   |_|  |_|");
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
