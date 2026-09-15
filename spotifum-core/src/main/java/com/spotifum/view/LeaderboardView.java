package com.spotifum.view;

import com.spotifum.model.Song;
import com.spotifum.model.User;

public final class LeaderboardView {
    private LeaderboardView() {
    }

    public static int show() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\n--- Leaderboard ---");
        ConsoleIO.println("[1] Most played song");
        ConsoleIO.println("[2] Most listened to artist");
        ConsoleIO.println("[3] Most played genre");
        ConsoleIO.println("[4] Most active user");
        ConsoleIO.println("[0] Back");

        return ConsoleIO.readOption(4);
    }

    public static void showMostPlayedSong(Song song) {
        if (song != null) {
            ConsoleIO.println("Most played song: " + song.getTitle() + " (" + song.getPlayCount() + " plays)");
        } else {
            ConsoleIO.println("No songs have been played yet.");
        }
        ConsoleIO.waitForEnter();
    }

    public static void showMostListenedArtist(String artist, int plays) {
        if (artist != null) {
            ConsoleIO.println("Most listened to artist: " + artist + " (" + plays + " plays)");
        } else {
            ConsoleIO.println("No artist data available yet.");
        }
        ConsoleIO.waitForEnter();
    }

    public static void showMostPlayedGenre(String genre, int plays) {
        if (genre != null) {
            ConsoleIO.println("Most played genre: " + genre + " (" + plays + " plays)");
        } else {
            ConsoleIO.println("No genre data available yet.");
        }
        ConsoleIO.waitForEnter();
    }

    public static void showMostActiveUser(User user) {
        if (user != null) {
            ConsoleIO.println("Most active user: " + user.getName() + " (" + user.getTotalSongsPlayed() + " songs played)");
        } else {
            ConsoleIO.println("No user data available yet.");
        }
        ConsoleIO.waitForEnter();
    }
}
