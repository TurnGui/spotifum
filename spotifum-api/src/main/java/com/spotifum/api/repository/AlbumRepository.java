package com.spotifum.api.repository;

import com.spotifum.api.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtist(String artist);
}
