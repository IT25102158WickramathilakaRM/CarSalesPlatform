package com.carplatform.model;

/**
 * Represents a Seller user – inherits from User.
 *
 * OOP Concepts:
 *  - Inheritance   : Extends abstract User class
 *  - Encapsulation : Seller-specific data hidden behind accessors
 *  - Polymorphism  : Overrides getRole() and getDisplayInfo()
 *
 * Member 1 – User Management Module
 */
public class SellerUser extends User {

    private String businessName;
    private String location;
    private int    totalListings;
    private double rating;          // average seller rating

    // ─── Constructors ──────────────────────────────────────────────────────

    public SellerUser() {
        super();
        this.totalListings = 0;
        this.rating        = 0.0;
    }

    public SellerUser(String userId, String username, String email, String password,
                      String fullName, String phoneNumber, String registrationDate,
                      String businessName, String location) {
        super(userId, username, email, password, fullName, phoneNumber, registrationDate);
        this.businessName  = businessName;
        this.location      = location;
        this.totalListings = 0;
        this.rating        = 0.0;
    }

    // ─── Polymorphic Overrides ─────────────────────────────────────────────

    @Override
    public String getRole() {
        return "SELLER";
    }

    @Override
    public String getDisplayInfo() {
        return "[SELLER] " + getFullName() + " | Business: " + businessName
                + " | Location: " + location + " | Rating: " + rating + "/5";
    }

    // ─── File Serialisation ───────────────────────────────────────────────

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + businessName + "|" + location + "|" + totalListings + "|" + rating;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────

    public String getBusinessName()                      { return businessName; }
    public void   setBusinessName(String b)              { this.businessName = b; }

    public String getLocation()                          { return location; }
    public void   setLocation(String l)                  { this.location = l; }

    public int  getTotalListings()                       { return totalListings; }
    public void setTotalListings(int n)                  { this.totalListings = n; }

    public double getRating()                            { return rating; }
    public void   setRating(double r)                   { this.rating = r; }
}
