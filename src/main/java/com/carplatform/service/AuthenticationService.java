package com.carplatform.service;

import com.carplatform.model.*;
import com.carplatform.util.AdminFileHandler;
import com.carplatform.util.UserFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
// C1 - register/login, users.txt + admins.txt
public class AuthenticationService {

    private final UserFileHandler userFileHandler;
    private final AdminFileHandler adminFileHandler;

    // set up a new AuthenticationService
    public AuthenticationService(@Value("${app.data.directory:data}") String dataDir) {
        this.userFileHandler = new UserFileHandler(dataDir);
        this.adminFileHandler = new AdminFileHandler(dataDir);
    }


    // sign up a new buyer account
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

    // sign up a new seller account
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


    
    // check credentials and return user
    public User login(String username, String password) {
        User user = userFileHandler.findByUsername(username);
        if (user != null) {
            if (!user.isActive()) return null;
            if (!user.getPassword().equals(password)) return null;
            return user;
        }
        AdminUser admin = adminFileHandler.findByUsername(username);
        if (admin == null) return null;
        if (!admin.isActive()) return null;
        if (!admin.getPassword().equals(password)) return null;
        return admin;
    }


    public User getUserById(String userId) {
        return userFileHandler.findById(userId);
    }

    public User getUserByUsername(String username) {
        return userFileHandler.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userFileHandler.getAllUsers();
    }


    // save name, phone, email, optional new password
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


    // remove account and log out
    public boolean deleteAccount(String userId) {
        return userFileHandler.deleteUser(userId);
    }

    // deactivate account
    public boolean deactivateAccount(String userId) {
        User user = userFileHandler.findById(userId);
        if (user == null) return false;
        user.setActive(false);
        return userFileHandler.updateUser(user);
    }


    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    // t17
}
