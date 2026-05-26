package com.carplatform.model;


// C4 - audit row for admin actions
public class ActivityLog {

    public enum ActionType {
        USER_REGISTERED, USER_DELETED, USER_UPDATED,
        CAR_LISTED, CAR_APPROVED, CAR_REJECTED, CAR_DELETED,
        INQUIRY_SUBMITTED, PURCHASE_CONFIRMED,
        REVIEW_POSTED, REVIEW_DELETED,
        ADMIN_LOGIN, ADMIN_LOGOUT
    }

    private String     logId;
    private String     adminId;
    private ActionType actionType;
    private String     targetId;
    private String     description;
    private String     timestamp;
    private String     ipAddress;


    // default values when creating empty object
    public ActivityLog() {}

    // set up a new ActivityLog
    public ActivityLog(String logId, String adminId, ActionType actionType,
                       String targetId, String description, String timestamp, String ipAddress) {
        this.logId       = logId;
        this.adminId     = adminId;
        this.actionType  = actionType;
        this.targetId    = targetId;
        this.description = description;
        this.timestamp   = timestamp;
        this.ipAddress   = ipAddress;
    }


    // one line for the data file
    public String toFileString() {
        return logId + "|" + adminId + "|" + actionType.name() + "|" + targetId
                + "|" + description + "|" + timestamp + "|" + ipAddress;
    }

    // build object from one line in the txt file
    public static ActivityLog fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 7) return null;
        // set up a new ActivityLog
        return new ActivityLog(p[0], p[1], ActionType.valueOf(p[2]), p[3], p[4], p[5], p[6]);
    }


    public String     getLogId()                           { return logId; }
    public void       setLogId(String id)                  { this.logId = id; }

    public String     getAdminId()                         { return adminId; }
    public void       setAdminId(String id)                { this.adminId = id; }

    public ActionType getActionType()                      { return actionType; }
    public void       setActionType(ActionType t)          { this.actionType = t; }

    public String     getTargetId()                        { return targetId; }
    public void       setTargetId(String id)               { this.targetId = id; }

    public String     getDescription()                     { return description; }
    public void       setDescription(String d)             { this.description = d; }

    public String     getTimestamp()                       { return timestamp; }
    public void       setTimestamp(String t)               { this.timestamp = t; }

    public String     getIpAddress()                       { return ipAddress; }
    public void       setIpAddress(String ip)              { this.ipAddress = ip; }

    // t48
}
