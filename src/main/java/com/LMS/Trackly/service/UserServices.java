package com.LMS.Trackly.service;

import com.LMS.Trackly.model.User;
import com.LMS.Trackly.model.PasswordReset;
import com.LMS.Trackly.repository.UserRepository;
import com.LMS.Trackly.repository.PasswordResetRepository;
import com.LMS.Trackly.helper.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetRepository passwordResetTokenRepository;

    @Autowired
    private MailService mailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServices(UserRepository userRepository, PasswordResetRepository passwordResetTokenRepository, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailService = mailService;
    }

    /**
     * Registers a new user and sends a verification email.
     */
    public User registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail().toLowerCase());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        String token = UUID.randomUUID().toString();

        user.setPassword(hashedPassword);
        user.setToken(token);
        user.setVerified(false);
        user.setEmail(user.getEmail().toLowerCase());

        User savedUser = userRepository.save(user);

        String subject = "Email Verification - Trackly LMS";
        String content = "<p>Hello " + user.getUsername() + ",</p>"
                + "<p>Please verify your email address by clicking the link below:</p>"
                + "<a href=\"http://localhost:8080/api/verify?token=" + token + "\">Verify Email</a>";

        mailService.sendMail(user.getEmail(), subject, content);

        return savedUser;
    }

    /**
     * Verifies a user's email using the provided token.
     */
    public boolean verifyUser(String token) {
        Optional<User> userOptional = userRepository.findByToken(token);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setVerified(true);
            user.setToken(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    /**
     * Get the Users Having Role "Employee"
     */
    public List<User> getEmployee() {
        return userRepository.findByRole("Employee");
    }

    /**
     * Authenticates a user with email and password.
     */
    public User login(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email.toLowerCase());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOptional.get();

//        if (!user.isVerified()) {
//            throw new RuntimeException("Email not verified");
//        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }

    /**
     * Initiates the password reset process by sending a reset email.
     */
    public void initiatePasswordReset(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email.toLowerCase());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Email not found");
        }

        User user = userOptional.get();

        String token = UUID.randomUUID().toString();
        PasswordReset resetToken = new PasswordReset();
        resetToken.setEmail(email.toLowerCase());
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        passwordResetTokenRepository.save(resetToken);

        String subject = "Password Reset Request - Trackly LMS";
        String content = "<p>Hello " + user.getUsername() + ",</p>"
                + "<p>You have requested to reset your password. Click the link below to reset it:</p>"
                + "<a href=\"http://localhost:8080/api/reset-password?token=" + token + "\">Reset Password</a>";

        mailService.sendMail(email, subject, content);
    }

    /**
     * Verifies the password reset token.
     */
    public <PasswordToken> boolean verifyResetToken(String token) {
        Optional<PasswordToken> tokenOptional = passwordResetTokenRepository.findByToken(token);
        if (tokenOptional.isPresent()) {
            PasswordReset resetToken = (PasswordReset) tokenOptional.get();
            return resetToken.getExpiryDate().isAfter(Instant.from(LocalDateTime.now()));
        }
        return false;
    }

    /**
     * Resets the user's password using the provided token.
     */
    public void resetPassword(String token, String newPassword) {
        Optional<PasswordReset> tokenOptional = passwordResetTokenRepository.findByToken(token);
        if (tokenOptional.isEmpty()) {
            throw new RuntimeException("Invalid or expired token");
        }

        PasswordReset resetToken = tokenOptional.get();
        Optional<User> userOptional = userRepository.findByEmail(resetToken.getEmail());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }

    /**
     * Updates the user's profile information.
     */
    public void updateUserProfile(int id, User updatedData) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOptional.get();
        user.setUsername(updatedData.getUsername());
        user.setPhoneNumber(updatedData.getPhoneNumber());
        user.setDistrict(updatedData.getDistrict());
        // Update other fields as necessary

        userRepository.save(user);
    }

    /**
     * Deletes a user by their ID.
     */
    public void deleteUserById(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    public User saveCredentials(User cred) {
        return userRepository.save(cred);
    }

    public Optional<User> getCredById(int id) {
        return userRepository.findById(id);
    }
}
