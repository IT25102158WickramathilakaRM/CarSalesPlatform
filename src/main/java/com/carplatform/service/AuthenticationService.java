package com.carplatform.service;

import com.carplatform.model.*;
import com.carplatform.util.UserFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service for user authentication and profile management.
 *
 * OOP: Encapsulation – all business logic hidden from servlets.
 *      Polymorphism – works with any User subtype.
 * Member 1 – User Management Module
 */
@Service
public class AuthenticationService {

    private final UserFileHandler userFileHandler;

    public AuthenticationService(@Value("${app.data.directory:data}") String dataDir) {
        this.userFileHandler = new UserFileHandler(dataDir);
    }

    // ── REGISTER ─────────────────────────────────────────────────────────

    public String registerBuyer(String username, String email, String password,
                                String fullName, String phone,
                                String preferredCarType, double budgetLimit) {
        if (userFileHandler.usernameExists(username)) return "USERNAME_EXISTS";
        if (userFileHandler.emailExists(email))       return "EMAIL_EXISTS";
        if (!isValidEmail(email))                     return "INVALID_EMAIL";
        if (password.length() < 6)                   return "WEAK_PASSWORD";

        String id = "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        BuyerUser buyer = new BuyerUser(id, username, email, password, fullName, phone,
                LocalDate.now().toString(), preferredCarType, budgetLimit);
        return userFileHandler.saveUser(buyer) ? "SUCCESS" : "SAVE_ERROR";
    }

    public String registerSeller(String username, String email, String password,
                                 String fullName, String phone,
                                 String businessName, String location) {
        if (userFileHandler.usernameExists(username)) return "USERNAME_EXISTS";
        if (userFileHandler.emailExists(email))       return "EMAIL_EXISTS";
        if (!isValidEmail(email))                     return "INVALID_EMAIL";
        if (password.length() < 6)                   return "WEAK_PASSWORD";

        String id = "USR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        SellerUser seller = new SellerUser(id, username, email, password, fullName, phone,
                LocalDate.now().toString(), businessName, location);
        return userFileHandler.saveUser(seller) ? "SUCCESS" : "SAVE_ERROR";
    }

    // ── LOGIN ─────────────────────────────────────────────────────────────

    /**
     * Validates credentials and returns the User object on success.
     * Returns null on failure.
     */
    public User login(String username, String password) {
        User user = userFileHandler.findByUsername(username);
        if (user == null) return null;
        if (!user.isActive()) return null;
        if (!user.getPassword().equals(password)) return null;
        return user;
    }

    // ── READ ──────────────────────────────────────────────────────────────

    public User getUserById(String userId) {
        return userFileHandler.findById(userId);
    }

    public User getUserByUsername(String username) {
        return userFileHandler.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userFileHandler.getAllUsers();
    }

    // ── UPDATE ────────────────────────────────────────────────────────────

    public boolean updateProfile(String userId, String fullName, String phone,
                                 String email, String newPassword) {
        User user = userFileHandler.findById(userId);
        if (user == null) return false;
        user.setFullName(fullName);
        user.setPhoneNumber(phone);
        user.setEmail(email);
        if (newPassword != null && newPassword.length() >= 6) {
            user.setPassword(newPassword);
        }
        return userFileHandler.updateUser(user);
    }

    // ── DELETE ────────────────────────────────────────────────────────────

    public boolean deleteAccount(String userId) {
        return userFileHandler.deleteUser(userId);
    }

    public boolean deactivateAccount(String userId) {
        User user = userFileHandler.findById(userId);
        if (user == null) return false;
        user.setActive(false);
        return userFileHandler.updateUser(user);
    }

    // ── VALIDATION ────────────────────────────────────────────────────────

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
