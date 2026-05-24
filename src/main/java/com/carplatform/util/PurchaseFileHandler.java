package com.carplatform.util;

import com.carplatform.model.Purchase;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


// C3 - purchases.txt
public class PurchaseFileHandler {

    private final String filePath;

    // set up a new PurchaseFileHandler
    public PurchaseFileHandler(String dataDir) {
        this.filePath = dataDir + File.separator + "purchases.txt";
        ensureFileExists();
    }

    // make sure the data file exists on disk
    private void ensureFileExists() {
        try { File f = new File(filePath); f.getParentFile().mkdirs(); if (!f.exists()) f.createNewFile(); }
        catch (IOException e) { System.err.println("[PurchaseFileHandler] " + e.getMessage()); }
    }

    // save purchase
    public boolean savePurchase(Purchase p) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath, true))) {
            w.write(p.toFileString()); w.newLine(); return true;
        } catch (IOException e) { return false; }
    }

    public List<Purchase> getAllPurchases() {
        List<Purchase> list = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = r.readLine()) != null) {
                if (!line.isBlank()) { Purchase p = Purchase.fromFileString(line); if (p != null) list.add(p); }
            }
        } catch (IOException e) { System.err.println("[PurchaseFileHandler] Read error: " + e.getMessage()); }
        return list;
    }

    // scan file for matching id
    public Purchase findById(String id) {
        return getAllPurchases().stream().filter(p -> p.getPurchaseId().equals(id)).findFirst().orElse(null);
    }

    // find by buyer
    public List<Purchase> findByBuyer(String buyerId) {
        return getAllPurchases().stream().filter(p -> p.getBuyerId().equals(buyerId)).collect(Collectors.toList());
    }

    // find by seller
    public List<Purchase> findBySeller(String sellerId) {
        return getAllPurchases().stream().filter(p -> p.getSellerId().equals(sellerId)).collect(Collectors.toList());
    }

    // update purchase
    public boolean updatePurchase(Purchase updated) {
        List<Purchase> all = getAllPurchases();
        boolean found = false;
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getPurchaseId().equals(updated.getPurchaseId())) { all.set(i, updated); found = true; break; }
        }
        return found && rewrite(all);
    }

    // delete purchase
    public boolean deletePurchase(String id) {
        List<Purchase> all = getAllPurchases();
        boolean r = all.removeIf(p -> p.getPurchaseId().equals(id));
        return r && rewrite(all);
    }

    // rewrite
    private boolean rewrite(List<Purchase> list) {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(filePath, false))) {
            for (Purchase p : list) { w.write(p.toFileString()); w.newLine(); } return true;
        } catch (IOException e) { return false; }
    }

    // t40
}
