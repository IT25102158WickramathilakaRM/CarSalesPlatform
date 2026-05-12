package com.carplatform.model;

/**
 * Represents a recently viewed car entry for a user.
 * Member 6 – Wishlist & Search Enhancement Module
 */
public class RecentView {

    private String recentViewId;
    private String userId;
    private String carId;
    private String viewedDate;
    private int    viewCount;

    public RecentView() {}

    public RecentView(String recentViewId, String userId, String carId, String viewedDate) {
        this.recentViewId = recentViewId;
        this.userId       = userId;
        this.carId        = carId;
        this.viewedDate   = viewedDate;
        this.viewCount    = 1;
    }

    /** Format: recentViewId|userId|carId|viewedDate|viewCount */
    public String toFileString() {
        return recentViewId + "|" + userId + "|" + carId + "|" + viewedDate + "|" + viewCount;
    }

    public static RecentView fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 5) return null;
        RecentView rv = new RecentView(p[0], p[1], p[2], p[3]);
        rv.setViewCount(Integer.parseInt(p[4]));
        return rv;
    }

    public String getRecentViewId()              { return recentViewId; }
    public void   setRecentViewId(String id)     { this.recentViewId = id; }
    public String getUserId()                    { return userId; }
    public void   setUserId(String id)           { this.userId = id; }
    public String getCarId()                     { return carId; }
    public void   setCarId(String id)            { this.carId = id; }
    public String getViewedDate()                { return viewedDate; }
    public void   setViewedDate(String d)        { this.viewedDate = d; }
    public int    getViewCount()                 { return viewCount; }
    public void   setViewCount(int c)            { this.viewCount = c; }
    public void   incrementViewCount()           { this.viewCount++; }
}
