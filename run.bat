@echo off
cd /d "%~dp0"
echo Starting AutoMart on http://localhost:8080/cars
echo Keep this window open while you use the site.
echo.
call mvnw.cmd spring-boot:run
pause
# t6
