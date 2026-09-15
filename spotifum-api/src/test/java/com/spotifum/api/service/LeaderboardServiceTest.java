package com.spotifum.api.service;

import com.spotifum.api.dto.LeaderboardResponse;
import com.spotifum.api.model.Song;
import com.spotifum.api.model.User;
import com.spotifum.api.repository.SongRepository;
import com.spotifum.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaderboardServiceTest {

    @Mock private SongRepository songRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private LeaderboardService leaderboardService;

    @Test
    void getLeaderboard_returnsCorrectWinners() {
        Song s1 = new Song();
        s1.setTitle("Money");
        s1.setArtist("Pink Floyd");
        s1.setGenre("Rock");
        s1.setPlayCount(5);

        Song s2 = new Song();
        s2.setTitle("Thriller");
        s2.setArtist("Michael Jackson");
        s2.setGenre("Pop");
        s2.setPlayCount(2);

        User u = new User();
        u.setName("Test User");
        u.setPoints(10);

        when(songRepository.findAll()).thenReturn(List.of(s1, s2));
        when(userRepository.findAll()).thenReturn(List.of(u));

        LeaderboardResponse result = leaderboardService.getLeaderboard();

        assertEquals("Money - Pink Floyd", result.getMostPlayedSong());
        assertEquals("Pink Floyd", result.getMostPlayedArtist());
        assertEquals("Rock", result.getMostPlayedGenre());
        assertEquals("Test User (10 pts)", result.getMostActiveListener());
    }

    @Test
    void getLeaderboard_noData_returnsNA() {
        when(songRepository.findAll()).thenReturn(List.of());
        when(userRepository.findAll()).thenReturn(List.of());

        LeaderboardResponse result = leaderboardService.getLeaderboard();

        assertEquals("N/A", result.getMostPlayedSong());
        assertEquals("N/A", result.getMostActiveListener());
    }
}