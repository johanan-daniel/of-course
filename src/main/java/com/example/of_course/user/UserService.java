package com.example.of_course.user;

import com.example.of_course.config.PasswordPolicyConfig;
import com.example.of_course.exception.UserEmailAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicyConfig passwordPolicyConfig;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordPolicyConfig config) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordPolicyConfig = config;
    }

    public User getUserById(int id) {
        return userRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("No user with id %d.", id))
        );
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException(String.format("No user with email %s.", email))
        );
    }

    public String registerUser(SignupRequest request) {
        Optional<User> existingUser = userRepo.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserEmailAlreadyExistsException("Email address is already in use.");
        }

        int nameLen = request.getName().length();
        int maxNameLen = 50;
        if (nameLen > maxNameLen) {
            throw new IllegalArgumentException("Name length must be less than " + maxNameLen + " chars.");
        }

        int emailLen = request.getEmail().length();
        int minEmailLen = 5;
        int maxEmailLen = 254;
//        TODO more robust email validation
        if (emailLen < minEmailLen || emailLen > maxEmailLen) {
            throw new IllegalArgumentException("Invalid email length.");
        }

        int passwordLen = request.getPassword().length();
        int minPasswordLen = passwordPolicyConfig.getMinLength();
        int maxPasswordLen = passwordPolicyConfig.getMaxLength();
//        TODO password character validation (exclude invalid/non-printable ones?)
        if (passwordLen < minPasswordLen || passwordLen > maxPasswordLen) {
            throw new IllegalArgumentException(
                "Password length must be between " + minPasswordLen + " and " + maxPasswordLen + " chars."
            );
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User(request.getEmail(), hashedPassword);
        userRepo.save(newUser);

        return "User registered successfully.";
    }

    public boolean loginUser(SigninRequest request) {
        Optional<User> wrappedUser = userRepo.findByEmail(request.getEmail());
        User user = wrappedUser.orElseThrow(
                () -> new EntityNotFoundException(String.format("No user with email %s", request.getEmail()))
        );
        String encrypted_password = user.getPassword();

//        validate password
        return true;
    }
}
