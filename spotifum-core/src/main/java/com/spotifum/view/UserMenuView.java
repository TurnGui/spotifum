package com.spotifum.view;

public final class UserMenuView {
    private UserMenuView() {
    }

    public static int show() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nMAIN MENU\n");
        ConsoleIO.println("[1] Play a song");
        ConsoleIO.println("[2] View my playlists");
        ConsoleIO.println("[3] Create a playlist");
        ConsoleIO.println("[4] Albums");
        ConsoleIO.println("[5] View my points");
        ConsoleIO.println("[6] Leaderboard");
        ConsoleIO.println("[7] Explicit songs playlist (Premium+)");
        ConsoleIO.println("[8] Log out");
        ConsoleIO.println("[9] Exit\n");

        return ConsoleIO.readOption(9);
    }

    public static int showPointsMenu(double points) {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nYour points:");
        ConsoleIO.println("You have " + points + " points.\n");
        ConsoleIO.println("[0] Back");
        ConsoleIO.println("[1] See who has the most points");

        return ConsoleIO.readOption(1);
    }

    public static void showTopPointsUser(String name, double points) {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nUser with the most points:");
        ConsoleIO.println(name + " - " + points + " points.");
        ConsoleIO.waitForEnter();
    }

    public static void showPremiumRequired() {
        ConsoleIO.println("\nThis feature is only available to Premium and Premium+ users.");
        ConsoleIO.waitForEnter();
    }

    public static void showPremiumPlusRequired() {
        ConsoleIO.println("\nThis feature is only available to Premium+ users.");
        ConsoleIO.waitForEnter();
    }

    public static void showLoggingOut() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nLogging out...");
    }
}
