package com.carplatform.util;

import com.carplatform.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


// C2 - cars.txt read/write
public class CarFileHandler {

    private final String filePath;

    // set up a new CarFileHandler
    public CarFileHandler(String dataDir) {
        this.filePath = dataDir + File.separator + "cars.txt";
        ensureFileExists();
    }

    // make sure the data file exists on disk
    private void ensureFileExists() {
        try {
            File f = new File(filePath);
            f.getParentFile().mkdirs();
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            System.err.println("[CarFileHandler] Could not create cars.txt: " + e.getMessage());
        }
    }


    // append new car line to cars.txt
    public boolean saveCar(Car car) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath, true))) {
            w.write(car.toFileString());
            w.newLine();
            return true;
        } catch (IOException e) {
            System.err.println("[CarFileHandler] Save error: " + e.getMessage());
            return false;
        }
    }


    // read every car from cars.txt
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                Car c = parseLine(line);
                if (c != null) cars.add(c);
            }
        } catch (IOException e) {
            System.err.println("[CarFileHandler] Read error: " + e.getMessage());
        }
        return cars;
    }

    // scan file for matching id
    public Car findById(String carId) {
        return getAllCars().stream()
                .filter(c -> c.getCarId().equals(carId))
                .findFirst().orElse(null);
    }

    // find by seller
    public List<Car> findBySeller(String sellerId) {
        return getAllCars().stream()
                .filter(c -> c.getSellerId().equals(sellerId))
                .collect(Collectors.toList());
    }

    // find approved
    public List<Car> findApproved() {
        return getAllCars().stream()
                .filter(Car::isApproved)
                .collect(Collectors.toList());
    }

    // search by keyword
    public List<Car> searchByKeyword(String keyword) {
        String kw = keyword.toLowerCase();
        return getAllCars().stream()
                .filter(c -> c.getMake().toLowerCase().contains(kw)
                        || c.getModel().toLowerCase().contains(kw)
                        || c.getDescription().toLowerCase().contains(kw)
                        || c.getBodyType().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    // filter by price range
    public List<Car> filterByPriceRange(double min, double max) {
        return getAllCars().stream()
                .filter(c -> c.getPrice() >= min && c.getPrice() <= max)
                .collect(Collectors.toList());
    }

    // filter by body type
    public List<Car> filterByBodyType(String bodyType) {
        return getAllCars().stream()
                .filter(c -> c.getBodyType().equalsIgnoreCase(bodyType))
                .collect(Collectors.toList());
    }


    // update car
    public boolean updateCar(Car updated) {
        List<Car> all = getAllCars();
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getCarId().equals(updated.getCarId())) {
                all.set(i, updated);
                found = true;
                break;
            }
        }
        return found && rewriteFile(all);
    }


    // seller deletes their listing
    public boolean deleteCar(String carId) {
        List<Car> all = getAllCars();
        boolean removed = all.removeIf(c -> c.getCarId().equals(carId));
        return removed && rewriteFile(all);
    }


    // overwrite cars.txt with the current list
    private boolean rewriteFile(List<Car> cars) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Car c : cars) { w.write(c.toFileString()); w.newLine(); }
            return true;
        } catch (IOException e) {
            System.err.println("[CarFileHandler] Rewrite error: " + e.getMessage());
            return false;
        }
    }

    // turn one pipe-separated line into a Car object
    private Car parseLine(String line) {
        try {
            String[] p = line.split("\\|", -1);
            if (p.length < 16) return null;
            String bodyType = p[15];
            Car car;
            switch (bodyType) {
                case "SUV":
                    SUV suv = new SUV();
                    if (p.length >= 19) {
                        suv.setNumSeats(Integer.parseInt(p[16]));
                        suv.setHasFourWheelDrive(Boolean.parseBoolean(p[17]));
                        suv.setGroundClearance(Double.parseDouble(p[18]));
                    }
                    car = suv; break;
                case "Sedan":
                    Sedan sedan = new Sedan();
                    if (p.length >= 19) {
                        sedan.setNumDoors(Integer.parseInt(p[16]));
                        sedan.setHasSunroof(Boolean.parseBoolean(p[17]));
                        sedan.setBootCapacity(p[18]);
                    }
                    car = sedan; break;
                case "Hatchback":
                    Hatchback hb = new Hatchback();
                    if (p.length >= 19) {
                        hb.setHasFoldableRearSeats(Boolean.parseBoolean(p[16]));
                        hb.setRoofType(p[17]);
                        hb.setEngineCC(Integer.parseInt(p[18]));
                    }
                    car = hb; break;
                default:
                    car = new Sedan(); break;
            }
            car.setCarId(p[0]); car.setSellerId(p[1]); car.setMake(p[2]);
            car.setModel(p[3]); car.setYear(Integer.parseInt(p[4]));
            car.setPrice(Double.parseDouble(p[5])); car.setMileage(Integer.parseInt(p[6]));
            car.setColour(p[7]); car.setFuelType(p[8]); car.setTransmission(p[9]);
            car.setCondition(p[10]); car.setDescription(p[11]);
            car.setListedDate(p[12]); car.setStatus(p[13]);
            car.setApproved(Boolean.parseBoolean(p[14]));
            if (p.length > 19) {
                car.setImageUrl(p[19]);
            }
            return car;
        } catch (Exception e) {
            System.err.println("[CarFileHandler] Parse error: " + e.getMessage());
            return null;
        }
    }

    // t27
}
