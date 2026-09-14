package com.spotifum.model;

/** A manually curated playlist restricted to a single genre and a maximum total duration. */
public class GenreDurationPlaylist extends Playlist {
    private static final long serialVersionUID = 1L;

    private final String genre;
    private final int maxDurationSeconds;

    public GenreDurationPlaylist(String name, String genre, int maxDurationSeconds) {
        super(name);
        this.genre = genre;
        this.maxDurationSeconds = maxDurationSeconds;
    }

    public String getGenre() {
        return genre;
    }

    public int getMaxDurationSeconds() {
        return maxDurationSeconds;
    }

    @Override
    public String getKind() {
        return "Genre & Duration (" + genre + ")";
    }

    @Override
    public Playlist clone() {
        GenreDurationPlaylist copy = new GenreDurationPlaylist(name, genre, maxDurationSeconds);
        for (Song song : this.songs) {
            copy.addSong(song);
        }
        return copy;
    }
}
