package com.carplatform.model;

/**
 * A public review – any registered user can post.
 *
 * OOP Concepts:
 *  - Inheritance   : Extends Review
 *  - Polymorphism  : Overrides getReviewType() and getFormattedDisplay()
 *
 * Member 5 – Review & Rating Management Module
 */
public class PublicReview extends Review {

    private String displayName;   // shown publicly (may differ from real name)

    public PublicReview() { super(); }

    public PublicReview(String reviewId, String carId, String reviewerId, String sellerId,
                        int rating, String title, String body, String submittedDate,
                        String displayName) {
        super(reviewId, carId, reviewerId, sellerId, rating, title, body, submittedDate);
        this.displayName = displayName;
    }

    @Override
    public String getReviewType() { return "PUBLIC"; }

    @Override
    public String getFormattedDisplay() {
        return "⭐".repeat(getRating()) + " – " + title + " (by " + displayName + ")";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + displayName;
    }

    public String getDisplayName()              { return displayName; }
    public void   setDisplayName(String n)      { this.displayName = n; }
}
