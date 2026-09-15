package com.spotifum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.spotifum.controller.NavigationController;
import com.spotifum.controller.PersistenceController;
import com.spotifum.model.Album;
import com.spotifum.model.MusicCatalog;
import com.spotifum.model.AlbumCatalog;
import com.spotifum.model.Song;
import com.spotifum.model.SubscriptionPlan;
import com.spotifum.model.User;
import com.spotifum.model.UserRepository;
import com.spotifum.persistence.FileStorageService;

/**
 * Entry point: loads persisted state (or seeds a fresh sample dataset on
 * first run), runs the console menu loop, and guarantees a single save point
 * on the way out via {@link #shutdown()}.
 */
public final class SpotifUmApplication {

    public static void main(String[] args) {
        try {
            Files.createDirectories(Path.of("data"));
        } catch (IOException e) {
            System.err.println("Could not create the data directory: " + e.getMessage());
        }

        loadOrSeedSongs();
        loadOrSeedAlbums();
        loadOrSeedUsers();

        NavigationController.run();
        shutdown();
    }

    /** Persists every store and terminates the JVM. The only place the process should exit from. */
    public static void shutdown() {
        PersistenceController.saveUsers();
        FileStorageService.saveSongs(PersistenceController.SONGS_FILE, MusicCatalog.getAllSongs());
        FileStorageService.saveAlbums(PersistenceController.ALBUMS_FILE, AlbumCatalog.getAllAlbums());
        System.exit(0);
    }

    private static void loadOrSeedUsers() {
        if (new File(PersistenceController.USERS_FILE).exists()) {
            UserRepository loaded = FileStorageService.loadUsers(PersistenceController.USERS_FILE);
            if (loaded != null) {
                UserRepository.setInstance(loaded);
                return;
            }
        }
        seedDefaultUser();
    }

    private static void seedDefaultUser() {
        User admin = new User("Admin", "admin@spotifum.com", "Rua XPTO Mix 123, Braga", SubscriptionPlan.PREMIUM_PLUS);
        UserRepository.getInstance().add(admin);
        PersistenceController.saveUsers();
    }

    private static void loadOrSeedSongs() {
        if (new File(PersistenceController.SONGS_FILE).exists()) {
            List<Song> loaded = FileStorageService.loadSongs(PersistenceController.SONGS_FILE);
            if (loaded != null) {
                MusicCatalog.setSongs(loaded);
                return;
            }
        }
        seedSampleSongs();
    }

    private static void seedSampleSongs() {
        List<Song> songs = new ArrayList<>(List.of(
                Song.builder("Starlight Drive", "Nova Reyes")
                        .genre("Synthwave").label("Horizon Records")
                        .lyrics("Chasing the neon skyline again...")
                        .durationSeconds(214).build(),
                Song.builder("Midnight Bloom", "Nova Reyes")
                        .genre("Synthwave").label("Horizon Records")
                        .lyrics("Petals of light in the dark...")
                        .durationSeconds(198).build(),
                Song.builder("Coastal Echoes", "The Salt Line")
                        .genre("Indie Rock").label("Tidewater Music")
                        .lyrics("Waves keep calling my name...")
                        .durationSeconds(231).build(),
                Song.builder("Quiet Static", "The Salt Line")
                        .genre("Indie Rock").label("Tidewater Music")
                        .lyrics("Static hum beneath the porch light...")
                        .durationSeconds(205).build(),
                Song.builder("Concrete Bloom", "MC Fresco")
                        .genre("Hip-Hop").label("Blockwork Records")
                        .lyrics("Built this from concrete and will...")
                        .durationSeconds(187).explicit(true).build(),
                Song.builder("Late Night Freight", "MC Fresco")
                        .genre("Hip-Hop").label("Blockwork Records")
                        .lyrics("Running numbers past midnight...")
                        .durationSeconds(176).explicit(true).build(),
                Song.builder("Firelight Waltz", "Odessa Quartet")
                        .genre("Classical").label("Aria Editions")
                        .lyrics("(instrumental)")
                        .durationSeconds(264).build(),
                Song.builder("Glasswing", "Odessa Quartet")
                        .genre("Classical").label("Aria Editions")
                        .lyrics("(instrumental)")
                        .durationSeconds(241).build(),
                Song.builder("Backroad Gospel", "June Carraway")
                        .genre("Country").label("Red Clay Records")
                        .lyrics("Dust on the dashboard, prayer on my lips...")
                        .durationSeconds(219).build(),
                Song.builder("Static & Thunder", "Voltage Kids")
                        .genre("Pop Rock").label("Amplify Records")
                        .lyrics("We're the static and the thunder...")
                        .durationSeconds(192).build()
        ));

        songs.forEach(MusicCatalog::addSong);
        FileStorageService.saveSongs(PersistenceController.SONGS_FILE, MusicCatalog.getAllSongs());
    }

    private static void loadOrSeedAlbums() {
        if (new File(PersistenceController.ALBUMS_FILE).exists()) {
            List<Album> loaded = FileStorageService.loadAlbums(PersistenceController.ALBUMS_FILE);
            if (loaded != null) {
                AlbumCatalog.setAlbums(loaded);
                return;
            }
        }
        seedSampleAlbums();
    }

    private static void seedSampleAlbums() {
        Album synthwave = new Album("Horizon Line", "Nova Reyes", 2023);
        MusicCatalog.findByTitle("Starlight Drive").ifPresent(synthwave::addSong);
        MusicCatalog.findByTitle("Midnight Bloom").ifPresent(synthwave::addSong);
        AlbumCatalog.addAlbum(synthwave);

        Album indie = new Album("Low Tide", "The Salt Line", 2022);
        MusicCatalog.findByTitle("Coastal Echoes").ifPresent(indie::addSong);
        MusicCatalog.findByTitle("Quiet Static").ifPresent(indie::addSong);
        AlbumCatalog.addAlbum(indie);

        Album hiphop = new Album("Freight Hours", "MC Fresco", 2024);
        MusicCatalog.findByTitle("Concrete Bloom").ifPresent(hiphop::addSong);
        MusicCatalog.findByTitle("Late Night Freight").ifPresent(hiphop::addSong);
        AlbumCatalog.addAlbum(hiphop);

        FileStorageService.saveAlbums(PersistenceController.ALBUMS_FILE, AlbumCatalog.getAllAlbums());
    }
}
