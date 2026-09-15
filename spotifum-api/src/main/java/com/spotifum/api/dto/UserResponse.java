package com.spotifum.api.dto;

import com.spotifum.api.model.User;
import lombok.Getter;

@Getter
public class UserResponse {
    private final Long id;
    private final String email;
    private final String name;
    private final String address;
    private final String plan;
    private final int points;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.address = user.getAddress();
        this.plan = user.getPlan();
        this.points = user.getPoints();
    }
}