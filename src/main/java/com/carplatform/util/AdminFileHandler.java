package com.carplatform.util;

import com.carplatform.model.AdminUser;
import com.carplatform.model.ActivityLog;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles file I/O for Admin records (admins.txt) and Activity Logs (logs.txt).
 * IT25103531 – Gimhan U.V.K.K | Component 4 – Admin Management Module
 */
public class AdminFileHandler {

    private final String adminPath;
    private final String logPath;

    public AdminFileHandler(String dataDir) {
        this.adminPath = dataDir + File.separator + "admins.txt";
        this.logPath   = dataDir + File.separator + "logs.txt";
        ensureFiles();
    }

    private void ensureFiles() {
        try {
            new File(adminPath).getParentFile().mkdirs();
            if (!new File(adminPath).exists()) new File(adminPath).createNewFile();
            if (!new File(logPath).exists())   new File(logPath).createNewFile();
        } catch (IOException e) { System.err.println("[AdminFileHandler] " + e.getMessage()); }
    }

    // ── ADMIN CRUD ────────────────────────────────────────────────────────

    public boolean saveAdmin(AdminUser admin) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(adminPath, true))) {
            w.write(admin.toFileString()); w.newLine(); return true;
        } catch (IOException e) { return false; }
    }

    public List<AdminUser> getAllAdmins() {
        List<AdminUser> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(adminPath))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.isBlank()) { AdminUser a = parseAdminLine(line); if (a != null) list.add(a); }
            }
        } catch (IOException e) { System.err.println("[AdminFileHandler] " + e.getMessage()); }
        return list;
    }

    public AdminUser findById(String id) {
        return getAllAdmins().stream().filter(a -> a.getUserId().equals(id)).findFirst().orElse(null);
    }

    public AdminUser findByUsername(String username) {
        return getAllAdmins().stream().filter(a -> a.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    public boolean updateAdmin(AdminUser updated) {
        List<AdminUser> all = getAllAdmins();
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getUserId().equals(updated.getUserId())) { all.set(i, updated); found = true; break; }
        }
        return found && rewriteAdmins(all);
    }

    public boolean deleteAdmin(String id) {
        List<AdminUser> all = getAllAdmins();
        boolean r = all.removeIf(a -> a.getUserId().equals(id));
        return r && rewriteAdmins(all);
    }

    private boolean rewriteAdmins(List<AdminUser> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(adminPath, false))) {
            for (AdminUser a : list) { w.write(a.toFileString()); w.newLine(); } return true;
        } catch (IOException e) { return false; }
    }

    private AdminUser parseAdminLine(String line) {
        try {
            String[] p = line.split("\\|", -1);
            if (p.length < 15) return null;
            AdminUser a = new AdminUser();
            a.setUserId(p[0]); a.setUsername(p[1]); a.setEmail(p[2]); a.setPassword(p[3]);
            a.setFullName(p[4]); a.setPhoneNumber(p[5]); a.setRegistrationDate(p[6]);
            a.setActive(Boolean.parseBoolean(p[7]));
            // p[8] = ADMIN; p[9]–p[14] = level, dept, permissions, lastLogin
            a.setAdminLevel(AdminUser.AdminLevel.valueOf(p[9]));
            a.setDepartment(p[10]);
            a.setCanApproveListings(Boolean.parseBoolean(p[11]));
            a.setCanDeleteUsers(Boolean.parseBoolean(p[12]));
            a.setCanViewLogs(Boolean.parseBoolean(p[13]));
            if (p.length > 14 && p[14] != null && !p[14].isBlank()) {
                a.setLastLoginDate("N/A".equals(p[14]) ? null : p[14]);
            }
            return a;
        } catch (Exception e) { System.err.println("[AdminFileHandler] Parse error: " + e.getMessage()); return null; }
    }

    // ── LOG CRUD ──────────────────────────────────────────────────────────

    public boolean appendLog(ActivityLog log) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(logPath, true))) {
            w.write(log.toFileString()); w.newLine(); return true;
        } catch (IOException e) { return false; }
    }

    public List<ActivityLog> getAllLogs() {
        List<ActivityLog> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(logPath))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.isBlank()) { ActivityLog l = ActivityLog.fromFileString(line); if (l != null) list.add(l); }
            }
        } catch (IOException e) { System.err.println("[AdminFileHandler] Log read error: " + e.getMessage()); }
        return list;
    }

    public boolean clearLogs() {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(logPath, false))) {
            return true;
        } catch (IOException e) { return false; }
    }
}
