<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register – AutoMart</title>
  <meta name="description" content="Create your AutoMart account to buy or sell second-hand cars.">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="auth-page">
  <div class="auth-card" style="max-width:560px">
    <div class="auth-logo">Auto<span>Mart</span></div>
    <h2 style="text-align:center;font-size:1.3rem;margin-bottom:1.5rem;color:var(--text-secondary)">Create your account</h2>

    <c:if test="${not empty error}">
      <div class="alert alert-error">⚠️ ${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post" id="registerForm">
      <!-- Role selector -->
      <div class="form-group">
        <label class="form-label">I want to</label>
        <div style="display:grid;grid-template-columns:1fr 1fr;gap:.75rem">
          <label style="cursor:pointer">
            <input type="radio" name="role" value="BUYER" id="roleBuyer" checked style="display:none" onchange="toggleRole()">
            <div class="role-card" id="cardBuyer" style="border:2px solid var(--primary);border-radius:10px;padding:1rem;text-align:center;background:var(--primary-light)">
              🛒 <strong>Buy a Car</strong><br><small>Browse &amp; purchase</small>
            </div>
          </label>
          <label style="cursor:pointer">
            <input type="radio" name="role" value="SELLER" id="roleSeller" style="display:none" onchange="toggleRole()">
            <div class="role-card" id="cardSeller" style="border:2px solid var(--border);border-radius:10px;padding:1rem;text-align:center">
              🏷️ <strong>Sell a Car</strong><br><small>List your vehicles</small>
            </div>
          </label>
        </div>
      </div>

      <div class="form-row">
        <div class="form-group">
          <label class="form-label" for="fullName">Full Name</label>
          <input id="fullName" name="fullName" type="text" class="form-control" placeholder="Your full name" required>
        </div>
        <div class="form-group">
          <label class="form-label" for="username">Username</label>
          <input id="username" name="username" type="text" class="form-control" placeholder="Choose a username" required>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label class="form-label" for="email">Email</label>
          <input id="email" name="email" type="email" class="form-control" placeholder="your@email.com" required>
        </div>
        <div class="form-group">
          <label class="form-label" for="phone">Phone</label>
          <input id="phone" name="phone" type="text" class="form-control" placeholder="07X XXX XXXX" required>
        </div>
      </div>
      <div class="form-group">
        <label class="form-label" for="password">Password <small>(min. 6 chars)</small></label>
        <input id="password" name="password" type="password" class="form-control" placeholder="Create a strong password" required minlength="6">
      </div>

      <!-- Buyer fields -->
      <div id="buyerFields">
        <div class="form-row">
          <div class="form-group">
            <label class="form-label">Preferred Car Type</label>
            <select name="preferredCarType" class="form-control form-select">
              <option value="Any">Any</option>
              <option value="SUV">SUV</option>
              <option value="Sedan">Sedan</option>
              <option value="Hatchback">Hatchback</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label">Budget Limit (LKR)</label>
            <input name="budgetLimit" type="number" class="form-control" placeholder="e.g. 5000000">
          </div>
        </div>
      </div>

      <!-- Seller fields -->
      <div id="sellerFields" style="display:none">
        <div class="form-row">
          <div class="form-group">
            <label class="form-label">Business Name</label>
            <input name="businessName" type="text" class="form-control" placeholder="Your dealership name">
          </div>
          <div class="form-group">
            <label class="form-label">Location</label>
            <input name="location" type="text" class="form-control" placeholder="City / District">
          </div>
        </div>
      </div>

      <button type="submit" class="btn btn-primary btn-block btn-lg" style="margin-top:.5rem">Create Account</button>
    </form>

    <p style="text-align:center;margin-top:1.5rem;color:var(--text-secondary);font-size:.9rem">
      Already have an account? <a href="${pageContext.request.contextPath}/login" style="font-weight:600">Sign in</a>
    </p>
  </div>
</div>

<script>
function toggleRole() {
  const isSeller = document.getElementById('roleSeller').checked;
  document.getElementById('buyerFields').style.display  = isSeller ? 'none'  : 'block';
  document.getElementById('sellerFields').style.display = isSeller ? 'block' : 'none';
  document.getElementById('cardBuyer').style.border  = isSeller ? '2px solid var(--border)' : '2px solid var(--primary)';
  document.getElementById('cardBuyer').style.background = isSeller ? '' : 'var(--primary-light)';
  document.getElementById('cardSeller').style.border = isSeller ? '2px solid var(--primary)' : '2px solid var(--border)';
  document.getElementById('cardSeller').style.background = isSeller ? 'var(--primary-light)' : '';
}
document.querySelectorAll('input[name="role"]').forEach(r => r.addEventListener('change', toggleRole));
</script>
</body>
</html>
