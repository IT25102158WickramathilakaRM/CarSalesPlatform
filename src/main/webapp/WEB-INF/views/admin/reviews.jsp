<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Review Moderation – Admin</title>
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
  <div class="page-header"><h1 class="page-title">Review Moderation</h1><p class="page-subtitle">Approve, flag, or remove user reviews</p></div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>

  <h2 style="font-size:1rem;font-weight:700;margin-bottom:.75rem">Pending Reviews (${pendingReviews.size()})</h2>
  <div style="display:grid;gap:1rem;margin-bottom:2rem">
    <c:forEach var="rev" items="${pendingReviews}">
      <div class="card">
        <div class="card-body">
          <div class="flex justify-between items-center">
            <div>
              <div class="stars">${"*".repeat(rev.rating)}</div>
              <div class="font-bold">${rev.title}</div>
              <div class="text-sm text-muted">Car: ${rev.carId} · By: ${rev.reviewerId} · ${rev.submittedDate} · ${rev.reviewType}</div>
            </div>
          </div>
          <p class="text-secondary mt-1" style="font-size:.9rem">${rev.body}</p>
          <div class="flex gap-1 mt-2">
            <form action="${pageContext.request.contextPath}/reviews/admin/approve/${rev.reviewId}" method="post">
              <button type="submit" class="btn btn-success btn-sm">Approve</button>
            </form>
            <form action="${pageContext.request.contextPath}/reviews/admin/flag/${rev.reviewId}" method="post">
              <button type="submit" class="btn btn-warning btn-sm">Flag</button>
            </form>
            <form action="${pageContext.request.contextPath}/reviews/admin/delete/${rev.reviewId}" method="post" onsubmit="return confirm('Delete review?')">
              <button type="submit" class="btn btn-danger btn-sm">Delete</button>
            </form>
          </div>
        </div>
      </div>
    </c:forEach>
    <c:if test="${empty pendingReviews}">
      <div class="card text-center" style="padding:2rem"><p class="text-muted">No pending reviews.</p></div>
    </c:if>
  </div>

  <h2 style="font-size:1rem;font-weight:700;margin-bottom:.75rem">All Reviews (${allReviews.size()})</h2>
  <div class="table-wrapper">
    <table>
      <thead><tr><th>Review ID</th><th>Rating</th><th>Title</th><th>Car</th><th>Type</th><th>Status</th><th>Date</th><th>Actions</th></tr></thead>
      <tbody>
        <c:forEach var="rev" items="${allReviews}">
          <tr>
            <td class="text-sm text-muted">${rev.reviewId}</td>
            <td class="stars" style="font-size:.9rem">${"*".repeat(rev.rating)}</td>
            <td>${rev.title}</td>
            <td class="text-sm">${rev.carId}</td>
            <td class="text-sm">${rev.reviewType}</td>
            <td>
              <c:choose>
                <c:when test="${rev.approved}"><span class="car-badge badge-available">Approved</span></c:when>
                <c:when test="${rev.flagged}"><span class="car-badge badge-sold">Flagged</span></c:when>
                <c:otherwise><span class="car-badge badge-pending">Pending</span></c:otherwise>
              </c:choose>
            </td>
            <td class="text-sm text-muted">${rev.submittedDate}</td>
            <td>
              <form action="${pageContext.request.contextPath}/reviews/admin/delete/${rev.reviewId}" method="post" onsubmit="return confirm('Delete?')">
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
