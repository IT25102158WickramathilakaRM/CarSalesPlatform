# AutoMart – Second-Hand Car Sales and Purchase Platform
## SE1020 Object Oriented Programming | Group Project

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)
![JSP](https://img.shields.io/badge/JSP-Servlets-blue)
![File Handling](https://img.shields.io/badge/Storage-File%20I%2FO-yellow)

---

## Project Overview

**AutoMart** is a web-based second-hand car sales and purchase platform built using Java, Spring Boot, JSP Servlets, and file-based data storage. This project was developed as part of the SE1020 Object-Oriented Programming module.

---

## Group Members & Modules

| Name | Student ID | Component | Module | Branch |
|------|------------|-----------|--------|--------|
| Gunawardhena P.G.M.B.P. | IT25100694 | 1 | User Management | `feature/user-management` |
| Wickramathilaka R.M | IT25102158 | 2 | Car Listing Management | `feature/car-listing` |
| Nanayakkara T.P.D.N.L | IT25102391 | 3 | Purchase & Inquiry | `feature/purchase-inquiry` |
| Gimhan U.V.K.K | IT25103531 | 4 | Admin Management | `feature/admin-management` |
| Amadini G. G. A. | IT25103979 | 5 | Review & Rating | `feature/review-rating` |
| Pulasthi M.V.C | IT25101535 | 6 | Wishlist & Search | `feature/wishlist-search` |

**Repository:** https://github.com/IT25102158WickramathilakaRM/CarSalesPlatform

| Component | Key Classes |
|-----------|-------------|
| 1 – User Management | `User`, `BuyerUser`, `SellerUser`, `AuthenticationService`, `UserFileHandler` |
| 2 – Car Listing | `Car`, `SUV`, `Sedan`, `Hatchback`, `CarService`, `CarFileHandler` |
| 3 – Purchase & Inquiry | `Inquiry`, `Purchase`, `TransactionService`, `InquiryFileHandler`, `PurchaseFileHandler` |
| 4 – Admin Management | `AdminUser`, `ActivityLog`, `AdminService`, `AdminFileHandler` |
| 5 – Review & Rating | `Review`, `PublicReview`, `VerifiedReview`, `ReviewService`, `ReviewFileHandler` |
| 6 – Wishlist & Search | `Wishlist`, `RecentView`, `WishlistService`, `WishlistFileHandler` |

---

## How to Run

### Prerequisites
- Java 17 or newer (21+ recommended)
- IntelliJ IDEA (recommended) **or** use the included Maven Wrapper (no global Maven install needed)

### Steps (Windows)
```powershell
cd CarSalesPlatform

# Start the server (keep the window open)
.\run.bat
# Or: .\mvnw.cmd spring-boot:run
```

### Steps (Mac/Linux)
```bash
cd CarSalesPlatform
./mvnw spring-boot:run
```

Then open **http://localhost:8080/cars** in your browser (not `https://localhost` and not port 80).

### Access
- **User Site:** http://localhost:8080/cars
- **Admin Panel:** http://localhost:8080/admin/dashboard

### Default Admin Login
| Username | Password |
|----------|----------|
| `superadmin` | `Admin@123` |

### Sample User Logins
| Username | Password | Role |
|----------|----------|------|
| `john_buyer` | `pass123` | Buyer |
| `amal_sell`  | `sell123` | Seller |

---

## Project Structure

```
CarSalesPlatform/
├── src/main/java/com/carplatform/
│   ├── CarSalesPlatformApplication.java
│   ├── model/
│   │   ├── User.java              (C1 – IT25100694)
│   │   ├── BuyerUser.java         (C1 – IT25100694)
│   │   ├── SellerUser.java        (C1 – IT25100694)
│   │   ├── Car.java               (C2 – IT25102158)
│   │   ├── SUV.java               (C2 – IT25102158)
│   │   ├── Sedan.java             (C2 – IT25102158)
│   │   ├── Hatchback.java         (C2 – IT25102158)
│   │   ├── Inquiry.java           (C3 – IT25102391)
│   │   ├── Purchase.java          (C3 – IT25102391)
│   │   ├── AdminUser.java         (C4 – IT25103531)
│   │   ├── ActivityLog.java       (C4 – IT25103531)
│   │   ├── Review.java            (C5 – IT25103979)
│   │   ├── PublicReview.java      (C5 – IT25103979)
│   │   ├── VerifiedReview.java    (C5 – IT25103979)
│   │   ├── Wishlist.java          (C6 – IT25101535)
│   │   └── RecentView.java        (C6 – IT25101535)
│   ├── service/
│   │   ├── AuthenticationService.java  (C1 – IT25100694)
│   │   ├── CarService.java             (C2 – IT25102158)
│   │   ├── TransactionService.java     (C3 – IT25102391)
│   │   ├── AdminService.java           (C4 – IT25103531)
│   │   ├── ReviewService.java          (C5 – IT25103979)
│   │   └── WishlistService.java        (C6 – IT25101535)
│   ├── servlet/ (Spring MVC Controllers)
│   │   ├── UserController.java         (C1 – IT25100694)
│   │   ├── CarController.java          (C2 – IT25102158)
│   │   ├── TransactionController.java  (C3 – IT25102391)
│   │   ├── AdminController.java        (C4 – IT25103531)
│   │   ├── ReviewController.java       (C5 – IT25103979)
│   │   └── WishlistController.java     (C6 – IT25101535)
│   └── util/ (File Handlers)
│       ├── UserFileHandler.java        (C1 – IT25100694)
│       ├── CarFileHandler.java         (C2 – IT25102158)
│       ├── InquiryFileHandler.java     (C3 – IT25102391)
│       ├── PurchaseFileHandler.java    (C3 – IT25102391)
│       ├── AdminFileHandler.java       (C4 – IT25103531)
│       ├── ReviewFileHandler.java      (C5 – IT25103979)
│       └── WishlistFileHandler.java    (C6 – IT25101535)
├── src/main/webapp/
│   ├── WEB-INF/views/
│   │   ├── login.jsp, register.jsp, profile.jsp
│   │   ├── car-list.jsp, car-detail.jsp, car-add.jsp, car-edit.jsp, car-my-listings.jsp
│   │   ├── inquiry-form.jsp, inquiry-list.jsp
│   │   ├── purchase-form.jsp, purchase-history.jsp
│   │   ├── review-submit.jsp, review-list.jsp, review-edit.jsp
│   │   ├── wishlist.jsp, recent-views.jsp
│   │   └── admin/
│   │       ├── dashboard.jsp, users.jsp, cars.jsp
│   │       ├── reviews.jsp, manage.jsp, logs.jsp
│   └── css/style.css
├── data/
│   ├── users.txt, cars.txt, inquiries.txt
│   ├── purchases.txt, reviews.txt, admins.txt
│   ├── wishlist.txt, recentviews.txt, logs.txt
├── docs/
│   └── (class diagrams, report)
└── pom.xml
```

---

## OOP Concepts Demonstrated

| Concept | Where Applied |
|---------|--------------|
| **Abstraction** | `User`, `Car`, `Review` – abstract classes |
| **Encapsulation** | All model classes – private fields + getters/setters |
| **Inheritance** | `BuyerUser`, `SellerUser`, `AdminUser` extend `User`; `SUV`, `Sedan`, `Hatchback` extend `Car`; `PublicReview`, `VerifiedReview` extend `Review` |
| **Polymorphism** | `getRole()`, `getDisplayInfo()`, `getBodyType()`, `calculateInsuranceEstimate()`, `getFormattedDisplay()` |
| **Information Hiding** | `AdminUser.hasListingApprovalPermission()`, `hasUserDeletionPermission()` |

---

## Data Files (Text File Storage)

| File | Description | Format |
|------|-------------|--------|
| `users.txt` | All user accounts | pipe-delimited |
| `cars.txt` | All car listings | pipe-delimited |
| `inquiries.txt` | Buyer inquiries | pipe-delimited |
| `purchases.txt` | Purchase transactions | pipe-delimited |
| `reviews.txt` | Car reviews | pipe-delimited |
| `admins.txt` | Admin accounts | pipe-delimited |
| `wishlist.txt` | User wishlists | pipe-delimited |
| `recentviews.txt` | Recently viewed cars | pipe-delimited |
| `logs.txt` | Admin activity logs | pipe-delimited |

---

## GitHub Contribution Strategy

Each member works on their own branch (see [docs/WORKLOAD_DISTRIBUTION.md](docs/WORKLOAD_DISTRIBUTION.md)):

| Branch | Student | ID |
|--------|---------|-----|
| `feature/user-management` | Gunawardhena P.G.M.B.P. | IT25100694 |
| `feature/car-listing` | Wickramathilaka R.M | IT25102158 |
| `feature/purchase-inquiry` | Nanayakkara T.P.D.N.L | IT25102391 |
| `feature/admin-management` | Gimhan U.V.K.K | IT25103531 |
| `feature/review-rating` | Amadini G. G. A. | IT25103979 |
| `feature/wishlist-search` | Pulasthi M.V.C | IT25101535 |

---
# t161
