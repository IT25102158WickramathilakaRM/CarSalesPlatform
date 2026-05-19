package com.carplatform.model;

// C5 - review without purchase proof

/**
* PublicReview class represents a review that is visible to all users.
* It extends the base Review class and adds a display name for public viewing.
*/

public class PublicReview extends Review {

    // Name shown publicly instead of reviewer ID
    private String displayName;

    // default values when creating empty object
    /**
    * Default constructor
    * Calls parent class constructor
    */
    public PublicReview() 
    {
        super();            // call Review constructor
    }

    // set up a new PublicReview
    /**
    * Parameterized constructor
    * Initializes both parent class fields and displayName
    */
    
    public PublicReview(String reviewId, String carId, String reviewerId, String sellerId,
                        int rating, String title, String body, String submittedDate,
                        String displayName)
    {
        // Call parent constructor to initialize common review data
        super(reviewId, carId, reviewerId, sellerId, rating, title, body, submittedDate);

        // Initialize PublicReview-specific field
        this.displayName = displayName;
    }

    /**
    * Returns type of review
    * Used to identify this as PUBLIC review
    */
    @Override
    public String getReviewType()
    {
        return "PUBLIC"; 
    }

    /**
    * Returns formatted string for displaying review in UI
    * Example: ***** - Great Car (by Adhi)
    */
    @Override
    
    // stars and title shown on the page
    public String getFormattedDisplay() 
    {
        return "*".repeat(getRating()) + " - " + getTitle() + " (by " + displayName + ")";
    }

    /**
    * Converts object into string format for file storage
    */
    @Override
    // one line for the data file
    public String toFileString() 
    {
        return super.toFileString() + "|" + displayName;
    }

    // Getter for displayName
    public String getDisplayName()              { return displayName; }
    public void   setDisplayName(String n)      { this.displayName = n; }

    // t60
}
