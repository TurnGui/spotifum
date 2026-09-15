package com.spotifum.model;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MusicCatalogTest {

    @Test
    void findByTitleIsCaseInsensitive() {
        Song song = Song.builder("Starlight Drive", "Nova Reyes").build();
        MusicCatalog.setSongs(List.of(song));

        assertEquals(song, MusicCatalog.findByTitle("starlight drive").orElseThrow());
    }

    @Test
    void findByTitleReturnsEmptyWhenMissing() {
        MusicCatalog.setSongs(List.of());

        assertTrue(MusicCatalog.findByTitle("nothing here").isEmpty());
    }
}
