package com.spotifum.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

import com.spotifum.model.Album;
import com.spotifum.model.Song;
import com.spotifum.model.UserRepository;

/**
 * Persists application state to disk via plain Java serialization. Intentionally
 * simple: this is a learning project, not a service backed by a real database,
 * and the goal here is to demonstrate {@link java.io.Serializable} I/O cleanly
 * rather than to reinvent a persistence framework.
 */
public final class FileStorageService {

    private FileStorageService() {
    }

    public static void saveUsers(String path, UserRepository repository) {
        write(path, repository);
    }

    public static UserRepository loadUsers(String path) {
        Object loaded = read(path);
        return loaded instanceof UserRepository repository ? repository : null;
    }

    public static void saveSongs(String path, List<Song> songs) {
        write(path, (Serializable) songs);
    }

    @SuppressWarnings("unchecked")
    public static List<Song> loadSongs(String path) {
        Object loaded = read(path);
        return loaded instanceof List<?> ? (List<Song>) loaded : null;
    }

    public static void saveAlbums(String path, List<Album> albums) {
        write(path, (Serializable) albums);
    }

    @SuppressWarnings("unchecked")
    public static List<Album> loadAlbums(String path) {
        Object loaded = read(path);
        return loaded instanceof List<?> ? (List<Album>) loaded : null;
    }

    private static void write(String path, Serializable data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(data);
        } catch (IOException e) {
            System.err.println("Could not save '" + path + "': " + e.getMessage());
        }
    }

    private static Object read(String path) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load '" + path + "': " + e.getMessage());
            return null;
        }
    }
}
