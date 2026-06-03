package com.carplatform.model;


// C5 - review without purchase proof
public class PublicReview extends Review {

    private String displayName;

    // default values when creating empty object
    public PublicReview() { super(); }

    // set up a new PublicReview
    public PublicReview(String reviewId, String carId, String reviewerId, String sellerId,
                        int rating, String title, String body, String submittedDate,
                        String displayName) {
        super(reviewId, carId, reviewerId, sellerId, rating, title, body, submittedDate);
        this.displayName = displayName;
    }

    @Override
    public String getReviewType() { return "PUBLIC"; }

    @Override
    // stars and title shown on the page
    public String getFormattedDisplay() {
        return "*".repeat(getRating()) + " - " + getTitle() + " (by " + displayName + ")";
    }

    @Override
    // one line for the data file
    public String toFileString() {
        return super.toFileString() + "|" + displayName;
    }

    public String getDisplayName()              { return displayName; }
    public void   setDisplayName(String n)      { this.displayName = n; }

    // t60
}
