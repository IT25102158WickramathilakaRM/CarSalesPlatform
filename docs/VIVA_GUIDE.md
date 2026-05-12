# SE1020 – VIVA PREPARATION GUIDE
# AutoMart – Second-Hand Car Sales and Purchase Platform

---

## GENERAL VIVA QUESTIONS & ANSWERS

---

### Q1: What is your project about?

**A:** AutoMart is a web-based second-hand car sales and purchase platform. It allows buyers to browse, search, inquire about, and purchase used vehicles. Sellers can list their vehicles. Admins moderate the platform. The entire backend is built in Java using Spring Boot with JSP Servlets, and all data is stored in text files using Java file I/O instead of a database.

---

### Q2: Why did you use file handling instead of a database?

**A:** The assignment specification required file read/write operations for data storage. We implemented this using `BufferedReader` and `BufferedWriter` for efficient line-by-line reading and writing. Each entity (users, cars, purchases, etc.) has a dedicated `.txt` file with pipe-delimited records. The file handlers provide full CRUD operations by reading all records into memory, modifying them, and rewriting the file.

---

### Q3: Explain your OOP concepts with examples from YOUR code.

**A – Abstraction:**
The `User` class is abstract. It has the abstract method `getRole()` which forces every subclass to declare what role they are. Similarly, `Car` has abstract methods `getBodyType()` and `calculateInsuranceEstimate()`. Callers can use a `Car` reference without knowing if it's an SUV, Sedan, or Hatchback.

**A – Encapsulation:**
All fields in every model class are `private`. For example, in `User.java`, the `password` field is private and can only be accessed via `getPassword()` and `setPassword()`. This hides the internal representation.

**A – Inheritance:**
`BuyerUser` and `SellerUser` both extend the abstract `User` class. They inherit `userId`, `email`, `password` etc. and add their own specific fields. `SUV`, `Sedan`, `Hatchback` all extend `Car`.

**A – Polymorphism:**
When a `Car` reference points to an `SUV` object, calling `car.calculateInsuranceEstimate()` calls the SUV version (3% of price + 10% if 4WD). If it points to a `Sedan`, it uses 2.5% of price. This is runtime polymorphism via method overriding.

**A – Information Hiding:**
In `AdminUser`, the method `hasListingApprovalPermission()` checks both `isActive()` AND `canApproveListings` internally. The caller doesn't know HOW the permission is determined — the logic is hidden inside the class.

---

### Q4: How does file handling work in your project?

**A:** We have seven dedicated file handler classes in the `util` package. Each one handles a specific `.txt` file. Here is how each CRUD operation works:

- **CREATE (Write Append):** Open a `BufferedWriter` with `append=true` and write the object's `toFileString()` followed by a newline.
- **READ:** Open a `BufferedReader`, read line by line, parse each line by splitting on `|`, reconstruct the Java object.
- **UPDATE:** Load ALL records into a `List`, find the matching record by ID, replace it, then rewrite the entire file from scratch using `append=false`.
- **DELETE:** Load ALL records, call `removeIf()` with the matching ID predicate, rewrite the entire file.

All streams are managed using try-with-resources to ensure they are closed even if an exception occurs.

---

### Q5: Why did you use Spring Boot instead of plain Servlets?

**A:** Spring Boot provides an embedded Tomcat server, eliminating the need for a separate server installation. The `@Controller` annotation-based approach is cleaner than extending `HttpServlet` manually. However, the controllers act as Servlets conceptually — they receive HTTP requests, invoke service layer logic, and forward to JSP views. The `@GetMapping` and `@PostMapping` annotations map to `doGet()` and `doPost()` in traditional Servlet terms.

---

### Q6: How did you divide the work among 6 members?

**A:** Each member owns a complete vertical slice of the application:
- Their own model classes (Java OOP)
- Their own service class (business logic)
- Their own file handler (data persistence)
- Their own Spring MVC controller (HTTP layer)
- Their own JSP views (UI)
- Their own GitHub branch and commits

There is no overlap — Member 1 cannot break Member 2's module because they operate on separate files and classes.

---

## MEMBER-SPECIFIC VIVA QUESTIONS

---

### MEMBER 1 – User Management

**Q: How does your registration work?**
A: The `UserController` receives a POST request at `/register`. It calls `AuthenticationService.registerBuyer()` or `registerSeller()` based on the role parameter. The service validates for duplicate username, duplicate email, valid email format, and password length. If valid, it creates a `BuyerUser` or `SellerUser` object with a UUID-based ID and calls `UserFileHandler.saveUser()` which appends the record to `users.txt`.

**Q: Explain Inheritance in your User classes.**
A: `User` is abstract with common fields (userId, username, email, password, fullName, phoneNumber). `BuyerUser` adds `preferredCarType`, `budgetLimit`, `totalPurchases`. `SellerUser` adds `businessName`, `location`, `rating`. Both override `getRole()` returning "BUYER" or "SELLER" respectively.

**Q: How do you prevent duplicate registrations?**
A: `UserFileHandler.usernameExists()` calls `getAllUsers()` and streams through checking `equalsIgnoreCase()`. Similarly for `emailExists()`. Both are checked before saving.

**Q: How does login work?**
A: `AuthenticationService.login()` calls `findByUsername()`, checks if the user is active (`isActive`), then compares the plain-text password. On success, the `User` object is stored in the `HttpSession` with key `"loggedUser"`.

---

### MEMBER 2 – Car Listing Management

**Q: Explain polymorphism in your Car classes.**
A: The `calculateInsuranceEstimate()` method is defined abstract in `Car`. Each subclass overrides it differently. `SUV` uses 3% with a 10% surcharge for 4WD. `Sedan` uses 2.5% with a 5% surcharge for sunroof. `Hatchback` uses 2% with an 8% surcharge if engine > 1600cc. When the car-detail page calls `car.calculateInsuranceEstimate()`, Java's runtime dispatch calls the correct version.

**Q: How does search and filtering work?**
A: `CarService.searchCars()` calls `CarFileHandler.searchByKeyword()` which streams all cars and filters using `toLowerCase().contains(kw)` on make, model, description, and bodyType. Filtering by price uses Java Streams with `.filter(c -> c.getPrice() >= min && c.getPrice() <= max)`. Sorting uses `Comparator.comparingDouble(Car::getPrice)`.

**Q: How does admin approval work?**
A: When a seller submits a listing, `car.setApproved(false)` is set. The car appears in `getPendingApproval()` but NOT in `getAllApprovedListings()`. When an admin approves, `AdminController` calls `CarService.approveListing()` which sets `isApproved=true` and rewrites the file.

---

### MEMBER 3 – Purchase & Inquiry Management

**Q: Explain how an inquiry is created.**
A: `TransactionController` handles POST `/inquiry/submit`. It validates the message is not blank and the email is valid. Then `TransactionService.submitInquiry()` creates an `Inquiry` object with status `PENDING`, a UUID-based ID, and today's date. `InquiryFileHandler.saveInquiry()` appends it to `inquiries.txt`.

**Q: How do you update inquiry status?**
A: The full rewrite pattern: load all inquiries into a List, find the matching one by ID, modify its status and response fields, then call `rewriteFile()` which opens a `BufferedWriter` with `append=false` and writes all records fresh.

**Q: What enums did you use and why?**
A: `Inquiry` uses `Status` enum with `PENDING`, `RESPONDED`, `CLOSED`. `Purchase` uses `PaymentMethod` (CASH, BANK_TRANSFER, INSTALLMENT) and `PurchaseStatus` (PENDING, CONFIRMED, COMPLETED, CANCELLED). Enums are type-safe — you can't accidentally assign an invalid status string.

---

### MEMBER 4 – Admin Management

**Q: What is Information Hiding in your AdminUser class?**
A: The `canApproveListings` and `canDeleteUsers` boolean fields are private. Callers cannot directly check them. Instead they call `hasListingApprovalPermission()` and `hasUserDeletionPermission()` which also check `isActive()`. This hides the permission logic — if we later add more conditions (e.g., account expiry), only the method changes, not all callers.

**Q: How does the activity log work?**
A: Every significant admin action calls `AdminService.logActivity()` which creates an `ActivityLog` object with a UUID log ID, the admin's ID, an `ActionType` enum value, the affected entity ID, a description, timestamp, and IP address. This is appended to `logs.txt` using `AdminFileHandler.appendLog()`.

**Q: How do you control admin permissions?**
A: `AdminUser.AdminLevel` enum has three values: SUPER_ADMIN, MODERATOR, SUPPORT. The constructor sets permissions based on level — SUPER_ADMIN gets all permissions, MODERATOR gets listing approval but not user deletion, SUPPORT gets view-only. This is done in the constructor, not in external logic.

---

### MEMBER 5 – Review & Rating Management

**Q: What is the difference between PublicReview and VerifiedReview?**
A: Both extend abstract `Review`. `PublicReview` stores a `displayName` (what is shown publicly). `VerifiedReview` stores a `purchaseId` linking it to an actual transaction, and an `isVerified` boolean. The `getFormattedDisplay()` method shows a "✅ Verified Purchase" badge for verified reviews — this is polymorphism. In the file, field index 10 stores the type ("PUBLIC" or "VERIFIED") so the parser can reconstruct the correct subclass.

**Q: How do you calculate average ratings?**
A: `ReviewFileHandler.getAverageRatingForCar(carId)` calls `findByCar()` to get all reviews for that car, then uses `mapToInt(Review::getRating).average().orElse(0.0)` — a Java Stream aggregation.

**Q: How does review moderation work?**
A: Submitted reviews have `isApproved=false` by default. `getPendingReviews()` filters `!isApproved && !isFlagged`. Admins can approve (sets `isApproved=true`), flag (sets `isFlagged=true`), or delete. Only approved reviews appear on the public car-detail page.

---

### MEMBER 6 – Wishlist & Search Enhancement

**Q: How does the wishlist work?**
A: Each user has one `Wishlist` object stored as a single line in `wishlist.txt`. The car IDs are comma-separated in the last field. When a user adds a car, `WishlistFileHandler.addCarToWishlist()` loads the user's existing wishlist (or creates a new one), calls `wishlist.addCar(carId)` which prevents duplicates, then saves it back (entire user wishlist line is replaced).

**Q: How does recently viewed work?**
A: When a user views a car detail page, `WishlistService.recordView()` creates a `RecentView` object. `WishlistFileHandler.recordRecentView()` checks if that user-car pair already exists in `recentviews.txt` — if so, it increments `viewCount` and updates the date. If not, it adds a new record. This is the UPDATE operation.

**Q: How does the advanced search integrate with Member 2's work?**
A: The `car-list.jsp` page sends keyword, bodyType, fuelType, minPrice, maxPrice, and sort parameters as query parameters. The `CarController` uses `CarService` methods — `searchCars()`, `filterByBodyType()`, `filterByFuelType()`, `sortByPriceAsc()` etc. The Wishlist module contributes the integration of the ❤️ wishlist button on each car card and the recently viewed tracking on car detail page.

---

## COMMON FOLLOW-UP QUESTIONS

**Q: What would you add if you had more time?**
A: Email notifications for inquiry responses, image upload for car listings, a proper authentication token system, and a MySQL database migration with proper indexing for faster search.

**Q: How does your project demonstrate encapsulation beyond just getters/setters?**
A: The service layer itself is a form of encapsulation — `AuthenticationService` hides all the file handler logic from the controller. Controllers only call high-level methods like `login()`, `registerBuyer()`. They don't know about `UserFileHandler` at all.

**Q: What design pattern did you use?**
A: MVC (Model-View-Controller) — models are POJOs in the `model` package, views are JSP files, controllers are Spring MVC `@Controller` classes. We also used the Service Layer pattern where business logic is separated from the HTTP layer.

**Q: How does your project handle exceptions?**
A: All file I/O operations use try-with-resources. If an exception occurs, it logs to `System.err` and returns `false` to the caller. The service layer returns string result codes ("SUCCESS", "USERNAME_EXISTS", etc.) rather than throwing exceptions upward. Controllers map these codes to user-friendly messages.
