package com.carplatform.model;

/**
 * Abstract base class representing a Car listing.
 *
 * OOP Concepts Demonstrated:
 *  - Abstraction   : Abstract class with abstract methods
 *  - Encapsulation : All fields private with getters/setters
 *  - Inheritance   : Subclasses (SUV, Sedan, Hatchback) extend this
 *
 * Member 2 – Car Listing Management Module
 */
public abstract class Car {

    // ─── Encapsulated Fields ───────────────────────────────────────────────
    private String carId;
    private String sellerId;
    private String make;            // e.g., Toyota, Honda
    private String model;
    private int    year;
    private double price;
    private int    mileage;         // in kilometres
    private String colour;
    private String fuelType;        // Petrol, Diesel, Hybrid, Electric
    private String transmission;    // Manual, Automatic
    private String condition;       // Excellent, Good, Fair
    private String description;
    private String status;          // Available, Sold, Pending
    private String listedDate;
    private boolean isApproved;

    // ─── Constructors ──────────────────────────────────────────────────────

    public Car() {
        this.status     = "Available";
        this.isApproved = false;
    }

    public Car(String carId, String sellerId, String make, String model,
               int year, double price, int mileage, String colour,
               String fuelType, String transmission, String condition,
               String description, String listedDate) {
        this.carId        = carId;
        this.sellerId     = sellerId;
        this.make         = make;
        this.model        = model;
        this.year         = year;
        this.price        = price;
        this.mileage      = mileage;
        this.colour       = colour;
        this.fuelType     = fuelType;
        this.transmission = transmission;
        this.condition    = condition;
        this.description  = description;
        this.listedDate   = listedDate;
        this.status       = "Available";
        this.isApproved   = false;
    }

    // ─── Abstract Methods ─────────────────────────────────────────────────

    /**
     * Returns the car body type (e.g., SUV, Sedan, Hatchback).
     */
    public abstract String getBodyType();

    /**
     * Polymorphic method: each car type calculates its own estimated insurance.
     */
    public abstract double calculateInsuranceEstimate();

    /**
     * Polymorphic method: returns a formatted listing summary.
     */
    public abstract String getListingSummary();

    // ─── File Serialisation ───────────────────────────────────────────────

    public String toFileString() {
        return carId + "|" + sellerId + "|" + make + "|" + model + "|" + year + "|"
                + price + "|" + mileage + "|" + colour + "|" + fuelType + "|"
                + transmission + "|" + condition + "|" + description + "|"
                + listedDate + "|" + status + "|" + isApproved + "|" + getBodyType();
    }

    // ─── Getters & Setters ────────────────────────────────────────────────

    public String getCarId()                         { return carId; }
    public void   setCarId(String carId)             { this.carId = carId; }

    public String getSellerId()                      { return sellerId; }
    public void   setSellerId(String sellerId)       { this.sellerId = sellerId; }

    public String getMake()                          { return make; }
    public void   setMake(String make)               { this.make = make; }

    public String getModel()                         { return model; }
    public void   setModel(String model)             { this.model = model; }

    public int  getYear()                            { return year; }
    public void setYear(int year)                    { this.year = year; }

    public double getPrice()                         { return price; }
    public void   setPrice(double price)             { this.price = price; }

    public int  getMileage()                         { return mileage; }
    public void setMileage(int mileage)              { this.mileage = mileage; }

    public String getColour()                        { return colour; }
    public void   setColour(String colour)           { this.colour = colour; }

    public String getFuelType()                      { return fuelType; }
    public void   setFuelType(String fuelType)       { this.fuelType = fuelType; }

    public String getTransmission()                  { return transmission; }
    public void   setTransmission(String t)          { this.transmission = t; }

    public String getCondition()                     { return condition; }
    public void   setCondition(String condition)     { this.condition = condition; }

    public String getDescription()                   { return description; }
    public void   setDescription(String d)           { this.description = d; }

    public String getStatus()                        { return status; }
    public void   setStatus(String status)           { this.status = status; }

    public String getListedDate()                    { return listedDate; }
    public void   setListedDate(String d)            { this.listedDate = d; }

    public boolean isApproved()                      { return isApproved; }
    public void    setApproved(boolean approved)     { this.isApproved = approved; }

    @Override
    public String toString() {
        return "Car{" + carId + " | " + year + " " + make + " " + model
                + " | LKR " + price + " | " + status + "}";
    }
}
