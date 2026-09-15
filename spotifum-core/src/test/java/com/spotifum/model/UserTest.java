package com.spotifum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    @Test
    void premiumPlusUserStartsWithSignUpBonus() {
        User user = new User("Ada", "ada@example.com", "1 Analytical Engine Way", SubscriptionPlan.PREMIUM_PLUS);

        assertEquals(100.0, user.getPoints());
    }

    @Test
    void recordPlaybackIncrementsCountAndAwardsPoints() {
        User user = new User("Grace", "grace@example.com", "Compiler Lane", SubscriptionPlan.PREMIUM);
        Song song = Song.builder("Test Track", "Test Artist").build();

        user.recordPlayback(song);

        assertEquals(1, user.getTotalSongsPlayed());
        assertEquals(10.0, user.getPoints());
        assertEquals(1, song.getPlayCount());
    }

    @Test
    void addingToFavoritesCreatesAnAutoManagedPlaylist() {
        User user = new User("Grace", "grace@example.com", "Compiler Lane", SubscriptionPlan.PREMIUM);
        Song song = Song.builder("Test Track", "Test Artist").build();

        user.addToFavorites(song);
        user.addToFavorites(song);

        assertEquals(1, user.getFavoriteSongs().size());
        Playlist favorites = user.findPlaylistByName(FavoritesPlaylist.NAME).orElseThrow();
        assertEquals(1, favorites.getSongs().size());
    }

    @Test
    void cloneDeepCopiesPlaylistsAndFavorites() {
        User original = new User("Grace", "grace@example.com", "Compiler Lane", SubscriptionPlan.PREMIUM);
        original.addToFavorites(Song.builder("Test Track", "Test Artist").build());

        User clone = original.clone();
        clone.addToFavorites(Song.builder("Another Track", "Another Artist").build());

        assertNotSame(original, clone);
        assertEquals(1, original.getFavoriteSongs().size());
        assertEquals(2, clone.getFavoriteSongs().size());
    }

    @Test
    void freeUserIsNotPremium() {
        User user = new User("Bob", "bob@example.com", "Nowhere", SubscriptionPlan.FREE);

        assertFalse(user.isPremium());
        assertTrue(new User("Amy", "amy@example.com", "Somewhere", SubscriptionPlan.PREMIUM).isPremium());
    }
}
