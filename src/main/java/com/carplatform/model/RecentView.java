package com.carplatform.model;


// C6 - recently viewed car + count
public class RecentView {

    private String recentViewId;
    private String userId;
    private String carId;
    private String viewedDate;
    private int    viewCount;

    // default values when creating empty object
    public RecentView() {}

    // set up a new RecentView
    public RecentView(String recentViewId, String userId, String carId, String viewedDate) {
        this.recentViewId = recentViewId;
        this.userId       = userId;
        this.carId        = carId;
        this.viewedDate   = viewedDate;
        this.viewCount    = 1;
    }

    
    // one line for the data file
    public String toFileString() {
        return recentViewId + "|" + userId + "|" + carId + "|" + viewedDate + "|" + viewCount;
    }

    // build object from one line in the txt file
    public static RecentView fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 5) return null;
        // set up a new RecentView
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
    // increment view count
    public void   incrementViewCount()           { this.viewCount++; }

    // t71
}
