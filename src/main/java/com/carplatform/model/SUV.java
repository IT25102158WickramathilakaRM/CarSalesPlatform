package com.carplatform.model;

/**
 * Represents an SUV car listing – extends Car.
 *
 * OOP Concepts:
 *  - Inheritance   : Extends abstract Car
 *  - Polymorphism  : Overrides calculateInsuranceEstimate() and getListingSummary()
 *  - Encapsulation : SUV-specific fields hidden
 *
 * Member 2 – Car Listing Management Module
 */
public class SUV extends Car {

    private int    numSeats;
    private boolean hasFourWheelDrive;
    private double groundClearance;   // in mm

    // ─── Constructors ──────────────────────────────────────────────────────

    public SUV() {
        super();
        this.numSeats = 7;
        this.hasFourWheelDrive = false;
    }

    public SUV(String carId, String sellerId, String make, String model,
               int year, double price, int mileage, String colour,
               String fuelType, String transmission, String condition,
               String description, String listedDate,
               int numSeats, boolean hasFourWheelDrive, double groundClearance) {
        super(carId, sellerId, make, model, year, price, mileage, colour,
              fuelType, transmission, condition, description, listedDate);
        this.numSeats          = numSeats;
        this.hasFourWheelDrive = hasFourWheelDrive;
        this.groundClearance   = groundClearance;
    }

    // ─── Polymorphic Overrides ─────────────────────────────────────────────

    @Override
    public String getBodyType() {
        return "SUV";
    }

    @Override
    public double calculateInsuranceEstimate() {
        // SUVs have higher insurance (~3% of price per year)
        double base = getPrice() * 0.03;
        if (hasFourWheelDrive) base *= 1.1;
        return Math.round(base * 100.0) / 100.0;
    }

    @Override
    public String getListingSummary() {
        return "[SUV] " + getYear() + " " + getMake() + " " + getModel()
                + " | " + numSeats + " seats | 4WD: " + (hasFourWheelDrive ? "Yes" : "No")
                + " | LKR " + getPrice();
    }

    // ─── File Serialisation ───────────────────────────────────────────────

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + numSeats + "|" + hasFourWheelDrive + "|" + groundClearance;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────

    public int  getNumSeats()                             { return numSeats; }
    public void setNumSeats(int numSeats)                 { this.numSeats = numSeats; }

    public boolean isHasFourWheelDrive()                  { return hasFourWheelDrive; }
    public void    setHasFourWheelDrive(boolean f)        { this.hasFourWheelDrive = f; }

    public double getGroundClearance()                    { return groundClearance; }
    public void   setGroundClearance(double g)            { this.groundClearance = g; }
}
