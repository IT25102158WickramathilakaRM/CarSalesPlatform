package com.carplatform.util;

import com.carplatform.model.Wishlist;
import com.carplatform.model.RecentView;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all file operations related to:
 * 1. Wishlist management
 * 2. Recent viewed cars management
 *
 * Data is stored in:
 * - wishlist.txt
 * - recentviews.txt
 *
 * IT25101535 – Pulasthi M.V.C
 * Component 6 – Wishlist & Search Enhancement Module
 */
public class WishlistFileHandler {

    // File path for wishlist storage and recent views storage
    private final String wishlistPath;

    private final String recentViewPath;

    /**
     * Constructor
     * Initializes file paths and ensures files exist.
     *
     * @param dataDir Directory where text files are stored
     */
    public WishlistFileHandler(String dataDir) {

        // Create path for wishlist.txt and recentviews.txt
        this.wishlistPath = dataDir + File.separator + "wishlist.txt";

        this.recentViewPath = dataDir + File.separator + "recentviews.txt";

        // Create files if they do not exist
        ensureFiles();
    }

    /**
     * Creates required folders/files if missing.
     */
    private void ensureFiles() {
        try {

            // Create parent directory if it does not exist
            new File(wishlistPath).getParentFile().mkdirs();

            // Create wishlist file if missing
            if (!new File(wishlistPath).exists()) {
                new File(wishlistPath).createNewFile();
            }

            // Create recent views file if missing
            if (!new File(recentViewPath).exists()) {
                new File(recentViewPath).createNewFile();
            }

        } catch (IOException e) {

            // Display error message if file creation fails
            System.err.println("[WishlistFileHandler] " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────
    // WISHLIST CRUD OPERATIONS
    // ─────────────────────────────────────────────────────────────

    /**
     * Saves a wishlist.
     * If a wishlist for the same user already exists,
     * it will be replaced.
     *
     * @param w Wishlist object
     * @return true if save successful
     */
    public boolean saveWishlist(Wishlist w) {

        // Get all existing wishlists
        List<Wishlist> all = getAllWishlists();

        // Remove old wishlist of the same user
        all.removeIf(x -> x.getUserId().equals(w.getUserId()));

        // Add updated/new wishlist
        all.add(w);

        // Rewrite entire file
        return rewriteWishlist(all);
    }

    /**
     * Reads all wishlists from wishlist.txt
     *
     * @return List of Wishlist objects
     */
    public List<Wishlist> getAllWishlists() {

        List<Wishlist> list = new ArrayList<>();

        try (BufferedReader r = new BufferedReader(new FileReader(wishlistPath))) {

            String line;

            // Read file line by line
            while ((line = r.readLine()) != null) {

                // Ignore empty lines
                if (!line.isBlank()) {

                    // Convert file text into Wishlist object
                    Wishlist wl = Wishlist.fromFileString(line);

                    // Add valid object to list
                    if (wl != null) {
                        list.add(wl);
                    }
                }
            }

        } catch (IOException e) {

            System.err.println("[WishlistFileHandler] " + e.getMessage());
        }

        return list;
    }

    /**
     * Finds wishlist using user ID.
     *
     * @param userId User ID
     * @return Wishlist object or null
     */
    public Wishlist findByUser(String userId) {

        return getAllWishlists()
                .stream()
                .filter(w -> w.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Adds a car to user's wishlist.
     *
     * @param userId User ID
     * @param carId  Car ID
     * @param date   Current date
     * @return true if successful
     */
    public boolean addCarToWishlist(String userId, String carId, String date) {

        // Find existing wishlist
        Wishlist w = findByUser(userId);

        // Create new wishlist if user has none
        if (w == null) {
            w = new Wishlist("WL-" + userId, userId, date);
        }

        // Add car to wishlist
        w.addCar(carId);

        // Update modified date
        w.setLastUpdated(date);

        // Save updated wishlist
        return saveWishlist(w);
    }

    /**
     * Removes a car from wishlist.
     *
     * @param userId User ID
     * @param carId  Car ID
     * @param date   Current date
     * @return true if successful
     */
    public boolean removeCarFromWishlist(String userId, String carId, String date) {

        // Find wishlist
        Wishlist w = findByUser(userId);

        // Return false if wishlist does not exist
        if (w == null) {
            return false;
        }

        // Remove car
        w.removeCar(carId);

        // Update modified date
        w.setLastUpdated(date);

        // Save updated wishlist
        return saveWishlist(w);
    }

    /**
     * Deletes a user's wishlist.
     *
     * @param userId User ID
     * @return true if deletion successful
     */
    public boolean deleteWishlist(String userId) {

        // Get all wishlists
        List<Wishlist> all = getAllWishlists();

        // Remove matching wishlist
        boolean r = all.removeIf(w -> w.getUserId().equals(userId));

        // Rewrite file after deletion
        return r && rewriteWishlist(all);
    }

    /**
     * Rewrites entire wishlist file.
     *
     * @param list Updated wishlist list
     * @return true if successful
     */
    private boolean rewriteWishlist(List<Wishlist> list) {

        try (BufferedWriter w = new BufferedWriter(
                new FileWriter(wishlistPath, false))) {

            // Write each wishlist into file
            for (Wishlist wl : list) {

                w.write(wl.toFileString());
                w.newLine();
            }

            return true;

        } catch (IOException e) {

            return false;
        }
    }

    // ─────────────────────────────────────────────────────────────
    // RECENT VIEW CRUD OPERATIONS
    // ─────────────────────────────────────────────────────────────

    /**
     * Records a recently viewed car.
     * If already viewed before, view count is increased.
     *
     * @param rv RecentView object
     * @return true if successful
     */
    public boolean recordRecentView(RecentView rv) {

        // Get all recent views
        List<RecentView> all = getAllRecentViews();

        boolean exists = false;

        // Check whether same user already viewed same car
        for (RecentView r : all) {

            if (r.getUserId().equals(rv.getUserId())
                    && r.getCarId().equals(rv.getCarId())) {

                // Increase view count
                r.incrementViewCount();

                // Update viewed date
                r.setViewedDate(rv.getViewedDate());

                exists = true;
                break;
            }
        }

        // Add new record if not already present
        if (!exists) {
            all.add(rv);
        }

        // Rewrite file
        return rewriteRecentViews(all);
    }

    /**
     * Reads all recent views from file.
     *
     * @return List of RecentView objects
     */
    public List<RecentView> getAllRecentViews() {

        List<RecentView> list = new ArrayList<>();

        try (BufferedReader r = new BufferedReader(
                new FileReader(recentViewPath))) {

            String line;

            // Read file line by line
            while ((line = r.readLine()) != null) {

                // Ignore empty lines
                if (!line.isBlank()) {

                    // Convert text into RecentView object
                    RecentView rv = RecentView.fromFileString(line);

                    // Add valid object to list
                    if (rv != null) {
                        list.add(rv);
                    }
                }
            }

        } catch (IOException e) {

            System.err.println(
                    "[WishlistFileHandler] RecentView read error: "
                            + e.getMessage());
        }

        return list;
    }

    /**
     * Gets recent views for a specific user.
     *
     * @param userId User ID
     * @return List of viewed cars
     */
    public List<RecentView> getRecentViewsForUser(String userId) {

        List<RecentView> result = new ArrayList<>();

        // Filter recent views by user ID
        for (RecentView rv : getAllRecentViews()) {

            if (rv.getUserId().equals(userId)) {
                result.add(rv);
            }
        }

        return result;
    }

    /**
     * Rewrites recentviews.txt file.
     *
     * @param list Updated recent view list
     * @return true if successful
     */
    private boolean rewriteRecentViews(List<RecentView> list) {

        try (BufferedWriter w = new BufferedWriter(
                new FileWriter(recentViewPath, false))) {

            // Write all records into file
            for (RecentView rv : list) {

                w.write(rv.toFileString());
                w.newLine();
            }

            return true;

        } catch (IOException e) {

            return false;
        }
    }
}