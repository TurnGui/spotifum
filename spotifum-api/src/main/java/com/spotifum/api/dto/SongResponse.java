package com.spotifum.api.dto;

import com.spotifum.api.model.Song;
import lombok.Getter;

@Getter
public class SongResponse {
    private final Long id;
    private final String title;
    private final String artist;
    private final String genre;
    private final int durationSeconds;
    private final String label;
    private final int playCount;
    private final Long albumId;

    public SongResponse(Song song) {
        this.id = song.getId();
        this.title = song.getTitle();
        this.artist = song.getArtist();
        this.genre = song.getGenre();
        this.durationSeconds = song.getDurationSeconds();
        this.label = song.getLabel();
        this.playCount = song.getPlayCount();
        this.albumId = song.getAlbum() != null ? song.getAlbum().getId() : null;
    }
}