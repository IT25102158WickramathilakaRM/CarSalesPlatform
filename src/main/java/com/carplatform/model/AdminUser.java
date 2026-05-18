package com.carplatform.model;


// C4 - admin with level + permissions
public class AdminUser extends User {

    public enum AdminLevel { SUPER_ADMIN, MODERATOR, SUPPORT }

    private AdminLevel adminLevel;
    private String     department;
    private boolean    canApproveListings;
    private boolean    canDeleteUsers;
    private boolean    canViewLogs;
    private String     lastLoginDate;


    // default values when creating empty object
    public AdminUser() {
        super();
        this.adminLevel         = AdminLevel.SUPPORT;
        this.canApproveListings = false;
        this.canDeleteUsers     = false;
        this.canViewLogs        = true;
    }

    // set up a new AdminUser
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


    @Override
    // Buyer, Seller, or Admin
    public String getRole() {
        return "ADMIN";
    }

    @Override
    // name and role line for profile
    public String getDisplayInfo() {
        return "[ADMIN] " + getFullName() + " | Level: " + adminLevel
                + " | Dept: " + department;
    }


    
    public boolean hasListingApprovalPermission() {
        return isActive() && canApproveListings;
    }

    
    public boolean hasUserDeletionPermission() {
        return isActive() && canDeleteUsers;
    }


    @Override
    // one line for the data file
    public String toFileString() {
        return super.toFileString() + "|" + adminLevel.name() + "|" + department + "|"
                + canApproveListings + "|" + canDeleteUsers + "|" + canViewLogs + "|"
                + (lastLoginDate != null ? lastLoginDate : "N/A");
    }


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
