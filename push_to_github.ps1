# Push main + all 6 feature branches to GitHub
# Usage: .\push_to_github.ps1 "https://github.com/YOUR_USERNAME/CarSalesPlatform.git"

param(
    [Parameter(Mandatory = $true)]
    [string]$RepoUrl
)

$ErrorActionPreference = "Stop"
Set-Location $PSScriptRoot

if (-not (git remote get-url origin 2>$null)) {
    git remote add origin $RepoUrl
} else {
    git remote set-url origin $RepoUrl
}

Write-Host "Pushing main..."
git push -u origin main

$branches = @(
    "feature/user-management",
    "feature/car-listing",
    "feature/purchase-inquiry",
    "feature/admin-management",
    "feature/review-rating",
    "feature/wishlist-search"
)

foreach ($b in $branches) {
    Write-Host "Pushing $b..."
    git push -u origin $b
}

Write-Host ""
Write-Host "Done. Branches on GitHub:"
git branch -r
