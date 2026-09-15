package com.spotifum.api.controller;

import com.spotifum.api.dto.AlbumResponse;
import com.spotifum.api.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
public class AlbumController {

    private final CatalogService catalogService;

    public AlbumController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponse>> getAllAlbums() {
        return ResponseEntity.ok(catalogService.getAllAlbums());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponse> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.ok(catalogService.getAlbumById(id));
    }
}