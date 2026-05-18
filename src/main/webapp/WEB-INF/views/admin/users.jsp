<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Manage Users – Admin</title>
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
  <div class="page-header"><h1 class="page-title">User Management</h1><p class="page-subtitle">View and manage all registered users</p></div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
  <div class="table-wrapper">
    <table>
      <thead><tr><th>User ID</th><th>Username</th><th>Full Name</th><th>Email</th><th>Role</th><th>Active</th><th>Registered</th><th>Actions</th></tr></thead>
      <tbody>
        <c:forEach var="user" items="${users}">
          <tr>
            <td class="text-sm text-muted">${user.userId}</td>
            <td><strong>${user.username}</strong></td>
            <td>${user.fullName}</td>
            <td class="text-sm">${user.email}</td>
            <td><span class="car-badge" style="background:${user.role=='SELLER'?'#dcfce7':user.role=='ADMIN'?'#fee2e2':'#dbeafe'};color:${user.role=='SELLER'?'#15803d':user.role=='ADMIN'?'#dc2626':'#1d4ed8'}">${user.role}</span></td>
            <td>${user.active ? 'Yes' : 'No'}</td>
            <td class="text-sm text-muted">${user.registrationDate}</td>
            <td>
              <form action="${pageContext.request.contextPath}/admin/users/delete/${user.userId}" method="post"
                    onsubmit="return confirm('Delete user ${user.username}?')">
                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
              </form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty users}">
          <tr><td colspan="8" style="text-align:center;padding:2rem;color:var(--text-muted)">No users found.</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>
<%-- t172 --%>
