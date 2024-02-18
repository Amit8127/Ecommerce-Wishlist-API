package com.ecommerceSolutions.Config;

import com.ecommerceSolutions.Models.UserInfoUserDetails;
import com.ecommerceSolutions.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    private AuthRepository authRepository;

    // This method is called by Spring Security to load user details based on the provided email.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // Retrieve user information from the database based on the provided email.
        Optional<User> userInfo = authRepository.findByEmail(email);

        // If user information is found, create and return UserDetails using AdminInfoUserDetails.
        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }

        User user = userInfo.get();
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
