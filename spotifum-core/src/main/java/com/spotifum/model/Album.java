package com.spotifum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/** A collection of tracks released together under one title and year. */
public class Album implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private final String title;
    private final String artist;
    private final int year;
    private final List<Song> songs;

    public Album(String title, String artist, int year) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.songs = new ArrayList<>();
    }

    private Album(Album source) {
        this.title = source.title;
        this.artist = source.artist;
        this.year = source.year;
        this.songs = new ArrayList<>();
        for (Song song : source.songs) {
            this.songs.add(song.clone());
        }
    }

    public void addSong(Song song) {
        songs.add(song.clone());
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getYear() {
        return year;
    }

    public List<Song> getSongs() {
        return new ArrayList<>(songs);
    }

    public int getTotalDurationSeconds() {
        return songs.stream().mapToInt(Song::getDurationSeconds).sum();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(title).append(" - ").append(artist).append(" (").append(year).append(")");
        sb.append("\nTotal duration: ").append(getTotalDurationSeconds()).append("s");
        for (Song song : songs) {
            sb.append("\n  ").append(song);
        }
        return sb.toString();
    }

    @Override
    public Album clone() {
        return new Album(this);
    }
}
