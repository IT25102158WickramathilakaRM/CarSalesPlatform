package com.carplatform.service;

import com.carplatform.model.*;
import com.carplatform.util.WishlistFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
// C6 - wishlist and recent views
public class WishlistService {

    private final WishlistFileHandler wishlistFileHandler;

    // set up a new WishlistService
    public WishlistService(@Value("${app.data.directory:data}") String dataDir) {
        this.wishlistFileHandler = new WishlistFileHandler(dataDir);
    }

    // bookmark this car for later
    public boolean addToWishlist(String userId, String carId) {
        return wishlistFileHandler.addCarToWishlist(userId, carId, LocalDate.now().toString());
    }

    // remove from wishlist
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

    // clear wishlist
    public boolean clearWishlist(String userId) {
        return wishlistFileHandler.deleteWishlist(userId);
    }

    // remember buyer opened this car page
    public void recordView(String userId, String carId) {
        String id = "RV-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        RecentView rv = new RecentView(id, userId, carId, LocalDate.now().toString());
        wishlistFileHandler.recordRecentView(rv);
    }

    public List<RecentView> getRecentViews(String userId) {
        return wishlistFileHandler.getRecentViewsForUser(userId);
    }
}
