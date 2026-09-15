package com.spotifum.api.service;

import com.spotifum.api.dto.LeaderboardResponse;
import com.spotifum.api.model.Song;
import com.spotifum.api.model.User;
import com.spotifum.api.repository.SongRepository;
import com.spotifum.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public LeaderboardService(SongRepository songRepository, UserRepository userRepository) {
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    public LeaderboardResponse getLeaderboard() {
        List<Song> songs = songRepository.findAll();
        List<User> users = userRepository.findAll();

        String mostPlayedSong = songs.stream()
                .max(Comparator.comparingInt(Song::getPlayCount))
                .map(s -> s.getTitle() + " - " + s.getArtist())
                .orElse("N/A");

        String mostPlayedArtist = songs.stream()
                .collect(Collectors.groupingBy(Song::getArtist,
                        Collectors.summingInt(Song::getPlayCount)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        String mostPlayedGenre = songs.stream()
                .collect(Collectors.groupingBy(Song::getGenre,
                        Collectors.summingInt(Song::getPlayCount)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        String mostActiveListener = users.stream()
                .max(Comparator.comparingInt(User::getPoints))
                .map(u -> u.getName() + " (" + u.getPoints() + " pts)")
                .orElse("N/A");

        return new LeaderboardResponse(mostPlayedSong, mostPlayedArtist, mostPlayedGenre, mostActiveListener);
    }
}