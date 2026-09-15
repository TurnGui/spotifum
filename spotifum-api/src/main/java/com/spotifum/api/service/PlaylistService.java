package com.spotifum.api.service;

import com.spotifum.api.dto.CreatePlaylistRequest;
import com.spotifum.api.dto.PlaylistResponse;
import com.spotifum.api.exception.InvalidRequestException;
import com.spotifum.api.exception.ResourceNotFoundException;
import com.spotifum.api.model.Playlist;
import com.spotifum.api.model.Song;
import com.spotifum.api.model.User;
import com.spotifum.api.repository.PlaylistRepository;
import com.spotifum.api.repository.SongRepository;
import com.spotifum.api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public PlaylistService(PlaylistRepository playlistRepository, SongRepository songRepository, UserRepository userRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public List<PlaylistResponse> getMyPlaylists() {
        User user = getCurrentUser();
        return playlistRepository.findByOwnerId(user.getId())
                .stream().map(PlaylistResponse::new).toList();
    }

    public PlaylistResponse getPlaylistById(Long id) {
        User user = getCurrentUser();
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlist.getOwner().getId().equals(user.getId())) {
            throw new InvalidRequestException("Access denied");
        }
        return new PlaylistResponse(playlist);
    }

    public PlaylistResponse createPlaylist(CreatePlaylistRequest request) {
        User user = getCurrentUser();

        Playlist playlist = new Playlist();
        playlist.setName(request.getName());
        playlist.setKind(request.getKind());
        playlist.setOwner(user);

        switch (request.getKind()) {
            case "RANDOM" -> {
                List<Song> all = songRepository.findAll();
                Collections.shuffle(all);
                playlist.setSongs(all.stream().limit(5).toList());
            }
            case "GENRE_DURATION" -> {
                if (request.getGenre() == null) throw new InvalidRequestException("Genre is required");
                playlist.setGenre(request.getGenre());
                playlist.setMaxDurationSeconds(request.getMaxDurationSeconds());
                List<Song> songs = songRepository.findByGenre(request.getGenre());
                int total = 0;
                List<Song> filtered = new java.util.ArrayList<>();
                for (Song s : songs) {
                    if (total + s.getDurationSeconds() <= request.getMaxDurationSeconds()) {
                        filtered.add(s);
                        total += s.getDurationSeconds();
                    }
                }
                playlist.setSongs(filtered);
            }
            default -> playlist.setSongs(new java.util.ArrayList<>());
        }

        playlistRepository.save(playlist);
        return new PlaylistResponse(playlist);
    }

    public PlaylistResponse addSong(Long playlistId, Long songId) {
        User user = getCurrentUser();
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlist.getOwner().getId().equals(user.getId())) {
            throw new InvalidRequestException("Access denied");
        }
        if (!playlist.getKind().equals("CUSTOM") && !playlist.getKind().equals("FAVORITES")) {
            throw new InvalidRequestException("Can only add songs to CUSTOM or FAVORITES playlists");
        }
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        playlist.getSongs().add(song);
        playlistRepository.save(playlist);
        return new PlaylistResponse(playlist);
    }

    public PlaylistResponse removeSong(Long playlistId, Long songId) {
        User user = getCurrentUser();
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlist.getOwner().getId().equals(user.getId())) {
            throw new InvalidRequestException("Access denied");
        }
        playlist.getSongs().removeIf(s -> s.getId().equals(songId));
        playlistRepository.save(playlist);
        return new PlaylistResponse(playlist);
    }

    public void deletePlaylist(Long id) {
        User user = getCurrentUser();
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist not found"));
        if (!playlist.getOwner().getId().equals(user.getId())) {
            throw new InvalidRequestException("Access denied");
        }
        playlistRepository.delete(playlist);
    }
}