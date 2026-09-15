package com.spotifum.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryTest {

    @AfterEach
    void resetSingleton() {
        UserRepository.setInstance(null);
    }

    @Test
    void getInstanceAlwaysReturnsTheSameObject() {
        assertEquals(UserRepository.getInstance(), UserRepository.getInstance());
    }

    @Test
    void addAndFindByEmailRoundTrip() {
        UserRepository repository = UserRepository.getInstance();
        User user = new User("Ada", "ada@example.com", "Somewhere", SubscriptionPlan.FREE);

        repository.add(user);

        assertTrue(repository.exists("ada@example.com"));
        assertEquals(user, repository.findByEmail("ada@example.com").orElseThrow());
        assertFalse(repository.exists("nobody@example.com"));
    }
}
