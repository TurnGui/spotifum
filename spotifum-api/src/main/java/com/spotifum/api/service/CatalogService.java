package com.spotifum.api.service;

import com.spotifum.api.dto.AlbumResponse;
import com.spotifum.api.dto.SongResponse;
import com.spotifum.api.exception.ResourceNotFoundException;
import com.spotifum.api.model.Song;
import com.spotifum.api.repository.AlbumRepository;
import com.spotifum.api.repository.SongRepository;
import com.spotifum.api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public CatalogService(SongRepository songRepository, AlbumRepository albumRepository, UserRepository userRepository) {
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
    }

    public List<SongResponse> getAllSongs() {
        return songRepository.findAll().stream().map(SongResponse::new).toList();
    }

    public SongResponse getSongById(Long id) {
        return songRepository.findById(id)
                .map(SongResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
    }

    public List<SongResponse> getSongsByGenre(String genre) {
        return songRepository.findByGenre(genre).stream().map(SongResponse::new).toList();
    }

    public List<AlbumResponse> getAllAlbums() {
        return albumRepository.findAll().stream().map(AlbumResponse::new).toList();
    }

    public AlbumResponse getAlbumById(Long id) {
        return albumRepository.findById(id)
                .map(AlbumResponse::new)
                .orElseThrow(() -> new ResourceNotFoundException("Album not found"));
    }

    public SongResponse playSong(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        song.setPlayCount(song.getPlayCount() + 1);

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        int points = switch (user.getPlan()) {
            case "PREMIUM" -> 2;
            case "PREMIUM_PLUS" -> (int) (song.getDurationSeconds() * 0.1);
            default -> 1;
        };
        user.setPoints(user.getPoints() + points);

        songRepository.save(song);
        userRepository.save(user);

        return new SongResponse(song);
    }
}