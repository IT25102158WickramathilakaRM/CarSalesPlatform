package com.carplatform.util;

import com.carplatform.model.User;
import com.carplatform.model.BuyerUser;
import com.carplatform.model.SellerUser;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


// C1 - users.txt pipe format
public class UserFileHandler {

    private final String filePath;

    // set up a new UserFileHandler
    public UserFileHandler(String dataDir) {
        this.filePath = dataDir + File.separator + "users.txt";
        ensureFileExists();
    }

    // make sure the data file exists on disk
    private void ensureFileExists() {
        try {
            File f = new File(filePath);
            f.getParentFile().mkdirs();
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            System.err.println("[UserFileHandler] Could not create users.txt: " + e.getMessage());
        }
    }


    
    // save user
    public boolean saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.toFileString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("[UserFileHandler] Error saving user: " + e.getMessage());
            return false;
        }
    }


    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                User u = parseLine(line);
                if (u != null) users.add(u);
            }
        } catch (IOException e) {
            System.err.println("[UserFileHandler] Error reading users: " + e.getMessage());
        }
        return users;
    }

    
    // find by username
    public User findByUsername(String username) {
        return getAllUsers().stream()
                .filter(u -> u.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    
    // scan file for matching id
    public User findById(String userId) {
        return getAllUsers().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    
    // find by email
    public User findByEmail(String email) {
        return getAllUsers().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }


    
    // update user
    public boolean updateUser(User updatedUser) {
        List<User> all = getAllUsers();
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getUserId().equals(updatedUser.getUserId())) {
                all.set(i, updatedUser);
                found = true;
                break;
            }
        }
        if (!found) return false;
        return rewriteFile(all);
    }


    
    // delete user
    public boolean deleteUser(String userId) {
        List<User> all = getAllUsers();
        boolean removed = all.removeIf(u -> u.getUserId().equals(userId));
        if (!removed) return false;
        return rewriteFile(all);
    }


    // overwrite cars.txt with the current list
    private boolean rewriteFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (User u : users) {
                writer.write(u.toFileString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            System.err.println("[UserFileHandler] Error rewriting users: " + e.getMessage());
            return false;
        }
    }

    
    // turn one pipe-separated line into a Car object
    private User parseLine(String line) {
        try {
            String[] p = line.split("\\|", -1);
            if (p.length < 9) return null;

            String role = p[8];
            User user;

            if ("BUYER".equals(role) && p.length >= 12) {
                BuyerUser b = new BuyerUser();
                b.setPreferredCarType(p[9]);
                b.setBudgetLimit(Double.parseDouble(p[10]));
                b.setTotalPurchases(Integer.parseInt(p[11]));
                user = b;
            } else if ("SELLER".equals(role) && p.length >= 13) {
                SellerUser s = new SellerUser();
                s.setBusinessName(p[9]);
                s.setLocation(p[10]);
                s.setTotalListings(Integer.parseInt(p[11]));
                s.setRating(Double.parseDouble(p[12]));
                user = s;
            } else {
                // fallback – anonymous buyer
                user = new BuyerUser();
            }

            user.setUserId(p[0]);
            user.setUsername(p[1]);
            user.setEmail(p[2]);
            user.setPassword(p[3]);
            user.setFullName(p[4]);
            user.setPhoneNumber(p[5]);
            user.setRegistrationDate(p[6]);
            user.setActive(Boolean.parseBoolean(p[7]));
            return user;

        } catch (Exception e) {
            System.err.println("[UserFileHandler] Parse error: " + e.getMessage());
            return null;
        }
    }

    // username exists
    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }

    // email exists
    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }

    // t79
}
