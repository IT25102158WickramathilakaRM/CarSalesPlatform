<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Reviews – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link">Browse</a>
    <a href="${pageContext.request.contextPath}/reviews/my" class="nav-link active">My Reviews</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper">
  <div class="page-header"><h1 class="page-title">My Reviews</h1><p class="page-subtitle">Manage reviews you have written</p></div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:choose>
    <c:when test="${empty reviews}">
      <div class="card text-center" style="padding:3rem">
        <h3 class="mt-2">No reviews yet</h3>
        <p class="text-secondary mt-1">Purchase and review a car to share your experience.</p>
      </div>
    </c:when>
    <c:otherwise>
      <div style="display:grid;gap:1rem">
        <c:forEach var="rev" items="${reviews}">
          <div class="card">
            <div class="card-body">
              <div class="flex justify-between items-center">
                <div>
                  <div class="stars">${"*".repeat(rev.rating)}</div>
                  <div class="font-bold mt-1">${rev.title}</div>
                  <div class="text-secondary text-sm">Car: ${rev.carId} · ${rev.submittedDate} · ${rev.reviewType}</div>
                </div>
                <div>
                  <c:choose>
                    <c:when test="${rev.approved}"><span class="car-badge badge-available">Approved</span></c:when>
                    <c:when test="${rev.flagged}"><span class="car-badge badge-sold">Flagged</span></c:when>
                    <c:otherwise><span class="car-badge badge-pending">Pending</span></c:otherwise>
                  </c:choose>
                </div>
              </div>
              <p class="text-secondary mt-1" style="font-size:.9rem">${rev.body}</p>
              <div class="flex gap-1 mt-2">
                <a href="${pageContext.request.contextPath}/reviews/edit/${rev.reviewId}" class="btn btn-warning btn-sm">Edit</a>
                <form action="${pageContext.request.contextPath}/reviews/delete/${rev.reviewId}" method="post" onsubmit="return confirm('Delete review?')">
                  <button type="submit" class="btn btn-danger btn-sm">Delete</button>
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
<%-- t163 --%>
