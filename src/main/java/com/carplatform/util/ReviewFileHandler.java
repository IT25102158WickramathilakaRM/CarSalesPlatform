package com.carplatform.util;

import com.carplatform.model.Review;
import com.carplatform.model.PublicReview;
import com.carplatform.model.VerifiedReview;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


// C5 - reviews.txt
public class ReviewFileHandler {

    private final String filePath;

    // set up a new ReviewFileHandler
    public ReviewFileHandler(String dataDir) {
        this.filePath = dataDir + File.separator + "reviews.txt";
        ensureFileExists();
    }

    // make sure the data file exists on disk
    private void ensureFileExists() {
        try { File f = new File(filePath); f.getParentFile().mkdirs(); if (!f.exists()) f.createNewFile(); }
        catch (IOException e) { System.err.println("[ReviewFileHandler] " + e.getMessage()); }
    }

    // save review
    public boolean saveReview(Review rev) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath, true))) {
            w.write(rev.toFileString()); w.newLine(); return true;
        } catch (IOException e) { return false; }
    }

    public List<Review> getAllReviews() {
        List<Review> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.isBlank()) { Review rv = parseLine(line); if (rv != null) list.add(rv); }
            }
        } catch (IOException e) { System.err.println("[ReviewFileHandler] " + e.getMessage()); }
        return list;
    }

    // find by car
    public List<Review> findByCar(String carId) {
        return getAllReviews().stream().filter(r -> r.getCarId().equals(carId)).collect(Collectors.toList());
    }

    // find by reviewer
    public List<Review> findByReviewer(String reviewerId) {
        return getAllReviews().stream().filter(r -> r.getReviewerId().equals(reviewerId)).collect(Collectors.toList());
    }

    // find pending
    public List<Review> findPending() {
        return getAllReviews().stream().filter(r -> !r.isApproved() && !r.isFlagged()).collect(Collectors.toList());
    }

    public double getAverageRatingForCar(String carId) {
        List<Review> reviews = findByCar(carId);
        return reviews.isEmpty() ? 0.0
                : reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
    }

    // scan file for matching id
    public Review findById(String id) {
        return getAllReviews().stream().filter(r -> r.getReviewId().equals(id)).findFirst().orElse(null);
    }

    // update review
    public boolean updateReview(Review updated) {
        List<Review> all = getAllReviews();
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getReviewId().equals(updated.getReviewId())) { all.set(i, updated); found = true; break; }
        }
        return found && rewrite(all);
    }

    // delete review
    public boolean deleteReview(String id) {
        List<Review> all = getAllReviews();
        boolean r = all.removeIf(rv -> rv.getReviewId().equals(id));
        return r && rewrite(all);
    }

    // rewrite
    private boolean rewrite(List<Review> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Review r : list) { w.write(r.toFileString()); w.newLine(); } return true;
        } catch (IOException e) { return false; }
    }

    // turn one pipe-separated line into a Car object
    private Review parseLine(String line) {
        try {
            String[] p = line.split("\\|", -1);
            if (p.length < 11) return null;
            String type = p[10];
            if ("PUBLIC".equals(type)) {
                PublicReview pr = new PublicReview();
                pr.setReviewId(p[0]); pr.setCarId(p[1]); pr.setReviewerId(p[2]); pr.setSellerId(p[3]);
                pr.setRating(Integer.parseInt(p[4])); pr.setTitle(p[5]); pr.setBody(p[6]);
                pr.setSubmittedDate(p[7]); pr.setApproved(Boolean.parseBoolean(p[8]));
                pr.setFlagged(Boolean.parseBoolean(p[9]));
                if (p.length >= 12) pr.setDisplayName(p[11]);
                return pr;
            } else {
                VerifiedReview vr = new VerifiedReview();
                vr.setReviewId(p[0]); vr.setCarId(p[1]); vr.setReviewerId(p[2]); vr.setSellerId(p[3]);
                vr.setRating(Integer.parseInt(p[4])); vr.setTitle(p[5]); vr.setBody(p[6]);
                vr.setSubmittedDate(p[7]); vr.setApproved(Boolean.parseBoolean(p[8]));
                vr.setFlagged(Boolean.parseBoolean(p[9]));
                if (p.length >= 13) { vr.setPurchaseId(p[11]); vr.setVerified(Boolean.parseBoolean(p[12])); }
                return vr;
            }
        } catch (Exception e) { System.err.println("[ReviewFileHandler] Parse error: " + e.getMessage()); return null; }
    }
}
