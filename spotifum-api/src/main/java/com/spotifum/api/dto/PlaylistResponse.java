package com.spotifum.api.dto;

import com.spotifum.api.model.Playlist;
import lombok.Getter;

import java.util.List;

@Getter
public class PlaylistResponse {
    private final Long id;
    private final String name;
    private final String kind;
    private final String genre;
    private final int maxDurationSeconds;
    private final List<SongResponse> songs;

    public PlaylistResponse(Playlist playlist) {
        this.id = playlist.getId();
        this.name = playlist.getName();
        this.kind = playlist.getKind();
        this.genre = playlist.getGenre();
        this.maxDurationSeconds = playlist.getMaxDurationSeconds();
        this.songs = playlist.getSongs().stream().map(SongResponse::new).toList();
    }
}