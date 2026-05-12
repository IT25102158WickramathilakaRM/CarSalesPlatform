package com.carplatform.servlet;

import com.carplatform.model.*;
import com.carplatform.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for Review and Rating operations.
 * Member 5 – Review and Rating Management Module
 */
@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final CarService carService;

    public ReviewController(ReviewService rs, CarService cs) {
        this.reviewService = rs;
        this.carService    = cs;
    }

    @GetMapping("/submit/{carId}")
    public String showReviewForm(@PathVariable String carId, HttpSession session, Model model) {
        if (session.getAttribute("loggedUser") == null) return "redirect:/login";
        Car car = carService.getCarById(carId);
        if (car == null) return "redirect:/cars";
        model.addAttribute("car", car);
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "review-submit";
    }

    @PostMapping("/submit")
    public String submitReview(@RequestParam String carId,
                               @RequestParam String sellerId,
                               @RequestParam int rating,
                               @RequestParam String title,
                               @RequestParam String body,
                               @RequestParam(defaultValue = "") String purchaseId,
                               HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        String result;
        if (!purchaseId.isBlank()) {
            result = reviewService.submitVerifiedReview(carId, user.getUserId(), sellerId, rating, title, body, purchaseId);
        } else {
            result = reviewService.submitPublicReview(carId, user.getUserId(), sellerId, rating, title, body, user.getFullName());
        }
        ra.addFlashAttribute(result.startsWith("REV-") ? "success" : "error",
                result.startsWith("REV-") ? "Review submitted for moderation." : "Error: " + result);
        return "redirect:/cars/" + carId;
    }

    @GetMapping("/my")
    public String myReviews(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("reviews", reviewService.getReviewsByUser(user.getUserId()));
        model.addAttribute("loggedUser", user);
        return "review-list";
    }

    @GetMapping("/edit/{reviewId}")
    public String showEditReview(@PathVariable String reviewId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        model.addAttribute("review", reviewService.getAllReviews().stream()
                .filter(r -> r.getReviewId().equals(reviewId)).findFirst().orElse(null));
        model.addAttribute("loggedUser", user);
        return "review-edit";
    }

    @PostMapping("/edit/{reviewId}")
    public String editReview(@PathVariable String reviewId,
                             @RequestParam String title,
                             @RequestParam String body,
                             @RequestParam int rating,
                             HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        boolean ok = reviewService.editReview(reviewId, user.getUserId(), title, body, rating);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Review updated." : "Failed to update review.");
        return "redirect:/reviews/my";
    }

    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable String reviewId, HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        reviewService.deleteReview(reviewId);
        ra.addFlashAttribute("success", "Review deleted.");
        return "redirect:/reviews/my";
    }

    // ── ADMIN MODERATION ──────────────────────────────────────────────────

    @GetMapping("/admin/moderate")
    public String moderateReviews(HttpSession session, Model model) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        model.addAttribute("pendingReviews", reviewService.getPendingReviews());
        model.addAttribute("allReviews",     reviewService.getAllReviews());
        model.addAttribute("loggedUser",     session.getAttribute("loggedUser"));
        return "admin/reviews";
    }

    @PostMapping("/admin/approve/{id}")
    public String approveReview(@PathVariable String id, HttpSession session, RedirectAttributes ra) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        reviewService.approveReview(id);
        ra.addFlashAttribute("success", "Review approved.");
        return "redirect:/reviews/admin/moderate";
    }

    @PostMapping("/admin/flag/{id}")
    public String flagReview(@PathVariable String id, HttpSession session, RedirectAttributes ra) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        reviewService.flagReview(id);
        ra.addFlashAttribute("success", "Review flagged.");
        return "redirect:/reviews/admin/moderate";
    }

    @PostMapping("/admin/delete/{id}")
    public String adminDeleteReview(@PathVariable String id, HttpSession session, RedirectAttributes ra) {
        if (!"ADMIN".equals(session.getAttribute("userRole"))) return "redirect:/login";
        reviewService.deleteReview(id);
        ra.addFlashAttribute("success", "Review deleted.");
        return "redirect:/reviews/admin/moderate";
    }
}
