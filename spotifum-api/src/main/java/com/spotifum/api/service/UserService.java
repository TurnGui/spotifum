package com.spotifum.api.service;

import com.spotifum.api.dto.UpdateUserRequest;
import com.spotifum.api.dto.UserResponse;
import com.spotifum.api.exception.ResourceNotFoundException;
import com.spotifum.api.model.User;
import com.spotifum.api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse getMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return new UserResponse(user);
    }

    public UserResponse updateMe(UpdateUserRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setName(request.getName());
        if (request.getAddress() != null) user.setAddress(request.getAddress());
        if (request.getPlan() != null) user.setPlan(request.getPlan());

        userRepository.save(user);
        return new UserResponse(user);
    }
}