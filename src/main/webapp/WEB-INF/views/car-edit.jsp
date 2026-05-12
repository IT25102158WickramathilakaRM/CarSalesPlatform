<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Listing – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars/my-listings" class="nav-link">My Listings</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper" style="max-width:700px">
  <div class="page-header">
    <h1 class="page-title">Edit Listing</h1>
    <p class="page-subtitle">${car.year} ${car.make} ${car.model}</p>
  </div>
  <div class="card">
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/cars/edit/${car.carId}" method="post">
        <div class="form-group">
          <label class="form-label">Price (LKR) *</label>
          <input name="price" type="number" class="form-control" value="${car.price}" required min="1">
        </div>
        <div class="form-group">
          <label class="form-label">Condition</label>
          <select name="condition" class="form-control form-select">
            <option value="Excellent" ${car.condition == 'Excellent' ? 'selected' : ''}>Excellent</option>
            <option value="Good"      ${car.condition == 'Good'      ? 'selected' : ''}>Good</option>
            <option value="Fair"      ${car.condition == 'Fair'      ? 'selected' : ''}>Fair</option>
          </select>
        </div>
        <div class="form-group">
          <label class="form-label">Status</label>
          <select name="status" class="form-control form-select">
            <option value="Available" ${car.status == 'Available' ? 'selected' : ''}>Available</option>
            <option value="Sold"      ${car.status == 'Sold'      ? 'selected' : ''}>Sold</option>
            <option value="Pending"   ${car.status == 'Pending'   ? 'selected' : ''}>Pending</option>
          </select>
        </div>
        <div class="form-group">
          <label class="form-label">Description</label>
          <textarea name="description" class="form-control" rows="5" style="resize:vertical">${car.description}</textarea>
        </div>
        <div class="flex gap-2">
          <button type="submit" class="btn btn-primary">Save Changes</button>
          <a href="${pageContext.request.contextPath}/cars/my-listings" class="btn btn-secondary">Cancel</a>
        </div>
      </form>
    </div>
  </div>
</div>
</body>
</html>
