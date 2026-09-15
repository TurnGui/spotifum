package com.spotifum.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LeaderboardResponse {
    private final String mostPlayedSong;
    private final String mostPlayedArtist;
    private final String mostPlayedGenre;
    private final String mostActiveListener;
}