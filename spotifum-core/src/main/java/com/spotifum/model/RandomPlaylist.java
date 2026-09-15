package com.spotifum.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** A quick mix: a fixed number of tracks drawn at random from a source pool. */
public class RandomPlaylist extends Playlist {
    private static final long serialVersionUID = 1L;

    public RandomPlaylist(String name, List<Song> sourcePool, int size) {
        super(name);
        List<Song> shuffled = new ArrayList<>(sourcePool);
        Collections.shuffle(shuffled);
        for (int i = 0; i < Math.min(size, shuffled.size()); i++) {
            songs.add(shuffled.get(i).clone());
        }
    }

    @Override
    public String getKind() {
        return "Random Mix";
    }

    @Override
    public Playlist clone() {
        RandomPlaylist copy = new RandomPlaylist(name, List.of(), 0);
        for (Song song : this.songs) {
            copy.addSong(song);
        }
        return copy;
    }
}
