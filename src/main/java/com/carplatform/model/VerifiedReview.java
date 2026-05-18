package com.carplatform.model;


// C5 - review linked to purchaseId
public class VerifiedReview extends Review {

    private String purchaseId;
    private boolean isVerified;

    // default values when creating empty object
    public VerifiedReview() { super(); this.isVerified = false; }

    // set up a new VerifiedReview
    public VerifiedReview(String reviewId, String carId, String reviewerId, String sellerId,
                          int rating, String title, String body, String submittedDate,
                          String purchaseId, boolean isVerified) {
        super(reviewId, carId, reviewerId, sellerId, rating, title, body, submittedDate);
        this.purchaseId = purchaseId;
        this.isVerified = isVerified;
    }

    @Override public String getReviewType() { return "VERIFIED"; }

    @Override
    // stars and title shown on the page
    public String getFormattedDisplay() {
        String badge = isVerified ? " [Verified Purchase]" : "";
        return getRating() + "/5 – " + getTitle() + badge;
    }

    @Override
    // one line for the data file
    public String toFileString() {
        return super.toFileString() + "|" + purchaseId + "|" + isVerified;
    }

    public String  getPurchaseId()          { return purchaseId; }
    public void    setPurchaseId(String id) { this.purchaseId = id; }
    public boolean isVerified()             { return isVerified; }
    public void    setVerified(boolean v)   { this.isVerified = v; }
}
