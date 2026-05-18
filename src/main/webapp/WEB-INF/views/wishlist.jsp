<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Wishlist – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link">Browse</a>
    <a href="${pageContext.request.contextPath}/wishlist" class="nav-link active">Wishlist</a>
    <a href="${pageContext.request.contextPath}/wishlist/recent" class="nav-link">Recent</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper">
  <div class="page-header flex justify-between items-center">
    <div>
      <h1 class="page-title">My wishlist</h1>
      <p class="page-subtitle">${wishlist.size} saved vehicle${wishlist.size != 1 ? 's' : ''}</p>
    </div>
    <c:if test="${not empty wishlistCars}">
      <form action="${pageContext.request.contextPath}/wishlist/clear" method="post" onsubmit="return confirm('Clear entire wishlist?')">
        <button type="submit" class="btn btn-danger btn-sm">Clear All</button>
      </form>
    </c:if>
  </div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:choose>
    <c:when test="${empty wishlistCars}">
      <div class="card text-center" style="padding:3rem">
        <h3 class="mt-2">Your wishlist is empty</h3>
        <p class="text-secondary mt-1 mb-2">Save cars you like using the wishlist button on any listing.</p>
        <a href="${pageContext.request.contextPath}/cars" class="btn btn-primary">Browse Cars</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="car-grid">
        <c:forEach var="car" items="${wishlistCars}">
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
              <div class="flex justify-between items-center mb-1">
                <span class="car-badge badge-${car.bodyType.toLowerCase()}">${car.bodyType}</span>
                <span class="car-badge badge-${car.status.toLowerCase()}">${car.status}</span>
              </div>
              <div class="car-make-model">${car.year} ${car.make} ${car.model}</div>
              <div class="car-price" style="margin-top:.4rem">LKR <fmt:formatNumber value="${car.price}" pattern="#,###"/></div>
              <div class="car-meta">
                <span class="car-meta-item">${car.fuelType}</span>
                <span class="car-meta-item"><fmt:formatNumber value="${car.mileage}" pattern="#,###"/> km</span>
              </div>
              <div class="flex gap-1" style="margin-top:1rem">
                <a href="${pageContext.request.contextPath}/cars/${car.carId}" class="btn btn-primary btn-sm" style="flex:1">View</a>
                <form action="${pageContext.request.contextPath}/wishlist/remove/${car.carId}" method="post">
                  <button type="submit" class="btn btn-danger btn-sm">Remove</button>
                </form>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
