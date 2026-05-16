package com.carplatform.util;

import com.carplatform.model.Wishlist;
import com.carplatform.model.RecentView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file I/O for Wishlist (wishlist.txt) and RecentView (recentviews.txt).
 * IT25101535 – Pulasthi M.V.C | Component 6 – Wishlist & Search Enhancement Module
 */
public class WishlistFileHandler {

    private final String wishlistPath;
    private final String recentViewPath;

    public WishlistFileHandler(String dataDir) {
        this.wishlistPath   = dataDir + File.separator + "wishlist.txt";
        this.recentViewPath = dataDir + File.separator + "recentviews.txt";
        ensureFiles();
    }

    private void ensureFiles() {
        try {
            new File(wishlistPath).getParentFile().mkdirs();
            if (!new File(wishlistPath).exists())   new File(wishlistPath).createNewFile();
            if (!new File(recentViewPath).exists()) new File(recentViewPath).createNewFile();
        } catch (IOException e) { System.err.println("[WishlistFileHandler] " + e.getMessage()); }
    }

    // ── WISHLIST CRUD ─────────────────────────────────────────────────────

    public boolean saveWishlist(Wishlist w) {
        List<Wishlist> all = getAllWishlists();
        all.removeIf(x -> x.getUserId().equals(w.getUserId()));
        all.add(w);
        return rewriteWishlist(all);
    }

    public List<Wishlist> getAllWishlists() {
        List<Wishlist> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(wishlistPath))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.isBlank()) { Wishlist wl = Wishlist.fromFileString(line); if (wl != null) list.add(wl); }
            }
        } catch (IOException e) { System.err.println("[WishlistFileHandler] " + e.getMessage()); }
        return list;
    }

    public Wishlist findByUser(String userId) {
        return getAllWishlists().stream().filter(w -> w.getUserId().equals(userId)).findFirst().orElse(null);
    }

    public boolean addCarToWishlist(String userId, String carId, String date) {
        Wishlist w = findByUser(userId);
        if (w == null) {
            w = new Wishlist("WL-" + userId, userId, date);
        }
        w.addCar(carId);
        w.setLastUpdated(date);
        return saveWishlist(w);
    }

    public boolean removeCarFromWishlist(String userId, String carId, String date) {
        Wishlist w = findByUser(userId);
        if (w == null) return false;
        w.removeCar(carId);
        w.setLastUpdated(date);
        return saveWishlist(w);
    }

    public boolean deleteWishlist(String userId) {
        List<Wishlist> all = getAllWishlists();
        boolean r = all.removeIf(w -> w.getUserId().equals(userId));
        return r && rewriteWishlist(all);
    }

    private boolean rewriteWishlist(List<Wishlist> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(wishlistPath, false))) {
            for (Wishlist wl : list) { w.write(wl.toFileString()); w.newLine(); } return true;
        } catch (IOException e) { return false; }
    }

    // ── RECENT VIEWS CRUD ─────────────────────────────────────────────────

    public boolean recordRecentView(RecentView rv) {
        List<RecentView> all = getAllRecentViews();
        boolean exists = false;
        for (RecentView r : all) {
            if (r.getUserId().equals(rv.getUserId()) && r.getCarId().equals(rv.getCarId())) {
                r.incrementViewCount(); r.setViewedDate(rv.getViewedDate()); exists = true; break;
            }
        }
        if (!exists) all.add(rv);
        // Keep only last 50 per user
        return rewriteRecentViews(all);
    }

    public List<RecentView> getAllRecentViews() {
        List<RecentView> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(recentViewPath))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.isBlank()) { RecentView rv = RecentView.fromFileString(line); if (rv != null) list.add(rv); }
            }
        } catch (IOException e) { System.err.println("[WishlistFileHandler] RecentView read error: " + e.getMessage()); }
        return list;
    }

    public List<RecentView> getRecentViewsForUser(String userId) {
        List<RecentView> result = new ArrayList<>();
        for (RecentView rv : getAllRecentViews()) { if (rv.getUserId().equals(userId)) result.add(rv); }
        return result;
    }

    private boolean rewriteRecentViews(List<RecentView> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(recentViewPath, false))) {
            for (RecentView rv : list) { w.write(rv.toFileString()); w.newLine(); } return true;
        } catch (IOException e) { return false; }
    }
}
