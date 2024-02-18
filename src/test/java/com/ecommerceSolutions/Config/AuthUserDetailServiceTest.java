package com.ecommerceSolutions.Config;

import com.ecommerceSolutions.Models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthUserDetailServiceTest {

    @InjectMocks
    private AuthUserDetailService authUserDetailService;

    @Mock
    private AuthRepository authRepository;

    @Test
    void testLoadUserByUsername_UserFound() {
        // Arrange
        String email = "test@example.com";
        User user = new User("user",email, "password");
        when(authRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = authUserDetailService.loadUserByUsername(email);

        // Assert
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(authRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> authUserDetailService.loadUserByUsername(email));
    }
}
