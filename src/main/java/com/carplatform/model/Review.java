package com.carplatform.model;


// C5 - base review fields
public abstract class Review {

    private String reviewId;
    private String carId;
    private String reviewerId;
    private String sellerId;
    private int    rating;
    private String title;
    private String body;
    private String submittedDate;
    private boolean isApproved;
    private boolean isFlagged;


    // default values when creating empty object
    public Review() {
        this.isApproved = false;
        this.isFlagged  = false;
    }

    // set up a new Review
    public Review(String reviewId, String carId, String reviewerId, String sellerId,
                  int rating, String title, String body, String submittedDate) {
        this.reviewId      = reviewId;
        this.carId         = carId;
        this.reviewerId    = reviewerId;
        this.sellerId      = sellerId;
        this.rating        = rating;
        this.title         = title;
        this.body          = body;
        this.submittedDate = submittedDate;
        this.isApproved    = false;
        this.isFlagged     = false;
    }


    // PUBLIC or VERIFIED label
    public abstract String getReviewType();

    // stars and title shown on the page
    public abstract String getFormattedDisplay();


    // one line for the data file
    public String toFileString() {
        return reviewId + "|" + carId + "|" + reviewerId + "|" + sellerId + "|"
                + rating + "|" + title + "|" + body + "|" + submittedDate + "|"
                + isApproved + "|" + isFlagged + "|" + getReviewType();
    }


    public String getReviewId()                          { return reviewId; }
    public void   setReviewId(String id)                 { this.reviewId = id; }

    public String getCarId()                             { return carId; }
    public void   setCarId(String carId)                 { this.carId = carId; }

    public String getReviewerId()                        { return reviewerId; }
    public void   setReviewerId(String id)               { this.reviewerId = id; }

    public String getSellerId()                          { return sellerId; }
    public void   setSellerId(String id)                 { this.sellerId = id; }

    public int  getRating()                              { return rating; }
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

    // t59
}
