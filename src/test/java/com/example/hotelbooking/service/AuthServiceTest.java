package com.example.hotelbooking.service;

import com.example.hotelbooking.dto.JwtRequest;
import com.example.hotelbooking.dto.JwtResponse;
import com.example.hotelbooking.exceptions.AuthenticationException;
import com.example.hotelbooking.utils.JwtTokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenUtils jwtTokenUtils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(jwtTokenUtils, "secret", "your-secret-key");
    }
    @Test
    void createAuthTokenTest_SuccessfulAuthentication() {

        JwtRequest authRequest = new JwtRequest("username", "password");
        UserDetails userDetails = mock(UserDetails.class);
        String token = "your-generated-jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        when(userService.loadUserByUsername(authRequest.getUsername())).thenReturn(userDetails);

        when(jwtTokenUtils.generateToken(userDetails)).thenReturn(token);

        JwtResponse response = authService.createAuthToken(authRequest);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService).loadUserByUsername(authRequest.getUsername());
        verify(jwtTokenUtils).generateToken(userDetails);
        assertEquals(token, response.getToken());
    }

    @Test
    void createAuthTokenTest_BadCredentials() {

        JwtRequest authRequest = new JwtRequest("username", "incorrect-password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        try {
            authService.createAuthToken(authRequest);
            fail("Expected BadCredentialsException");
        } catch (AuthenticationException e) {
            assertEquals("Wrong password or username", e.getMessage());
        }

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoMoreInteractions(userService, jwtTokenUtils);
    }
}