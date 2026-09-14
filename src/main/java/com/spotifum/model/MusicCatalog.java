package com.spotifum.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The in-memory catalog of every track available on the platform. Backed by
 * the file the application was seeded/loaded from (see
 * {@code com.spotifum.persistence.FileStorageService}).
 */
public final class MusicCatalog {
    private static List<Song> songs = new ArrayList<>();

    private MusicCatalog() {
    }

    public static void addSong(Song song) {
        songs.add(song);
    }

    public static List<Song> getAllSongs() {
        return new ArrayList<>(songs);
    }

    public static void setSongs(List<Song> newCatalog) {
        songs = new ArrayList<>(newCatalog);
    }

    public static Optional<Song> findByTitle(String title) {
        return songs.stream()
                .filter(song -> song.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }
}
