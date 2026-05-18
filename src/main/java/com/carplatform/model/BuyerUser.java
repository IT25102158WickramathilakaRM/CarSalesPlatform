package com.carplatform.model;


// C1 - buyer account with budget
public class BuyerUser extends User {

    private String preferredCarType;
    private double budgetLimit;
    private int    totalPurchases;


    // default values when creating empty object
    public BuyerUser() {
        super();
        this.totalPurchases = 0;
    }

    // set up a new BuyerUser
    public BuyerUser(String userId, String username, String email, String password,
                     String fullName, String phoneNumber, String registrationDate,
                     String preferredCarType, double budgetLimit) {
        super(userId, username, email, password, fullName, phoneNumber, registrationDate);
        this.preferredCarType = preferredCarType;
        this.budgetLimit      = budgetLimit;
        this.totalPurchases   = 0;
    }


    @Override
    // Buyer, Seller, or Admin
    public String getRole() {
        return "BUYER";
    }

    @Override
    // name and role line for profile
    public String getDisplayInfo() {
        return "[BUYER] " + getFullName() + " | Budget: LKR " + budgetLimit
                + " | Preferred: " + preferredCarType;
    }


    @Override
    // one line for the data file
    public String toFileString() {
        return super.toFileString() + "|" + preferredCarType + "|" + budgetLimit + "|" + totalPurchases;
    }


    public String getPreferredCarType()                    { return preferredCarType; }
    public void   setPreferredCarType(String t)            { this.preferredCarType = t; }

    public double getBudgetLimit()                         { return budgetLimit; }
    public void   setBudgetLimit(double b)                 { this.budgetLimit = b; }

    public int  getTotalPurchases()                        { return totalPurchases; }
    public void setTotalPurchases(int n)                   { this.totalPurchases = n; }
}
