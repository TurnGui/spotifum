package com.spotifum.controller;

import java.util.HashMap;
import java.util.Map;

import com.spotifum.model.MusicCatalog;
import com.spotifum.model.Song;
import com.spotifum.model.User;
import com.spotifum.model.UserRepository;
import com.spotifum.view.LeaderboardView;

public final class LeaderboardController {
    private LeaderboardController() {
    }

    public static void run() {
        while (true) {
            int option = LeaderboardView.show();
            switch (option) {
                case 1 -> showMostPlayedSong();
                case 2 -> showMostListenedArtist();
                case 3 -> showMostPlayedGenre();
                case 4 -> showMostActiveUser();
                case 0 -> {
                    return;
                }
                default -> {
                }
            }
        }
    }

    private static void showMostPlayedSong() {
        Song top = MusicCatalog.getAllSongs().stream()
                .max(java.util.Comparator.comparingInt(Song::getPlayCount))
                .filter(song -> song.getPlayCount() > 0)
                .orElse(null);
        LeaderboardView.showMostPlayedSong(top);
    }

    private static void showMostListenedArtist() {
        Map<String, Integer> playsByArtist = new HashMap<>();
        for (Song song : MusicCatalog.getAllSongs()) {
            playsByArtist.merge(song.getArtist(), song.getPlayCount(), Integer::sum);
        }
        Map.Entry<String, Integer> top = topEntry(playsByArtist);
        LeaderboardView.showMostListenedArtist(top == null ? null : top.getKey(), top == null ? 0 : top.getValue());
    }

    private static void showMostPlayedGenre() {
        Map<String, Integer> playsByGenre = new HashMap<>();
        for (Song song : MusicCatalog.getAllSongs()) {
            playsByGenre.merge(song.getGenre(), song.getPlayCount(), Integer::sum);
        }
        Map.Entry<String, Integer> top = topEntry(playsByGenre);
        LeaderboardView.showMostPlayedGenre(top == null ? null : top.getKey(), top == null ? 0 : top.getValue());
    }

    private static void showMostActiveUser() {
        User top = UserRepository.getInstance().getAll().values().stream()
                .max(java.util.Comparator.comparingInt(User::getTotalSongsPlayed))
                .filter(user -> user.getTotalSongsPlayed() > 0)
                .orElse(null);
        LeaderboardView.showMostActiveUser(top);
    }

    private static Map.Entry<String, Integer> topEntry(Map<String, Integer> counts) {
        return counts.entrySet().stream()
                .filter(e -> e.getValue() > 0)
                .max(Map.Entry.comparingByValue())
                .orElse(null);
    }
}
