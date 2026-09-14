package com.spotifum.view;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.spotifum.model.Playlist;
import com.spotifum.model.Song;

public final class PlaylistView {
    private PlaylistView() {
    }

    public enum CreateChoice { CUSTOM, RANDOM, GENRE_DURATION, BACK }

    // ---------------------------------------------------------------- browsing

    public static void showNoPlaylists() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nYou don't have any playlists yet.");
        ConsoleIO.waitForEnter();
    }

    /** Returns the chosen playlist, or {@code null} if the user went back or made an invalid choice. */
    public static Playlist promptPlaylistChoice(List<Playlist> playlists) {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nYour playlists:");
        for (int i = 0; i < playlists.size(); i++) {
            Playlist p = playlists.get(i);
            ConsoleIO.println((i + 1) + ". " + p.getName() + " (" + p.getKind() + ", " + p.getSongs().size() + " songs)");
        }

        int choice = ConsoleIO.readInt("\nPick a playlist to view (0 to go back): ");
        if (choice <= 0 || choice > playlists.size()) {
            return null;
        }
        return playlists.get(choice - 1);
    }

    public static void showEmptyPlaylist() {
        ConsoleIO.println("This playlist has no songs yet.");
        ConsoleIO.waitForEnter();
    }

    /** Lists the songs in a playlist and asks whether to play it now. */
    public static boolean promptPlayPlaylist(Playlist playlist) {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\n" + playlist.getName() + " (" + playlist.getKind() + "):");
        List<Song> songs = playlist.getSongs();
        for (int i = 0; i < songs.size(); i++) {
            ConsoleIO.println((i + 1) + ". " + songs.get(i).getTitle());
        }

        ConsoleIO.println("\n1. Play\n2. Go back");
        int choice = ConsoleIO.readInt("Choose an option: ");
        return choice == 1;
    }

    // ---------------------------------------------------------------- creation

    public static CreateChoice showCreateMenu() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nCreate a playlist:");
        ConsoleIO.println("[1] Custom playlist (Premium)");
        ConsoleIO.println("[2] Random mix");
        ConsoleIO.println("[3] Genre & duration playlist (Premium)");
        ConsoleIO.println("[4] Back");

        int choice = ConsoleIO.readOption(4);
        return switch (choice) {
            case 1 -> CreateChoice.CUSTOM;
            case 2 -> CreateChoice.RANDOM;
            case 3 -> CreateChoice.GENRE_DURATION;
            default -> CreateChoice.BACK;
        };
    }

    public static String promptPlaylistName() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        return ConsoleIO.readLine("Playlist name: ");
    }

    public static boolean promptShuffle() {
        return ConsoleIO.readLine("Shuffle playback? (y/n): ").equalsIgnoreCase("y");
    }

    public static int promptRandomPlaylistSize(int catalogSize) {
        return ConsoleIO.readInt("How many songs should the mix contain (catalog has " + catalogSize + "): ");
    }

    public static String promptGenre() {
        return ConsoleIO.readLine("Genre to filter by: ");
    }

    public static int promptMaxDurationSeconds() {
        return ConsoleIO.readInt("Maximum total duration, in seconds: ");
    }

    public static void showNoSongsForGenre(String genre) {
        ConsoleIO.println("There are no songs available for genre \"" + genre + "\".");
        ConsoleIO.waitForEnter();
    }

    /**
     * Interactively builds a list of songs picked by title from {@code candidates},
     * skipping duplicates and, when {@code maxDurationSeconds} is given, refusing
     * songs that would push the running total over the limit. Shared by every
     * playlist-creation flow that requires manual song selection.
     */
    public static List<Song> selectSongs(List<Song> candidates, String playlistName, Integer maxDurationSeconds) {
        List<Song> selected = new ArrayList<>();
        Set<String> addedKeys = new HashSet<>();
        int totalDuration = 0;

        while (true) {
            ConsoleIO.clearScreen();
            ConsoleIO.printHeader();
            ConsoleIO.println("\nAdd songs to \"" + playlistName + "\" (0 to finish):\n");
            candidates.forEach(s -> ConsoleIO.println("- " + s.getTitle() + " by " + s.getArtist() + " (" + s.getDurationSeconds() + "s)"));
            if (maxDurationSeconds != null) {
                ConsoleIO.println("\nDuration so far: " + totalDuration + "/" + maxDurationSeconds + "s");
            }

            String choice = ConsoleIO.readLine("\nSong title to add: ");
            if (choice.equals("0")) {
                return selected;
            }

            Song found = findByNormalizedTitle(candidates, choice);
            String key = found == null ? null : normalize(found.getTitle());

            if (found == null) {
                ConsoleIO.println("Song not found.");
            } else if (addedKeys.contains(key)) {
                ConsoleIO.println("That song is already in the playlist.");
            } else if (maxDurationSeconds != null && totalDuration + found.getDurationSeconds() > maxDurationSeconds) {
                ConsoleIO.println("Adding that song would exceed the duration limit.");
            } else {
                selected.add(found);
                addedKeys.add(key);
                totalDuration += found.getDurationSeconds();
                ConsoleIO.println("Added!");
            }
            ConsoleIO.waitForEnter();
        }
    }

    public static void showPlaylistCreated(String name) {
        ConsoleIO.println("Playlist \"" + name + "\" created successfully!");
        ConsoleIO.waitForEnter();
    }

    public static void showPlaylistNotCreatedEmpty() {
        ConsoleIO.println("No songs were selected, so the playlist was not created.");
        ConsoleIO.waitForEnter();
    }

    // ---------------------------------------------------------------- explicit songs

    public static void showExplicitSongs(List<Song> songs) {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\n--- Explicit songs ---");
        for (int i = 0; i < songs.size(); i++) {
            ConsoleIO.println((i + 1) + ". " + songs.get(i).getTitle() + " - " + songs.get(i).getArtist());
        }
    }

    public static void showNoExplicitSongs() {
        ConsoleIO.println("There are no explicit songs in the catalog.");
        ConsoleIO.waitForEnter();
    }

    /** Returns the chosen 1-based index, or 0 if the user wants to go back / entered garbage. */
    public static int promptExplicitSongChoice() {
        String input = ConsoleIO.readLine("\nEnter a song number to play, or 0 to go back: ");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void showInvalidChoice() {
        ConsoleIO.println("Invalid choice.");
    }

    private static Song findByNormalizedTitle(List<Song> candidates, String title) {
        String target = normalize(title);
        return candidates.stream()
                .filter(s -> normalize(s.getTitle()).equals(target))
                .findFirst()
                .orElse(null);
    }

    private static String normalize(String input) {
        String stripped = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return stripped.toLowerCase();
    }
}
