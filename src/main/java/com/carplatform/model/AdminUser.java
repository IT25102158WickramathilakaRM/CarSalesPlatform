package com.carplatform.model;

/**
 * Represents an Admin user – extends User.
 *
 * OOP Concepts:
 *  - Inheritance       : Extends abstract User
 *  - Encapsulation     : Admin-specific fields secured
 *  - Information Hiding: Admin permissions not accessible from public API
 *
 * IT25103531 – Gimhan U.V.K.K | Component 4 – Admin Management Module
 */
public class AdminUser extends User {

    public enum AdminLevel { SUPER_ADMIN, MODERATOR, SUPPORT }

    private AdminLevel adminLevel;
    private String     department;
    private boolean    canApproveListings;
    private boolean    canDeleteUsers;
    private boolean    canViewLogs;
    private String     lastLoginDate;

    // ─── Constructors ──────────────────────────────────────────────────────

    public AdminUser() {
        super();
        this.adminLevel         = AdminLevel.SUPPORT;
        this.canApproveListings = false;
        this.canDeleteUsers     = false;
        this.canViewLogs        = true;
    }

    public AdminUser(String userId, String username, String email, String password,
                     String fullName, String phoneNumber, String registrationDate,
                     AdminLevel adminLevel, String department) {
        super(userId, username, email, password, fullName, phoneNumber, registrationDate);
        this.adminLevel         = adminLevel;
        this.department         = department;
        // Permissions based on level
        this.canApproveListings = (adminLevel == AdminLevel.SUPER_ADMIN || adminLevel == AdminLevel.MODERATOR);
        this.canDeleteUsers     = (adminLevel == AdminLevel.SUPER_ADMIN);
        this.canViewLogs        = true;
    }

    // ─── Polymorphic Overrides ─────────────────────────────────────────────

    @Override
    public String getRole() {
        return "ADMIN";
    }

    @Override
    public String getDisplayInfo() {
        return "[ADMIN] " + getFullName() + " | Level: " + adminLevel
                + " | Dept: " + department;
    }

    // ─── Admin-Specific Methods (Information Hiding) ──────────────────────

    /**
     * Checks if this admin has permission to approve car listings.
     * Logic hidden from external callers.
     */
    public boolean hasListingApprovalPermission() {
        return isActive() && canApproveListings;
    }

    /**
     * Checks if this admin has permission to delete user accounts.
     */
    public boolean hasUserDeletionPermission() {
        return isActive() && canDeleteUsers;
    }

    // ─── File Serialisation ───────────────────────────────────────────────

    @Override
    public String toFileString() {
        return super.toFileString() + "|" + adminLevel.name() + "|" + department + "|"
                + canApproveListings + "|" + canDeleteUsers + "|" + canViewLogs + "|"
                + (lastLoginDate != null ? lastLoginDate : "N/A");
    }

    // ─── Getters & Setters ────────────────────────────────────────────────

    public AdminLevel getAdminLevel()                        { return adminLevel; }
    public void       setAdminLevel(AdminLevel l)            { this.adminLevel = l; }

    public String getDepartment()                            { return department; }
    public void   setDepartment(String d)                    { this.department = d; }

    public boolean isCanApproveListings()                    { return canApproveListings; }
    public void    setCanApproveListings(boolean b)          { this.canApproveListings = b; }

    public boolean isCanDeleteUsers()                        { return canDeleteUsers; }
    public void    setCanDeleteUsers(boolean b)              { this.canDeleteUsers = b; }

    public boolean isCanViewLogs()                           { return canViewLogs; }
    public void    setCanViewLogs(boolean b)                 { this.canViewLogs = b; }

    public String getLastLoginDate()                         { return lastLoginDate; }
    public void   setLastLoginDate(String d)                 { this.lastLoginDate = d; }
}
