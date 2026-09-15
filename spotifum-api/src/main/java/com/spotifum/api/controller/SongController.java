package com.spotifum.api.controller;

import com.spotifum.api.dto.SongResponse;
import com.spotifum.api.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final CatalogService catalogService;

    public SongController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public ResponseEntity<List<SongResponse>> getAllSongs() {
        return ResponseEntity.ok(catalogService.getAllSongs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponse> getSongById(@PathVariable Long id) {
        return ResponseEntity.ok(catalogService.getSongById(id));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<SongResponse>> getSongsByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(catalogService.getSongsByGenre(genre));
    }

    @PostMapping("/{id}/play")
    public ResponseEntity<SongResponse> playSong(@PathVariable Long id) {
        return ResponseEntity.ok(catalogService.playSong(id));
    }
}