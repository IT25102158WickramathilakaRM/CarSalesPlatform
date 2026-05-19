package com.carplatform.service;

import com.carplatform.model.*;
import com.carplatform.util.ReviewFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/** * Service class that handles all business logic related to Reviews.
* Acts as a middle layer between UI/controller and file storage.
*/
@Service
// C5 - reviews + average rating
public class ReviewService {

    // Handles file operations (save, load, update, delete)
    private final ReviewFileHandler reviewFileHandler;

    // set up a new ReviewService
    /**
    * Constructor injection for data directory 
    */
    public ReviewService(@Value("${app.data.directory:data}") String dataDir) 
    {
        this.reviewFileHandler = new ReviewFileHandler(dataDir);
    }

    // submit public review
    /**
    * Submit a public review 
    */
    public String submitPublicReview(String carId, String reviewerId, String sellerId,
                                     int rating, String title, String body, String displayName)
    {
        // Validation checks
        if (title == null || title.isBlank()) return "EMPTY_TITLE";
        if (body  == null || body.isBlank())  return "EMPTY_BODY";
        if (rating < 1 || rating > 5)         return "INVALID_RATING";

        // Generate unique review ID
        String id = "REV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Create PublicReview object
        PublicReview pr = new PublicReview(id, carId, reviewerId, sellerId, rating,
                title, body, LocalDate.now().toString(), displayName);

        // Save to file
        return reviewFileHandler.saveReview(pr) ? id : "SAVE_ERROR";
    }

    // submit verified review
    /** 
    * Submit a verified review 
    */
    public String submitVerifiedReview(String carId, String reviewerId, String sellerId,
                                       int rating, String title, String body, String purchaseId) 
    {
        if (title == null || title.isBlank()) return "EMPTY_TITLE";
        if (body  == null || body.isBlank())  return "EMPTY_BODY";
        if (rating < 1 || rating > 5)         return "INVALID_RATING";
        
        String id = "REV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Verified review always marked as true
        VerifiedReview vr = new VerifiedReview(id, carId, reviewerId, sellerId, rating,
                title, body, LocalDate.now().toString(), purchaseId, true);
        return reviewFileHandler.saveReview(vr) ? id : "SAVE_ERROR";
    }

    /** 
    * Get all reviews for a specific car 
    */

    public List<Review> getReviewsForCar(String carId)      { return reviewFileHandler.findByCar(carId); }

    /** 
    * Get all reviews written by a specific user 
    */
    public List<Review> getReviewsByUser(String userId)      { return reviewFileHandler.findByReviewer(userId); }

    /** 
    * Get reviews that are not approved yet 
    */
    public List<Review> getPendingReviews()                  { return reviewFileHandler.findPending(); }

    /**
    * Get all reviews 
    */
    public List<Review> getAllReviews()                       { return reviewFileHandler.getAllReviews(); }

    /**
    * Calculate average rating for a car
    */
    public double       getAverageRating(String carId)       { return reviewFileHandler.getAverageRatingForCar(carId); }

    /** 
    * Get rating distribution (1–5 stars)'
    */
    public int[] getRatingDistribution(String carId) 
    {
        int[] dist = new int[6];        // index 1–5 used
        
        for (Review r : getReviewsForCar(carId))
            if (r.getRating() >= 1 && r.getRating() <= 5) dist[r.getRating()]++;
        return dist;
    }

    // edit review
    /** 
    * Edit review (only by original reviewer) 
    */
    public boolean editReview(String reviewId, String userId, String newTitle, String newBody, int newRating) 
 {
        Review r = reviewFileHandler.findById(reviewId);

     // Security check: only owner can edit
        if (r == null || !r.getReviewerId().equals(userId)) return false;
     
        r.setTitle(newTitle);
         r.setBody(newBody);
         r.setRating(newRating);
     
        return reviewFileHandler.updateReview(r);
    }

    // approve review
    /** 
    * Approve a review (admin action)
    */
    public boolean approveReview(String reviewId)
    {
        Review r = reviewFileHandler.findById(reviewId);
        if (r == null) return false;
        
        r.setApproved(true); 
        r.setFlagged(false);
        
        return reviewFileHandler.updateReview(r);
    }

    // flag review
    /** 
    * Flag a review as inappropriate 
    */
    public boolean flagReview(String reviewId) {
        Review r = reviewFileHandler.findById(reviewId);
        if (r == null) return false;
        r.setFlagged(true);
        return reviewFileHandler.updateReview(r);
    }

    // delete review
    public boolean deleteReview(String reviewId) { return reviewFileHandler.deleteReview(reviewId); }

    // t175
}
