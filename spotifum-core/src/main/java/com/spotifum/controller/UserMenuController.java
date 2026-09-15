package com.spotifum.controller;

import com.spotifum.SpotifUmApplication;
import com.spotifum.model.User;
import com.spotifum.model.UserRepository;
import com.spotifum.view.UserMenuView;

/** The signed-in session loop for a single user; returns to {@link NavigationController} on logout. */
public final class UserMenuController {
    private UserMenuController() {
    }

    public static void run(User user) {
        while (true) {
            int option = UserMenuView.show();
            switch (option) {
                case 1 -> PlaybackController.playSong(user);
                case 2 -> PlaylistController.browsePlaylists(user);
                case 3 -> PlaylistController.createPlaylistMenu(user);
                case 4 -> AlbumController.menu(user);
                case 5 -> showPointsMenu(user);
                case 6 -> LeaderboardController.run();
                case 7 -> PlaylistController.showExplicitSongsPlaylist(user);
                case 8 -> {
                    UserMenuView.showLoggingOut();
                    return;
                }
                case 9 -> SpotifUmApplication.shutdown();
                default -> {
                }
            }
        }
    }

    private static void showPointsMenu(User user) {
        while (true) {
            int option = UserMenuView.showPointsMenu(user.getPoints());
            if (option == 0) {
                return;
            }
            if (option == 1) {
                UserRepository.getInstance().getAll().values().stream()
                        .max(java.util.Comparator.comparingDouble(User::getPoints))
                        .ifPresent(top -> UserMenuView.showTopPointsUser(top.getName(), top.getPoints()));
            }
        }
    }
}
