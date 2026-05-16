package com.carplatform.servlet;

import com.carplatform.model.*;
import com.carplatform.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for Wishlist and Recently Viewed cars.
 * IT25101535 – Pulasthi M.V.C | Component 6 – Wishlist & Search Enhancement Module
 */
@Controller
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final CarService carService;

    public WishlistController(WishlistService ws, CarService cs) {
        this.wishlistService = ws;
        this.carService      = cs;
    }

    @GetMapping
    public String viewWishlist(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        Wishlist wishlist = wishlistService.getWishlistForUser(user.getUserId());
        List<Car> cars = new ArrayList<>();
        for (String carId : wishlist.getCarIds()) {
            Car car = carService.getCarById(carId);
            if (carService.isPublicBrowseListing(car)) cars.add(car);
        }
        model.addAttribute("wishlistCars", cars);
        model.addAttribute("wishlist",     wishlist);
        model.addAttribute("loggedUser",   user);
        return "wishlist";
    }

    @PostMapping("/add/{carId}")
    public String addToWishlist(@PathVariable String carId, HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        Car car = carService.getCarById(carId);
        if (!carService.isPublicBrowseListing(car)) {
            ra.addFlashAttribute("error", "Only approved listings can be added to your wishlist.");
            return "redirect:/cars";
        }
        boolean ok = wishlistService.addToWishlist(user.getUserId(), carId);
        ra.addFlashAttribute(ok ? "success" : "error", ok ? "Added to wishlist." : "Already in wishlist.");
        return "redirect:/cars/" + carId;
    }

    @PostMapping("/remove/{carId}")
    public String removeFromWishlist(@PathVariable String carId, HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        wishlistService.removeFromWishlist(user.getUserId(), carId);
        ra.addFlashAttribute("success", "Removed from wishlist.");
        return "redirect:/wishlist";
    }

    @PostMapping("/clear")
    public String clearWishlist(HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        wishlistService.clearWishlist(user.getUserId());
        ra.addFlashAttribute("success", "Wishlist cleared.");
        return "redirect:/wishlist";
    }

    @GetMapping("/recent")
    public String recentViews(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        List<RecentView> recentViews = wishlistService.getRecentViews(user.getUserId());
        List<Car> recentCars = new ArrayList<>();
        for (RecentView rv : recentViews) {
            Car car = carService.getCarById(rv.getCarId());
            if (carService.isPublicBrowseListing(car)) recentCars.add(car);
        }
        model.addAttribute("recentCars",  recentCars);
        model.addAttribute("recentViews", recentViews);
        model.addAttribute("loggedUser",  user);
        return "recent-views";
    }
}
