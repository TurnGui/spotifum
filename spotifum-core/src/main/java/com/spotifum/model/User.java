package com.spotifum.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** A registered listener: their profile, subscription, loyalty points and personal collections. */
public class User implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String email;
    private final String address;
    private final SubscriptionPlan plan;
    private double points;
    private int totalSongsPlayed;
    private final List<Playlist> playlists;
    private final List<Album> library;
    private final List<Song> favoriteSongs;

    public User(String name, String email, String address, SubscriptionPlan plan) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.plan = plan;
        this.points = plan.signUpBonus();
        this.totalSongsPlayed = 0;
        this.playlists = new ArrayList<>();
        this.library = new ArrayList<>();
        this.favoriteSongs = new ArrayList<>();
    }

    private User(User source) {
        this.name = source.name;
        this.email = source.email;
        this.address = source.address;
        this.plan = source.plan;
        this.points = source.points;
        this.totalSongsPlayed = source.totalSongsPlayed;

        this.playlists = new ArrayList<>();
        for (Playlist playlist : source.playlists) {
            this.playlists.add(playlist.clone());
        }

        this.library = new ArrayList<>();
        for (Album album : source.library) {
            this.library.add(album.clone());
        }

        this.favoriteSongs = new ArrayList<>();
        for (Song song : source.favoriteSongs) {
            this.favoriteSongs.add(song.clone());
        }
    }

    /** Plays a track and credits the loyalty points earned under the user's plan. */
    public void recordPlayback(Song song) {
        song.play();
        totalSongsPlayed++;
        points += plan.pointsForPlay(points);
    }

    public boolean isPremium() {
        return plan.isPremium();
    }

    public boolean isTopTier() {
        return plan.isTopTier();
    }

    public void addPlaylist(Playlist playlist) {
        playlists.add(playlist.clone());
    }

    public List<Playlist> getPlaylists() {
        return new ArrayList<>(playlists);
    }

    public Optional<Playlist> findPlaylistByName(String name) {
        return playlists.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Marks a song as a favorite and mirrors it into the user's auto-maintained
     * {@link FavoritesPlaylist}, creating that playlist on first use.
     */
    public void addToFavorites(Song song) {
        if (favoriteSongs.stream().anyMatch(s -> s.getTitle().equalsIgnoreCase(song.getTitle()))) {
            return;
        }
        favoriteSongs.add(song.clone());

        Playlist favorites = findPlaylistByName(FavoritesPlaylist.NAME).orElse(null);
        if (favorites == null) {
            favorites = new FavoritesPlaylist();
            playlists.add(favorites);
        }
        favorites.addSong(song);
    }

    public List<Song> getFavoriteSongs() {
        return new ArrayList<>(favoriteSongs);
    }

    public void addAlbumToLibrary(Album album) {
        library.add(album.clone());
    }

    public List<Album> getLibrary() {
        return new ArrayList<>(library);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public double getPoints() {
        return points;
    }

    public int getTotalSongsPlayed() {
        return totalSongsPlayed;
    }

    @Override
    public String toString() {
        return "\nUser: " + name +
                "\nEmail: " + email +
                "\nAddress: " + address +
                "\nPlan: " + plan +
                "\nPoints: " + points +
                "\nSongs played: " + totalSongsPlayed +
                "\nPlaylists: " + playlists.size() +
                "\nAlbums in library: " + library.size();
    }

    @Override
    public User clone() {
        return new User(this);
    }
}
