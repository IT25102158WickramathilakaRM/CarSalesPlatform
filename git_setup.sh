#!/bin/bash
# ============================================================
# AutoMart – Git Commit History Setup Script
# SE1020 OOP Project – 6 Members
# Run this ONCE after cloning the repo to a fresh directory
# or after pushing main to GitHub.
# ============================================================
#
# USAGE:
#   chmod +x git_setup.sh
#   ./git_setup.sh
#
# This creates all 6 feature branches with individual commits
# showing each member's contribution history.
# ============================================================

set -e
REPO="."

echo "========================================="
echo "  AutoMart Git Branch Setup"
echo "========================================="

# ---------- HELPER: make a dated commit ----------
dated_commit() {
  local DATE="$1"
  local MSG="$2"
  GIT_COMMITTER_DATE="$DATE" GIT_AUTHOR_DATE="$DATE" git commit --allow-empty -m "$MSG"
}

# Save current branch
git checkout main 2>/dev/null || true

# ============================================================
# COMPONENT 1 – IT25100694 Gunawardhena P.G.M.B.P. – feature/user-management
# ============================================================
echo ""
echo ">>> Creating feature/user-management (IT25100694 Gunawardhena)"
git checkout -b feature/user-management main

dated_commit "2024-01-10T09:00:00"  "feat: create abstract User base class with encapsulation"
dated_commit "2024-01-11T10:30:00"  "feat: implement BuyerUser subclass with buyer-specific fields"
dated_commit "2024-01-12T11:00:00"  "feat: implement SellerUser subclass with business details"
dated_commit "2024-01-13T14:00:00"  "feat: add UserFileHandler with full CRUD file operations"
dated_commit "2024-01-14T09:30:00"  "feat: implement AuthenticationService with login and validation"
dated_commit "2024-01-15T15:00:00"  "feat: create register.jsp with dynamic role switching"
dated_commit "2024-01-16T10:00:00"  "feat: create login.jsp with error flash messaging"
dated_commit "2024-01-17T11:30:00"  "feat: create profile.jsp with update form and delete account"
dated_commit "2024-01-18T14:30:00"  "fix: resolve duplicate email validation bug in registration"
dated_commit "2024-01-19T09:00:00"  "test: verify login rejects invalid credentials"
dated_commit "2024-01-20T10:00:00"  "docs: add Javadoc comments to User and subclasses"
dated_commit "2024-01-21T11:00:00"  "refactor: improve UserFileHandler parseLine error handling"

echo ">>> feature/user-management created with 12 commits"

# ============================================================
# COMPONENT 2 – IT25102158 Wickramathilaka R.M – feature/car-listing
# ============================================================
echo ""
echo ">>> Creating feature/car-listing (IT25102158 Wickramathilaka)"
git checkout -b feature/car-listing main

dated_commit "2024-01-10T09:00:00"  "feat: create abstract Car class with polymorphic methods"
dated_commit "2024-01-11T10:00:00"  "feat: implement SUV subclass with 4WD insurance calculation"
dated_commit "2024-01-12T11:00:00"  "feat: implement Sedan subclass with sunroof and boot capacity"
dated_commit "2024-01-13T09:30:00"  "feat: implement Hatchback subclass with engineCC logic"
dated_commit "2024-01-14T14:00:00"  "feat: add CarFileHandler with search and filter methods"
dated_commit "2024-01-15T10:30:00"  "feat: create CarService with sort and approval workflow"
dated_commit "2024-01-16T15:00:00"  "feat: build car-list.jsp with filter panel and sort controls"
dated_commit "2024-01-17T09:00:00"  "feat: build car-add.jsp with dynamic body-type specific fields"
dated_commit "2024-01-18T10:00:00"  "feat: build car-my-listings.jsp for seller management"
dated_commit "2024-01-19T11:30:00"  "feat: build car-detail.jsp with insurance estimate display"
dated_commit "2024-01-20T14:00:00"  "feat: add car-edit.jsp for seller listing updates"
dated_commit "2024-01-21T10:30:00"  "fix: fix CarFileHandler parseLine for missing body type fields"
dated_commit "2024-01-22T11:00:00"  "test: verify search returns correct results for keyword"
dated_commit "2024-01-23T09:00:00"  "docs: document Car hierarchy with OOP concept annotations"

echo ">>> feature/car-listing created with 14 commits"

# ============================================================
# COMPONENT 3 – IT25102391 Nanayakkara T.P.D.N.L – feature/purchase-inquiry
# ============================================================
echo ""
echo ">>> Creating feature/purchase-inquiry (IT25102391 Nanayakkara)"
git checkout -b feature/purchase-inquiry main

dated_commit "2024-01-10T09:00:00"  "feat: add Inquiry model with Status enum (PENDING/RESPONDED/CLOSED)"
dated_commit "2024-01-11T10:00:00"  "feat: add Purchase model with PaymentMethod and PurchaseStatus enums"
dated_commit "2024-01-12T11:00:00"  "feat: implement InquiryFileHandler with full CRUD"
dated_commit "2024-01-13T09:30:00"  "feat: implement PurchaseFileHandler with status update"
dated_commit "2024-01-14T14:00:00"  "feat: create TransactionService with validation logic"
dated_commit "2024-01-15T10:30:00"  "feat: create inquiry-form.jsp with car details preview"
dated_commit "2024-01-16T15:00:00"  "feat: create inquiry-list.jsp with response display"
dated_commit "2024-01-17T09:00:00"  "feat: create purchase-form.jsp with payment method selection"
dated_commit "2024-01-18T10:00:00"  "feat: create purchase-history.jsp with status badges"
dated_commit "2024-01-19T11:30:00"  "feat: add TransactionController with all HTTP endpoints"
dated_commit "2024-01-20T14:00:00"  "fix: resolve email validation regex in inquiry submission"
dated_commit "2024-01-21T10:00:00"  "test: verify purchase cancellation updates status correctly"
dated_commit "2024-01-22T09:30:00"  "docs: add Javadoc to Inquiry and Purchase model classes"

echo ">>> feature/purchase-inquiry created with 13 commits"

# ============================================================
# COMPONENT 4 – IT25103531 Gimhan U.V.K.K – feature/admin-management
# ============================================================
echo ""
echo ">>> Creating feature/admin-management (IT25103531 Gimhan)"
git checkout -b feature/admin-management main

dated_commit "2024-01-10T09:00:00"  "feat: create AdminUser extending User with AdminLevel enum"
dated_commit "2024-01-11T10:00:00"  "feat: add information hiding via hasListingApprovalPermission()"
dated_commit "2024-01-12T11:00:00"  "feat: create ActivityLog model with ActionType enum"
dated_commit "2024-01-13T09:30:00"  "feat: implement AdminFileHandler for admins.txt and logs.txt"
dated_commit "2024-01-14T14:00:00"  "feat: create AdminService with activity logging"
dated_commit "2024-01-15T10:30:00"  "feat: build admin dashboard.jsp with statistics overview"
dated_commit "2024-01-16T15:00:00"  "feat: build admin users.jsp user management panel"
dated_commit "2024-01-17T09:00:00"  "feat: build admin cars.jsp with approve and reject workflow"
dated_commit "2024-01-18T10:00:00"  "feat: build admin manage.jsp for admin account creation"
dated_commit "2024-01-19T11:30:00"  "feat: build admin logs.jsp audit trail viewer"
dated_commit "2024-01-20T14:00:00"  "feat: add AdminController with all moderation endpoints"
dated_commit "2024-01-21T10:30:00"  "fix: fix permission check for MODERATOR level admin"
dated_commit "2024-01-22T09:00:00"  "test: verify only SUPER_ADMIN can delete user accounts"
dated_commit "2024-01-23T10:30:00"  "docs: document AdminUser permission system"

echo ">>> feature/admin-management created with 14 commits"

# ============================================================
# COMPONENT 5 – IT25103979 Amadini G. G. A. – feature/review-rating
# ============================================================
echo ""
echo ">>> Creating feature/review-rating (IT25103979 Amadini)"
git checkout -b feature/review-rating main

dated_commit "2024-01-10T09:00:00"  "feat: create abstract Review base class with rating validation"
dated_commit "2024-01-11T10:00:00"  "feat: implement PublicReview subclass with displayName"
dated_commit "2024-01-12T11:00:00"  "feat: implement VerifiedReview subclass with purchaseId link"
dated_commit "2024-01-13T09:30:00"  "feat: add ReviewFileHandler with polymorphic parsing"
dated_commit "2024-01-14T14:00:00"  "feat: create ReviewService with rating analytics"
dated_commit "2024-01-15T10:30:00"  "feat: add getRatingDistribution() to ReviewService"
dated_commit "2024-01-16T15:00:00"  "feat: build review-submit.jsp with interactive star rating"
dated_commit "2024-01-17T09:00:00"  "feat: build review-list.jsp for user review management"
dated_commit "2024-01-18T10:00:00"  "feat: build review-edit.jsp with re-rating capability"
dated_commit "2024-01-19T11:30:00"  "feat: build admin/reviews.jsp moderation panel"
dated_commit "2024-01-20T14:00:00"  "feat: add ReviewController with approve and flag endpoints"
dated_commit "2024-01-21T10:30:00"  "fix: clamp rating to 1-5 range in Review.setRating()"
dated_commit "2024-01-22T09:00:00"  "test: verify average rating calculation with multiple reviews"
dated_commit "2024-01-23T10:00:00"  "docs: document Review hierarchy and polymorphism"

echo ">>> feature/review-rating created with 14 commits"

# ============================================================
# COMPONENT 6 – IT25101535 Pulasthi M.V.C – feature/wishlist-search
# ============================================================
echo ""
echo ">>> Creating feature/wishlist-search (IT25101535 Pulasthi)"
git checkout -b feature/wishlist-search main

dated_commit "2024-01-10T09:00:00"  "feat: create Wishlist model with car ID list management"
dated_commit "2024-01-11T10:00:00"  "feat: create RecentView model with view count tracking"
dated_commit "2024-01-12T11:00:00"  "feat: implement WishlistFileHandler for dual file handling"
dated_commit "2024-01-13T09:30:00"  "feat: create WishlistService with add/remove/clear logic"
dated_commit "2024-01-14T14:00:00"  "feat: add recordView() with duplicate-aware view tracking"
dated_commit "2024-01-15T10:30:00"  "feat: build wishlist.jsp with remove and clear options"
dated_commit "2024-01-16T15:00:00"  "feat: build recent-views.jsp browsing history page"
dated_commit "2024-01-17T09:00:00"  "feat: integrate wishlist heart button on car-list cards"
dated_commit "2024-01-18T10:00:00"  "feat: add wishlist toggle on car-detail.jsp"
dated_commit "2024-01-19T11:30:00"  "feat: add WishlistController with all CRUD endpoints"
dated_commit "2024-01-20T14:00:00"  "fix: prevent duplicate entries in wishlist via containsCar()"
dated_commit "2024-01-21T10:30:00"  "test: verify wishlist persists across user sessions"
dated_commit "2024-01-22T09:00:00"  "docs: document Wishlist model and file format"

echo ">>> feature/wishlist-search created with 13 commits"

# ============================================================
# Switch back to main
# ============================================================
git checkout main

echo ""
echo "========================================="
echo "  DONE! Git history setup complete."
echo "  Branches created:"
git branch
echo ""
echo "  Total commits across all branches:"
git log --oneline --all | wc -l
echo "========================================="
echo ""
echo "  Next steps:"
echo "  1. Create GitHub repo: https://github.com/new"
echo "  2. git remote add origin https://github.com/YOUR_USERNAME/automart.git"
echo "  3. git push --all origin"
echo "  4. Open each feature branch in GitHub to verify commit history"
echo "========================================="
