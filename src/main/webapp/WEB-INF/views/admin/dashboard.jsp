<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Dashboard – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <style>
    .admin-sidebar { background:var(--secondary); border-radius:var(--radius); padding:1.25rem; }
    .admin-sidebar a { display:flex;align-items:center;gap:.75rem;padding:.65rem 1rem;border-radius:var(--radius-sm);color:#94a3b8;font-size:.9rem;font-weight:500;transition:all .2s;margin-bottom:.1rem;text-decoration:none; }
    .admin-sidebar a:hover,.admin-sidebar a.active { background:rgba(255,255,255,.1);color:#fff; }
    .admin-sidebar .section-label { color:#475569;font-size:.7rem;text-transform:uppercase;letter-spacing:.1em;font-weight:600;padding:.5rem 1rem;margin-top:.5rem; }
  </style>
</head>
<body style="background:#0f172a">
<nav class="navbar" style="background:#020617;border-bottom:1px solid #1e293b">
  <a href="${pageContext.request.contextPath}/admin/dashboard" class="navbar-brand">Auto<span>Mart</span> <span style="font-size:.7rem;background:var(--danger);color:#fff;padding:.1rem .4rem;border-radius:4px;margin-left:.4rem">ADMIN</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link" target="_blank">View Site</a>
    <span class="nav-link" style="color:#94a3b8">${loggedUser.username}</span>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>

<div style="display:grid;grid-template-columns:220px 1fr;gap:1.5rem;padding:1.5rem;max-width:1400px;margin:0 auto">
  <!-- Sidebar -->
  <div class="admin-sidebar" style="height:fit-content;position:sticky;top:calc(var(--nav-height)+1rem)">
    <div class="section-label">Overview</div>
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="active">Dashboard</a>
    <div class="section-label">Manage</div>
    <a href="${pageContext.request.contextPath}/admin/users">Users</a>
    <a href="${pageContext.request.contextPath}/admin/cars">Car listings</a>
    <a href="${pageContext.request.contextPath}/reviews/admin/moderate">Reviews</a>
    <a href="${pageContext.request.contextPath}/admin/manage">Admins</a>
    <div class="section-label">System</div>
    <a href="${pageContext.request.contextPath}/admin/logs">Activity logs</a>
  </div>

  <!-- Main Content -->
  <div>
    <div class="page-header">
      <h1 class="page-title" style="color:#f1f5f9">Admin Dashboard</h1>
      <p style="color:#64748b">Welcome back, ${loggedUser.fullName}</p>
    </div>

    <!-- Stats Grid -->
    <div class="stats-grid mb-3">
      <div class="stat-card" style="background:#1e293b;border-color:#334155">
        <div class="stat-value">${totalUsers}</div>
        <div class="stat-label" style="color:#94a3b8">Total users</div>
      </div>
      <div class="stat-card" style="background:#1e293b;border-color:#334155">
        <div class="stat-value">${totalCars}</div>
        <div class="stat-label" style="color:#94a3b8">Total listings</div>
      </div>
      <div class="stat-card" style="background:#1e293b;border-color:#334155">
        <div class="stat-value" style="color:#f59e0b">${pendingCars}</div>
        <div class="stat-label" style="color:#94a3b8">Pending approval</div>
      </div>
      <div class="stat-card" style="background:#1e293b;border-color:#334155">
        <div class="stat-value" style="color:#10b981">${totalPurchases}</div>
        <div class="stat-label" style="color:#94a3b8">Total purchases</div>
      </div>
    </div>

    <!-- Quick Actions -->
    <div class="card mb-3" style="background:#1e293b;border-color:#334155">
      <div class="card-header" style="color:#f1f5f9;border-color:#334155">Quick actions</div>
      <div class="card-body" style="display:flex;gap:1rem;flex-wrap:wrap">
        <a href="${pageContext.request.contextPath}/admin/cars" class="btn btn-warning">Review Pending Listings</a>
        <a href="${pageContext.request.contextPath}/reviews/admin/moderate" class="btn btn-primary">Moderate Reviews</a>
        <a href="${pageContext.request.contextPath}/admin/users" class="btn btn-secondary">Manage Users</a>
        <a href="${pageContext.request.contextPath}/admin/logs" class="btn btn-secondary">View Logs</a>
      </div>
    </div>

    <!-- Recent Logs -->
    <div class="card" style="background:#1e293b;border-color:#334155">
      <div class="card-header" style="color:#f1f5f9;border-color:#334155">Recent activity</div>
      <div class="table-wrapper" style="border:none">
        <table style="color:#e2e8f0">
          <thead style="background:#0f172a"><tr><th style="color:#64748b">Log ID</th><th style="color:#64748b">Action</th><th style="color:#64748b">Target</th><th style="color:#64748b">Timestamp</th></tr></thead>
          <tbody>
            <c:forEach var="log" items="${recentLogs}" end="9">
              <tr style="border-color:#334155">
                <td class="text-sm" style="color:#64748b">${log.logId}</td>
                <td><span class="car-badge badge-pending">${log.actionType}</span></td>
                <td class="text-sm">${log.targetId}</td>
                <td class="text-sm" style="color:#64748b">${log.timestamp}</td>
              </tr>
            </c:forEach>
            <c:if test="${empty recentLogs}">
              <tr><td colspan="4" style="text-align:center;padding:2rem;color:#64748b">No activity logged yet.</td></tr>
            </c:if>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</body>
</html>
