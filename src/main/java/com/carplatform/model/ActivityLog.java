package com.carplatform.model;

/**
 * Represents an activity log entry for admin monitoring.
 *
 * OOP Concepts:
 *  - Encapsulation : All fields hidden behind accessors
 *
 * Member 4 – Admin Management Module
 */
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
    private String     targetId;       // ID of affected entity (user/car/etc.)
    private String     description;
    private String     timestamp;
    private String     ipAddress;

    // ─── Constructors ──────────────────────────────────────────────────────

    public ActivityLog() {}

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

    // ─── File Serialisation ───────────────────────────────────────────────

    public String toFileString() {
        return logId + "|" + adminId + "|" + actionType.name() + "|" + targetId
                + "|" + description + "|" + timestamp + "|" + ipAddress;
    }

    public static ActivityLog fromFileString(String line) {
        String[] p = line.split("\\|", -1);
        if (p.length < 7) return null;
        return new ActivityLog(p[0], p[1], ActionType.valueOf(p[2]), p[3], p[4], p[5], p[6]);
    }

    // ─── Getters & Setters ────────────────────────────────────────────────

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
}
