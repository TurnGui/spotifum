package com.spotifum.model;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaylistTest {

    private static final Song SONG_A = Song.builder("A", "Artist").durationSeconds(100).build();
    private static final Song SONG_B = Song.builder("B", "Artist").durationSeconds(150).build();

    @Test
    void customPlaylistAccumulatesTotalDuration() {
        CustomPlaylist playlist = new CustomPlaylist("My Mix", false);
        playlist.addSong(SONG_A);
        playlist.addSong(SONG_B);

        assertEquals(250, playlist.getTotalDurationSeconds());
        assertEquals(2, playlist.getSongs().size());
    }

    @Test
    void addSongStoresADefensiveCopyNotTheOriginalReference() {
        CustomPlaylist playlist = new CustomPlaylist("My Mix", false);
        playlist.addSong(SONG_A);

        assertNotSame(SONG_A, playlist.getSongs().get(0));
        assertEquals(SONG_A.getTitle(), playlist.getSongs().get(0).getTitle());
    }

    @Test
    void randomPlaylistNeverExceedsRequestedSize() {
        RandomPlaylist playlist = new RandomPlaylist("Quick Mix", List.of(SONG_A, SONG_B), 5);

        assertEquals(2, playlist.getSongs().size());
    }

    @Test
    void clonedPlaylistIsIndependentOfTheOriginal() {
        CustomPlaylist original = new CustomPlaylist("My Mix", false);
        original.addSong(SONG_A);

        Playlist clone = original.clone();
        clone.addSong(SONG_B);

        assertEquals(1, original.getSongs().size());
        assertEquals(2, clone.getSongs().size());
        assertTrue(clone instanceof CustomPlaylist);
    }

    @Test
    void genreDurationPlaylistKeepsItsFilterMetadata() {
        GenreDurationPlaylist playlist = new GenreDurationPlaylist("Chill", "Lo-fi", 600);

        assertEquals("Lo-fi", playlist.getGenre());
        assertEquals(600, playlist.getMaxDurationSeconds());
    }
}
