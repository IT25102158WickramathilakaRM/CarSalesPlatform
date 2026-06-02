package com.carplatform.service;

import com.carplatform.model.*;
import com.carplatform.util.AdminFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@Service
// C4 - admin accounts, approve cars, logs
public class AdminService {

    private final AdminFileHandler adminFileHandler;
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // set up a new AdminService
    public AdminService(@Value("${app.data.directory:data}") String dataDir) {
        this.adminFileHandler = new AdminFileHandler(dataDir);
    }


    // create admin
    public String createAdmin(String username, String email, String password,
                              String fullName, String phone, String levelStr, String department) {
        if (adminFileHandler.findByUsername(username) != null) return "USERNAME_EXISTS";

        AdminUser.AdminLevel level;
        try { level = AdminUser.AdminLevel.valueOf(levelStr.toUpperCase()); }
        catch (Exception e) { level = AdminUser.AdminLevel.SUPPORT; }

        String id = "ADM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        AdminUser admin = new AdminUser(id, username, email, password, fullName, phone,
                LocalDateTime.now().format(TIMESTAMP), level, department);
        return adminFileHandler.saveAdmin(admin) ? id : "SAVE_ERROR";
    }

    public List<AdminUser> getAllAdmins() {
        return adminFileHandler.getAllAdmins();
    }

    public AdminUser getAdminById(String id) {
        return adminFileHandler.findById(id);
    }

    // validate admin login
    public AdminUser validateAdminLogin(String username, String password) {
        AdminUser admin = adminFileHandler.findByUsername(username);
        if (admin == null || !admin.getPassword().equals(password)) return null;
        admin.setLastLoginDate(LocalDateTime.now().format(TIMESTAMP));
        adminFileHandler.updateAdmin(admin);
        logActivity(admin.getUserId(), ActivityLog.ActionType.ADMIN_LOGIN,
                admin.getUserId(), "Admin logged in: " + username, "N/A");
        return admin;
    }

    // update admin
    public boolean updateAdmin(AdminUser updated) {
        return adminFileHandler.updateAdmin(updated);
    }

    // delete admin
    public boolean deleteAdmin(String adminId, String requestingAdminId) {
        AdminUser requester = adminFileHandler.findById(requestingAdminId);
        if (requester == null || !requester.hasUserDeletionPermission()) return false;
        boolean result = adminFileHandler.deleteAdmin(adminId);
        if (result) logActivity(requestingAdminId, ActivityLog.ActionType.USER_DELETED,
                adminId, "Admin account deleted", "N/A");
        return result;
    }


    // log activity
    public void logActivity(String adminId, ActivityLog.ActionType type,
                            String targetId, String description, String ip) {
        String logId = "LOG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        ActivityLog log = new ActivityLog(logId, adminId, type, targetId,
                description, LocalDateTime.now().format(TIMESTAMP), ip);
        adminFileHandler.appendLog(log);
    }

    public List<ActivityLog> getActivityLogs() {
        return adminFileHandler.getAllLogs();
    }

    // clear logs
    public boolean clearLogs(String adminId) {
        AdminUser admin = adminFileHandler.findById(adminId);
        if (admin == null || admin.getAdminLevel() != AdminUser.AdminLevel.SUPER_ADMIN) return false;
        return adminFileHandler.clearLogs();
    }

    // t52
}
