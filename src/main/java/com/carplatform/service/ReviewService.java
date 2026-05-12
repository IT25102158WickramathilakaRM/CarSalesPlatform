package com.carplatform.service;

import com.carplatform.model.*;
import com.carplatform.util.ReviewFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Service for Review and Rating management.
 * Member 5 – Review and Rating Management Module
 */
@Service
public class ReviewService {

    private final ReviewFileHandler reviewFileHandler;

    public ReviewService(@Value("${app.data.directory:data}") String dataDir) {
        this.reviewFileHandler = new ReviewFileHandler(dataDir);
    }

    public String submitPublicReview(String carId, String reviewerId, String sellerId,
                                     int rating, String title, String body, String displayName) {
        if (title == null || title.isBlank()) return "EMPTY_TITLE";
        if (body  == null || body.isBlank())  return "EMPTY_BODY";
        if (rating < 1 || rating > 5)         return "INVALID_RATING";
        String id = "REV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        PublicReview pr = new PublicReview(id, carId, reviewerId, sellerId, rating,
                title, body, LocalDate.now().toString(), displayName);
        return reviewFileHandler.saveReview(pr) ? id : "SAVE_ERROR";
    }

    public String submitVerifiedReview(String carId, String reviewerId, String sellerId,
                                       int rating, String title, String body, String purchaseId) {
        if (title == null || title.isBlank()) return "EMPTY_TITLE";
        if (body  == null || body.isBlank())  return "EMPTY_BODY";
        if (rating < 1 || rating > 5)         return "INVALID_RATING";
        String id = "REV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        VerifiedReview vr = new VerifiedReview(id, carId, reviewerId, sellerId, rating,
                title, body, LocalDate.now().toString(), purchaseId, true);
        return reviewFileHandler.saveReview(vr) ? id : "SAVE_ERROR";
    }

    public List<Review> getReviewsForCar(String carId)      { return reviewFileHandler.findByCar(carId); }
    public List<Review> getReviewsByUser(String userId)      { return reviewFileHandler.findByReviewer(userId); }
    public List<Review> getPendingReviews()                  { return reviewFileHandler.findPending(); }
    public List<Review> getAllReviews()                       { return reviewFileHandler.getAllReviews(); }
    public double       getAverageRating(String carId)       { return reviewFileHandler.getAverageRatingForCar(carId); }

    public int[] getRatingDistribution(String carId) {
        int[] dist = new int[6];
        for (Review r : getReviewsForCar(carId))
            if (r.getRating() >= 1 && r.getRating() <= 5) dist[r.getRating()]++;
        return dist;
    }

    public boolean editReview(String reviewId, String userId, String newTitle, String newBody, int newRating) {
        Review r = reviewFileHandler.findById(reviewId);
        if (r == null || !r.getReviewerId().equals(userId)) return false;
        r.setTitle(newTitle); r.setBody(newBody); r.setRating(newRating);
        return reviewFileHandler.updateReview(r);
    }

    public boolean approveReview(String reviewId) {
        Review r = reviewFileHandler.findById(reviewId);
        if (r == null) return false;
        r.setApproved(true); r.setFlagged(false);
        return reviewFileHandler.updateReview(r);
    }

    public boolean flagReview(String reviewId) {
        Review r = reviewFileHandler.findById(reviewId);
        if (r == null) return false;
        r.setFlagged(true);
        return reviewFileHandler.updateReview(r);
    }

    public boolean deleteReview(String reviewId) { return reviewFileHandler.deleteReview(reviewId); }
}
