package com.spotifum.api.service;

import com.spotifum.api.dto.LoginRequest;
import com.spotifum.api.dto.LoginResponse;
import com.spotifum.api.dto.RegisterRequest;
import com.spotifum.api.exception.InvalidRequestException;
import com.spotifum.api.exception.ResourceNotFoundException;
import com.spotifum.api.model.User;
import com.spotifum.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtService jwtService;

    @InjectMocks private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@spotifum.com");
        registerRequest.setPassword("123456");
        registerRequest.setName("Test User");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@spotifum.com");
        loginRequest.setPassword("123456");

        user = new User();
        user.setEmail("test@spotifum.com");
        user.setPassword("hashed");
        user.setName("Test User");
        user.setPlan("FREE");
        user.setPoints(0);
        user.setAdmin(false);
    }

    @Test
    void register_success() {
        when(userRepository.existsByEmail(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("hashed");
        when(userRepository.save(any())).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("token");

        LoginResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("test@spotifum.com", response.getEmail());
        assertEquals("FREE", response.getPlan());
        verify(userRepository).save(any());
    }

    @Test
    void register_emailAlreadyInUse_throwsException() {
        when(userRepository.existsByEmail(any())).thenReturn(true);

        assertThrows(InvalidRequestException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_success() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtService.generateToken(any())).thenReturn("token");

        LoginResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("test@spotifum.com", response.getEmail());
    }

    @Test
    void login_wrongPassword_throwsException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> authService.login(loginRequest));
    }

    @Test
    void login_userNotFound_throwsException() {
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authService.login(loginRequest));
    }
}