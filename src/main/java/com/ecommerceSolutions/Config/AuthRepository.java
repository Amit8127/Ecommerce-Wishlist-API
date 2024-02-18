package com.ecommerceSolutions.Config;

import com.ecommerceSolutions.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {

    // Query method to find an AdminInfo entity by email
    Optional<User> findByEmail(String email);
}
