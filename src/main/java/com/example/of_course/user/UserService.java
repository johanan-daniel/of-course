package com.example.of_course.user;

import com.example.of_course.config.PasswordPolicyConfig;
import com.example.of_course.exception.UserEmailAlreadyExistsException;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.of_course.security.JwtService;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final PasswordPolicyConfig passwordPolicyConfig;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordPolicyConfig config, JwtService jwtService) {
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordPolicyConfig = config;
        this.jwtService = jwtService;
    }

    public User getUserById(int id) {
        return userRepo.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("No user with id %d.", id))
        );
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("No user with email %s.", email))
        );
    }

    /**
     * Validates request by checking for existing user with given email, and invalid lengths.
     *
     * @param request SignupRequest of {email, password, name (optional)}
     * @return true if successful, false if anything invalid
     */
    public Boolean registerUser(SignupRequest request) {
        Optional<User> existingUser = userRepo.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserEmailAlreadyExistsException("Email address is already in use.", request.getEmail());
        }

        int nameLen = request.getName().length();
        int maxNameLen = 50;
        if (nameLen > maxNameLen) {
            throw new IllegalArgumentException("Name length must be less than " + maxNameLen + " chars.");
        }

        int emailLen = request.getEmail().length();
        // TODO move to config file & class
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

        return true;
    }

    public String loginUser(LoginRequest request) {
        String email = request.getEmail();
        String rawPassword = request.getPassword();

        if (email == null) {
            throw new AuthenticationCredentialsNotFoundException("The email address field was empty.");
        }
        if (rawPassword == null) {
            throw new AuthenticationCredentialsNotFoundException("The password field was empty.");
        }

        // TODO validate email and password before db request & checking against hashed
        int emailLen = email.length();
        int minEmailLen = 5;
        int maxEmailLen = 254;
        if (emailLen < minEmailLen || emailLen > maxEmailLen) {
            throw new IllegalArgumentException("Email length is invalid.");
        }

        int rawPasswordLen = rawPassword.length();
        int minPasswordLen = passwordPolicyConfig.getMinLength();
        int maxPasswordLen = passwordPolicyConfig.getMaxLength();
        if (rawPasswordLen < minPasswordLen || rawPasswordLen > maxPasswordLen) {
            throw new IllegalArgumentException("Password length is invalid.");
        }

        Optional<User> wrappedUser = userRepo.findByEmail(email);
        User user = wrappedUser.orElseThrow(() ->
                new EntityNotFoundException(String.format("No user with email %s.", email))
        );
        String encrypted_password = user.getPassword();

        if (!passwordEncoder.matches(rawPassword, encrypted_password)) {
            throw new BadCredentialsException("Password is invalid.");
        }

        // CHECK catch exception when making token?
        return jwtService.generateToken(email);
    }
}
