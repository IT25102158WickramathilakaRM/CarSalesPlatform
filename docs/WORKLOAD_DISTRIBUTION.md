SE1020 – Object Oriented Programming
AutoMart – Second-Hand Car Sales and Purchase Platform
WORKLOAD DISTRIBUTION DOCUMENT

Project Title : AutoMart – Second-Hand Car Sales and Purchase Platform
Module        : SE1020 Object Oriented Programming
Group Size    : 6 Members

═══════════════════════════════════════════════════════════════════════
COMPONENT 1 – USER MANAGEMENT MODULE
═══════════════════════════════════════════════════════════════════════

Description:
  Manages user registration, authentication, profile management,
  password updates, and account deletion for both Buyers and Sellers.

CRUD Operations:
  CREATE : Register a new Buyer or Seller account → saved to users.txt
  READ   : Search user by username, email, or ID
  UPDATE : Modify profile details (name, phone, email, password)
  DELETE : Permanently remove a user account from the system

UI Components:
  1. register.jsp   – Registration page with dynamic Buyer/Seller role switching
  2. login.jsp      – Secure authentication page
  3. profile.jsp    – Profile view with update form and delete account

Java Classes:
  • User.java               – Abstract base class (Abstraction + Encapsulation)
  • BuyerUser.java          – Inherits User; adds budget and preferred car type
  • SellerUser.java         – Inherits User; adds business name and location
  • AuthenticationService.java – CRUD business logic, validation, session management
  • UserFileHandler.java    – BufferedReader/Writer operations on users.txt
  • UserController.java     – Spring MVC controller (@GetMapping / @PostMapping)

OOP Concepts Applied:
  • Abstraction       : User is abstract; subclasses must implement getRole() and getDisplayInfo()
  • Encapsulation     : All fields private; accessed only via getters/setters
  • Inheritance       : BuyerUser and SellerUser extend abstract User
  • Polymorphism      : getRole() returns different values per subtype
  • Information Hiding: Password field hidden; service layer controls all access

Text File:
  users.txt
  Format: userId|username|email|password|fullName|phone|regDate|isActive|role|[role-specific...]
  Sample:
    USR-A1B2C3D4|john_buyer|john@email.com|pass123|John Perera|0771234567|2024-01-10|true|BUYER|Sedan|3500000|2

GitHub Branch: feature/user-management
GitHub Commits (Minimum 8):
  1. feat: create abstract User base class with encapsulation
  2. feat: implement BuyerUser subclass with buyer-specific fields
  3. feat: implement SellerUser subclass with business details
  4. feat: add UserFileHandler with full CRUD file operations
  5. feat: implement AuthenticationService with login validation
  6. feat: create register.jsp with dynamic role switching JS
  7. feat: create login.jsp with error flash messaging
  8. feat: create profile.jsp with update form and danger zone
  9. fix: resolve duplicate email validation bug
  10. test: verify login rejects invalid credentials

Viva Focus:
  • Explain why User is abstract and cannot be instantiated directly
  • Demonstrate reading from users.txt using BufferedReader
  • Show how BuyerUser overrides getRole() polymorphically
  • Explain encapsulation of the password field

═══════════════════════════════════════════════════════════════════════
COMPONENT 2 – CAR LISTING MANAGEMENT MODULE
═══════════════════════════════════════════════════════════════════════

Description:
  Manages the complete lifecycle of second-hand car listings including
  adding, searching, filtering, sorting, editing, and deleting listings.

CRUD Operations:
  CREATE : Add new car listing (SUV/Sedan/Hatchback) → saved to cars.txt
  READ   : Search by keyword; filter by type, fuel, price; sort results
  UPDATE : Edit price, condition, description, availability status
  DELETE : Remove own listings

UI Components:
  1. car-list.jsp        – Browse page with filter panel and sort controls
  2. car-add.jsp         – Add listing with body-type-specific dynamic fields
  3. car-edit.jsp        – Edit existing listing
  4. car-my-listings.jsp – Seller's personal listings management table
  5. car-detail.jsp      – Full detail view with insurance estimate

Java Classes:
  • Car.java          – Abstract base (Abstraction + Encapsulation + Polymorphism)
  • SUV.java          – Extends Car; adds numSeats, hasFourWheelDrive, groundClearance
  • Sedan.java        – Extends Car; adds numDoors, hasSunroof, bootCapacity
  • Hatchback.java    – Extends Car; adds engineCC, roofType, hasFoldableRearSeats
  • CarService.java   – CRUD + search + filter + sort + admin approval logic
  • CarFileHandler.java – File I/O for cars.txt
  • CarController.java – HTTP endpoints

OOP Concepts Applied:
  • Abstraction    : Car has abstract getBodyType(), calculateInsuranceEstimate(), getListingSummary()
  • Inheritance    : SUV, Sedan, Hatchback all extend abstract Car
  • Polymorphism   : calculateInsuranceEstimate() uses different formula per subtype
  • Encapsulation  : All car fields private; accessed via getters/setters

Text File:
  cars.txt
  Format: carId|sellerId|make|model|year|price|mileage|colour|fuelType|transmission|
          condition|description|listedDate|status|isApproved|bodyType|[type-fields...]

GitHub Branch: feature/car-listing
GitHub Commits (Minimum 9):
  1. feat: create abstract Car class with polymorphic methods
  2. feat: implement SUV subclass with insurance calculation
  3. feat: implement Sedan subclass with sunroof logic
  4. feat: implement Hatchback subclass with engine CC
  5. feat: add CarFileHandler with search and filter methods
  6. feat: create CarService with sort and approval workflow
  7. feat: build car-list.jsp with filter panel and search bar
  8. feat: build car-add.jsp with dynamic body-type fields
  9. feat: build car-my-listings.jsp with seller management table
  10. feat: build car-detail.jsp with insurance estimate display

═══════════════════════════════════════════════════════════════════════
COMPONENT 3 – PURCHASE & INQUIRY MANAGEMENT MODULE
═══════════════════════════════════════════════════════════════════════

Description:
  Manages buyer inquiries to sellers and vehicle purchase transactions
  including status tracking, payment method recording, and cancellation.

CRUD Operations (Inquiry):
  CREATE : Submit new inquiry → inquiries.txt
  READ   : View all inquiries for a buyer; view inquiries per car
  UPDATE : Respond to inquiry; close inquiry
  DELETE : Remove inquiry record

CRUD Operations (Purchase):
  CREATE : Submit purchase request → purchases.txt
  READ   : View purchase history for buyer; view per seller
  UPDATE : Update status (PENDING→CONFIRMED→COMPLETED or CANCELLED)
  DELETE : Remove cancelled purchase records

UI Components:
  1. inquiry-form.jsp     – Submit inquiry for a specific vehicle
  2. inquiry-list.jsp     – View all buyer inquiries with seller responses
  3. purchase-form.jsp    – Confirm purchase with payment method
  4. purchase-history.jsp – Full transaction history with status tracking

Java Classes:
  • Inquiry.java              – Status enum: PENDING, RESPONDED, CLOSED
  • Purchase.java             – PaymentMethod enum + PurchaseStatus enum
  • TransactionService.java   – Validation and CRUD for both entities
  • InquiryFileHandler.java   – File I/O for inquiries.txt
  • PurchaseFileHandler.java  – File I/O for purchases.txt
  • TransactionController.java – HTTP endpoints

OOP Concepts Applied:
  • Encapsulation : All fields private; status transitions managed through service
  • Abstraction   : Enums abstract the valid states
  • Polymorphism  : Static factory method fromFileString() on both classes

Text Files:
  inquiries.txt  – Format: inquiryId|carId|buyerId|sellerId|message|email|phone|date|status|response|responseDate
  purchases.txt  – Format: purchaseId|carId|buyerId|sellerId|price|paymentMethod|status|date|completionDate|notes

GitHub Branch: feature/purchase-inquiry

═══════════════════════════════════════════════════════════════════════
COMPONENT 4 – ADMIN MANAGEMENT MODULE
═══════════════════════════════════════════════════════════════════════

Description:
  Provides admin dashboard with full system monitoring, user moderation,
  car listing approval/rejection, admin account management, and audit logging.

CRUD Operations:
  CREATE : Register new admin accounts → admins.txt; append activity logs → logs.txt
  READ   : View all users, pending listings, system statistics, activity logs
  UPDATE : Approve/reject car listings; update admin permissions
  DELETE : Remove admin accounts; delete flagged content

UI Components:
  1. admin/dashboard.jsp – Statistics overview with quick action links
  2. admin/users.jsp     – User listing and deletion panel
  3. admin/cars.jsp      – Car listing moderation (approve/reject/delete)
  4. admin/manage.jsp    – Admin account creation and deletion
  5. admin/logs.jsp      – Complete audit trail viewer

Java Classes:
  • AdminUser.java         – Extends User; AdminLevel enum + permission methods
  • ActivityLog.java       – Audit log with ActionType enum (13 action types)
  • AdminService.java      – Admin CRUD + activity logging
  • AdminFileHandler.java  – File I/O for admins.txt AND logs.txt
  • AdminController.java   – HTTP endpoints

OOP Concepts Applied:
  • Inheritance       : AdminUser extends abstract User class
  • Information Hiding: hasListingApprovalPermission() hides permission logic
  • Abstraction       : Admin-only operations abstracted from regular user flow
  • Encapsulation     : canApproveListings, canDeleteUsers are private booleans

Text Files:
  admins.txt – Format: userId|username|email|password|fullName|phone|regDate|active|ADMIN|level|dept|canApprove|canDelete|canLog|lastLogin
  logs.txt   – Format: logId|adminId|actionType|targetId|description|timestamp|ip

GitHub Branch: feature/admin-management

═══════════════════════════════════════════════════════════════════════
COMPONENT 5 – REVIEW & RATING MANAGEMENT MODULE
═══════════════════════════════════════════════════════════════════════

Description:
  Manages car reviews and star ratings submitted by users, including
  moderation workflow, verified purchase badges, and rating analytics.

CRUD Operations:
  CREATE : Submit public or verified review → reviews.txt
  READ   : View reviews per car; calculate average rating; get distribution
  UPDATE : Edit own review; admin approve/flag review
  DELETE : Delete own review; admin removal of inappropriate content

UI Components:
  1. review-submit.jsp      – Interactive star rating + text submission
  2. review-list.jsp        – User's own reviews with edit and delete
  3. review-edit.jsp        – Edit submitted review with star re-rating
  4. admin/reviews.jsp      – Admin moderation: approve, flag, delete

Java Classes:
  • Review.java          – Abstract base class
  • PublicReview.java    – Extends Review; adds displayName
  • VerifiedReview.java  – Extends Review; adds purchaseId + isVerified
  • ReviewService.java   – Including getRatingDistribution() analytics
  • ReviewFileHandler.java – File I/O + polymorphic parsing
  • ReviewController.java – HTTP endpoints

OOP Concepts Applied:
  • Inheritance  : PublicReview and VerifiedReview extend abstract Review
  • Polymorphism : getFormattedDisplay() shows different content per subtype
  • Encapsulation: setRating() clamps value to 1–5 range internally

Text File:
  reviews.txt – Format: reviewId|carId|reviewerId|sellerId|rating|title|body|date|isApproved|isFlagged|type|[type-fields...]

GitHub Branch: feature/review-rating

═══════════════════════════════════════════════════════════════════════
COMPONENT 6 – WISHLIST & SEARCH ENHANCEMENT MODULE
═══════════════════════════════════════════════════════════════════════

Description:
  Manages user wishlists (saved car listings), recently viewed car tracking,
  and integrates with the advanced search and filter system.

CRUD Operations:
  CREATE : Add car to wishlist / record new view → wishlist.txt, recentviews.txt
  READ   : Display wishlist contents; view recent browsing history
  UPDATE : Modify wishlist (add/remove items); increment view count
  DELETE : Remove specific car from wishlist; clear entire wishlist

UI Components:
  1. wishlist.jsp      – Saved cars with remove and clear options
  2. recent-views.jsp  – Recently browsed car history

Java Classes:
  • Wishlist.java         – Maintains List<String> of car IDs; add/remove/contains
  • RecentView.java       – Tracks viewCount and last viewed date
  • WishlistService.java  – Business logic for both wishlist and recent views
  • WishlistFileHandler.java – Dual file handler for wishlist.txt and recentviews.txt
  • WishlistController.java – HTTP endpoints

OOP Concepts Applied:
  • Encapsulation : Wishlist's car ID list hidden; add/remove via methods
  • Abstraction   : WishlistService hides file handler complexity
  • Polymorphism  : Static fromFileString() factory methods

Text Files:
  wishlist.txt    – Format: wishlistId|userId|createdDate|lastUpdated|carId1,carId2,...
  recentviews.txt – Format: recentViewId|userId|carId|viewedDate|viewCount

GitHub Branch: feature/wishlist-search

═══════════════════════════════════════════════════════════════════════
GITHUB STRATEGY SUMMARY
═══════════════════════════════════════════════════════════════════════

Repository Structure:
  main                      ← stable, tested, merged code
  ├── feature/user-management      (Member 1)
  ├── feature/car-listing          (Member 2)
  ├── feature/purchase-inquiry     (Member 3)
  ├── feature/admin-management     (Member 4)
  ├── feature/review-rating        (Member 5)
  └── feature/wishlist-search      (Member 6)

Weekly Plan:
  Week 1: Create branches; implement model classes; initial commits
  Week 2: Implement file handlers; add CRUD operations
  Week 3: Implement service layer; write unit tests
  Week 4: Build JSP views; connect frontend to backend
  Week 5: Integration testing; bug fixes; merge to main
  Week 6: Documentation; final review; submission

Merge Strategy:
  Each member creates a Pull Request from their feature branch to main.
  At least one other member reviews the PR before merging.
  No direct commits to main branch.

Contribution Evidence:
  Each member must have minimum 8 commits on their feature branch
  with meaningful commit messages following the convention:
    feat: [description]   – new feature
    fix:  [description]   – bug fix
    docs: [description]   – documentation
    test: [description]   – test cases
    refactor: [description] – code improvement
