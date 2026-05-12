<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Activity Logs – Admin</title>
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
  <div class="page-header"><h1 class="page-title">📋 Activity Logs</h1><p class="page-subtitle">System-wide admin activity audit trail</p></div>
  <div class="table-wrapper">
    <table>
      <thead><tr><th>Log ID</th><th>Admin</th><th>Action</th><th>Target</th><th>Description</th><th>Timestamp</th></tr></thead>
      <tbody>
        <c:forEach var="log" items="${logs}">
          <tr>
            <td class="text-sm text-muted">${log.logId}</td>
            <td class="text-sm">${log.adminId}</td>
            <td><span class="car-badge badge-pending" style="font-size:.7rem">${log.actionType}</span></td>
            <td class="text-sm">${log.targetId}</td>
            <td class="text-sm text-secondary">${log.description}</td>
            <td class="text-sm text-muted">${log.timestamp}</td>
          </tr>
        </c:forEach>
        <c:if test="${empty logs}">
          <tr><td colspan="6" style="text-align:center;padding:2rem;color:var(--text-muted)">No activity logs yet.</td></tr>
        </c:if>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>
