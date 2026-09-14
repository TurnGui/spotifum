package com.spotifum.controller;

import com.spotifum.SpotifUmApplication;
import com.spotifum.view.MainMenuView;

/** Owns the top-level loop; every screen below it returns here once the user goes back or logs out. */
public final class NavigationController {
    private NavigationController() {
    }

    public static void run() {
        while (true) {
            int option = MainMenuView.show();
            switch (option) {
                case 1 -> AuthController.login();
                case 2 -> AuthController.signUp();
                case 3 -> {
                    return;
                }
                default -> {
                }
            }
        }
    }
}
