package com.carplatform.service;

import com.carplatform.model.*;
import com.carplatform.util.CarFileHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
// C2 - validation, search, approval helpers
public class CarService {

    private final CarFileHandler carFileHandler;
    private final boolean requireAdminApproval;

    // set up a new CarService
    public CarService(@Value("${app.data.directory:data}") String dataDir,
                      @Value("${app.listings.require-admin-approval:false}") boolean requireAdminApproval) {
        this.carFileHandler = new CarFileHandler(dataDir);
        this.requireAdminApproval = requireAdminApproval;
    }


    // seller posts a new car
    public String addCarListing(Car car) {
        if (car.getMake() == null || car.getMake().isBlank()) return "INVALID_MAKE";
        if (car.getPrice() <= 0)                              return "INVALID_PRICE";
        if (car.getYear() < 1980 || car.getYear() > LocalDate.now().getYear()) return "INVALID_YEAR";

        String id = "CAR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        car.setCarId(id);
        car.setListedDate(LocalDate.now().toString());
        car.setStatus("Available");
        car.setApproved(!requireAdminApproval);
        return carFileHandler.saveCar(car) ? id : "SAVE_ERROR";
    }


    public List<Car> getAllApprovedListings() {
        return carFileHandler.findApproved();
    }

    public List<Car> getAllListings() {
        return carFileHandler.getAllCars();
    }

    public Car getCarById(String carId) {
        return carFileHandler.findById(carId);
    }

    
    public boolean isPublicBrowseListing(Car car) {
        if (car == null) return false;
        return car.isApproved() && !"Rejected".equals(car.getStatus());
    }

    
    // may view car detail
    public boolean mayViewCarDetail(Car car, User loggedUser, String userRole) {
        if (car == null) return false;
        if (isPublicBrowseListing(car)) return true;
        if ("ADMIN".equals(userRole)) return true;
        return loggedUser != null && loggedUser.getUserId().equals(car.getSellerId());
    }

    
    // may buyer transact
    public boolean mayBuyerTransact(Car car) {
        return car != null && car.isApproved() && "Available".equals(car.getStatus());
    }

    public List<Car> getCarsBySeller(String sellerId) {
        return carFileHandler.findBySeller(sellerId);
    }

    // search cars
    public List<Car> searchCars(String keyword) {
        if (keyword == null || keyword.isBlank()) return getAllApprovedListings();
        return carFileHandler.searchByKeyword(keyword).stream()
                .filter(Car::isApproved).collect(Collectors.toList());
    }

    // filter by price range
    public List<Car> filterByPriceRange(double min, double max) {
        return carFileHandler.filterByPriceRange(min, max).stream()
                .filter(Car::isApproved).collect(Collectors.toList());
    }

    // filter by body type
    public List<Car> filterByBodyType(String bodyType) {
        return carFileHandler.filterByBodyType(bodyType).stream()
                .filter(Car::isApproved).collect(Collectors.toList());
    }

    // filter by fuel type
    public List<Car> filterByFuelType(String fuelType) {
        return getAllApprovedListings().stream()
                .filter(c -> c.getFuelType().equalsIgnoreCase(fuelType))
                .collect(Collectors.toList());
    }

    // sort by price asc
    public List<Car> sortByPriceAsc(List<Car> cars) {
        return cars.stream().sorted(Comparator.comparingDouble(Car::getPrice)).collect(Collectors.toList());
    }

    // sort by price desc
    public List<Car> sortByPriceDesc(List<Car> cars) {
        return cars.stream().sorted(Comparator.comparingDouble(Car::getPrice).reversed()).collect(Collectors.toList());
    }

    // sort by year
    public List<Car> sortByYear(List<Car> cars) {
        return cars.stream().sorted(Comparator.comparingInt(Car::getYear).reversed()).collect(Collectors.toList());
    }


    // seller saves edits to their car
    public boolean updateListing(Car updated) {
        if (updated.getPrice() <= 0) return false;
        return carFileHandler.updateCar(updated);
    }

    // mark as sold
    public boolean markAsSold(String carId) {
        Car car = carFileHandler.findById(carId);
        if (car == null) return false;
        car.setStatus("Sold");
        return carFileHandler.updateCar(car);
    }


    // seller removes their listing
    public boolean deleteListing(String carId) {
        return carFileHandler.deleteCar(carId);
    }


    // approve listing
    public boolean approveListing(String carId) {
        Car car = carFileHandler.findById(carId);
        if (car == null) return false;
        car.setApproved(true);
        return carFileHandler.updateCar(car);
    }

    // reject listing
    public boolean rejectListing(String carId) {
        Car car = carFileHandler.findById(carId);
        if (car == null) return false;
        car.setApproved(false);
        car.setStatus("Rejected");
        return carFileHandler.updateCar(car);
    }

    public List<Car> getPendingApproval() {
        return carFileHandler.getAllCars().stream()
                .filter(c -> !c.isApproved() && "Available".equals(c.getStatus()))
                .collect(Collectors.toList());
    }
}
