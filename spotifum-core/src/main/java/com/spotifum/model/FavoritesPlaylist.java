package com.spotifum.model;

import java.util.List;

/**
 * The auto-maintained collection of songs a listener has explicitly favorited
 * while playing music. Played back in the order the tracks were favorited.
 */
public class FavoritesPlaylist extends Playlist {
    private static final long serialVersionUID = 1L;

    public static final String NAME = "Favorites";

    public FavoritesPlaylist() {
        super(NAME);
    }

    private FavoritesPlaylist(List<Song> songs) {
        super(NAME);
        songs.forEach(this::addSong);
    }

    @Override
    public String getKind() {
        return "Favorites";
    }

    @Override
    public Playlist clone() {
        return new FavoritesPlaylist(this.songs);
    }
}
