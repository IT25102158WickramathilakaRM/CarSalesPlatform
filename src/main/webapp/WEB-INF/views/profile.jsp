<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Profile – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link">Browse</a>
    <a href="${pageContext.request.contextPath}/cars/my-listings" class="nav-link">My Listings</a>
    <a href="${pageContext.request.contextPath}/wishlist" class="nav-link">Wishlist</a>
    <a href="${pageContext.request.contextPath}/purchase/history" class="nav-link">Purchases</a>
    <a href="${pageContext.request.contextPath}/cars/add" class="nav-link nav-btn">+ List Car</a>
    <a href="${pageContext.request.contextPath}/profile" class="nav-link active">${loggedUser.username}</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>

<div class="page-wrapper" style="max-width:800px">
  <div class="page-header">
    <h1 class="page-title">My Profile</h1>
    <p class="page-subtitle">Manage your account details</p>
  </div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>

  <!-- Profile Info Card -->
  <div class="card mb-3">
    <div class="card-header">Account Information</div>
    <div class="card-body">
      <div style="display:grid;grid-template-columns:80px 1fr;gap:1.5rem;align-items:center;margin-bottom:1.5rem">
        <div style="width:80px;height:80px;border-radius:50%;background:var(--primary);display:flex;align-items:center;justify-content:center;font-size:2rem;color:#fff">
          ${user.initial}
        </div>
        <div>
          <div style="font-size:1.3rem;font-weight:700">${user.fullName}</div>
          <div class="text-secondary">@${user.username}</div>
          <span class="car-badge" style="background:var(--primary-light);color:var(--primary);margin-top:.25rem">${user.role}</span>
        </div>
      </div>

      <form action="${pageContext.request.contextPath}/profile/update" method="post">
        <div class="form-row">
          <div class="form-group">
            <label class="form-label">Full Name</label>
            <input name="fullName" type="text" class="form-control" value="${user.fullName}" required>
          </div>
          <div class="form-group">
            <label class="form-label">Email</label>
            <input name="email" type="email" class="form-control" value="${user.email}" required>
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label class="form-label">Phone</label>
            <input name="phone" type="text" class="form-control" value="${user.phoneNumber}" required>
          </div>
          <div class="form-group">
            <label class="form-label">New Password <small class="text-muted">(leave blank to keep current)</small></label>
            <input name="newPassword" type="password" class="form-control" placeholder="New password (optional)" minlength="6">
          </div>
        </div>
        <div class="text-secondary text-sm mb-2">Member since: ${user.registrationDate} · ID: ${user.userId}</div>
        <button type="submit" class="btn btn-primary">Update Profile</button>
      </form>
    </div>
  </div>

  <!-- Danger Zone -->
  <div class="card" style="border:1.5px solid #fca5a5">
    <div class="card-header" style="color:var(--danger);font-weight:700">Danger zone</div>
    <div class="card-body">
      <p class="text-secondary mb-2">Permanently delete your account. This action cannot be undone.</p>
      <form action="${pageContext.request.contextPath}/profile/delete" method="post"
            onsubmit="return confirm('Are you sure? This will permanently delete your account!')">
        <button type="submit" class="btn btn-danger">Delete My Account</button>
      </form>
    </div>
  </div>
</div>
</body>
</html>
<%-- t21 --%>
