<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Management – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar" style="background:#020617">
  <a href="${pageContext.request.contextPath}/admin/dashboard" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="nav-link">Dashboard</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>
<div class="page-wrapper">
  <div class="page-header"><h1 class="page-title">Admin Management</h1></div>
  <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>
  <div style="display:grid;grid-template-columns:1fr 360px;gap:2rem;align-items:start">
    <div class="table-wrapper">
      <table>
        <thead><tr><th>Username</th><th>Name</th><th>Level</th><th>Dept</th><th>Actions</th></tr></thead>
        <tbody>
          <c:forEach var="admin" items="${admins}">
            <tr>
              <td><strong>${admin.username}</strong></td>
              <td>${admin.fullName}</td>
              <td>${admin.adminLevel}</td>
              <td class="text-sm">${admin.department}</td>
              <td>
                <form action="${pageContext.request.contextPath}/admin/delete/${admin.userId}" method="post" onsubmit="return confirm('Delete?')">
                  <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <div class="card">
      <div class="card-header">Create New Admin</div>
      <div class="card-body">
        <form action="${pageContext.request.contextPath}/admin/create" method="post">
          <div class="form-group"><label class="form-label">Username</label><input name="username" type="text" class="form-control" required></div>
          <div class="form-group"><label class="form-label">Email</label><input name="email" type="email" class="form-control" required></div>
          <div class="form-group"><label class="form-label">Password</label><input name="password" type="password" class="form-control" required></div>
          <div class="form-group"><label class="form-label">Full Name</label><input name="fullName" type="text" class="form-control" required></div>
          <div class="form-group"><label class="form-label">Phone</label><input name="phone" type="text" class="form-control" required></div>
          <div class="form-group"><label class="form-label">Level</label>
            <select name="level" class="form-control form-select">
              <option value="SUPPORT">Support</option>
              <option value="MODERATOR">Moderator</option>
              <option value="SUPER_ADMIN">Super Admin</option>
            </select>
          </div>
          <div class="form-group"><label class="form-label">Department</label><input name="department" type="text" class="form-control" required></div>
          <button type="submit" class="btn btn-primary btn-block">Create Admin</button>
        </form>
      </div>
    </div>
  </div>
</div>
</body>
</html>
