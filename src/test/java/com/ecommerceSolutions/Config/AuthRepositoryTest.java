package com.ecommerceSolutions.Config;

import com.ecommerceSolutions.Models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    @Test
    void testFindByEmail_ExistingEmail() {
        // Arrange
        User user = new User("user","test@example.com", "password");
        authRepository.save(user);

        // Act
        Optional<User> foundUser = authRepository.findByEmail("test@example.com");

        // Assert
        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    void testFindByEmail_NonExistingEmail() {
        // Act
        Optional<User> foundUser = authRepository.findByEmail("nonexistent@example.com");

        // Assert
        assertFalse(foundUser.isPresent());
    }
}
