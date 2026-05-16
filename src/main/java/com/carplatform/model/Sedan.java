package com.carplatform.model;

/**
 * Represents a Sedan car listing – extends Car.
 *
 * OOP Concepts:
 *  - Inheritance   : Extends abstract Car
 *  - Polymorphism  : Overrides calculateInsuranceEstimate() and getListingSummary()
 *  - Encapsulation : Sedan-specific fields hidden
 *
 * Member 2 – Car Listing Management Module
 */
public class Sedan extends Car {

    private int    numDoors;
    private boolean hasSunroof;
    private String bootCapacity;   // e.g., "460L"

    // ─── Constructors ──────────────────────────────────────────────────────

    public Sedan() {
        super();
        this.numDoors = 4;
        this.hasSunroof = false;
    }

    public Sedan(String carId, String sellerId, String make, String model,
                 int year, double price, int mileage, String colour,
                 String fuelType, String transmission, String condition,
                 String description, String listedDate,
                 int numDoors, boolean hasSunroof, String bootCapacity) {
        super(carId, sellerId, make, model, year, price, mileage, colour,
              fuelType, transmission, condition, description, listedDate);
        this.numDoors     = numDoors;
        this.hasSunroof   = hasSunroof;
        this.bootCapacity = bootCapacity;
    }

    // ─── Polymorphic Overrides ─────────────────────────────────────────────

    @Override
    public String getBodyType() {
        return "Sedan";
    }

    @Override
    public double calculateInsuranceEstimate() {
        // Sedans have moderate insurance (~2.5% of price)
        double base = getPrice() * 0.025;
        if (hasSunroof) base *= 1.05;
        return Math.round(base * 100.0) / 100.0;
    }

    @Override
    public String getListingSummary() {
        return "[Sedan] " + getYear() + " " + getMake() + " " + getModel()
                + " | " + numDoors + " doors | Sunroof: " + (hasSunroof ? "Yes" : "No")
                + " | LKR " + getPrice();
    }

    // ─── File Serialisation ───────────────────────────────────────────────

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + numDoors + "|" + hasSunroof + "|"
                + bootCapacity + tailImageUrl();
    }

    // ─── Getters & Setters ────────────────────────────────────────────────

    public int  getNumDoors()                         { return numDoors; }
    public void setNumDoors(int n)                    { this.numDoors = n; }

    public boolean isHasSunroof()                     { return hasSunroof; }
    public void    setHasSunroof(boolean s)           { this.hasSunroof = s; }

    public String getBootCapacity()                   { return bootCapacity; }
    public void   setBootCapacity(String b)           { this.bootCapacity = b; }
}
