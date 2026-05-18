package com.carplatform.servlet;

import com.carplatform.model.User;
import com.carplatform.service.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
* IT25100694 – Gunawardhena P.G.M.B.P | Component 1 – User Management Module

 * Controller for User Authentication and Profile Management.
 */
@Controller
public class UserController {

    private final AuthenticationService authService;

    public UserController(AuthenticationService authService) {
        this.authService = authService;
    }

    // ── REGISTER ─────────────────────────────────────────────────────────

    @GetMapping("/register")
    public String showRegister() { return "register"; }

    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String fullName,
                                  @RequestParam String phone,
                                  @RequestParam String role,
                                  @RequestParam(defaultValue = "") String preferredCarType,
                                  @RequestParam(defaultValue = "0") double budgetLimit,
                                  @RequestParam(defaultValue = "") String businessName,
                                  @RequestParam(defaultValue = "") String location,
                                  RedirectAttributes ra) {
        String result;
        if ("SELLER".equalsIgnoreCase(role)) {
            result = authService.registerSeller(username, email, password, fullName, phone, businessName, location);
        } else {
            result = authService.registerBuyer(username, email, password, fullName, phone, preferredCarType, budgetLimit);
        }
        if ("SUCCESS".equals(result)) {
            ra.addFlashAttribute("success", "Account created successfully! Please log in.");
            return "redirect:/login";
        }
        ra.addFlashAttribute("error", mapError(result));
        return "redirect:/register";
    }

    // ── LOGIN ────────────────────────────────────────────────────────────

    @GetMapping("/login")
    public String showLogin() { return "login"; }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               RedirectAttributes ra) {
        User user = authService.login(username, password);
        if (user == null) {
            ra.addFlashAttribute("error", "Invalid username or password.");
            return "redirect:/login";
        }
        session.setAttribute("loggedUser", user);
        session.setAttribute("userId",     user.getUserId());
        session.setAttribute("userRole",   user.getRole());
        if ("ADMIN".equals(user.getRole())) return "redirect:/admin/dashboard";
        return "redirect:/cars";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // ── PROFILE ──────────────────────────────────────────────────────────

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("user", user);
        model.addAttribute("loggedUser", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String fullName,
                                @RequestParam String phone,
                                @RequestParam String email,
                                @RequestParam(defaultValue = "") String newPassword,
                                HttpSession session,
                                RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        boolean ok = authService.updateProfile(user.getUserId(), fullName, phone, email,
                newPassword.isBlank() ? null : newPassword);
        if (ok) {
            User updated = authService.getUserById(user.getUserId());
            session.setAttribute("loggedUser", updated);
            ra.addFlashAttribute("success", "Profile updated successfully.");
        } else {
            ra.addFlashAttribute("error", "Failed to update profile.");
        }
        return "redirect:/profile";
    }

    @PostMapping("/profile/delete")
    public String deleteAccount(HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        authService.deleteAccount(user.getUserId());
        session.invalidate();
        ra.addFlashAttribute("success", "Your account has been deleted.");
        return "redirect:/login";
    }

    // ── HOME ──────────────────────────────────────────────────────────────

    @GetMapping("/")
    public String home() { return "redirect:/cars"; }

    // ── HELPERS ──────────────────────────────────────────────────────────

    private String mapError(String code) {
        return switch (code) {
            case "USERNAME_EXISTS" -> "Username is already taken.";
            case "EMAIL_EXISTS"    -> "Email address is already registered.";
            case "INVALID_EMAIL"   -> "Please enter a valid email address.";
            case "WEAK_PASSWORD"   -> "Password must be at least 6 characters.";
            default                -> "Registration failed. Please try again.";
        };
    }
}
