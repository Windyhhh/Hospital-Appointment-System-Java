@echo off
chcp 65001 >nul
echo ========================================
echo   医院预约挂号管理系统
echo ========================================
echo.

REM 检查是否存在编译好的JAR文件
if exist "target\appointment-system-1.0.0.jar" (
    echo 正在启动系统...
    java -cp "target\appointment-system-1.0.0.jar;lib\*" com.hospital.Main
) else (
    echo 未找到编译好的JAR文件！
    echo 请先运行 compile.bat 或 compile-simple.bat 编译项目
    echo.
    pause
    exit /b 1
)

pause

