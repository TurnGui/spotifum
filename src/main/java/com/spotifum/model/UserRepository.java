package com.spotifum.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Singleton in-memory directory of every registered {@link User}, keyed by
 * email. Serialized as-is by {@code FileStorageService} to persist accounts
 * between runs.
 */
public final class UserRepository implements Serializable {
    private static final long serialVersionUID = 1L;

    private static UserRepository instance;

    private final Map<String, User> usersByEmail;

    private UserRepository() {
        this.usersByEmail = new HashMap<>();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public static void setInstance(UserRepository repository) {
        instance = repository;
    }

    public void add(User user) {
        usersByEmail.put(user.getEmail(), user);
    }

    public boolean exists(String email) {
        return usersByEmail.containsKey(email);
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(email));
    }

    public Map<String, User> getAll() {
        return new HashMap<>(usersByEmail);
    }

    public int size() {
        return usersByEmail.size();
    }
}
