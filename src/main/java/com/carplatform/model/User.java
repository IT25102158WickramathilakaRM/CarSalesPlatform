package com.carplatform.model;


// C1 - shared user account fields
public abstract class User {

    private String userId;
    private String username;
    private String email;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String registrationDate;
    private boolean isActive;


    // default values when creating empty object
    public User() {
        this.isActive = true;
    }

    // set up user with id, login, and contact details
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


    
    // Buyer, Seller, or Admin
    public abstract String getRole();

    
    // name and role line for profile
    public abstract String getDisplayInfo();


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

    
    public String getInitial() {
        if (fullName != null && !fullName.isBlank()) {
            return fullName.substring(0, 1).toUpperCase();
        }
        if (username != null && !username.isBlank()) {
            return username.substring(0, 1).toUpperCase();
        }
        return "?";
    }


    
    // one line for the data file
    public String toFileString() {
        return userId + "|" + username + "|" + email + "|" + password + "|"
                + fullName + "|" + phoneNumber + "|" + registrationDate + "|"
                + isActive + "|" + getRole();
    }

    @Override
    // handy text when debugging in IntelliJ
    public String toString() {
        return "User{id='" + userId + "', username='" + username + "', role='" + getRole() + "'}";
    }

    // t147
}
