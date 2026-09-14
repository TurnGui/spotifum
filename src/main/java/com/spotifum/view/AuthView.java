package com.spotifum.view;

import com.spotifum.model.SubscriptionPlan;

public final class AuthView {
    private AuthView() {
    }

    /** Returns the entered email, or {@code null} if the user chose to go back. */
    public static String promptLogin() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nLog in\n");
        ConsoleIO.println("Enter your email, or 0 to go back.");
        String email = ConsoleIO.readEmail("Email: ");
        return email.equals("0") ? null : email;
    }

    public static void showUserNotFound() {
        ConsoleIO.println("\nNo account with that email. Try logging in with a different one.");
        ConsoleIO.waitForEnter();
    }

    public record SignUpDetails(String email, String name, String address) {
    }

    /** Returns the entered details, or {@code null} if the user chose to go back. */
    public static SignUpDetails promptSignUp() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nSign up\n");
        ConsoleIO.println("Enter your email, or 0 to go back.");
        String email = ConsoleIO.readEmail("Email: ");
        if (email.equals("0")) {
            return null;
        }

        String name = ConsoleIO.readLine("Name: ");
        String address = ConsoleIO.readLine("Address: ");
        return new SignUpDetails(email, name, address);
    }

    public static void showEmailTaken() {
        ConsoleIO.println("\nAn account with that email already exists.");
        ConsoleIO.waitForEnter();
    }

    public static SubscriptionPlan promptPlanSelection() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nChoose your subscription plan:\n");
        ConsoleIO.println("[1] Free");
        ConsoleIO.println("[2] Premium");
        ConsoleIO.println("[3] Premium+");

        int choice;
        while (true) {
            choice = ConsoleIO.readOption(3);
            if (choice >= 1) {
                break;
            }
            ConsoleIO.println("\nInvalid choice, please try again.");
        }

        return switch (choice) {
            case 1 -> SubscriptionPlan.FREE;
            case 2 -> SubscriptionPlan.PREMIUM;
            default -> SubscriptionPlan.PREMIUM_PLUS;
        };
    }
}
