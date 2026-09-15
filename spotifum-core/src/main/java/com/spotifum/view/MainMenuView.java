package com.spotifum.view;

public final class MainMenuView {
    private MainMenuView() {
    }

    public static int show() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\n[1] Log in");
        ConsoleIO.println("[2] Sign up");
        ConsoleIO.println("[3] Exit");

        return ConsoleIO.readOption(3);
    }
}
