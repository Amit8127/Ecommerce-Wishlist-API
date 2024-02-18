package com.ecommerceSolutions.Config;

import com.ecommerceSolutions.Dtos.RequestDtos.CreateUserDto;
import com.ecommerceSolutions.Models.User;
import com.ecommerceSolutions.Models.JwtRequest;
import com.ecommerceSolutions.Models.JwtResponse;
import com.ecommerceSolutions.Dtos.ResponseDtos.UserResponseDto;
import com.ecommerceSolutions.Config.JWTSecurity.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtService jwtService;

    @Test
    void testSignupUser_Success() {
        // Arrange
        CreateUserDto userDto = new CreateUserDto("user","test@example.com", "password");
        when(authRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");

        // Act
        ResponseEntity<?> response = authController.signupUser(userDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof UserResponseDto);
    }

    @Test
    void testSignupUser_UserAlreadyExists() {
        // Arrange
        CreateUserDto userDto = new CreateUserDto("user","existing@example.com", "password");
        when(authRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.of(new User()));

        // Act
        ResponseEntity<?> response = authController.signupUser(userDto);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof String);
    }

    @Test
    void testLoginUser_Success() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest("test@example.com", "password");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername(jwtRequest.getEmail())).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("generatedToken");

        // Act
        ResponseEntity<?> response = authController.loginUser(jwtRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof JwtResponse);
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest("invalid@example.com", "invalidPassword");
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        // Act
        ResponseEntity<?> response = authController.loginUser(jwtRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof String);
    }
}
