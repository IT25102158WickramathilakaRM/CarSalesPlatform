package com.carplatform.service;

import com.carplatform.model.*;
import com.carplatform.util.WishlistFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service for Wishlist management and recently viewed cars.
 * IT25101535 – Pulasthi M.V.C | Component 6 – Wishlist & Search Enhancement Module
 */
@Service
public class WishlistService {

    private final WishlistFileHandler wishlistFileHandler;

    public WishlistService(@Value("${app.data.directory:data}") String dataDir) {
        this.wishlistFileHandler = new WishlistFileHandler(dataDir);
    }

    public boolean addToWishlist(String userId, String carId) {
        return wishlistFileHandler.addCarToWishlist(userId, carId, LocalDate.now().toString());
    }

    public boolean removeFromWishlist(String userId, String carId) {
        return wishlistFileHandler.removeCarFromWishlist(userId, carId, LocalDate.now().toString());
    }

    public Wishlist getWishlistForUser(String userId) {
        Wishlist w = wishlistFileHandler.findByUser(userId);
        if (w == null) w = new Wishlist("WL-" + userId, userId, LocalDate.now().toString());
        return w;
    }

    public boolean isInWishlist(String userId, String carId) {
        Wishlist w = wishlistFileHandler.findByUser(userId);
        return w != null && w.containsCar(carId);
    }

    public boolean clearWishlist(String userId) {
        return wishlistFileHandler.deleteWishlist(userId);
    }

    public void recordView(String userId, String carId) {
        String id = "RV-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        RecentView rv = new RecentView(id, userId, carId, LocalDate.now().toString());
        wishlistFileHandler.recordRecentView(rv);
    }

    public List<RecentView> getRecentViews(String userId) {
        return wishlistFileHandler.getRecentViewsForUser(userId);
    }
}
