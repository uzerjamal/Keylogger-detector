@echo off
setlocal

echo ============================================
echo  Keylogger Detector — build and run
echo ============================================
echo.

:: Check Java
java -version >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Java is not installed or not on PATH.
    echo         Download it from https://adoptium.net/
    pause
    exit /b 1
)

echo [1/2] Compiling...
javac -d . ^
    ProcessDetector\ProcessData.java ^
    ProcessDetector\ProcessDetector.java ^
    Gui\Kscreen.java ^
    Verifier\Verifier.java ^
    KeyloggerDetector.java

if errorlevel 1 (
    echo.
    echo [ERROR] Compilation failed. Check the output above.
    pause
    exit /b 1
)

echo [2/2] Starting Keylogger Detector...
echo        (Press Ctrl+C to stop)
echo.
java KeyloggerDetector

endlocal
