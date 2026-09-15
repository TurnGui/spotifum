package com.spotifum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Base type for every playlist flavor the platform supports. Concrete
 * subclasses decide how they get populated (random draw, genre/duration
 * filter, manually curated...) and must provide a deep {@link #clone()} so a
 * playlist handed to a user is never aliased back to the catalog.
 */
public abstract class Playlist implements Serializable {
    private static final long serialVersionUID = 1L;

    protected final String name;
    protected List<Song> songs;

    protected Playlist(String name) {
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public void addSong(Song song) {
        songs.add(song.clone());
    }

    public int getTotalDurationSeconds() {
        return songs.stream().mapToInt(Song::getDurationSeconds).sum();
    }

    /** Describes how this playlist type presents itself in menus (e.g. "Random", "Favorites"). */
    public abstract String getKind();

    public abstract Playlist clone();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" (").append(getKind()).append(", ").append(songs.size()).append(" songs)");
        for (Song song : songs) {
            sb.append("\n  ").append(song);
        }
        return sb.toString();
    }
}
