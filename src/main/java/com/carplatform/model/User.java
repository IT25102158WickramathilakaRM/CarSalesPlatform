package com.carplatform.model;

/**
 * Abstract base class representing a User in the system.
 *
 * OOP Concepts Demonstrated:
 *  - Abstraction   : Abstract class with abstract method getRole()
 *  - Encapsulation : Private fields with public getters/setters
 *  - Information Hiding: Password stored securely, not exposed freely
 *
 * IT25100694 – Gunawardhena P.G.M.B.P. | Component 1 – User Management Module
 */
public abstract class User {

    // ─── Encapsulated Fields ───────────────────────────────────────────────
    private String userId;
    private String username;
    private String email;
    private String password;       // stored as plain text for file-based storage
    private String fullName;
    private String phoneNumber;
    private String registrationDate;
    private boolean isActive;

    // ─── Constructors ──────────────────────────────────────────────────────

    public User() {
        this.isActive = true;
    }

    public User(String userId, String username, String email, String password,
                String fullName, String phoneNumber, String registrationDate) {
        this.userId           = userId;
        this.username         = username;
        this.email            = email;
        this.password         = password;
        this.fullName         = fullName;
        this.phoneNumber      = phoneNumber;
        this.registrationDate = registrationDate;
        this.isActive         = true;
    }

    // ─── Abstract Methods (Abstraction) ───────────────────────────────────

    /**
     * Each subclass must declare its own role identifier.
     * @return role string (e.g., "BUYER", "SELLER", "ADMIN")
     */
    public abstract String getRole();

    /**
     * Polymorphic display – each subclass shows user info differently.
     */
    public abstract String getDisplayInfo();

    // ─── Getters & Setters (Encapsulation) ───────────────────────────────

    public String getUserId()                          { return userId; }
    public void   setUserId(String userId)             { this.userId = userId; }

    public String getUsername()                        { return username; }
    public void   setUsername(String username)         { this.username = username; }

    public String getEmail()                           { return email; }
    public void   setEmail(String email)               { this.email = email; }

    public String getPassword()                        { return password; }
    public void   setPassword(String password)         { this.password = password; }

    public String getFullName()                        { return fullName; }
    public void   setFullName(String fullName)         { this.fullName = fullName; }

    public String getPhoneNumber()                     { return phoneNumber; }
    public void   setPhoneNumber(String phoneNumber)   { this.phoneNumber = phoneNumber; }

    public String getRegistrationDate()                { return registrationDate; }
    public void   setRegistrationDate(String d)        { this.registrationDate = d; }

    public boolean isActive()                          { return isActive; }
    public void    setActive(boolean active)           { this.isActive = active; }

    /** First letter for avatar display; safe when fullName is blank (JSP-friendly). */
    public String getInitial() {
        if (fullName != null && !fullName.isBlank()) {
            return fullName.substring(0, 1).toUpperCase();
        }
        if (username != null && !username.isBlank()) {
            return username.substring(0, 1).toUpperCase();
        }
        return "?";
    }

    // ─── File Serialisation ───────────────────────────────────────────────

    /**
     * Converts the user object to a pipe-delimited line for file storage.
     * Format: userId|username|email|password|fullName|phoneNumber|registrationDate|isActive|role
     */
    public String toFileString() {
        return userId + "|" + username + "|" + email + "|" + password + "|"
                + fullName + "|" + phoneNumber + "|" + registrationDate + "|"
                + isActive + "|" + getRole();
    }

    @Override
    public String toString() {
        return "User{id='" + userId + "', username='" + username + "', role='" + getRole() + "'}";
    }
}
