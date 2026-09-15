package com.spotifum.model;

import java.io.Serializable;

/**
 * A catalog track. Instances are built through {@link Builder} rather than a
 * telescoping constructor, since a track carries several optional attributes
 * (label, lyrics, genre...) alongside its two required ones (title, artist).
 */
public class Song implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private final String title;
    private final String artist;
    private final String label;
    private final String lyrics;
    private final String genre;
    private final int durationSeconds;
    private final boolean explicit;
    private int playCount;

    private Song(Builder builder) {
        this.title = builder.title;
        this.artist = builder.artist;
        this.label = builder.label;
        this.lyrics = builder.lyrics;
        this.genre = builder.genre;
        this.durationSeconds = builder.durationSeconds;
        this.explicit = builder.explicit;
        this.playCount = 0;
    }

    private Song(Song source) {
        this.title = source.title;
        this.artist = source.artist;
        this.label = source.label;
        this.lyrics = source.lyrics;
        this.genre = source.genre;
        this.durationSeconds = source.durationSeconds;
        this.explicit = source.explicit;
        this.playCount = source.playCount;
    }

    public static Builder builder(String title, String artist) {
        return new Builder(title, artist);
    }

    /** Simulates playback: bumps the play counter and prints the track to the console. */
    public void play() {
        playCount++;
        System.out.println("Now playing: " + title + " - " + artist);
        if (lyrics != null && !lyrics.isBlank()) {
            System.out.println("Lyrics: " + lyrics);
        }
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getLabel() {
        return label;
    }

    public String getLyrics() {
        return lyrics;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public int getPlayCount() {
        return playCount;
    }

    @Override
    public String toString() {
        return "%s - %s [%s, %ds]%s".formatted(title, artist, genre, durationSeconds, explicit ? " (explicit)" : "");
    }

    @Override
    public Song clone() {
        return new Song(this);
    }

    public static final class Builder {
        private final String title;
        private final String artist;
        private String label = "Independent";
        private String lyrics = "";
        private String genre = "Unknown";
        private int durationSeconds;
        private boolean explicit;

        private Builder(String title, String artist) {
            this.title = title;
            this.artist = artist;
        }

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder lyrics(String lyrics) {
            this.lyrics = lyrics;
            return this;
        }

        public Builder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public Builder durationSeconds(int durationSeconds) {
            this.durationSeconds = durationSeconds;
            return this;
        }

        public Builder explicit(boolean explicit) {
            this.explicit = explicit;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }
}
