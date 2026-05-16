package com.carplatform.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user's wishlist of saved car listings.
 *
 * OOP Concepts:
 *  - Encapsulation : wishlist data hidden behind accessors
 *  - Abstraction   : wishlist operations managed via WishlistService
 *
 * IT25101535 – Pulasthi M.V.C | Component 6 – Wishlist & Search Enhancement Module
 */
public class Wishlist {

    private String       wishlistId;
    private String       userId;
    private List<String> carIds;       // list of saved car IDs
    private String       createdDate;
    private String       lastUpdated;

    public Wishlist() {
        this.carIds = new ArrayList<>();
    }

    public Wishlist(String wishlistId, String userId, String createdDate) {
        this.wishlistId  = wishlistId;
        this.userId      = userId;
        this.createdDate = createdDate;
        this.lastUpdated = createdDate;
        this.carIds      = new ArrayList<>();
    }

    public void addCar(String carId) {
        if (!carIds.contains(carId)) carIds.add(carId);
    }

    public void removeCar(String carId) {
        carIds.remove(carId);
    }

    public boolean containsCar(String carId) {
        return carIds.contains(carId);
    }

    public int getSize() { return carIds.size(); }

    /** Format: wishlistId|userId|createdDate|lastUpdated|carId1,carId2,... */
    public String toFileString() {
        String cars = carIds.isEmpty() ? "NONE" : String.join(",", carIds);
        return wishlistId + "|" + userId + "|" + createdDate + "|" + lastUpdated + "|" + cars;
    }

    public static Wishlist fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 5) return null;
        Wishlist w = new Wishlist(p[0], p[1], p[2]);
        w.setLastUpdated(p[3]);
        if (!"NONE".equals(p[4])) {
            for (String cid : p[4].split(",")) w.addCar(cid);
        }
        return w;
    }

    public String       getWishlistId()                   { return wishlistId; }
    public void         setWishlistId(String id)          { this.wishlistId = id; }
    public String       getUserId()                       { return userId; }
    public void         setUserId(String id)              { this.userId = id; }
    public List<String> getCarIds()                       { return carIds; }
    public void         setCarIds(List<String> ids)       { this.carIds = ids; }
    public String       getCreatedDate()                  { return createdDate; }
    public void         setCreatedDate(String d)          { this.createdDate = d; }
    public String       getLastUpdated()                  { return lastUpdated; }
    public void         setLastUpdated(String d)          { this.lastUpdated = d; }
}
