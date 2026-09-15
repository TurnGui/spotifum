package com.spotifum.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePlaylistRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String kind; // CUSTOM, RANDOM, GENRE_DURATION, FAVORITES

    private String genre;
    private int maxDurationSeconds;
}