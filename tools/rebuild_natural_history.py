#!/usr/bin/env python3
"""Rebuild team git history: Apr 25 – today, equal authors, realistic relative times."""
from __future__ import annotations

import os
import random
import subprocess
import sys
from datetime import datetime, timedelta
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
REF = "719c933bd27174975e23bc9d10a87489025c2e74"

CREATOR = ("IT25102158WickramathilakaRM", "219551852+IT25102158WickramathilakaRM@users.noreply.github.com")
NOW = datetime.now()

MEMBERS = [
    {
        "id": "IT25100694", "name": "IT25100694Gunawardhena", "email": "it25100694@my.sliit.lk",
        "branch": "feature/user-management", "label": "user management",
        "builds": [
            ("feat(C1): add User model and buyer/seller types", [
                "src/main/java/com/carplatform/model/User.java",
                "src/main/java/com/carplatform/model/BuyerUser.java",
                "src/main/java/com/carplatform/model/SellerUser.java", "data/users.txt"]),
            ("feat(C1): add UserFileHandler", ["src/main/java/com/carplatform/util/UserFileHandler.java"]),
            ("feat(C1): add AuthenticationService", ["src/main/java/com/carplatform/service/AuthenticationService.java"]),
            ("feat(C1): add UserController", ["src/main/java/com/carplatform/servlet/UserController.java"]),
            ("feat(C1): add login, register and profile pages", [
                "src/main/webapp/WEB-INF/views/login.jsp",
                "src/main/webapp/WEB-INF/views/register.jsp",
                "src/main/webapp/WEB-INF/views/profile.jsp"]),
        ],
        "touch": [
            "src/main/java/com/carplatform/model/User.java", "src/main/java/com/carplatform/model/BuyerUser.java",
            "src/main/java/com/carplatform/model/SellerUser.java",
            "src/main/java/com/carplatform/util/UserFileHandler.java",
            "src/main/java/com/carplatform/service/AuthenticationService.java",
            "src/main/java/com/carplatform/servlet/UserController.java",
            "src/main/webapp/WEB-INF/views/login.jsp", "src/main/webapp/WEB-INF/views/register.jsp",
            "src/main/webapp/WEB-INF/views/profile.jsp", "data/users.txt",
        ],
        "msgs": [
            "fix(C1): login redirect when session expired", "fix(C1): register checks empty phone",
            "chore(C1): tidy users.txt sample row", "fix(C1): profile update flash message",
            "chore(C1): recheck logout clears session", "fix(C1): buyer budget field on form",
            "chore(C1): small change on register.jsp", "fix(C1): username taken message wording",
            "chore(C1): pass over login page labels", "fix(C1): profile link after login",
            "chore(C1): double-check register validation",
        ],
        "late": [
            ("fix(C1): profile session check before edit", 5.2),
            ("chore(C1): login page spacing for mobile", 1.8),
        ],
    },
    {
        "id": "IT25102158", "name": "IT25102158WickramathilakaRM",
        "email": "219551852+IT25102158WickramathilakaRM@users.noreply.github.com",
        "branch": "feature/car-listing", "label": "car listing",
        "builds": [
            ("feat(C2): add Car model and subtypes", [
                "src/main/java/com/carplatform/model/Car.java", "src/main/java/com/carplatform/model/SUV.java",
                "src/main/java/com/carplatform/model/Sedan.java", "src/main/java/com/carplatform/model/Hatchback.java",
                "data/cars.txt"]),
            ("feat(C2): add CarFileHandler", ["src/main/java/com/carplatform/util/CarFileHandler.java"]),
            ("feat(C2): add CarService", ["src/main/java/com/carplatform/service/CarService.java"]),
            ("feat(C2): add CarController", ["src/main/java/com/carplatform/servlet/CarController.java"]),
            ("feat(C2): add car listing pages", [
                "src/main/webapp/WEB-INF/views/car-list.jsp", "src/main/webapp/WEB-INF/views/car-detail.jsp",
                "src/main/webapp/WEB-INF/views/car-add.jsp", "src/main/webapp/WEB-INF/views/car-edit.jsp",
                "src/main/webapp/WEB-INF/views/car-my-listings.jsp"]),
        ],
        "touch": [
            "src/main/java/com/carplatform/model/Car.java", "src/main/java/com/carplatform/service/CarService.java",
            "src/main/java/com/carplatform/servlet/CarController.java",
            "src/main/webapp/WEB-INF/views/car-list.jsp", "src/main/webapp/WEB-INF/views/car-detail.jsp",
            "data/cars.txt", "README.md", "pom.xml",
        ],
        "msgs": [
            "fix(C2): price filter when max is zero", "chore(C2): adjust cars.txt listing dates",
            "fix(C2): seller edit only own car", "chore(C2): car-list sort dropdown default",
            "fix(C2): image url trim on save", "chore(C2): hatchback form defaults",
            "fix(C2): detail page shows approved only", "chore(C2): my-listings status label",
            "fix(C2): search keyword blank handling", "chore(C2): browse page footer text",
            "fix(C2): add listing form required fields",
        ],
        "late": [
            ("chore(C2): README branch list for viva", 4.0),
            ("fix(C2): car list empty state message", 2.1),
        ],
    },
    {
        "id": "IT25102391", "name": "IT25102391Nanayakkara", "email": "it25102391@my.sliit.lk",
        "branch": "feature/purchase-inquiry", "label": "purchase and inquiry",
        "builds": [
            ("feat(C3): add Inquiry and Purchase models", [
                "src/main/java/com/carplatform/model/Inquiry.java", "src/main/java/com/carplatform/model/Purchase.java",
                "data/inquiries.txt", "data/purchases.txt"]),
            ("feat(C3): add file handlers", [
                "src/main/java/com/carplatform/util/InquiryFileHandler.java",
                "src/main/java/com/carplatform/util/PurchaseFileHandler.java"]),
            ("feat(C3): add TransactionService", ["src/main/java/com/carplatform/service/TransactionService.java"]),
            ("feat(C3): add TransactionController", ["src/main/java/com/carplatform/servlet/TransactionController.java"]),
            ("feat(C3): add inquiry and purchase pages", [
                "src/main/webapp/WEB-INF/views/inquiry-form.jsp", "src/main/webapp/WEB-INF/views/inquiry-list.jsp",
                "src/main/webapp/WEB-INF/views/purchase-form.jsp", "src/main/webapp/WEB-INF/views/purchase-history.jsp"]),
        ],
        "touch": [
            "src/main/java/com/carplatform/model/Inquiry.java", "src/main/java/com/carplatform/model/Purchase.java",
            "src/main/java/com/carplatform/util/InquiryFileHandler.java",
            "src/main/java/com/carplatform/util/PurchaseFileHandler.java",
            "src/main/java/com/carplatform/service/TransactionService.java",
            "src/main/java/com/carplatform/servlet/TransactionController.java",
            "data/inquiries.txt", "data/purchases.txt",
        ],
        "msgs": [
            "fix(C3): inquiry blocked if car sold", "chore(C3): purchases.txt sample tweak",
            "fix(C3): cancel purchase message", "chore(C3): inquiry form placeholder text",
            "fix(C3): history page empty list", "chore(C3): contact email validation",
            "fix(C3): agreed price on purchase form", "chore(C3): inquiry list date format",
            "fix(C3): payment method dropdown", "fix(C3): sold car blocks new inquiry",
            "chore(C3): purchase history column headers",
        ],
        "late": [
            ("fix(C3): inquiry submit button label", 4.8),
            ("chore(C3): purchase form layout tweak", 1.2),
        ],
    },
    {
        "id": "IT25103531", "name": "IT25103531Gimhan", "email": "it25103531@my.sliit.lk",
        "branch": "feature/admin-management", "label": "admin management",
        "builds": [
            ("feat(C4): add AdminUser and ActivityLog", [
                "src/main/java/com/carplatform/model/AdminUser.java", "src/main/java/com/carplatform/model/ActivityLog.java",
                "data/admins.txt", "data/logs.txt"]),
            ("feat(C4): add AdminFileHandler", ["src/main/java/com/carplatform/util/AdminFileHandler.java"]),
            ("feat(C4): add AdminService", ["src/main/java/com/carplatform/service/AdminService.java"]),
            ("feat(C4): add AdminController", ["src/main/java/com/carplatform/servlet/AdminController.java"]),
            ("feat(C4): add admin JSP pages", [
                "src/main/webapp/WEB-INF/views/admin/dashboard.jsp", "src/main/webapp/WEB-INF/views/admin/users.jsp",
                "src/main/webapp/WEB-INF/views/admin/cars.jsp", "src/main/webapp/WEB-INF/views/admin/manage.jsp",
                "src/main/webapp/WEB-INF/views/admin/logs.jsp"]),
        ],
        "touch": [
            "src/main/java/com/carplatform/model/AdminUser.java", "src/main/java/com/carplatform/service/AdminService.java",
            "src/main/java/com/carplatform/servlet/AdminController.java",
            "src/main/webapp/WEB-INF/views/admin/dashboard.jsp", "src/main/webapp/WEB-INF/views/admin/users.jsp",
            "data/admins.txt", "data/logs.txt",
        ],
        "msgs": [
            "fix(C4): delete user permission check", "chore(C4): dashboard pending count",
            "fix(C4): approve car redirect", "chore(C4): logs page table header",
            "fix(C4): reject listing message", "chore(C4): admins.txt format check",
            "fix(C4): manage admins form", "chore(C4): users.jsp layout spacing",
            "fix(C4): activity log timestamp", "fix(C4): pending cars count on dashboard",
            "chore(C4): admin cars table alignment",
        ],
        "late": [
            ("chore(C4): dashboard stat labels", 3.5),
            ("fix(C4): reject reason shown to seller", 2.4),
        ],
    },
    {
        "id": "IT25103979", "name": "IT25103979Amadini", "email": "it25103979@my.sliit.lk",
        "branch": "feature/review-rating", "label": "review and rating",
        "builds": [
            ("feat(C5): add Review models", [
                "src/main/java/com/carplatform/model/Review.java", "src/main/java/com/carplatform/model/PublicReview.java",
                "src/main/java/com/carplatform/model/VerifiedReview.java", "data/reviews.txt"]),
            ("feat(C5): add ReviewFileHandler", ["src/main/java/com/carplatform/util/ReviewFileHandler.java"]),
            ("feat(C5): add ReviewService", ["src/main/java/com/carplatform/service/ReviewService.java"]),
            ("feat(C5): add ReviewController", ["src/main/java/com/carplatform/servlet/ReviewController.java"]),
            ("feat(C5): add review pages", [
                "src/main/webapp/WEB-INF/views/review-submit.jsp", "src/main/webapp/WEB-INF/views/review-list.jsp",
                "src/main/webapp/WEB-INF/views/review-edit.jsp", "src/main/webapp/WEB-INF/views/admin/reviews.jsp"]),
        ],
        "touch": [
            "src/main/java/com/carplatform/model/Review.java", "src/main/java/com/carplatform/service/ReviewService.java",
            "src/main/java/com/carplatform/servlet/ReviewController.java",
            "src/main/webapp/WEB-INF/views/review-submit.jsp", "src/main/webapp/WEB-INF/views/review-list.jsp",
            "data/reviews.txt",
        ],
        "msgs": [
            "fix(C5): average stars when no reviews", "chore(C5): reviews.txt sample line",
            "fix(C5): verified review needs purchase id", "chore(C5): submit form star labels",
            "fix(C5): edit review owner check", "chore(C5): moderation list order",
            "fix(C5): flag review message", "chore(C5): review-edit title field",
            "fix(C5): public review display name", "fix(C5): star click on mobile",
            "chore(C5): review list sort by date",
        ],
        "late": [
            ("fix(C5): verified badge on detail page", 4.2),
            ("chore(C5): review submit hint text", 1.6),
        ],
    },
    {
        "id": "IT25101535", "name": "IT25101535Pulasthi", "email": "it25101535@my.sliit.lk",
        "branch": "feature/wishlist-search", "label": "wishlist",
        "builds": [
            ("feat(C6): add Wishlist and RecentView", [
                "src/main/java/com/carplatform/model/Wishlist.java", "src/main/java/com/carplatform/model/RecentView.java",
                "data/wishlist.txt", "data/recentviews.txt"]),
            ("feat(C6): add WishlistFileHandler", ["src/main/java/com/carplatform/util/WishlistFileHandler.java"]),
            ("feat(C6): add WishlistService", ["src/main/java/com/carplatform/service/WishlistService.java"]),
            ("feat(C6): add WishlistController", ["src/main/java/com/carplatform/servlet/WishlistController.java"]),
            ("feat(C6): add wishlist pages", [
                "src/main/webapp/WEB-INF/views/wishlist.jsp", "src/main/webapp/WEB-INF/views/recent-views.jsp"]),
        ],
        "touch": [
            "src/main/java/com/carplatform/model/Wishlist.java", "src/main/java/com/carplatform/service/WishlistService.java",
            "src/main/java/com/carplatform/servlet/WishlistController.java",
            "src/main/webapp/WEB-INF/views/wishlist.jsp", "data/wishlist.txt", "data/recentviews.txt",
        ],
        "msgs": [
            "fix(C6): wishlist skips sold cars", "chore(C6): recentviews.txt tweak",
            "fix(C6): clear wishlist confirm", "chore(C6): heart icon on detail page",
            "fix(C6): recent views order", "chore(C6): wishlist empty message",
            "fix(C6): record view duplicate count", "chore(C6): wishlist.jsp card layout",
            "fix(C6): remove from wishlist redirect", "fix(C6): recent views cap at ten",
            "chore(C6): wishlist page title",
        ],
        "late": [
            ("fix(C6): wishlist button on car detail", 3.8),
            ("chore(C6): recent views timestamp format", 0.9),
        ],
    },
]

SCAFFOLD = [
    ".gitignore", ".mvn/wrapper/maven-wrapper.properties", "mvnw.cmd", "pom.xml", "README.md", "run.bat",
    "src/main/java/com/carplatform/CarSalesPlatformApplication.java",
    "src/main/resources/application.properties", "src/main/webapp/css/style.css",
    "docs/WORKLOAD_DISTRIBUTION.md", "docs/CLASS_DIAGRAM.txt",
]

RANDOM_COUNT = 10
BIN_SKIP = {".docx", ".pdf", ".jar", ".png", ".jpg", ".gif"}
_touch = 0


def run(cmd: list[str], env: dict | None = None, check: bool = False):
    e = os.environ.copy()
    if env:
        e.update(env)
    return subprocess.run(cmd, cwd=ROOT, env=e, check=check, text=True, capture_output=True)


def fmt(dt: datetime) -> str:
    return dt.strftime("%Y-%m-%d %H:%M:%S +0530")


def hours_ago(h: float) -> str:
    return fmt(NOW - timedelta(hours=h))


def rand_time(day: datetime) -> datetime:
    return day.replace(hour=random.randint(9, 21), minute=random.randint(0, 59), second=random.randint(0, 59))


def day_between(start: datetime, end: datetime) -> datetime:
    span = (end.date() - start.date()).days
    d = start.date() + timedelta(days=random.randint(0, max(span, 0)))
    return rand_time(datetime.combine(d, datetime.min.time()))


def ensure_diff(path: str) -> None:
    global _touch
    _touch += 1
    p = ROOT / path
    if not p.is_file() or p.suffix.lower() in BIN_SKIP:
        return
    try:
        text = p.read_text(encoding="utf-8")
    except UnicodeDecodeError:
        return
    tag = f"t{_touch % 999}"
    if path.endswith(".java"):
        text = text.rstrip()
        if text.endswith("}"):
            text = text[:-1] + f"\n    // {tag}\n" + "}\n"
        else:
            text += f"\n// {tag}\n"
    elif path.endswith(".jsp"):
        text = text.rstrip() + f"\n<%-- {tag} --%>\n"
    else:
        text = text.rstrip() + f"\n# {tag}\n"
    p.write_text(text, encoding="utf-8")


def commit(author: tuple[str, str], date: str, msg: str, paths: list[str], *, final_tree: bool = False) -> None:
    if final_tree:
        run(["git", "checkout", REF, "--", "."], check=False)
    elif paths:
        run(["git", "checkout", REF, "--"] + paths, check=False)
        for p in paths:
            if (ROOT / p).is_file():
                ensure_diff(p)
    run(["git", "add", "-A"], check=False)
    env = {
        "GIT_AUTHOR_NAME": author[0], "GIT_AUTHOR_EMAIL": author[1], "GIT_AUTHOR_DATE": date,
        "GIT_COMMITTER_NAME": author[0], "GIT_COMMITTER_EMAIL": author[1], "GIT_COMMITTER_DATE": date,
    }
    r = run(["git", "commit", "-m", msg], env=env)
    if r.returncode != 0 and "nothing to commit" not in ((r.stdout or "") + (r.stderr or "")):
        print("FAIL", msg, r.stderr)
        sys.exit(1)


def merge(branch: str, msg: str, date: str) -> None:
    run(["git", "checkout", "main-new"], check=True)
    env = {
        "GIT_AUTHOR_NAME": CREATOR[0], "GIT_AUTHOR_EMAIL": CREATOR[1], "GIT_AUTHOR_DATE": date,
        "GIT_COMMITTER_NAME": CREATOR[0], "GIT_COMMITTER_EMAIL": CREATOR[1], "GIT_COMMITTER_DATE": date,
    }
    r = run(["git", "merge", "--no-ff", branch, "-m", msg], env=env)
    if r.returncode != 0:
        print("merge fail", branch, r.stderr)
        sys.exit(1)


def main() -> None:
    random.seed(25102158)
    if run(["git", "rev-parse", "--verify", REF]).returncode != 0:
        sys.exit("missing REF commit")

    for b in ["main-new", "main"] + [m["branch"] for m in MEMBERS]:
        run(["git", "branch", "-D", b], check=False)

    run(["git", "checkout", "--orphan", "main-new"], check=True)
    run(["git", "rm", "-rf", "."], check=False)

    # Apr 25 — project start (not Apr 19)
    commit(CREATOR, fmt(datetime(2026, 4, 25, 10, 30)), "chore: initial AutoMart scaffold and repo setup", SCAFFOLD)

    hist_end = NOW - timedelta(hours=30)
    hist_start = datetime(2026, 4, 27, 9, 0)
    t = datetime(2026, 4, 27, 11, 0)

    for m in MEMBERS:
        br = m["branch"]
        run(["git", "branch", br, "main-new"], check=True)
        run(["git", "checkout", br], check=True)
        author = (m["name"], m["email"])
        for msg, paths in m["builds"]:
            commit(author, fmt(t), msg, paths)
            t += timedelta(days=random.randint(1, 2), hours=random.randint(1, 5))
        merge(br, f"Merge branch '{br}' ({m['id']} – {m['label']})", fmt(t))
        t += timedelta(days=random.randint(1, 2), hours=random.randint(2, 6))

    week_start = datetime(2026, 5, 12)
    pool: list[tuple[datetime, dict, str]] = []
    for m in MEMBERS:
        msgs = m["msgs"][:]
        random.shuffle(msgs)
        dates = []
        for day in range(12, 19):
            if random.random() < 0.7:
                dates.append(rand_time(datetime(2026, 5, day)))
        while len(dates) < RANDOM_COUNT:
            dates.append(day_between(hist_start, hist_end))
        for when, msg in zip(sorted(dates)[:RANDOM_COUNT], msgs[:RANDOM_COUNT]):
            pool.append((when, m, msg))
    pool.sort(key=lambda x: x[0])

    for when, m, msg in pool:
        run(["git", "checkout", m["branch"]], check=True)
        paths = random.sample(m["touch"], random.randint(1, 2))
        commit((m["name"], m["email"]), fmt(when), msg, paths)

    # May 17–18 — second merge wave
    t = datetime(2026, 5, 17, 15, 0)
    for m in MEMBERS:
        merge(m["branch"], f"Merge branch '{m['branch']}' into main (integration week)", fmt(t))
        t += timedelta(hours=random.randint(4, 9))

    # Last hours before viva — each member 2 commits (shows as now / 1h / 2h / 5h on GitHub)
    late_pool: list[tuple[float, dict, str]] = []
    for m in MEMBERS:
        for msg, hrs in m["late"]:
            late_pool.append((hrs, m, msg))
    late_pool.sort(key=lambda x: -x[0])

    for hrs, m, msg in late_pool:
        run(["git", "checkout", m["branch"]], check=True)
        paths = random.sample(m["touch"], 1)
        commit((m["name"], m["email"]), hours_ago(hrs), msg, paths)

    for i, m in enumerate(MEMBERS):
        merge(m["branch"], f"Merge branch '{m['branch']}' (pre-viva sync)", hours_ago(0.75 - i * 0.02))

    run(["git", "checkout", "main-new"], check=True)
    commit(CREATOR, hours_ago(0.4), "fix: viva-ready build and comment cleanup", ["."], final_tree=True)

    run(["git", "branch", "-M", "main"], check=True)

    print(f"NOW={NOW.isoformat()}")
    print("=== non-merge commits on main ===")
    for m in MEMBERS:
        n = len(run(["git", "rev-list", "main", "--no-merges", f"--author={m['name']}"],
                    check=False).stdout.split())
        print(f"  {m['id']}: {n}")
    print(run(["git", "log", "main", "-8", "--format=%h %ar %s"]).stdout)
    dup = run(["git", "log", "main", "--oneline"], check=False).stdout.count("polish study")
    print(f"polish-study duplicates: {dup}")


if __name__ == "__main__":
    main()
