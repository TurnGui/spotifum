package com.spotifum.view;

import java.util.List;

import com.spotifum.model.Song;

public final class PlaybackView {
    private PlaybackView() {
    }

    public enum PostPlayAction { BACK, ADD_TO_FAVORITES }

    /** Returns the chosen song title, or {@code null} if the user typed 0 to go back. */
    public static String promptSongChoice(List<Song> catalog) {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nPlay a song\n");

        for (Song song : catalog) {
            ConsoleIO.println("- " + song.getTitle() + " by " + song.getArtist());
        }

        String title = ConsoleIO.readLine("\nEnter a song title to play (0 to go back): ");
        return title.equals("0") ? null : title;
    }

    public static void showNowPlaying(Song song) {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nNow playing: " + song.getTitle());
        ConsoleIO.println("Artist: " + song.getArtist());
        ConsoleIO.println("Duration: " + song.getDurationSeconds() + "s");
        ConsoleIO.println("Genre: " + song.getGenre());
        ConsoleIO.println("Label: " + song.getLabel());
        if (song.getLyrics() != null && !song.getLyrics().isBlank()) {
            ConsoleIO.println("Lyrics: " + song.getLyrics());
        }
    }

    public static PostPlayAction promptPostPlayAction(boolean isPremium) {
        ConsoleIO.println("\n1. Go back");
        if (isPremium) {
            ConsoleIO.println("2. Add to favorites");
        }
        String choice = ConsoleIO.readLine("Choose an option: ");
        return choice.equals("2") ? PostPlayAction.ADD_TO_FAVORITES : PostPlayAction.BACK;
    }

    public static void showNotPremium() {
        ConsoleIO.println("Only Premium users can use this feature.");
    }

    public static void showSongNotFound() {
        ConsoleIO.println("Song not found.");
    }

    /** Returns true if the listener wants to keep playing the next track in the queue. */
    public static boolean promptContinueQueue() {
        ConsoleIO.println("\n1. Play next song\n2. Go back");
        int choice = ConsoleIO.readInt("Choose an option: ");
        return choice == 1;
    }
}
