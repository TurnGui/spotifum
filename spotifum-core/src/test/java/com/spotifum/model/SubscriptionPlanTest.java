package com.spotifum.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SubscriptionPlanTest {

    @Test
    void freeIsNeitherPremiumNorTopTier() {
        assertFalse(SubscriptionPlan.FREE.isPremium());
        assertFalse(SubscriptionPlan.FREE.isTopTier());
        assertEquals(0.0, SubscriptionPlan.FREE.signUpBonus());
    }

    @Test
    void premiumIsPremiumButNotTopTier() {
        assertTrue(SubscriptionPlan.PREMIUM.isPremium());
        assertFalse(SubscriptionPlan.PREMIUM.isTopTier());
    }

    @Test
    void premiumPlusGrantsASignUpBonus() {
        assertTrue(SubscriptionPlan.PREMIUM_PLUS.isPremium());
        assertTrue(SubscriptionPlan.PREMIUM_PLUS.isTopTier());
        assertEquals(100.0, SubscriptionPlan.PREMIUM_PLUS.signUpBonus());
    }

    @Test
    void pointsForPlayFollowsEachPlanFormula() {
        assertEquals(5.0, SubscriptionPlan.FREE.pointsForPlay(50));
        assertEquals(10.0, SubscriptionPlan.PREMIUM.pointsForPlay(50));
        assertEquals(5.0, SubscriptionPlan.PREMIUM_PLUS.pointsForPlay(200));
    }
}
