package com.smnk107.UserService.Services;

import com.smnk107.UserService.DTOs.RegisterRequest;
import com.smnk107.UserService.Entities.User;
import com.smnk107.UserService.Repositories.UserRepository;
import com.smnk107.UserService.Util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String registerUser(RegisterRequest request) {

        // Check if user already exists
        Optional<User> existingUser = userRepository.findByUsername(request.getUsername());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Encrypt password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setRole(request.getRole() != null ? request.getRole() : "USER");

        userRepository.save(user);

        return "User registered successfully";
    }

    public String logInUser(User request){

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Invalid credentials");
            }

            return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }

}
