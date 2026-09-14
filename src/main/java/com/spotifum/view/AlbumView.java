package com.spotifum.view;

import java.util.List;

import com.spotifum.model.Album;

public final class AlbumView {
    private AlbumView() {
    }

    public enum Choice { BROWSE, LIBRARY, BACK }

    public static Choice showMenu() {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\nAlbums:");
        ConsoleIO.println("[1] Browse catalog");
        ConsoleIO.println("[2] My library");
        ConsoleIO.println("[3] Back");

        int choice = ConsoleIO.readOption(3);
        return switch (choice) {
            case 1 -> Choice.BROWSE;
            case 2 -> Choice.LIBRARY;
            default -> Choice.BACK;
        };
    }

    /** Returns the chosen album, or {@code null} if the user went back / made an invalid choice. */
    public static Album promptAlbumChoice(List<Album> albums, String title) {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\n" + title);
        for (int i = 0; i < albums.size(); i++) {
            Album a = albums.get(i);
            ConsoleIO.println((i + 1) + ". " + a.getTitle() + " - " + a.getArtist() + " (" + a.getYear() + ")");
        }

        int choice = ConsoleIO.readInt("\nPick an album for details (0 to go back): ");
        if (choice <= 0 || choice > albums.size()) {
            return null;
        }
        return albums.get(choice - 1);
    }

    public static void showEmptyLibrary() {
        ConsoleIO.println("Your library is empty. Browse the catalog to add albums.");
        ConsoleIO.waitForEnter();
    }

    /** Shows an album's tracklist and asks whether to add it to the library. */
    public static boolean promptAddToLibrary(Album album) {
        ConsoleIO.clearScreen();
        ConsoleIO.printHeader();
        ConsoleIO.println("\n" + album);
        ConsoleIO.println("\n1. Add to my library\n2. Go back");
        int choice = ConsoleIO.readInt("Choose an option: ");
        return choice == 1;
    }

    public static void showAddedToLibrary(String title) {
        ConsoleIO.println("\"" + title + "\" was added to your library!");
        ConsoleIO.waitForEnter();
    }

    public static void showAlreadyInLibrary() {
        ConsoleIO.println("That album is already in your library.");
        ConsoleIO.waitForEnter();
    }
}
