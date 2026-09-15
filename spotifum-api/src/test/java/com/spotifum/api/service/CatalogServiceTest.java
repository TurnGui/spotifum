package com.spotifum.api.service;

import com.spotifum.api.dto.SongResponse;
import com.spotifum.api.exception.ResourceNotFoundException;
import com.spotifum.api.model.Song;
import com.spotifum.api.model.User;
import com.spotifum.api.repository.AlbumRepository;
import com.spotifum.api.repository.SongRepository;
import com.spotifum.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CatalogServiceTest {

    @Mock private SongRepository songRepository;
    @Mock private AlbumRepository albumRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private CatalogService catalogService;

    private Song song;
    private User user;

    @BeforeEach
    void setUp() {
        song = new Song();
        song.setTitle("Money");
        song.setArtist("Pink Floyd");
        song.setGenre("Rock");
        song.setDurationSeconds(382);
        song.setPlayCount(0);

        user = new User();
        user.setEmail("test@spotifum.com");
        user.setPlan("FREE");
        user.setPoints(0);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("test@spotifum.com");
        SecurityContext ctx = mock(SecurityContext.class);
        when(ctx.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(ctx);
    }

    @Test
    void getAllSongs_returnsList() {
        when(songRepository.findAll()).thenReturn(List.of(song));

        List<SongResponse> result = catalogService.getAllSongs();

        assertEquals(1, result.size());
        assertEquals("Money", result.get(0).getTitle());
    }

    @Test
    void getSongById_notFound_throwsException() {
        when(songRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> catalogService.getSongById(1L));
    }

    @Test
    void playSong_incrementsPlayCount() {
        when(songRepository.findById(any())).thenReturn(Optional.of(song));
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(songRepository.save(any())).thenReturn(song);
        when(userRepository.save(any())).thenReturn(user);

        SongResponse result = catalogService.playSong(1L);

        assertEquals(1, result.getPlayCount());
        assertEquals(1, user.getPoints());
    }
}