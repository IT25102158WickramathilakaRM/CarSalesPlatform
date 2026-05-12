package com.carplatform.model;

/**
 * A verified review – posted only by confirmed buyers of that vehicle.
 *
 * OOP Concepts:
 *  - Inheritance   : Extends Review
 *  - Polymorphism  : Overrides getReviewType() and getFormattedDisplay()
 *
 * Member 5 – Review & Rating Management Module
 */
public class VerifiedReview extends Review {

    private String purchaseId;
    private boolean isVerified;

    public VerifiedReview() { super(); this.isVerified = false; }

    public VerifiedReview(String reviewId, String carId, String reviewerId, String sellerId,
                          int rating, String title, String body, String submittedDate,
                          String purchaseId, boolean isVerified) {
        super(reviewId, carId, reviewerId, sellerId, rating, title, body, submittedDate);
        this.purchaseId = purchaseId;
        this.isVerified = isVerified;
    }

    @Override public String getReviewType() { return "VERIFIED"; }

    @Override
    public String getFormattedDisplay() {
        String badge = isVerified ? " [Verified Purchase]" : "";
        return getRating() + "/5 – " + getTitle() + badge;
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + purchaseId + "|" + isVerified;
    }

    public String  getPurchaseId()          { return purchaseId; }
    public void    setPurchaseId(String id) { this.purchaseId = id; }
    public boolean isVerified()             { return isVerified; }
    public void    setVerified(boolean v)   { this.isVerified = v; }
}
