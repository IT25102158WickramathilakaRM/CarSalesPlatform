package com.carplatform.model;

/**
 * Represents a Buyer user – inherits from User.
 *
 * OOP Concepts:
 *  - Inheritance   : Extends abstract User class
 *  - Encapsulation : Buyer-specific fields hidden behind getters/setters
 *  - Polymorphism  : Overrides getRole() and getDisplayInfo()
 *
 * Member 1 – User Management Module
 */
public class BuyerUser extends User {

    private String preferredCarType;
    private double budgetLimit;
    private int    totalPurchases;

    // ─── Constructors ──────────────────────────────────────────────────────

    public BuyerUser() {
        super();
        this.totalPurchases = 0;
    }

    public BuyerUser(String userId, String username, String email, String password,
                     String fullName, String phoneNumber, String registrationDate,
                     String preferredCarType, double budgetLimit) {
        super(userId, username, email, password, fullName, phoneNumber, registrationDate);
        this.preferredCarType = preferredCarType;
        this.budgetLimit      = budgetLimit;
        this.totalPurchases   = 0;
    }

    // ─── Polymorphic Overrides ─────────────────────────────────────────────

    @Override
    public String getRole() {
        return "BUYER";
    }

    @Override
    public String getDisplayInfo() {
        return "[BUYER] " + getFullName() + " | Budget: LKR " + budgetLimit
                + " | Preferred: " + preferredCarType;
    }

    // ─── File Serialisation ───────────────────────────────────────────────

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + preferredCarType + "|" + budgetLimit + "|" + totalPurchases;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────

    public String getPreferredCarType()                    { return preferredCarType; }
    public void   setPreferredCarType(String t)            { this.preferredCarType = t; }

    public double getBudgetLimit()                         { return budgetLimit; }
    public void   setBudgetLimit(double b)                 { this.budgetLimit = b; }

    public int  getTotalPurchases()                        { return totalPurchases; }
    public void setTotalPurchases(int n)                   { this.totalPurchases = n; }
}
