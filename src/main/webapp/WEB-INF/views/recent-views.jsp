<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Recently Viewed – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link">Browse</a>
    <a href="${pageContext.request.contextPath}/wishlist" class="nav-link">Wishlist</a>
    <a href="${pageContext.request.contextPath}/wishlist/recent" class="nav-link active">Recent</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper">
  <div class="page-header">
    <h1 class="page-title">Recently viewed</h1>
    <p class="page-subtitle">Cars you have browsed recently</p>
  </div>
  <c:choose>
    <c:when test="${empty recentCars}">
      <div class="card text-center" style="padding:3rem">
        <h3 class="mt-2">No recently viewed cars</h3>
        <p class="text-secondary mt-1 mb-2">Start browsing to see your history here.</p>
        <a href="${pageContext.request.contextPath}/cars" class="btn btn-primary">Browse Cars</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="car-grid">
        <c:forEach var="car" items="${recentCars}">
          <div class="car-card">
            <c:choose>
              <c:when test="${not empty car.imageUrl}">
                <div class="car-card-img"><img src="${car.imageUrl}" alt="${car.year} ${car.make} ${car.model}" loading="lazy"></div>
              </c:when>
              <c:otherwise>
                <div class="car-card-img">No photo</div>
              </c:otherwise>
            </c:choose>
            <div class="car-card-body">
              <span class="car-badge badge-${car.bodyType.toLowerCase()}">${car.bodyType}</span>
              <div class="car-make-model mt-1">${car.year} ${car.make} ${car.model}</div>
              <div class="car-price" style="margin-top:.4rem">LKR <fmt:formatNumber value="${car.price}" pattern="#,###"/></div>
              <div class="car-meta">
                <span class="car-meta-item">${car.fuelType}</span>
                <span class="car-meta-item"><fmt:formatNumber value="${car.mileage}" pattern="#,###"/> km</span>
              </div>
              <a href="${pageContext.request.contextPath}/cars/${car.carId}" class="btn btn-primary btn-sm btn-block" style="margin-top:1rem">View Again</a>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
