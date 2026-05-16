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

| Member | Module | Key Classes |
|--------|--------|------------|
| Member 1 | User Management | `User`, `BuyerUser`, `SellerUser`, `AuthenticationService`, `UserFileHandler` |
| Member 2 | Car Listing Management | `Car`, `SUV`, `Sedan`, `Hatchback`, `CarService`, `CarFileHandler` |
| Member 3 | Purchase & Inquiry | `Inquiry`, `Purchase`, `TransactionService`, `InquiryFileHandler`, `PurchaseFileHandler` |
| Member 4 | Admin Management | `AdminUser`, `ActivityLog`, `AdminService`, `AdminFileHandler` |
| Member 5 | Review & Rating | `Review`, `PublicReview`, `VerifiedReview`, `ReviewService`, `ReviewFileHandler` |
| Member 6 | Wishlist & Search | `Wishlist`, `RecentView`, `WishlistService`, `WishlistFileHandler` |

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
│   │   ├── User.java              (Abstract – Member 1)
│   │   ├── BuyerUser.java         (Member 1)
│   │   ├── SellerUser.java        (Member 1)
│   │   ├── Car.java               (Abstract – Member 2)
│   │   ├── SUV.java               (Member 2)
│   │   ├── Sedan.java             (Member 2)
│   │   ├── Hatchback.java         (Member 2)
│   │   ├── Inquiry.java           (Member 3)
│   │   ├── Purchase.java          (Member 3)
│   │   ├── AdminUser.java         (Member 4)
│   │   ├── ActivityLog.java       (Member 4)
│   │   ├── Review.java            (Abstract – Member 5)
│   │   ├── PublicReview.java      (Member 5)
│   │   ├── VerifiedReview.java    (Member 5)
│   │   ├── Wishlist.java          (Member 6)
│   │   └── RecentView.java        (Member 6)
│   ├── service/
│   │   ├── AuthenticationService.java  (Member 1)
│   │   ├── CarService.java             (Member 2)
│   │   ├── TransactionService.java     (Member 3)
│   │   ├── AdminService.java           (Member 4)
│   │   ├── ReviewService.java          (Member 5)
│   │   └── WishlistService.java        (Member 6)
│   ├── servlet/ (Spring MVC Controllers)
│   │   ├── UserController.java         (Member 1)
│   │   ├── CarController.java          (Member 2)
│   │   ├── TransactionController.java  (Member 3)
│   │   ├── AdminController.java        (Member 4)
│   │   ├── ReviewController.java       (Member 5)
│   │   └── WishlistController.java     (Member 6)
│   └── util/ (File Handlers)
│       ├── UserFileHandler.java        (Member 1)
│       ├── CarFileHandler.java         (Member 2)
│       ├── InquiryFileHandler.java     (Member 3)
│       ├── PurchaseFileHandler.java    (Member 3)
│       ├── AdminFileHandler.java       (Member 4)
│       ├── ReviewFileHandler.java      (Member 5)
│       └── WishlistFileHandler.java    (Member 6)
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

Each member works on their own branch:
- `feature/user-management` – Member 1
- `feature/car-listing` – Member 2
- `feature/purchase-inquiry` – Member 3
- `feature/admin-management` – Member 4
- `feature/review-rating` – Member 5
- `feature/wishlist-search` – Member 6

---

## License
Academic project – SE1020, IIT Sri Lanka / University of Westminster.
