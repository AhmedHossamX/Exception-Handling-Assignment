@echo off
javac Main.java
if %errorlevel% neq 0 (
    echo Compilation failed
    pause
    exit /b %errorlevel%
)
echo Compiled successfully
echo.

rem Normal case
java Main 11111.arxml
echo Normal test case successful
echo.

rem Not valid Autosar file case
java Main 22222.txt
echo Not valid Autosar file test case successful
echo.

rem Empty file case
type nul > 33333.arxml
java Main 33333.arxml
echo Empty file test case successful
echo.

pause