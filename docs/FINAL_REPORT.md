# SE1020 Object Oriented Programming
# AutoMart – Second-Hand Car Sales & Purchase Platform
# FINAL PROJECT REPORT

---

## COVER PAGE

**Project Title:** AutoMart – Second-Hand Car Sales and Purchase Platform
**Module:** SE1020 – Object Oriented Programming
**Assessment:** Group Project (10% of final grade)
**Submission Date:** 2024

### Group Members

| Member | Name | Student ID | Module |
|--------|------|-----------|--------|
| 1 | [Member 1 Name] | [ID] | User Management |
| 2 | [Member 2 Name] | [ID] | Car Listing Management |
| 3 | [Member 3 Name] | [ID] | Purchase & Inquiry Management |
| 4 | [Member 4 Name] | [ID] | Admin Management |
| 5 | [Member 5 Name] | [ID] | Review & Rating Management |
| 6 | [Member 6 Name] | [ID] | Wishlist & Search Enhancement |

---

## DECLARATION

We, the undersigned members of Group [X], declare that:
- The work submitted is our own and has not been submitted previously for any other module.
- All sources and references have been appropriately acknowledged.
- All group members have contributed equitably to this project.

Signatures: _________________ (All 6 members)

---

## ACKNOWLEDGEMENTS

We would like to express our sincere gratitude to our module lecturer for guidance throughout
this project. We also thank our peers for their constructive feedback during development.

---

## TABLE OF CONTENTS

1. Introduction
2. Problem Statement
3. Objectives
4. System Features
5. Technologies Used
6. System Architecture
7. Module Breakdown & Workload Distribution
8. OOP Concepts Implementation
9. File Handling Implementation
10. User Interface Design
11. Testing
12. GitHub Contribution History
13. Conclusion
14. References
15. Appendices (Class Diagrams, Sample Data)

---

## 1. INTRODUCTION

AutoMart is a web-based second-hand car sales and purchase platform developed using Java
Object-Oriented Programming principles. The system allows buyers to browse and purchase
used vehicles, sellers to list their vehicles, and administrators to moderate the platform.

The application is built using:
- Java 17 with Spring Boot 3.2
- JSP Servlets for web layer
- File I/O for data persistence
- HTML/CSS with custom design system
- GitHub for version control

This project demonstrates the practical application of OOP concepts including
Encapsulation, Inheritance, Polymorphism, Abstraction, and Information Hiding.

---

## 2. PROBLEM STATEMENT

The second-hand vehicle market in Sri Lanka lacks a centralised, trustworthy digital
platform where buyers and sellers can interact safely. Existing solutions are fragmented,
lack proper verification, and do not provide adequate moderation. AutoMart addresses
this gap by providing:
- Verified listings with admin approval workflow
- Structured inquiry and purchase management
- Review and rating system for seller accountability
- Wishlist functionality for buyer convenience

---

## 3. OBJECTIVES

Primary objectives:
1. Apply OOP concepts: Encapsulation, Inheritance, Polymorphism, Abstraction, Information Hiding
2. Implement minimum 3 CRUD operations per module (6 modules × 4 CRUD = 24+ operations total)
3. Create user-friendly web interfaces (3+ per member)
4. Use file read/write operations for all data storage
5. Demonstrate collaborative development using GitHub

---

## 4. SYSTEM FEATURES

### Buyer Features
- Register and login as a buyer
- Browse and search car listings
- Filter by body type, fuel type, price range
- Sort listings by price and year
- View detailed car information and reviews
- Submit inquiries to sellers
- Purchase vehicles online
- Manage wishlist
- View recently viewed cars

### Seller Features
- Register as a seller with business details
- Add car listings (SUV, Sedan, Hatchback)
- Edit listing price, condition, description
- Delete own listings
- View listing approval status

### Admin Features
- Secure admin dashboard with statistics
- Approve or reject car listings
- Manage user accounts
- Moderate reviews (approve, flag, delete)
- Create and manage admin accounts
- View full activity audit logs

---

## 5. TECHNOLOGIES USED

| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 17 | Core programming language |
| Spring Boot | 3.2.0 | Web framework |
| JSP/JSTL | 3.0 | View layer (server-side rendering) |
| HTML5 | - | Page structure |
| CSS3 | - | Custom design system |
| JavaScript | ES6+ | Frontend interactions |
| Maven | 3.9 | Build automation |
| GitHub | - | Version control |
| File I/O | Java NIO | Data persistence |

---

## 6. SYSTEM ARCHITECTURE

```
┌─────────────────────────────────────────────────────────┐
│                    Web Browser (Client)                  │
└────────────────────────┬────────────────────────────────┘
                         │ HTTP Request
┌────────────────────────▼────────────────────────────────┐
│            Spring Boot (Embedded Tomcat)                 │
│  ┌─────────────────────────────────────────────────┐    │
│  │         Spring MVC Controllers (Servlets)        │    │
│  │  UserController  │  CarController  │ AdminCtrl   │    │
│  │  TransactionCtrl │  ReviewCtrl    │ WishlistCtrl │    │
│  └────────────┬─────┴────────────────┴─────────────┘    │
│               │ Service Layer                            │
│  ┌────────────▼──────────────────────────────────────┐  │
│  │  AuthenticationService │ CarService │ AdminService  │  │
│  │  TransactionService   │ ReviewService│WishlistService│  │
│  └────────────┬──────────┴──────────────────────────┘  │
│               │ File Handler Layer                       │
│  ┌────────────▼──────────────────────────────────────┐  │
│  │  UserFileHandler │ CarFileHandler │ AdminFileHandler│  │
│  │  InquiryFH │ PurchaseFH │ ReviewFH │ WishlistFH   │  │
│  └────────────┬──────────────────────────────────────┘  │
│               │ File System                              │
│  ┌────────────▼──────────────────────────────────────┐  │
│  │  users.txt │ cars.txt │ inquiries.txt │ purchases.txt│  │
│  │  reviews.txt │ admins.txt │ wishlist.txt │ logs.txt  │  │
│  └───────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

**Architectural Pattern:** MVC (Model-View-Controller)
- **Model:** Java classes in `model` package
- **View:** JSP pages in `WEB-INF/views`
- **Controller:** Spring MVC controllers in `servlet` package

---

## 7. MODULE BREAKDOWN & WORKLOAD DISTRIBUTION

### Module 1 – User Management (Member 1)

**Description:** Manages user registration, authentication, and profile management.

**CRUD Operations:**
- **Create:** Register new buyer/seller accounts (stored in users.txt)
- **Read:** Find users by ID, username, or email
- **Update:** Modify profile details and password
- **Delete:** Permanently remove user accounts

**Java Classes:**
- `User.java` – Abstract base class (Abstraction, Encapsulation)
- `BuyerUser.java` – Extends User (Inheritance, Polymorphism)
- `SellerUser.java` – Extends User (Inheritance, Polymorphism)
- `AuthenticationService.java` – Business logic layer
- `UserFileHandler.java` – File I/O for users.txt
- `UserController.java` – HTTP request handler

**UI Pages:**
1. `register.jsp` – New user registration with role selection
2. `login.jsp` – Authentication page
3. `profile.jsp` – View and update profile, delete account

**OOP Concepts:**
- **Abstraction:** `User` is abstract; subclasses define `getRole()` and `getDisplayInfo()`
- **Encapsulation:** All fields private; accessed via getters/setters
- **Inheritance:** `BuyerUser` and `SellerUser` extend `User`
- **Polymorphism:** `getRole()` returns different values per subtype

**File Format (users.txt):**
```
userId|username|email|password|fullName|phone|regDate|isActive|role|[role-specific fields]
```

---

### Module 2 – Car Listing Management (Member 2)

**Description:** Manages the complete lifecycle of car listings.

**CRUD Operations:**
- **Create:** Add new car listing (SUV/Sedan/Hatchback) to cars.txt
- **Read:** Search, filter, sort listings by multiple criteria
- **Update:** Edit price, condition, description, status
- **Delete:** Remove own listings

**Java Classes:**
- `Car.java` – Abstract base class
- `SUV.java` – Extends Car (4WD, ground clearance, seats)
- `Sedan.java` – Extends Car (doors, sunroof, boot capacity)
- `Hatchback.java` – Extends Car (engine CC, roof type, foldable seats)
- `CarService.java` – CRUD + search/filter/sort logic
- `CarFileHandler.java` – File I/O for cars.txt
- `CarController.java` – HTTP endpoints

**UI Pages:**
1. `car-list.jsp` – Browse with search, filter, sort
2. `car-add.jsp` – Add new listing with type-specific fields
3. `car-edit.jsp` – Edit existing listing
4. `car-my-listings.jsp` – Seller's own listings management
5. `car-detail.jsp` – Full car detail view

**OOP Concepts:**
- **Abstraction:** `Car` has abstract `getBodyType()`, `calculateInsuranceEstimate()`, `getListingSummary()`
- **Inheritance:** `SUV`, `Sedan`, `Hatchback` extend `Car`
- **Polymorphism:** Each car type calculates insurance differently

---

### Module 3 – Purchase & Inquiry Management (Member 3)

**Description:** Manages buyer inquiries and vehicle purchase transactions.

**CRUD Operations (Inquiry):**
- Create, Read, Update (respond), Delete inquiries

**CRUD Operations (Purchase):**
- Create, Read, Update (status), Delete purchase records

**Java Classes:**
- `Inquiry.java` – With status enum (PENDING/RESPONDED/CLOSED)
- `Purchase.java` – With payment method and status enums
- `TransactionService.java` – Business logic
- `InquiryFileHandler.java` – File I/O for inquiries.txt
- `PurchaseFileHandler.java` – File I/O for purchases.txt
- `TransactionController.java` – HTTP endpoints

**UI Pages:**
1. `inquiry-form.jsp` – Submit inquiry for a vehicle
2. `inquiry-list.jsp` – View and manage own inquiries
3. `purchase-form.jsp` – Submit purchase request
4. `purchase-history.jsp` – Transaction history

---

### Module 4 – Admin Management (Member 4)

**Description:** Full admin dashboard with moderation, user management, and audit logging.

**CRUD Operations:**
- **Create:** Register new admin accounts (admins.txt)
- **Read:** View all users, logs, pending listings
- **Update:** Approve/reject listings, modify permissions
- **Delete:** Remove admin accounts, delete users

**Java Classes:**
- `AdminUser.java` – Extends User with admin-level enum
- `ActivityLog.java` – Audit trail entries
- `AdminService.java` – Admin-specific business logic
- `AdminFileHandler.java` – File I/O for admins.txt and logs.txt
- `AdminController.java` – HTTP endpoints

**UI Pages:**
1. `admin/dashboard.jsp` – Statistics overview
2. `admin/users.jsp` – User management panel
3. `admin/cars.jsp` – Car moderation panel
4. `admin/manage.jsp` – Admin account management
5. `admin/logs.jsp` – Activity audit log viewer

**OOP Concepts:**
- **Information Hiding:** `hasListingApprovalPermission()` and `hasUserDeletionPermission()` hide permission logic
- **Inheritance:** `AdminUser` extends `User`
- **Abstraction:** Admin-only operations abstracted from regular user flow

---

### Module 5 – Review & Rating Management (Member 5)

**Description:** Manages car reviews, star ratings, and moderation workflow.

**CRUD Operations:**
- **Create:** Submit public or verified reviews
- **Read:** View reviews per car, average ratings, distribution
- **Update:** Edit own reviews, admin approve/flag
- **Delete:** Remove own reviews or admin deletion

**Java Classes:**
- `Review.java` – Abstract base class
- `PublicReview.java` – For general users
- `VerifiedReview.java` – For confirmed buyers (links to purchase)
- `ReviewService.java` – Including rating analytics
- `ReviewFileHandler.java` – File I/O for reviews.txt
- `ReviewController.java` – HTTP endpoints

**UI Pages:**
1. `review-submit.jsp` – Star rating submission with verified option
2. `review-list.jsp` – User's own review management
3. `review-edit.jsp` – Edit submitted review
4. `admin/reviews.jsp` – Admin moderation panel

**OOP Concepts:**
- **Polymorphism:** `getFormattedDisplay()` differs between Public and Verified reviews
- **Inheritance:** Both review types extend abstract `Review`
- **Encapsulation:** Rating clamped to 1–5 in setter

---

### Module 6 – Wishlist & Search Enhancement (Member 6)

**Description:** Wishlist management, recently viewed cars tracking, and advanced search.

**CRUD Operations:**
- **Create:** Add cars to wishlist (wishlist.txt)
- **Read:** View wishlist contents, recently viewed history
- **Update:** Modify wishlist (add/remove items)
- **Delete:** Remove individual items or clear entire wishlist

**Java Classes:**
- `Wishlist.java` – Maintains list of saved car IDs per user
- `RecentView.java` – Tracks view counts and dates
- `WishlistService.java` – Business logic
- `WishlistFileHandler.java` – File I/O for wishlist.txt and recentviews.txt
- `WishlistController.java` – HTTP endpoints

**UI Pages:**
1. `wishlist.jsp` – Saved vehicles with remove option
2. `recent-views.jsp` – Recently browsed cars
3. `car-list.jsp` integrates advanced search filters (shared with Member 2)

---

## 8. OOP CONCEPTS IMPLEMENTATION

### 8.1 Abstraction

Achieved through abstract classes:
- `User` (abstract): Forces subclasses to implement `getRole()` and `getDisplayInfo()`
- `Car` (abstract): Forces subclasses to implement `getBodyType()`, `calculateInsuranceEstimate()`, `getListingSummary()`
- `Review` (abstract): Forces subclasses to implement `getReviewType()` and `getFormattedDisplay()`

### 8.2 Encapsulation

All model class fields are declared `private`. Access is controlled through:
- `getXxx()` getter methods for reading values
- `setXxx()` setter methods with validation where appropriate
- Example: `Review.setRating()` clamps value to 1–5 range

### 8.3 Inheritance

Three distinct inheritance hierarchies:
```
User ──────┬─── BuyerUser
           ├─── SellerUser
           └─── AdminUser

Car ───────┬─── SUV
           ├─── Sedan
           └─── Hatchback

Review ────┬─── PublicReview
           └─── VerifiedReview
```

### 8.4 Polymorphism

Method overriding enables runtime polymorphism:
- `user.getRole()` → "BUYER", "SELLER", or "ADMIN"
- `car.calculateInsuranceEstimate()` → different formulae per type
- `review.getFormattedDisplay()` → different badges per review type
- `CarFileHandler` parses correct subclass based on bodyType field

### 8.5 Information Hiding

`AdminUser` encapsulates permission logic internally:
```java
public boolean hasListingApprovalPermission() {
    return isActive() && canApproveListings;  // logic hidden from callers
}
```

---

## 9. FILE HANDLING IMPLEMENTATION

### Reading Data (BufferedReader)
```java
try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    String line;
    while ((line = reader.readLine()) != null) {
        if (!line.isBlank()) {
            User u = parseLine(line);
            if (u != null) users.add(u);
        }
    }
}
```

### Writing Data (BufferedWriter – Append)
```java
try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
    writer.write(user.toFileString());
    writer.newLine();
}
```

### Updating Data (Full Rewrite)
```java
// 1. Load all records
List<User> all = getAllUsers();
// 2. Modify target record
all.set(index, updatedUser);
// 3. Rewrite entire file
try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
    for (User u : all) { writer.write(u.toFileString()); writer.newLine(); }
}
```

### Deleting Data (Filter + Rewrite)
```java
List<User> all = getAllUsers();
all.removeIf(u -> u.getUserId().equals(userId));
rewriteFile(all);
```

### Exception Handling
All file operations are wrapped in try-with-resources blocks ensuring streams are always closed. Errors are logged to System.err without crashing the application.

---

## 10. USER INTERFACE DESIGN

The UI is built with a custom CSS design system featuring:
- Inter font family (Google Fonts)
- Dark navy navigation bar
- Card-based layouts with subtle shadows
- Responsive grid system (mobile-friendly)
- Color-coded status badges
- Animated hover effects
- Star rating interaction with JavaScript
- Role-specific dynamic forms (buyer vs seller registration)

---

## 11. TESTING

### Functional Test Cases

| Test ID | Feature | Input | Expected | Status |
|---------|---------|-------|----------|--------|
| TC-01 | Register Buyer | Valid form data | Account created | Pass |
| TC-02 | Register – Duplicate Username | Existing username | Error shown | Pass |
| TC-03 | Login Valid | Correct credentials | Redirect to /cars | Pass |
| TC-04 | Login Invalid | Wrong password | Error message | Pass |
| TC-05 | Add Car Listing | Valid car data | Pending approval | Pass |
| TC-06 | Add Car – Invalid Price | Price = 0 | Validation error | Pass |
| TC-07 | Submit Inquiry | Valid message + email | Inquiry saved | Pass |
| TC-08 | Submit Inquiry – Empty | Blank message | Error returned | Pass |
| TC-09 | Purchase Request | Valid car + price | Purchase created | Pass |
| TC-10 | Admin Approve Car | Click Approve | Car visible live | Pass |
| TC-11 | Submit Review | Rating 1-5 + text | Review pending | Pass |
| TC-12 | Invalid Rating | Rating = 0 or 6 | Validation error | Pass |
| TC-13 | Add to Wishlist | Logged-in user | Car saved | Pass |
| TC-14 | Remove from Wishlist | Saved car | Car removed | Pass |
| TC-15 | Delete Account | Confirm dialog | Account removed | Pass |

---

## 12. GITHUB CONTRIBUTION HISTORY

### Branch Strategy
```
main
├── feature/user-management       (Member 1)
├── feature/car-listing           (Member 2)
├── feature/purchase-inquiry      (Member 3)
├── feature/admin-management      (Member 4)
├── feature/review-rating         (Member 5)
└── feature/wishlist-search       (Member 6)
```

### Sample Commit Messages

**Member 1:**
- `feat: add abstract User base class with encapsulation`
- `feat: implement BuyerUser and SellerUser subclasses`
- `feat: implement UserFileHandler with CRUD operations`
- `feat: add AuthenticationService with login validation`
- `feat: create register.jsp with dynamic role switching`
- `feat: create login.jsp with error handling`
- `feat: create profile.jsp with update and delete`
- `fix: resolve duplicate email validation in registration`
- `test: verify login with invalid credentials`

**Member 2:**
- `feat: add abstract Car class with polymorphic methods`
- `feat: implement SUV subclass with 4WD and insurance calc`
- `feat: implement Sedan subclass with sunroof and boot capacity`
- `feat: implement Hatchback subclass with engineCC`
- `feat: add CarFileHandler with search and filter`
- `feat: create CarService with sort and approve functions`
- `feat: build car-list.jsp with filter panel`
- `feat: build car-add.jsp with dynamic body-type fields`
- `feat: build car-edit.jsp for seller listing management`

**Member 3:**
- `feat: add Inquiry model with status enum`
- `feat: add Purchase model with payment method enum`
- `feat: implement InquiryFileHandler CRUD`
- `feat: implement PurchaseFileHandler CRUD`
- `feat: add TransactionService with validation`
- `feat: create inquiry-form.jsp`
- `feat: create purchase-form.jsp`
- `feat: create purchase-history.jsp`

**Member 4:**
- `feat: add AdminUser extending User with permission system`
- `feat: implement ActivityLog model`
- `feat: build AdminFileHandler for admins.txt and logs.txt`
- `feat: create AdminService with logging capability`
- `feat: build admin dashboard.jsp with stats`
- `feat: build admin users.jsp moderation panel`
- `feat: build admin cars.jsp approval workflow`
- `feat: add activity log viewer`

**Member 5:**
- `feat: add abstract Review base class`
- `feat: implement PublicReview subclass`
- `feat: implement VerifiedReview with purchase link`
- `feat: build ReviewFileHandler with rating analytics`
- `feat: add ReviewService with approve and flag`
- `feat: create review-submit.jsp with star rating`
- `feat: create review-list.jsp with edit/delete`
- `feat: create admin/reviews.jsp moderation panel`

**Member 6:**
- `feat: add Wishlist model with car ID list management`
- `feat: add RecentView model with view counter`
- `feat: implement WishlistFileHandler for dual files`
- `feat: build WishlistService with view tracking`
- `feat: create wishlist.jsp with remove and clear`
- `feat: create recent-views.jsp`
- `feat: integrate wishlist heart button on car cards`

---

## 13. CONCLUSION

AutoMart successfully demonstrates the application of Java OOP principles in a real-world
web application context. The project achieves:

- **6 independent modules** with clear separation of concerns
- **Complete CRUD** for all 9 data entities
- **3 inheritance hierarchies** (User, Car, Review)
- **5 OOP concepts** evidenced in code
- **File-based persistence** across 9 text files
- **18 JSP pages** providing a rich user interface
- **Admin moderation** workflow
- **GitHub collaboration** with feature branches

The system is production-ready for academic demonstration and meets all assignment
criteria for functionality, OOP implementation, file handling, UI design, and documentation.

---

## 14. REFERENCES

1. Spring Boot Documentation – https://spring.io/projects/spring-boot
2. JSP/JSTL Reference – https://jakarta.ee/specifications/pages/
3. Java SE 17 API – https://docs.oracle.com/en/java/javase/17/
4. Gamma, E. et al. (1994). Design Patterns. Addison-Wesley.
5. Bloch, J. (2018). Effective Java (3rd ed.). Addison-Wesley.

---

## 15. APPENDICES

### Appendix A – UML Class Diagram

See docs/class-diagram.png

### Appendix B – Sample Data Files

See data/ directory for:
- users.txt (10 records)
- cars.txt (10 records)
- inquiries.txt (10 records)
- purchases.txt (10 records)
- reviews.txt (10 records)
- admins.txt (5 records)
- wishlist.txt (5 records)
- recentviews.txt (10 records)
- logs.txt (10 records)

### Appendix C – GitHub Repository

Repository URL: [Your GitHub URL here]
Commit count: 50+ commits across 6 branches

---

*Report prepared by Group [X] | SE1020 OOP | 2024*
