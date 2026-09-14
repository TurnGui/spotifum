package com.spotifum.controller;

import com.spotifum.model.UserRepository;
import com.spotifum.persistence.FileStorageService;

/** Central place for the data file locations and the save operations triggered after every mutation. */
public final class PersistenceController {

    public static final String USERS_FILE = "data/users.dat";
    public static final String SONGS_FILE = "data/songs.dat";
    public static final String ALBUMS_FILE = "data/albums.dat";

    private PersistenceController() {
    }

    public static void saveUsers() {
        FileStorageService.saveUsers(USERS_FILE, UserRepository.getInstance());
    }
}
