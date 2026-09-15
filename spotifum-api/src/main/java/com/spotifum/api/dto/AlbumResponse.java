package com.spotifum.api.dto;

import com.spotifum.api.model.Album;
import lombok.Getter;

import java.util.List;

@Getter
public class AlbumResponse {
    private final Long id;
    private final String title;
    private final String artist;
    private final int year;
    private final List<SongResponse> songs;

    public AlbumResponse(Album album) {
        this.id = album.getId();
        this.title = album.getTitle();
        this.artist = album.getArtist();
        this.year = album.getYear();
        this.songs = album.getSongs().stream().map(SongResponse::new).toList();
    }
}