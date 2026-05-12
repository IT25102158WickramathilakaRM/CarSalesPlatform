<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login – AutoMart</title>
  <meta name="description" content="Sign in to AutoMart – the best second-hand car marketplace in Sri Lanka.">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-page">
  <div class="auth-card">
    <div class="auth-logo">Auto<span>Mart</span></div>
    <h2 style="text-align:center;font-size:1.3rem;margin-bottom:1.5rem;color:var(--text-secondary)">Sign in to your account</h2>

    <c:if test="${not empty success}">
      <div class="alert alert-success">✅ ${success}</div>
    </c:if>
    <c:if test="${not empty error}">
      <div class="alert alert-error">⚠️ ${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
      <div class="form-group">
        <label class="form-label" for="username">Username</label>
        <input id="username" name="username" type="text" class="form-control" placeholder="Enter your username" required>
      </div>
      <div class="form-group">
        <label class="form-label" for="password">Password</label>
        <input id="password" name="password" type="password" class="form-control" placeholder="Enter your password" required>
      </div>
      <button type="submit" class="btn btn-primary btn-block btn-lg" style="margin-top:.5rem">Sign In</button>
    </form>

    <p style="text-align:center;margin-top:1.5rem;color:var(--text-secondary);font-size:.9rem">
      Don't have an account?
      <a href="${pageContext.request.contextPath}/register" style="font-weight:600">Register here</a>
    </p>
  </div>
</div>
</body>
</html>
