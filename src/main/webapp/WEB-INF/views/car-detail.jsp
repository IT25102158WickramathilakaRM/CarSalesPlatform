<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${car.year} ${car.make} ${car.model} – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link">Browse</a>
    <c:if test="${not empty loggedUser}">
      <a href="${pageContext.request.contextPath}/wishlist" class="nav-link">❤️ Wishlist</a>
      <a href="${pageContext.request.contextPath}/profile" class="nav-link">👤 ${loggedUser.username}</a>
      <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
    </c:if>
    <c:if test="${empty loggedUser}">
      <a href="${pageContext.request.contextPath}/login" class="nav-link nav-btn">Sign In</a>
    </c:if>
  </div>
</nav>

<div class="page-wrapper">
  <c:if test="${not empty success}"><div class="alert alert-success">✅ ${success}</div></c:if>
  <c:if test="${not empty error}"><div class="alert alert-error">⚠️ ${error}</div></c:if>

  <a href="${pageContext.request.contextPath}/cars" class="btn btn-secondary btn-sm mb-2">← Back to Listings</a>

  <div style="display:grid;grid-template-columns:1fr 380px;gap:2rem;align-items:start">
    <!-- LEFT: Car Info -->
    <div>
      <div style="background:linear-gradient(135deg,#1e293b,#334155);border-radius:var(--radius);height:350px;display:flex;align-items:center;justify-content:center;font-size:6rem;margin-bottom:1.5rem">
        🚗
      </div>

      <div class="card">
        <div class="card-body">
          <div class="flex justify-between items-center mb-2">
            <div>
              <h1 class="page-title">${car.year} ${car.make} ${car.model}</h1>
              <div class="flex gap-1 mt-1">
                <span class="car-badge badge-${car.bodyType.toLowerCase()}">${car.bodyType}</span>
                <span class="car-badge badge-${car.status.toLowerCase()}">${car.status}</span>
              </div>
            </div>
            <div style="text-align:right">
              <div style="font-size:2rem;font-weight:800;color:var(--primary)">LKR <fmt:formatNumber value="${car.price}" pattern="#,###"/></div>
              <div class="text-secondary text-sm">Est. Insurance: LKR <fmt:formatNumber value="${car.calculateInsuranceEstimate()}" pattern="#,###"/>/yr</div>
            </div>
          </div>

          <hr style="border:none;border-top:1px solid var(--border);margin:1rem 0">

          <div style="display:grid;grid-template-columns:1fr 1fr;gap:1rem">
            <div><span class="text-muted text-sm">Fuel Type</span><div class="font-bold">${car.fuelType}</div></div>
            <div><span class="text-muted text-sm">Transmission</span><div class="font-bold">${car.transmission}</div></div>
            <div><span class="text-muted text-sm">Mileage</span><div class="font-bold"><fmt:formatNumber value="${car.mileage}" pattern="#,###"/> km</div></div>
            <div><span class="text-muted text-sm">Colour</span><div class="font-bold">${car.colour}</div></div>
            <div><span class="text-muted text-sm">Condition</span><div class="font-bold">${car.condition}</div></div>
            <div><span class="text-muted text-sm">Listed</span><div class="font-bold">${car.listedDate}</div></div>
          </div>

          <hr style="border:none;border-top:1px solid var(--border);margin:1rem 0">
          <h3 style="font-weight:600;margin-bottom:.5rem">Description</h3>
          <p style="color:var(--text-secondary);line-height:1.7">${car.description}</p>

          <hr style="border:none;border-top:1px solid var(--border);margin:1rem 0">
          <h3 style="font-weight:600;margin-bottom:.25rem">Listing Summary</h3>
          <p style="color:var(--text-secondary);font-size:.9rem;font-style:italic">${car.listingSummary}</p>
        </div>
      </div>

      <!-- Reviews Section -->
      <div class="card mt-3">
        <div class="card-header flex justify-between items-center">
          <span>⭐ Reviews &amp; Ratings</span>
          <span class="text-secondary text-sm">${reviews.size()} reviews · Avg: <fmt:formatNumber value="${avgRating}" maxFractionDigits="1"/> / 5</span>
        </div>
        <div class="card-body">
          <c:if test="${not empty loggedUser}">
            <a href="${pageContext.request.contextPath}/reviews/submit/${car.carId}" class="btn btn-outline btn-sm mb-2">Write a Review</a>
          </c:if>
          <c:choose>
            <c:when test="${empty reviews}">
              <p class="text-secondary text-center" style="padding:1rem">No reviews yet. Be the first!</p>
            </c:when>
            <c:otherwise>
              <c:forEach var="review" items="${reviews}">
                <div style="border:1px solid var(--border);border-radius:var(--radius-sm);padding:1rem;margin-bottom:.75rem">
                  <div class="flex justify-between">
                    <div class="stars">${"⭐".repeat(review.rating)}</div>
                    <span class="text-muted text-sm">${review.submittedDate}</span>
                  </div>
                  <div class="font-bold" style="margin-top:.25rem">${review.title}</div>
                  <p class="text-secondary text-sm mt-1">${review.body}</p>
                  <div class="text-muted text-sm mt-1">${review.formattedDisplay}</div>
                </div>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>

    <!-- RIGHT: Action Panel -->
    <div style="position:sticky;top:calc(var(--nav-height) + 1rem)">
      <div class="card">
        <div class="card-body">
          <c:choose>
            <c:when test="${empty loggedUser}">
              <p class="text-secondary text-center mb-2">Sign in to buy, inquire or save</p>
              <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-block">Sign In to Continue</a>
            </c:when>
            <c:when test="${car.status == 'Available'}">
              <a href="${pageContext.request.contextPath}/purchase/${car.carId}" class="btn btn-success btn-block btn-lg mb-2">🛒 Buy Now</a>
              <a href="${pageContext.request.contextPath}/inquiry/${car.carId}" class="btn btn-outline btn-block mb-2">💬 Send Inquiry</a>
              <form action="${pageContext.request.contextPath}/wishlist/${inWishlist ? 'remove' : 'add'}/${car.carId}" method="post">
                <button type="submit" class="btn btn-secondary btn-block">${inWishlist ? '💔 Remove from Wishlist' : '❤️ Add to Wishlist'}</button>
              </form>
            </c:when>
            <c:otherwise>
              <div class="alert alert-warning">This vehicle is no longer available.</div>
            </c:otherwise>
          </c:choose>
        </div>
        <div class="card-footer text-sm text-secondary">
          Car ID: <strong>${car.carId}</strong><br>
          Seller: <strong>${car.sellerId}</strong>
        </div>
      </div>
    </div>
  </div>
</div>

<footer style="background:var(--secondary);color:#94a3b8;text-align:center;padding:2rem;margin-top:3rem;font-size:.875rem">
  <strong style="color:#fff">AutoMart</strong> – SE1020 OOP Group Project &copy; 2024
</footer>
</body>
</html>
