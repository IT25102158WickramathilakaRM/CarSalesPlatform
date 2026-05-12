<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Edit Review – AutoMart</title>
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
  <div class="page-header"><h1 class="page-title">Edit Review</h1></div>
  <div class="card">
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/reviews/edit/${review.reviewId}" method="post">
        <div class="form-group">
          <label class="form-label">Rating *</label>
          <div style="display:flex;gap:.5rem;font-size:2rem" id="starRating">
            <c:forEach begin="1" end="5" var="i">
              <span class="star" data-val="${i}" style="cursor:pointer;color:${i <= review.rating ? '#f59e0b' : '#d1d5db'}" onclick="setRating(${i})">★</span>
            </c:forEach>
          </div>
          <input type="hidden" name="rating" id="ratingInput" value="${review.rating}">
        </div>
        <div class="form-group">
          <label class="form-label">Title *</label>
          <input name="title" type="text" class="form-control" value="${review.title}" required maxlength="100">
        </div>
        <div class="form-group">
          <label class="form-label">Review Body *</label>
          <textarea name="body" class="form-control" rows="6" required style="resize:vertical">${review.body}</textarea>
        </div>
        <div class="flex gap-2">
          <button type="submit" class="btn btn-primary">Save Changes</button>
          <a href="${pageContext.request.contextPath}/reviews/my" class="btn btn-secondary">Cancel</a>
        </div>
      </form>
    </div>
  </div>
</div>
<script>
function setRating(val) {
  document.getElementById('ratingInput').value = val;
  document.querySelectorAll('.star').forEach((s, i) => { s.style.color = i < val ? '#f59e0b' : '#d1d5db'; });
}
</script>
</body>
</html>
