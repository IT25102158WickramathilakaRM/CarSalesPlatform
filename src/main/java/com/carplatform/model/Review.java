package com.carplatform.model;

// C5 - base review fields
/** * Abstract class representing a general Review in the system.
* This is the base class for all review types. 
*/
public abstract class Review {

    private String reviewId;        // Unique ID for the review
    private String carId;            // ID of the car being reviewed
    private String reviewerId;         // ID of the user who wrote the review       
    private String sellerId;           // ID of the seller (if applicable) 
    private int    rating;             // Rating given (1 to 5)  
    private String title;              // Review title
    private String body;               // Review content/body 
    private String submittedDate;       // Date when review was submitted 
    private boolean isApproved;        // Whether review is approved by admin
    private boolean isFlagged;        // Whether review is flagged/reported


    // default values when creating empty object
    /** * Default constructor 
    * Initializes review as NOT approved and NOT flagged 
    */
    public Review() 
    {
        this.isApproved = false;
        this.isFlagged  = false;
    }

    // set up a new Review
    /** * Parameterized constructor 
    * Initializes all review data
    */
    public Review(String reviewId, String carId, String reviewerId, String sellerId,
                  int rating, String title, String body, String submittedDate) 
    {
        this.reviewId      = reviewId;
        this.carId         = carId;
        this.reviewerId    = reviewerId;
        this.sellerId      = sellerId;
        this.rating        = rating;
        this.title         = title;
        this.body          = body;
        this.submittedDate = submittedDate;

        // Default states
        this.isApproved    = false;
        this.isFlagged     = false;
    }


    // PUBLIC or VERIFIED label
    /** * Abstract method → must be implemented by child classes 
    * Used to define type of review (Public / Verified etc.)
    */
    public abstract String getReviewType();

    // stars and title shown on the page
    /** 
    * Abstract method → custom display format for each review type 
    */
    public abstract String getFormattedDisplay();


    // one line for the data file
    /** 
    * Converts review object into a string for file storage 
    */
    public String toFileString()
    {
        return reviewId + "|" + carId + "|" + reviewerId + "|" + sellerId + "|"
                + rating + "|" + title + "|" + body + "|" + submittedDate + "|"
                + isApproved + "|" + isFlagged + "|" + getReviewType();
    }

// ================= GETTERS & SETTERS =================
    public String getReviewId()                          { return reviewId; }
    public void   setReviewId(String id)                 { this.reviewId = id; }

    public String getCarId()                             { return carId; }
    public void   setCarId(String carId)                 { this.carId = carId; }

    public String getReviewerId()                        { return reviewerId; }
    public void   setReviewerId(String id)               { this.reviewerId = id; }

    public String getSellerId()                          { return sellerId; }
    public void   setSellerId(String id)                 { this.sellerId = id; }

    public int  getRating()                              { return rating; }

    /** 
    * Ensures rating is always between 1 and 5 
    */
    public void setRating(int rating)                    { this.rating = Math.max(1, Math.min(5, rating)); }

    public String getTitle()                             { return title; }
    public void   setTitle(String title)                 { this.title = title; }

    public String getBody()                              { return body; }
    public void   setBody(String body)                   { this.body = body; }

    public String getSubmittedDate()                     { return submittedDate; }
    public void   setSubmittedDate(String d)             { this.submittedDate = d; }

    public boolean isApproved()                          { return isApproved; }
    public void    setApproved(boolean approved)         { this.isApproved = approved; }

    public boolean isFlagged()                           { return isFlagged; }
    public void    setFlagged(boolean flagged)           { this.isFlagged = flagged; }

    // t134
}
