<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Car Moderation – Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar" style="background:#020617">
  <a href="${pageContext.request.contextPath}/admin/dashboard" class="navbar-brand">Auto<span>Mart</span> <span style="font-size:.7rem;background:var(--danger);color:#fff;padding:.1rem .4rem;border-radius:4px;margin-left:.4rem">ADMIN</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link">Dashboard</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper">
  <div class="page-header"><h1 class="page-title">Car Moderation</h1><p class="page-subtitle">Approve or reject car listings before they go live</p></div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>

  <h2 style="font-size:1rem;font-weight:700;margin-bottom:.75rem">Pending approval (${pendingCars.size()})</h2>
  <div class="table-wrapper mb-3">
    <table>
      <thead><tr><th>Car ID</th><th>Vehicle</th><th>Price</th><th>Seller</th><th>Type</th><th>Listed</th><th>Actions</th></tr></thead>
      <tbody>
        <c:forEach var="car" items="${pendingCars}">
          <tr>
            <td class="text-sm text-muted">${car.carId}</td>
            <td><strong>${car.year} ${car.make} ${car.model}</strong></td>
            <td style="font-weight:700;color:var(--primary)">LKR <fmt:formatNumber value="${car.price}" pattern="#,###"/></td>
            <td class="text-sm">${car.sellerId}</td>
            <td><span class="car-badge badge-${car.bodyType.toLowerCase()}">${car.bodyType}</span></td>
            <td class="text-sm text-muted">${car.listedDate}</td>
            <td>
              <div class="flex gap-1">
                <form action="${pageContext.request.contextPath}/admin/cars/approve/${car.carId}" method="post">
                  <button type="submit" class="btn btn-success btn-sm">Approve</button>
                </form>
                <form action="${pageContext.request.contextPath}/admin/cars/reject/${car.carId}" method="post">
                  <button type="submit" class="btn btn-danger btn-sm">Reject</button>
                </form>
              </div>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty pendingCars}">
          <tr><td colspan="7" style="text-align:center;padding:1.5rem;color:var(--text-muted)">No pending listings.</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>

  <h2 style="font-size:1rem;font-weight:700;margin-bottom:.75rem">All Listings (${allCars.size()})</h2>
  <div class="table-wrapper">
    <table>
      <thead><tr><th>Car ID</th><th>Vehicle</th><th>Price</th><th>Status</th><th>Approved</th><th>Actions</th></tr></thead>
      <tbody>
        <c:forEach var="car" items="${allCars}">
          <tr>
            <td class="text-sm text-muted">${car.carId}</td>
            <td>${car.year} ${car.make} ${car.model}</td>
            <td>LKR <fmt:formatNumber value="${car.price}" pattern="#,###"/></td>
            <td><span class="car-badge badge-${car.status.toLowerCase()}">${car.status}</span></td>
            <td>${car.approved ? 'Yes' : 'No'}</td>
            <td>
              <form action="${pageContext.request.contextPath}/admin/cars/delete/${car.carId}" method="post" onsubmit="return confirm('Delete this listing?')">
                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>
<%-- t56 --%>
