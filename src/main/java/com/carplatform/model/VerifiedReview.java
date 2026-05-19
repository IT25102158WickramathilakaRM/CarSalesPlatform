package com.carplatform.model;

// C5 - review linked to purchaseId
/** 
* VerifiedReview represents a review that is linked to a verified purchase.
* It extends the base Review class.
*/
public class VerifiedReview extends Review
    {

    private String purchaseId;        // ID of the purchase related to this review
    private boolean isVerified;        // Flag to indicate whether review is verified

    // default values when creating empty object
    /** 
    * Default constructor 
    */   
    public VerifiedReview() 
        { super();        // call parent constructor
         this.isVerified = false; 
        }

    // set up a new VerifiedReview
    /** 
    * Parameterized constructor
    */
        
    public VerifiedReview(String reviewId, String carId, String reviewerId, String sellerId,
                          int rating, String title, String body, String submittedDate,
                          String purchaseId, boolean isVerified)
        {
         // Initialize common fields using parent constructor 
        super(reviewId, carId, reviewerId, sellerId, rating, title, body, submittedDate);

        // Initialize child-specific fields  
        this.purchaseId = purchaseId;
        this.isVerified = isVerified;
        }

        /**
        * Returns review type 
        */
    @Override public String getReviewType() 
        {
            return "VERIFIED";
        }

       /** 
       * Returns formatted display string for UI 
       * Adds verified badge if applicable
       */ 
    @Override
    // stars and title shown on the page
    public String getFormattedDisplay()
        {
        String badge = isVerified ? " [Verified Purchase]" : "";
        return getRating() + "/5 – " + getTitle() + badge;
        }

        /** 
        * Converts object to string for file storage
        */
    @Override
    // one line for the data file
    public String toFileString()
        {
        return super.toFileString() + "|" + purchaseId + "|" + isVerified;
        }
        
// ================= GETTERS & SETTERS =================
    public String  getPurchaseId()          { return purchaseId; }
    public void    setPurchaseId(String id) { this.purchaseId = id; }
    public boolean isVerified()             { return isVerified; }
    public void    setVerified(boolean v)   { this.isVerified = v; }

    // t61
}
