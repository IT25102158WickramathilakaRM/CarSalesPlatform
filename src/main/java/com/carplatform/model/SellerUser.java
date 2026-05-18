package com.carplatform.model;


// C1 - seller account with shop details
public class SellerUser extends User {

    private String businessName;
    private String location;
    private int    totalListings;
    private double rating;


    // default values when creating empty object
    public SellerUser() {
        super();
        this.totalListings = 0;
        this.rating        = 0.0;
    }

    // set up a new SellerUser
    public SellerUser(String userId, String username, String email, String password,
                      String fullName, String phoneNumber, String registrationDate,
                      String businessName, String location) {
        super(userId, username, email, password, fullName, phoneNumber, registrationDate);
        this.businessName  = businessName;
        this.location      = location;
        this.totalListings = 0;
        this.rating        = 0.0;
    }


    @Override
    // Buyer, Seller, or Admin
    public String getRole() {
        return "SELLER";
    }

    @Override
    // name and role line for profile
    public String getDisplayInfo() {
        return "[SELLER] " + getFullName() + " | Business: " + businessName
                + " | Location: " + location + " | Rating: " + rating + "/5";
    }


    @Override
    // one line for the data file
    public String toFileString() {
        return super.toFileString() + "|" + businessName + "|" + location + "|" + totalListings + "|" + rating;
    }


    public String getBusinessName()                      { return businessName; }
    public void   setBusinessName(String b)              { this.businessName = b; }

    public String getLocation()                          { return location; }
    public void   setLocation(String l)                  { this.location = l; }

    public int  getTotalListings()                       { return totalListings; }
    public void setTotalListings(int n)                  { this.totalListings = n; }

    public double getRating()                            { return rating; }
    public void   setRating(double r)                   { this.rating = r; }
}
