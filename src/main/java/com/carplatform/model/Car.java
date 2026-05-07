package com.carplatform.model;


// C2 - base car listing
public abstract class Car {

    private String carId;
    private String sellerId;
    private String make;
    private String model;
    private int    year;
    private double price;
    private int    mileage;
    private String colour;
    private String fuelType;
    private String transmission;
    private String condition;
    private String description;
    private String status;
    private String listedDate;
    private boolean isApproved;
    
    private String imageUrl;


    // default values when creating empty object
    public Car() {
        this.status     = "Available";
        this.isApproved = false;
        this.imageUrl   = "";
    }

    // fill in all the main car details
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


    
    // SUV, Sedan, or Hatchback label for filters
    public abstract String getBodyType();

    
    // yearly insurance estimate from price
    public abstract double calculateInsuranceEstimate();

    
    // one-line text for browse cards
    public abstract String getListingSummary();


    // one line for the data file
    public String toFileString() {
        return carId + "|" + sellerId + "|" + make + "|" + model + "|" + year + "|"
                + price + "|" + mileage + "|" + colour + "|" + fuelType + "|"
                + transmission + "|" + condition + "|" + description + "|"
                + listedDate + "|" + status + "|" + isApproved + "|" + getBodyType();
    }


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

    public String getImageUrl()                      { return imageUrl; }
    public void   setImageUrl(String imageUrl)       { this.imageUrl = imageUrl == null ? "" : imageUrl.trim(); }

    
    public boolean hasImage() {
        return imageUrl != null && !imageUrl.isBlank();
    }

    
    // tail image url
    protected String tailImageUrl() {
        String u = imageUrl;
        return "|" + (u == null || u.isBlank() ? "" : u);
    }

    @Override
    // handy text when debugging in IntelliJ
    public String toString() {
        return "Car{" + carId + " | " + year + " " + make + " " + model
                + " | LKR " + price + " | " + status + "}";
    }

    // t22
}
