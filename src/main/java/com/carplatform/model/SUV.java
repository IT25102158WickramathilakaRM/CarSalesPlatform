package com.carplatform.model;


// C2 - SUV fields + insurance calc
public class SUV extends Car {

    private int    numSeats;
    private boolean hasFourWheelDrive;
    private double groundClearance;


    // default values when creating empty object
    public SUV() {
        super();
        this.numSeats = 7;
        this.hasFourWheelDrive = false;
    }

    // SUV with seats, 4WD, ground clearance
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


    @Override
    // SUV, Sedan, or Hatchback label for filters
    public String getBodyType() {
        return "SUV";
    }

    @Override
    // yearly insurance estimate from price
    public double calculateInsuranceEstimate() {
        // SUVs have higher insurance (~3% of price per year)
        double base = getPrice() * 0.03;
        if (hasFourWheelDrive) base *= 1.1;
        return Math.round(base * 100.0) / 100.0;
    }

    @Override
    // one-line text for browse cards
    public String getListingSummary() {
        return "[SUV] " + getYear() + " " + getMake() + " " + getModel()
                + " | " + numSeats + " seats | 4WD: " + (hasFourWheelDrive ? "Yes" : "No")
                + " | LKR " + getPrice();
    }


    @Override
    // one line for the data file
    public String toFileString() {
        return super.toFileString() + "|" + numSeats + "|" + hasFourWheelDrive + "|"
                + groundClearance + tailImageUrl();
    }


    public int  getNumSeats()                             { return numSeats; }
    public void setNumSeats(int numSeats)                 { this.numSeats = numSeats; }

    public boolean isHasFourWheelDrive()                  { return hasFourWheelDrive; }
    public void    setHasFourWheelDrive(boolean f)        { this.hasFourWheelDrive = f; }

    public double getGroundClearance()                    { return groundClearance; }
    public void   setGroundClearance(double g)            { this.groundClearance = g; }

    // t23
}
