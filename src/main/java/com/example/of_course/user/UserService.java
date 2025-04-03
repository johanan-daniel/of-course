package com.example.of_course.user;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepo = userRepository;
    }

    public User getUserById(int id) {
        return userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("No user with id" + id));
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("No user with email" + email)
        );
    }

    public String registerUser(SignupRequest request) {
        Optional<User> existingUser = userRepo.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email address is already in use.");
        }

        // TODO hash password
        String hashedPassword = request.getPassword();
        User newUser = new User(request.getEmail(), hashedPassword);
        userRepo.save(newUser);

        return "User registered successfully.";
    }
}
