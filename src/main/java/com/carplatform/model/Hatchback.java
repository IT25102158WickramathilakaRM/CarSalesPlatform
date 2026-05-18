package com.carplatform.model;


// C2 - hatchback fields + insurance calc
public class Hatchback extends Car {

    private boolean hasFoldableRearSeats;
    private String  roofType;
    private int     engineCC;


    // default values when creating empty object
    public Hatchback() {
        super();
        this.hasFoldableRearSeats = true;
        this.roofType             = "Standard";
    }

    // hatchback with engine size and roof type
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


    @Override
    // SUV, Sedan, or Hatchback label for filters
    public String getBodyType() {
        return "Hatchback";
    }

    @Override
    // yearly insurance estimate from price
    public double calculateInsuranceEstimate() {
        // Hatchbacks have lower insurance (~2% of price)
        double base = getPrice() * 0.02;
        if (engineCC > 1600) base *= 1.08;
        return Math.round(base * 100.0) / 100.0;
    }

    @Override
    // one-line text for browse cards
    public String getListingSummary() {
        return "[Hatchback] " + getYear() + " " + getMake() + " " + getModel()
                + " | " + engineCC + "cc | Foldable Seats: " + (hasFoldableRearSeats ? "Yes" : "No")
                + " | LKR " + getPrice();
    }


    @Override
    // one line for the data file
    public String toFileString() {
        return super.toFileString() + "|" + hasFoldableRearSeats + "|" + roofType + "|"
                + engineCC + tailImageUrl();
    }


    public boolean isHasFoldableRearSeats()               { return hasFoldableRearSeats; }
    public void    setHasFoldableRearSeats(boolean f)     { this.hasFoldableRearSeats = f; }

    public String getRoofType()                           { return roofType; }
    public void   setRoofType(String r)                   { this.roofType = r; }

    public int  getEngineCC()                             { return engineCC; }
    public void setEngineCC(int e)                        { this.engineCC = e; }
}
