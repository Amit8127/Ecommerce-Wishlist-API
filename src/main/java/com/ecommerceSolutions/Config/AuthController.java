package com.ecommerceSolutions.Config;

import com.ecommerceSolutions.Config.JWTSecurity.JwtService;
import com.ecommerceSolutions.Dtos.RequestDtos.CreateUserDto;
import com.ecommerceSolutions.Dtos.ResponseDtos.UserResponseDto;
import com.ecommerceSolutions.Models.JwtRequest;
import com.ecommerceSolutions.Models.JwtResponse;
import com.ecommerceSolutions.Models.User;
import com.ecommerceSolutions.Transformers.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {



    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    // Endpoint for user registration (signup)
    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody CreateUserDto userDto) {
        try{
            // Check if the user with the provided email already exists
            Optional<User> adminInfoOpt = authRepository.findByEmail(userDto.getEmail());
            if(adminInfoOpt.isPresent()) {
                throw new RuntimeException("User already Present with email : " + userDto.getEmail());
            }
            // Encode the password before saving it to the database
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

            // Save the user information to the database
            User user = UserTransformer.userDtoToUser(userDto);
            authRepository.save(user);

            UserResponseDto responseDto = new UserResponseDto(user.getId(), user.getEmail());

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    // Endpoint for user login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody JwtRequest request) {
        try {
            // Authenticate user credentials
            this.doAuthenticate(request.getEmail(), request.getPassword());

            // Generate JWT token for the authenticated user
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = this.jwtService.generateToken(userDetails);

            // Create and return JWT response containing the token
            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Helper method to perform authentication
    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            // Use the AuthenticationManager to authenticate the user
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            // Throw exception if credentials are invalid
            throw new BadCredentialsException("Invalid Username or Password !!");
        }

    }
}
