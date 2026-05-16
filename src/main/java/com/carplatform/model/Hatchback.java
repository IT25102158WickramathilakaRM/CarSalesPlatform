package com.carplatform.model;

/**
 * Represents a Hatchback car listing – extends Car.
 *
 * OOP Concepts:
 *  - Inheritance   : Extends abstract Car
 *  - Polymorphism  : Overrides calculateInsuranceEstimate() and getListingSummary()
 *  - Encapsulation : Hatchback-specific fields hidden
 *
 * IT25102158 – Wickramathilaka R.M | Component 2 – Car Listing Management Module
 */
public class Hatchback extends Car {

    private boolean hasFoldableRearSeats;
    private String  roofType;             // Standard, Convertible
    private int     engineCC;             // engine displacement in cc

    // ─── Constructors ──────────────────────────────────────────────────────

    public Hatchback() {
        super();
        this.hasFoldableRearSeats = true;
        this.roofType             = "Standard";
    }

    public Hatchback(String carId, String sellerId, String make, String model,
                     int year, double price, int mileage, String colour,
                     String fuelType, String transmission, String condition,
                     String description, String listedDate,
                     boolean hasFoldableRearSeats, String roofType, int engineCC) {
        super(carId, sellerId, make, model, year, price, mileage, colour,
              fuelType, transmission, condition, description, listedDate);
        this.hasFoldableRearSeats = hasFoldableRearSeats;
        this.roofType             = roofType;
        this.engineCC             = engineCC;
    }

    // ─── Polymorphic Overrides ─────────────────────────────────────────────

    @Override
    public String getBodyType() {
        return "Hatchback";
    }

    @Override
    public double calculateInsuranceEstimate() {
        // Hatchbacks have lower insurance (~2% of price)
        double base = getPrice() * 0.02;
        if (engineCC > 1600) base *= 1.08;
        return Math.round(base * 100.0) / 100.0;
    }

    @Override
    public String getListingSummary() {
        return "[Hatchback] " + getYear() + " " + getMake() + " " + getModel()
                + " | " + engineCC + "cc | Foldable Seats: " + (hasFoldableRearSeats ? "Yes" : "No")
                + " | LKR " + getPrice();
    }

    // ─── File Serialisation ───────────────────────────────────────────────

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + hasFoldableRearSeats + "|" + roofType + "|"
                + engineCC + tailImageUrl();
    }

    // ─── Getters & Setters ────────────────────────────────────────────────

    public boolean isHasFoldableRearSeats()               { return hasFoldableRearSeats; }
    public void    setHasFoldableRearSeats(boolean f)     { this.hasFoldableRearSeats = f; }

    public String getRoofType()                           { return roofType; }
    public void   setRoofType(String r)                   { this.roofType = r; }

    public int  getEngineCC()                             { return engineCC; }
    public void setEngineCC(int e)                        { this.engineCC = e; }
}
