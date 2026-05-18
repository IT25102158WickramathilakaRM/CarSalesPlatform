<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Browse Cars – AutoMart</title>
  <meta name="description" content="Search and browse second-hand cars for sale in Sri Lanka.">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- NAVBAR -->
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link active">Browse</a>
    <c:if test="${not empty loggedUser}">
      <a href="${pageContext.request.contextPath}/cars/my-listings" class="nav-link">My Listings</a>
      <a href="${pageContext.request.contextPath}/wishlist" class="nav-link">Wishlist</a>
      <a href="${pageContext.request.contextPath}/cars/add" class="nav-link nav-btn">+ List Car</a>
      <a href="${pageContext.request.contextPath}/profile" class="nav-link">${loggedUser.username}</a>
      <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
    </c:if>
    <c:if test="${empty loggedUser}">
      <a href="${pageContext.request.contextPath}/login" class="nav-link">Sign In</a>
      <a href="${pageContext.request.contextPath}/register" class="nav-link nav-btn">Get Started</a>
    </c:if>
  </div>
</nav>

<!-- HERO -->
<div class="hero">
  <h1>Find Your Perfect <span>Second-Hand Car</span></h1>
  <p>Thousands of verified listings · Trusted sellers · Best prices in Sri Lanka</p>
  <form action="${pageContext.request.contextPath}/cars" method="get" style="max-width:600px;margin:0 auto">
    <div class="search-bar">
      <input name="keyword" type="text" class="form-control" placeholder="Search by make, model or type..." value="${keyword}" style="font-size:1rem;padding:.8rem 1.25rem">
      <button type="submit" class="btn btn-primary btn-lg">Search</button>
    </div>
  </form>
</div>

<div class="page-wrapper">
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>

  <!-- FILTER PANEL -->
  <div class="filter-panel">
    <form action="${pageContext.request.contextPath}/cars" method="get">
      <input type="hidden" name="keyword" value="${keyword}">
      <div class="filter-grid">
        <div class="form-group" style="margin:0">
          <label class="form-label">Body Type</label>
          <select name="bodyType" class="form-control form-select">
            <option value="">All Types</option>
            <option value="SUV"       ${bodyType == 'SUV'       ? 'selected' : ''}>SUV</option>
            <option value="Sedan"     ${bodyType == 'Sedan'     ? 'selected' : ''}>Sedan</option>
            <option value="Hatchback" ${bodyType == 'Hatchback' ? 'selected' : ''}>Hatchback</option>
          </select>
        </div>
        <div class="form-group" style="margin:0">
          <label class="form-label">Fuel Type</label>
          <select name="fuelType" class="form-control form-select">
            <option value="">All Fuels</option>
            <option value="Petrol"  ${fuelType == 'Petrol'  ? 'selected' : ''}>Petrol</option>
            <option value="Diesel"  ${fuelType == 'Diesel'  ? 'selected' : ''}>Diesel</option>
            <option value="Hybrid"  ${fuelType == 'Hybrid'  ? 'selected' : ''}>Hybrid</option>
            <option value="Electric"${fuelType == 'Electric' ? 'selected' : ''}>Electric</option>
          </select>
        </div>
        <div class="form-group" style="margin:0">
          <label class="form-label">Min Price (LKR)</label>
          <input name="minPrice" type="number" class="form-control" placeholder="0" value="${minPrice > 0 ? minPrice : ''}">
        </div>
        <div class="form-group" style="margin:0">
          <label class="form-label">Max Price (LKR)</label>
          <input name="maxPrice" type="number" class="form-control" placeholder="Any" value="${maxPrice > 0 ? maxPrice : ''}">
        </div>
        <div class="form-group" style="margin:0">
          <label class="form-label">Sort By</label>
          <select name="sort" class="form-control form-select">
            <option value="default"    ${sort == 'default'    ? 'selected' : ''}>Default</option>
            <option value="price_asc"  ${sort == 'price_asc'  ? 'selected' : ''}>Price ↑</option>
            <option value="price_desc" ${sort == 'price_desc' ? 'selected' : ''}>Price ↓</option>
            <option value="year"       ${sort == 'year'       ? 'selected' : ''}>Newest Year</option>
          </select>
        </div>
        <div class="form-group" style="margin:0;display:flex;align-items:flex-end">
          <button type="submit" class="btn btn-primary w-full">Apply Filters</button>
        </div>
      </div>
    </form>
  </div>

  <!-- RESULTS -->
  <div class="flex justify-between items-center mb-2">
    <h2 style="font-size:1.1rem;font-weight:700">${cars.size()} listings found</h2>
  </div>

  <c:choose>
    <c:when test="${empty cars}">
      <div class="card text-center" style="padding:3rem">
        <h3>No cars found</h3>
        <p class="text-secondary mt-1">Try adjusting your search filters.</p>
      </div>
    </c:when>
    <c:otherwise>
      <div class="car-grid">
        <c:forEach var="car" items="${cars}">
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
                <span class="car-meta-item">${car.transmission}</span>
                <span class="car-meta-item">${car.colour}</span>
              </div>
              <div class="car-meta" style="margin-top:.4rem">
                <span class="car-meta-item"><fmt:formatNumber value="${car.mileage}" pattern="#,###"/> km</span>
                <span class="car-meta-item">${car.condition}</span>
              </div>
              <div class="flex gap-1" style="margin-top:1rem">
                <a href="${pageContext.request.contextPath}/cars/${car.carId}" class="btn btn-primary btn-sm" style="flex:1">View Details</a>
                <c:if test="${not empty loggedUser}">
                  <form action="${pageContext.request.contextPath}/wishlist/add/${car.carId}" method="post" style="margin:0">
                    <button type="submit" class="wishlist-btn" title="Add to wishlist">+</button>
                  </form>
                </c:if>
              </div>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
</div>

<footer style="background:var(--secondary);color:#94a3b8;text-align:center;padding:2rem;margin-top:3rem;font-size:.875rem">
  <strong style="color:#fff">AutoMart</strong> – SE1020 OOP Group Project &copy; 2024
</footer>
</body>
</html>
