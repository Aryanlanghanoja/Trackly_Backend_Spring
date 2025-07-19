package com.LMS.Trackly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpSession;

import com.LMS.Trackly.dto.LoginRequest;
import com.LMS.Trackly.model.User;
import com.LMS.Trackly.service.UserServices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "${frontend.url}", allowCredentials = "true")
public class UserController {
    @Autowired
    private UserServices userServices;

    /**
     * Registers a new user and sends verification email.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User savedUser = userServices.registerUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Verifies a user with a token.
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
        boolean result = userServices.verifyUser(token);
        if (result) {
            return ResponseEntity.ok("Email verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }

    /**
     * Authenticates a user with email and password.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request, HttpSession session) {
        try {
            String email = request.getEmail();
            String password = request.getPassword();

            // Authenticate user
            User user = userServices.login(email, password);

            // Start session and store user details (like userId or email, NOT the password)
            session.setAttribute("user", user);

            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get the Loggged in User
     */

    @GetMapping("/get-user")
    public ResponseEntity<?> getLoggedInUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        return ResponseEntity.ok(user);
    }


    /**
     * Initiates password reset process by sending email.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> initiateReset(@RequestParam String email) {
        try {
            userServices.initiatePasswordReset(email);
            return ResponseEntity.ok("Password reset email sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Get Employee List
     */
    @GetMapping("/get-employees")
    public ResponseEntity<?> getEmployees() {
        List<User> employee = userServices.getEmployee();
        return ResponseEntity.ok(employee); // ✅ Proper way to return the list
    }

    /**
     * Verifies reset token validity.
     */
    @GetMapping("/reset-password")
    public ResponseEntity<?> verifyResetToken(@RequestParam String token) {
        boolean valid = userServices.verifyResetToken(token);
        if (valid) {
            return ResponseEntity.ok("Token is valid");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }

    /**
     * Resets password using a valid token.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        try {
            userServices.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password reset successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Updates user profile info.
     */
    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @RequestBody User updatedData) {
        try {
            userServices.updateUserProfile(id, updatedData);
            return ResponseEntity.ok("User updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Deletes user by ID.
     */
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        try {
            userServices.deleteUserById(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
