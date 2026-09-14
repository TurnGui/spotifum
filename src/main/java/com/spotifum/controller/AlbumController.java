package com.spotifum.controller;

import java.util.List;

import com.spotifum.model.Album;
import com.spotifum.model.AlbumCatalog;
import com.spotifum.model.User;
import com.spotifum.view.AlbumView;
import com.spotifum.view.AlbumView.Choice;
import com.spotifum.view.UserMenuView;

public final class AlbumController {
    private AlbumController() {
    }

    public static void menu(User user) {
        Choice choice = AlbumView.showMenu();
        switch (choice) {
            case BROWSE -> browseCatalog(user);
            case LIBRARY -> viewLibrary(user);
            case BACK -> {
            }
        }
    }

    private static void browseCatalog(User user) {
        List<Album> catalog = AlbumCatalog.getAllAlbums();
        Album chosen = AlbumView.promptAlbumChoice(catalog, "Album catalog:");
        if (chosen == null) {
            return;
        }

        boolean add = AlbumView.promptAddToLibrary(chosen);
        if (!add) {
            return;
        }

        if (!user.isPremium()) {
            UserMenuView.showPremiumRequired();
            return;
        }

        boolean alreadyOwned = user.getLibrary().stream()
                .anyMatch(album -> album.getTitle().equalsIgnoreCase(chosen.getTitle()));
        if (alreadyOwned) {
            AlbumView.showAlreadyInLibrary();
            return;
        }

        user.addAlbumToLibrary(chosen);
        PersistenceController.saveUsers();
        AlbumView.showAddedToLibrary(chosen.getTitle());
    }

    private static void viewLibrary(User user) {
        List<Album> library = user.getLibrary();
        if (library.isEmpty()) {
            AlbumView.showEmptyLibrary();
            return;
        }
        AlbumView.promptAlbumChoice(library, "My library:");
    }
}
