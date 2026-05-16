<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Listings – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link">Browse</a>
    <a href="${pageContext.request.contextPath}/cars/my-listings" class="nav-link active">My Listings</a>
    <a href="${pageContext.request.contextPath}/cars/add" class="nav-link nav-btn">+ Add Listing</a>
    <a href="${pageContext.request.contextPath}/profile" class="nav-link">${loggedUser.username}</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper">
  <div class="page-header flex justify-between items-center">
    <div><h1 class="page-title">My Listings</h1><p class="page-subtitle">Manage your car listings</p></div>
    <a href="${pageContext.request.contextPath}/cars/add" class="btn btn-primary">+ New Listing</a>
  </div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>

  <c:choose>
    <c:when test="${empty cars}">
      <div class="card text-center" style="padding:3rem">
        <h3>No listings yet</h3>
        <p class="text-secondary mt-1 mb-2">Start selling by adding your first car listing.</p>
        <a href="${pageContext.request.contextPath}/cars/add" class="btn btn-primary">Add First Listing</a>
      </div>
    </c:when>
    <c:otherwise>
      <div class="table-wrapper">
        <table>
          <thead><tr><th>Car</th><th>Price</th><th>Status</th><th>Approved</th><th>Listed</th><th>Actions</th></tr></thead>
          <tbody>
            <c:forEach var="car" items="${cars}">
              <tr>
                <td><strong>${car.year} ${car.make} ${car.model}</strong><br><span class="car-badge badge-${car.bodyType.toLowerCase()}">${car.bodyType}</span></td>
                <td style="font-weight:700;color:var(--primary)">LKR <fmt:formatNumber value="${car.price}" pattern="#,###"/></td>
                <td><span class="car-badge badge-${car.status.toLowerCase()}">${car.status}</span></td>
                <td>${car.approved ? 'Yes' : 'Pending'}</td>
                <td class="text-secondary text-sm">${car.listedDate}</td>
                <td>
                  <div class="flex gap-1">
                    <a href="${pageContext.request.contextPath}/cars/${car.carId}" class="btn btn-secondary btn-sm">View</a>
                    <a href="${pageContext.request.contextPath}/cars/edit/${car.carId}" class="btn btn-warning btn-sm">Edit</a>
                    <form action="${pageContext.request.contextPath}/cars/delete/${car.carId}" method="post" onsubmit="return confirm('Delete this listing?')">
                      <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                    </form>
                  </div>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
