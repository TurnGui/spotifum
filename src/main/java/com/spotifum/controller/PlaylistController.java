package com.spotifum.controller;

import java.util.List;

import com.spotifum.model.CustomPlaylist;
import com.spotifum.model.GenreDurationPlaylist;
import com.spotifum.model.MusicCatalog;
import com.spotifum.model.Playlist;
import com.spotifum.model.RandomPlaylist;
import com.spotifum.model.Song;
import com.spotifum.model.User;
import com.spotifum.view.PlaylistView;
import com.spotifum.view.PlaylistView.CreateChoice;
import com.spotifum.view.UserMenuView;

public final class PlaylistController {
    private PlaylistController() {
    }

    public static void browsePlaylists(User user) {
        List<Playlist> playlists = user.getPlaylists();
        if (playlists.isEmpty()) {
            PlaylistView.showNoPlaylists();
            return;
        }

        Playlist chosen = PlaylistView.promptPlaylistChoice(playlists);
        if (chosen == null) {
            return;
        }
        if (chosen.getSongs().isEmpty()) {
            PlaylistView.showEmptyPlaylist();
            return;
        }

        boolean play = PlaylistView.promptPlayPlaylist(chosen);
        if (!play) {
            return;
        }

        boolean shuffle = chosen instanceof RandomPlaylist
                || chosen instanceof GenreDurationPlaylist
                || (chosen instanceof CustomPlaylist custom && custom.isShuffle());
        PlaybackController.playQueue(user, chosen.getSongs(), shuffle);
    }

    public static void createPlaylistMenu(User user) {
        CreateChoice choice = PlaylistView.showCreateMenu();
        switch (choice) {
            case CUSTOM -> createCustomPlaylist(user);
            case RANDOM -> createRandomPlaylist(user);
            case GENRE_DURATION -> createGenreDurationPlaylist(user);
            case BACK -> {
            }
        }
    }

    public static void showExplicitSongsPlaylist(User user) {
        if (!user.isTopTier()) {
            UserMenuView.showPremiumPlusRequired();
            return;
        }

        List<Song> explicitSongs = MusicCatalog.getAllSongs().stream().filter(Song::isExplicit).toList();
        if (explicitSongs.isEmpty()) {
            PlaylistView.showNoExplicitSongs();
            return;
        }

        while (true) {
            PlaylistView.showExplicitSongs(explicitSongs);
            int choice = PlaylistView.promptExplicitSongChoice();
            if (choice == 0) {
                return;
            }
            if (choice < 1 || choice > explicitSongs.size()) {
                PlaylistView.showInvalidChoice();
                continue;
            }
            user.recordPlayback(explicitSongs.get(choice - 1));
            PersistenceController.saveUsers();
        }
    }

    private static void createCustomPlaylist(User user) {
        if (!user.isPremium()) {
            UserMenuView.showPremiumRequired();
            return;
        }

        String name = PlaylistView.promptPlaylistName();
        boolean shuffle = PlaylistView.promptShuffle();
        List<Song> chosen = PlaylistView.selectSongs(MusicCatalog.getAllSongs(), name, null);
        if (chosen.isEmpty()) {
            PlaylistView.showPlaylistNotCreatedEmpty();
            return;
        }

        CustomPlaylist playlist = new CustomPlaylist(name, shuffle);
        chosen.forEach(playlist::addSong);
        user.addPlaylist(playlist);
        PersistenceController.saveUsers();
        PlaylistView.showPlaylistCreated(name);
    }

    private static void createRandomPlaylist(User user) {
        List<Song> catalog = MusicCatalog.getAllSongs();
        String name = PlaylistView.promptPlaylistName();
        int size = PlaylistView.promptRandomPlaylistSize(catalog.size());
        if (size <= 0 || catalog.isEmpty()) {
            PlaylistView.showPlaylistNotCreatedEmpty();
            return;
        }

        RandomPlaylist playlist = new RandomPlaylist(name, catalog, size);
        user.addPlaylist(playlist);
        PersistenceController.saveUsers();
        PlaylistView.showPlaylistCreated(name);
    }

    private static void createGenreDurationPlaylist(User user) {
        if (!user.isPremium()) {
            UserMenuView.showPremiumRequired();
            return;
        }

        String name = PlaylistView.promptPlaylistName();
        String genre = PlaylistView.promptGenre();
        int maxDuration = PlaylistView.promptMaxDurationSeconds();

        List<Song> matching = MusicCatalog.getAllSongs().stream()
                .filter(s -> s.getGenre().equalsIgnoreCase(genre))
                .toList();
        if (matching.isEmpty()) {
            PlaylistView.showNoSongsForGenre(genre);
            return;
        }

        List<Song> chosen = PlaylistView.selectSongs(matching, name, maxDuration);
        if (chosen.isEmpty()) {
            PlaylistView.showPlaylistNotCreatedEmpty();
            return;
        }

        GenreDurationPlaylist playlist = new GenreDurationPlaylist(name, genre, maxDuration);
        chosen.forEach(playlist::addSong);
        user.addPlaylist(playlist);
        PersistenceController.saveUsers();
        PlaylistView.showPlaylistCreated(name);
    }
}
