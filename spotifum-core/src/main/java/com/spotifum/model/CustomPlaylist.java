package com.spotifum.model;

/** A manually curated playlist, optionally shuffled when it is played back. */
public class CustomPlaylist extends Playlist {
    private static final long serialVersionUID = 1L;

    private final boolean shuffle;

    public CustomPlaylist(String name, boolean shuffle) {
        super(name);
        this.shuffle = shuffle;
    }

    public boolean isShuffle() {
        return shuffle;
    }

    @Override
    public String getKind() {
        return shuffle ? "Custom, shuffled" : "Custom, in order";
    }

    @Override
    public Playlist clone() {
        CustomPlaylist copy = new CustomPlaylist(name, shuffle);
        for (Song song : this.songs) {
            copy.addSong(song);
        }
        return copy;
    }
}
