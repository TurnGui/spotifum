package com.spotifum.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.spotifum.model.MusicCatalog;
import com.spotifum.model.Song;
import com.spotifum.model.User;
import com.spotifum.view.ConsoleIO;
import com.spotifum.view.PlaybackView;
import com.spotifum.view.PlaybackView.PostPlayAction;

public final class PlaybackController {
    private PlaybackController() {
    }

    public static void playSong(User user) {
        String title = PlaybackView.promptSongChoice(MusicCatalog.getAllSongs());
        if (title == null) {
            return;
        }

        Song song = MusicCatalog.findByTitle(title).orElse(null);
        if (song == null) {
            PlaybackView.showSongNotFound();
            ConsoleIO.waitForEnter();
            return;
        }

        user.recordPlayback(song);
        PersistenceController.saveUsers();
        PlaybackView.showNowPlaying(song);

        PostPlayAction action = PlaybackView.promptPostPlayAction(user.isPremium());
        if (action == PostPlayAction.ADD_TO_FAVORITES) {
            if (user.isPremium()) {
                user.addToFavorites(song);
                PersistenceController.saveUsers();
            } else {
                PlaybackView.showNotPremium();
            }
        }
    }

    /** Plays every song in {@code songs} in order (optionally shuffled first), crediting points as it goes. */
    public static void playQueue(User user, List<Song> songs, boolean shuffle) {
        List<Song> queue = new ArrayList<>(songs);
        if (shuffle) {
            Collections.shuffle(queue);
        }

        for (Song song : queue) {
            user.recordPlayback(song);
            PlaybackView.showNowPlaying(song);
            if (!PlaybackView.promptContinueQueue()) {
                break;
            }
        }
        PersistenceController.saveUsers();
    }
}
