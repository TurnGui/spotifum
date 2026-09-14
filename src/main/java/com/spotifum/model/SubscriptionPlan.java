package com.spotifum.model;

/**
 * The subscription tiers offered by the platform. Access rules and the loyalty
 * points formula are kept here, next to the data they depend on, instead of
 * being scattered as {@code if}/{@code switch} checks across the controllers.
 */
public enum SubscriptionPlan {

    FREE(false, false),
    PREMIUM(true, false),
    PREMIUM_PLUS(true, true);

    private final boolean premium;
    private final boolean topTier;

    SubscriptionPlan(boolean premium, boolean topTier) {
        this.premium = premium;
        this.topTier = topTier;
    }

    /** Premium and Premium+ can save playlists and add albums to their library. */
    public boolean isPremium() {
        return premium;
    }

    /** Only Premium+ can access curated explicit-content playlists. */
    public boolean isTopTier() {
        return topTier;
    }

    /** One-off bonus credited when an account is created on this plan. */
    public double signUpBonus() {
        return this == PREMIUM_PLUS ? 100.0 : 0.0;
    }

    /** Loyalty points earned for a single playback, given the listener's current balance. */
    public double pointsForPlay(double currentPoints) {
        return switch (this) {
            case FREE -> 5.0;
            case PREMIUM -> 10.0;
            case PREMIUM_PLUS -> currentPoints * 0.025;
        };
    }
}
