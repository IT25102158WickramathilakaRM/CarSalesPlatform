package com.carplatform.servlet;

import com.carplatform.model.*;
import com.carplatform.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for Admin dashboard, moderation, and management.
 * IT25103531 – Gimhan U.V.K.K | Component 4 – Admin Management Module
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    private final CarService carService;
    private final AuthenticationService authService;
    private final TransactionService transactionService;

    public AdminController(AdminService as, CarService cs, AuthenticationService aus, TransactionService ts) {
        this.adminService    = as;
        this.carService      = cs;
        this.authService     = aus;
        this.transactionService = ts;
    }

    private boolean isAdmin(HttpSession s) {
        return "ADMIN".equals(s.getAttribute("userRole"));
    }

    // ── DASHBOARD ─────────────────────────────────────────────────────────

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("totalUsers",    authService.getAllUsers().size());
        model.addAttribute("totalCars",     carService.getAllListings().size());
        model.addAttribute("pendingCars",   carService.getPendingApproval().size());
        model.addAttribute("totalPurchases",transactionService.getAllPurchases().size());
        model.addAttribute("recentLogs",    adminService.getActivityLogs());
        model.addAttribute("loggedUser",    session.getAttribute("loggedUser"));
        return "admin/dashboard";
    }

    // ── USER MANAGEMENT ───────────────────────────────────────────────────

    @GetMapping("/users")
    public String manageUsers(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("users", authService.getAllUsers());
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "admin/users";
    }

    @PostMapping("/users/delete/{userId}")
    public String deleteUser(@PathVariable String userId, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        AdminUser admin = (AdminUser) session.getAttribute("loggedUser");
        if (admin.hasUserDeletionPermission()) {
            authService.deleteAccount(userId);
            adminService.logActivity(admin.getUserId(), ActivityLog.ActionType.USER_DELETED, userId, "User deleted by admin", "N/A");
            ra.addFlashAttribute("success", "User deleted.");
        } else {
            ra.addFlashAttribute("error", "You do not have permission to delete users.");
        }
        return "redirect:/admin/users";
    }

    // ── CAR MODERATION ────────────────────────────────────────────────────

    @GetMapping("/cars")
    public String moderateCars(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("pendingCars", carService.getPendingApproval());
        model.addAttribute("allCars",     carService.getAllListings());
        model.addAttribute("loggedUser",  session.getAttribute("loggedUser"));
        return "admin/cars";
    }

    @PostMapping("/cars/approve/{carId}")
    public String approveCar(@PathVariable String carId, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        AdminUser admin = (AdminUser) session.getAttribute("loggedUser");
        if (admin.hasListingApprovalPermission()) {
            carService.approveListing(carId);
            adminService.logActivity(admin.getUserId(), ActivityLog.ActionType.CAR_APPROVED, carId, "Car listing approved", "N/A");
            ra.addFlashAttribute("success", "Listing approved.");
        } else {
            ra.addFlashAttribute("error", "Permission denied.");
        }
        return "redirect:/admin/cars";
    }

    @PostMapping("/cars/reject/{carId}")
    public String rejectCar(@PathVariable String carId, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        carService.rejectListing(carId);
        AdminUser admin = (AdminUser) session.getAttribute("loggedUser");
        adminService.logActivity(admin.getUserId(), ActivityLog.ActionType.CAR_REJECTED, carId, "Car listing rejected", "N/A");
        ra.addFlashAttribute("success", "Listing rejected.");
        return "redirect:/admin/cars";
    }

    @PostMapping("/cars/delete/{carId}")
    public String adminDeleteCar(@PathVariable String carId, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        carService.deleteListing(carId);
        ra.addFlashAttribute("success", "Listing deleted by admin.");
        return "redirect:/admin/cars";
    }

    // ── ADMIN ACCOUNT MANAGEMENT ──────────────────────────────────────────

    @GetMapping("/manage")
    public String manageAdmins(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("admins", adminService.getAllAdmins());
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "admin/manage";
    }

    @PostMapping("/create")
    public String createAdmin(@RequestParam String username, @RequestParam String email,
                              @RequestParam String password, @RequestParam String fullName,
                              @RequestParam String phone, @RequestParam String level,
                              @RequestParam String department,
                              HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        String result = adminService.createAdmin(username, email, password, fullName, phone, level, department);
        ra.addFlashAttribute(result.startsWith("ADM-") ? "success" : "error",
                result.startsWith("ADM-") ? "Admin created: " + result : "Error: " + result);
        return "redirect:/admin/manage";
    }

    @PostMapping("/delete/{adminId}")
    public String deleteAdmin(@PathVariable String adminId, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/login";
        AdminUser requester = (AdminUser) session.getAttribute("loggedUser");
        boolean ok = adminService.deleteAdmin(adminId, requester.getUserId());
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Admin deleted." : "Permission denied.");
        return "redirect:/admin/manage";
    }

    // ── LOGS ──────────────────────────────────────────────────────────────

    @GetMapping("/logs")
    public String viewLogs(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/login";
        model.addAttribute("logs", adminService.getActivityLogs());
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "admin/logs";
    }
}
