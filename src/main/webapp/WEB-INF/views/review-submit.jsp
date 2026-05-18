<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Submit Review – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/reviews/my" class="nav-link">My Reviews</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper" style="max-width:650px">
  <div class="page-header">
    <h1 class="page-title">Write a Review</h1>
    <p class="page-subtitle">Share your experience for: <strong>${car.year} ${car.make} ${car.model}</strong></p>
  </div>
  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
  <div class="card">
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/reviews/submit" method="post">
        <input type="hidden" name="carId"    value="${car.carId}">
        <input type="hidden" name="sellerId" value="${car.sellerId}">
        <div class="form-group">
          <label class="form-label">Star Rating *</label>
          <div style="display:flex;gap:.5rem;font-size:2rem" id="starRating">
            <c:forEach begin="1" end="5" var="i">
              <span class="star" data-val="${i}" style="cursor:pointer;color:#d1d5db" onclick="setRating(${i})">*</span>
            </c:forEach>
          </div>
          <input type="hidden" name="rating" id="ratingInput" value="5">
        </div>
        <div class="form-group">
          <label class="form-label">Review Title *</label>
          <input name="title" type="text" class="form-control" placeholder="Summarise your experience" required maxlength="100">
        </div>
        <div class="form-group">
          <label class="form-label">Detailed Review *</label>
          <textarea name="body" class="form-control" rows="6" placeholder="Describe the car's condition, the seller's service, and overall experience..." required style="resize:vertical"></textarea>
        </div>
        <div class="form-group">
          <label class="form-label">Purchase Reference ID <small class="text-muted">(leave blank for public review)</small></label>
          <input name="purchaseId" type="text" class="form-control" placeholder="PUR-XXXXXXXX (optional – adds Verified badge)">
        </div>
        <div class="alert alert-info">Reviews are moderated before being published.</div>
        <div class="flex gap-2">
          <button type="submit" class="btn btn-primary">Submit Review</button>
          <a href="${pageContext.request.contextPath}/cars/${car.carId}" class="btn btn-secondary">Cancel</a>
        </div>
      </form>
    </div>
  </div>
</div>
<script>
function setRating(val) {
  document.getElementById('ratingInput').value = val;
  document.querySelectorAll('.star').forEach((s, i) => {
    s.style.color = i < val ? '#f59e0b' : '#d1d5db';
  });
}
setRating(5);
</script>
</body>
</html>
<%-- t157 --%>
