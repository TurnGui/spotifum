package com.spotifum.model;

import java.util.ArrayList;
import java.util.List;

/** The in-memory catalog of albums Premium listeners can add to their personal library. */
public final class AlbumCatalog {
    private static List<Album> albums = new ArrayList<>();

    private AlbumCatalog() {
    }

    public static void addAlbum(Album album) {
        albums.add(album);
    }

    public static List<Album> getAllAlbums() {
        return new ArrayList<>(albums);
    }

    public static void setAlbums(List<Album> newCatalog) {
        albums = new ArrayList<>(newCatalog);
    }
}
