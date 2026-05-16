package com.carplatform.servlet;

import com.carplatform.model.*;
import com.carplatform.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller for Car Listing CRUD, search, and filter operations.
 * Member 2 – Car Listing Management Module
 */
@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final WishlistService wishlistService;
    private final ReviewService reviewService;
    private final boolean requireAdminApprovalForListings;

    public CarController(CarService carService, WishlistService wishlistService, ReviewService reviewService,
                         @Value("${app.listings.require-admin-approval:false}") boolean requireAdminApprovalForListings) {
        this.carService = carService;
        this.wishlistService = wishlistService;
        this.reviewService = reviewService;
        this.requireAdminApprovalForListings = requireAdminApprovalForListings;
    }

    // ── LIST ─────────────────────────────────────────────────────────────

    @GetMapping
    public String listCars(@RequestParam(defaultValue = "") String keyword,
                           @RequestParam(defaultValue = "") String bodyType,
                           @RequestParam(defaultValue = "") String fuelType,
                           @RequestParam(defaultValue = "0") double minPrice,
                           @RequestParam(defaultValue = "0") double maxPrice,
                           @RequestParam(defaultValue = "default") String sort,
                           HttpSession session, Model model) {
        List<Car> cars = keyword.isBlank() ? carService.getAllApprovedListings()
                                           : carService.searchCars(keyword);
        if (!bodyType.isBlank()) cars = cars.stream()
                .filter(c -> c.getBodyType().equalsIgnoreCase(bodyType)).toList();
        if (!fuelType.isBlank()) cars = cars.stream()
                .filter(c -> c.getFuelType().equalsIgnoreCase(fuelType)).toList();
        if (maxPrice > 0) cars = cars.stream()
                .filter(c -> c.getPrice() >= minPrice && c.getPrice() <= maxPrice).toList();
        if ("price_asc".equals(sort))  cars = carService.sortByPriceAsc(cars);
        if ("price_desc".equals(sort)) cars = carService.sortByPriceDesc(cars);
        if ("year".equals(sort))       cars = carService.sortByYear(cars);

        model.addAttribute("cars", cars);
        model.addAttribute("keyword",  keyword);
        model.addAttribute("bodyType", bodyType);
        model.addAttribute("fuelType", fuelType);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("sort",     sort);
        model.addAttribute("loggedUser", session.getAttribute("loggedUser"));
        return "car-list";
    }

    // ── DETAIL ────────────────────────────────────────────────────────────

    @GetMapping("/{carId}")
    public String carDetail(@PathVariable String carId, HttpSession session, Model model) {
        Car car = carService.getCarById(carId);
        if (car == null) return "redirect:/cars";
        User user = (User) session.getAttribute("loggedUser");
        String role = (String) session.getAttribute("userRole");
        if (!carService.mayViewCarDetail(car, user, role)) return "redirect:/cars";
        if (user != null && carService.isPublicBrowseListing(car)) {
            wishlistService.recordView(user.getUserId(), carId);
        }
        model.addAttribute("car",         car);
        model.addAttribute("publicListing", carService.isPublicBrowseListing(car));
        model.addAttribute("reviews",     reviewService.getReviewsForCar(carId));
        model.addAttribute("avgRating",   reviewService.getAverageRating(carId));
        model.addAttribute("inWishlist",  user != null && wishlistService.isInWishlist(user.getUserId(), carId));
        model.addAttribute("loggedUser",  user);
        return "car-detail";
    }

    // ── ADD ───────────────────────────────────────────────────────────────

    @GetMapping("/add")
    public String showAddCar(HttpSession session) {
        if (session.getAttribute("loggedUser") == null) return "redirect:/login";
        return "car-add";
    }

    @PostMapping("/add")
    public String addCar(@RequestParam String make,    @RequestParam String model,
                         @RequestParam int year,       @RequestParam double price,
                         @RequestParam int mileage,    @RequestParam String colour,
                         @RequestParam String fuelType,@RequestParam String transmission,
                         @RequestParam String condition,@RequestParam String description,
                         @RequestParam String bodyType,
                         @RequestParam(defaultValue = "7") int numSeats,
                         @RequestParam(defaultValue = "false") boolean hasFourWheelDrive,
                         @RequestParam(defaultValue = "200") double groundClearance,
                         @RequestParam(defaultValue = "4") int numDoors,
                         @RequestParam(defaultValue = "false") boolean hasSunroof,
                         @RequestParam(defaultValue = "460L") String bootCapacity,
                         @RequestParam(defaultValue = "true") boolean hasFoldableRearSeats,
                         @RequestParam(defaultValue = "Standard") String roofType,
                         @RequestParam(defaultValue = "1200") int engineCC,
                         @RequestParam(defaultValue = "") String imageUrl,
                         HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";

        Car car;
        switch (bodyType) {
            case "SUV"      -> car = new SUV(null, user.getUserId(), make, model, year, price,
                    mileage, colour, fuelType, transmission, condition, description, null,
                    numSeats, hasFourWheelDrive, groundClearance);
            case "Sedan"    -> car = new Sedan(null, user.getUserId(), make, model, year, price,
                    mileage, colour, fuelType, transmission, condition, description, null,
                    numDoors, hasSunroof, bootCapacity);
            default         -> car = new Hatchback(null, user.getUserId(), make, model, year, price,
                    mileage, colour, fuelType, transmission, condition, description, null,
                    hasFoldableRearSeats, roofType, engineCC);
        }

        car.setImageUrl(imageUrl);

        String result = carService.addCarListing(car);
        if (result.startsWith("CAR-")) {
            ra.addFlashAttribute("success", requireAdminApprovalForListings
                    ? "Listing submitted for admin approval."
                    : "Your listing is now visible on Browse.");
            return "redirect:/cars/my-listings";
        }
        ra.addFlashAttribute("error", "Failed to add listing: " + result);
        return "redirect:/cars/add";
    }

    // ── MY LISTINGS ───────────────────────────────────────────────────────

    @GetMapping("/my-listings")
    public String myListings(HttpSession session, Model mod) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        mod.addAttribute("cars", carService.getCarsBySeller(user.getUserId()));
        mod.addAttribute("loggedUser", user);
        return "car-my-listings";
    }

    // ── EDIT ──────────────────────────────────────────────────────────────

    @GetMapping("/edit/{carId}")
    public String showEdit(@PathVariable String carId, HttpSession session, Model mod) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        Car car = carService.getCarById(carId);
        if (car == null || !car.getSellerId().equals(user.getUserId())) return "redirect:/cars/my-listings";
        mod.addAttribute("car", car);
        mod.addAttribute("loggedUser", user);
        return "car-edit";
    }

    @PostMapping("/edit/{carId}")
    public String processEdit(@PathVariable String carId,
                              @RequestParam double price,
                              @RequestParam String description,
                              @RequestParam String condition,
                              @RequestParam String status,
                              @RequestParam(defaultValue = "") String imageUrl,
                              HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        Car car = carService.getCarById(carId);
        if (car == null || !car.getSellerId().equals(user.getUserId())) return "redirect:/cars/my-listings";
        car.setPrice(price); car.setDescription(description);
        car.setCondition(condition); car.setStatus(status);
        car.setImageUrl(imageUrl);
        carService.updateListing(car);
        ra.addFlashAttribute("success", "Listing updated successfully.");
        return "redirect:/cars/my-listings";
    }

    // ── DELETE ────────────────────────────────────────────────────────────

    @PostMapping("/delete/{carId}")
    public String deleteCar(@PathVariable String carId, HttpSession session, RedirectAttributes ra) {
        User user = (User) session.getAttribute("loggedUser");
        if (user == null) return "redirect:/login";
        Car car = carService.getCarById(carId);
        if (car != null && car.getSellerId().equals(user.getUserId())) {
            carService.deleteListing(carId);
            ra.addFlashAttribute("success", "Listing deleted.");
        }
        return "redirect:/cars/my-listings";
    }
}
