package com.spotifum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class SongTest {

    @Test
    void builderAppliesDefaultsForOptionalFields() {
        Song song = Song.builder("Test Track", "Test Artist").build();

        assertEquals("Independent", song.getLabel());
        assertEquals("Unknown", song.getGenre());
        assertFalse(song.isExplicit());
        assertEquals(0, song.getPlayCount());
    }

    @Test
    void playIncrementsPlayCount() {
        Song song = Song.builder("Test Track", "Test Artist").build();

        song.play();
        song.play();

        assertEquals(2, song.getPlayCount());
    }

    @Test
    void cloneCopiesStateButIsAnIndependentInstance() {
        Song original = Song.builder("Test Track", "Test Artist").durationSeconds(180).build();
        original.play();

        Song clone = original.clone();
        clone.play();

        assertNotSame(original, clone);
        assertEquals(1, original.getPlayCount());
        assertEquals(2, clone.getPlayCount());
    }
}
