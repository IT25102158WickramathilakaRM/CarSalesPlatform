<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add Car Listing – AutoMart</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav class="navbar">
  <a href="${pageContext.request.contextPath}/cars" class="navbar-brand">Auto<span>Mart</span></a>
  <div class="navbar-nav">
    <a href="${pageContext.request.contextPath}/cars" class="nav-link">Browse</a>
    <a href="${pageContext.request.contextPath}/cars/my-listings" class="nav-link">My Listings</a>
    <a href="${pageContext.request.contextPath}/logout" class="nav-link">Logout</a>
  </div>
</nav>

<div class="page-wrapper" style="max-width:800px">
  <div class="page-header">
    <h1 class="page-title">Add Car Listing</h1>
    <p class="page-subtitle">Fill in all details. Your listing will be reviewed before going live.</p>
  </div>

  <c:if test="${not empty error}"><div class="alert alert-error">${error}</div></c:if>

  <div class="card">
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/cars/add" method="post">

        <!-- Body Type -->
        <div class="form-group">
          <label class="form-label">Body Type *</label>
          <select name="bodyType" id="bodyType" class="form-control form-select" onchange="showTypeFields()" required>
            <option value="Hatchback">Hatchback</option>
            <option value="Sedan">Sedan</option>
            <option value="SUV">SUV</option>
          </select>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label class="form-label">Make (Brand) *</label>
            <input name="make" type="text" class="form-control" placeholder="e.g. Toyota, Honda" required>
          </div>
          <div class="form-group">
            <label class="form-label">Model *</label>
            <input name="model" type="text" class="form-control" placeholder="e.g. Corolla, Civic" required>
          </div>
        </div>

        <div class="form-row-3">
          <div class="form-group">
            <label class="form-label">Year *</label>
            <input name="year" type="number" class="form-control" min="1980" max="2030" placeholder="2020" required>
          </div>
          <div class="form-group">
            <label class="form-label">Price (LKR) *</label>
            <input name="price" type="number" class="form-control" min="1" placeholder="3500000" required>
          </div>
          <div class="form-group">
            <label class="form-label">Mileage (km) *</label>
            <input name="mileage" type="number" class="form-control" min="0" placeholder="45000" required>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label class="form-label">Colour *</label>
            <input name="colour" type="text" class="form-control" placeholder="e.g. Pearl White" required>
          </div>
          <div class="form-group">
            <label class="form-label">Condition *</label>
            <select name="condition" class="form-control form-select" required>
              <option value="Excellent">Excellent</option>
              <option value="Good">Good</option>
              <option value="Fair">Fair</option>
            </select>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label class="form-label">Fuel Type *</label>
            <select name="fuelType" class="form-control form-select" required>
              <option value="Petrol">Petrol</option>
              <option value="Diesel">Diesel</option>
              <option value="Hybrid">Hybrid</option>
              <option value="Electric">Electric</option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-label">Transmission *</label>
            <select name="transmission" class="form-control form-select" required>
              <option value="Automatic">Automatic</option>
              <option value="Manual">Manual</option>
            </select>
          </div>
        </div>

        <div class="form-group">
          <label class="form-label">Description *</label>
          <textarea name="description" class="form-control" rows="4" placeholder="Describe your vehicle in detail..." required style="resize:vertical"></textarea>
        </div>

        <div class="form-group">
          <label class="form-label">Photo URL <small class="text-muted">(optional)</small></label>
          <input name="imageUrl" type="text" class="form-control" placeholder="https://...">
        </div>

        <!-- SUV Fields -->
        <div id="suvFields" style="display:none;border:1px solid var(--border);border-radius:var(--radius-sm);padding:1rem;margin-bottom:1rem">
          <div style="font-weight:600;margin-bottom:.75rem;color:var(--primary)">SUV-specific details</div>
          <div class="form-row-3">
            <div class="form-group"><label class="form-label">Seats</label><input name="numSeats" type="number" class="form-control" value="7" min="5" max="9"></div>
            <div class="form-group"><label class="form-label">4WD</label><select name="hasFourWheelDrive" class="form-control form-select"><option value="false">No</option><option value="true">Yes</option></select></div>
            <div class="form-group"><label class="form-label">Ground Clearance (mm)</label><input name="groundClearance" type="number" class="form-control" value="200"></div>
          </div>
        </div>

        <!-- Sedan Fields -->
        <div id="sedanFields" style="display:none;border:1px solid var(--border);border-radius:var(--radius-sm);padding:1rem;margin-bottom:1rem">
          <div style="font-weight:600;margin-bottom:.75rem;color:var(--primary)">Sedan-specific details</div>
          <div class="form-row-3">
            <div class="form-group"><label class="form-label">Doors</label><input name="numDoors" type="number" class="form-control" value="4" min="2" max="5"></div>
            <div class="form-group"><label class="form-label">Sunroof</label><select name="hasSunroof" class="form-control form-select"><option value="false">No</option><option value="true">Yes</option></select></div>
            <div class="form-group"><label class="form-label">Boot Capacity</label><input name="bootCapacity" type="text" class="form-control" value="460L"></div>
          </div>
        </div>

        <!-- Hatchback Fields -->
        <div id="hatchbackFields" style="border:1px solid var(--border);border-radius:var(--radius-sm);padding:1rem;margin-bottom:1rem">
          <div style="font-weight:600;margin-bottom:.75rem;color:var(--primary)">Hatchback-specific details</div>
          <div class="form-row-3">
            <div class="form-group"><label class="form-label">Foldable Rear Seats</label><select name="hasFoldableRearSeats" class="form-control form-select"><option value="true">Yes</option><option value="false">No</option></select></div>
            <div class="form-group"><label class="form-label">Roof Type</label><select name="roofType" class="form-control form-select"><option value="Standard">Standard</option><option value="Convertible">Convertible</option></select></div>
            <div class="form-group"><label class="form-label">Engine CC</label><input name="engineCC" type="number" class="form-control" value="1200"></div>
          </div>
        </div>

        <div class="flex gap-2">
          <button type="submit" class="btn btn-primary btn-lg">Submit Listing</button>
          <a href="${pageContext.request.contextPath}/cars/my-listings" class="btn btn-secondary btn-lg">Cancel</a>
        </div>
      </form>
    </div>
  </div>
</div>

<script>
function showTypeFields() {
  const val = document.getElementById('bodyType').value;
  document.getElementById('suvFields').style.display      = val === 'SUV'       ? 'block' : 'none';
  document.getElementById('sedanFields').style.display    = val === 'Sedan'     ? 'block' : 'none';
  document.getElementById('hatchbackFields').style.display= val === 'Hatchback' ? 'block' : 'none';
}
showTypeFields();
</script>
</body>
</html>
