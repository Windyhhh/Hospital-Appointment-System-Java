@echo off
chcp 65001 >nul
echo ========================================
echo   简易编译脚本（需手动下载依赖）
echo ========================================
echo.

REM 检查lib目录和依赖
if not exist "lib\sqlite-jdbc-3.42.0.0.jar" (
    echo 错误：未找到 lib\sqlite-jdbc-3.42.0.0.jar
    echo.
    echo 请先下载以下JAR文件到lib目录：
    echo 1. sqlite-jdbc-3.42.0.0.jar
    echo    下载地址: https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar
    echo.
    echo 2. slf4j-api-2.0.7.jar
    echo    下载地址: https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.7/slf4j-api-2.0.7.jar
    echo.
    echo 3. slf4j-simple-2.0.7.jar
    echo    下载地址: https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.7/slf4j-simple-2.0.7.jar
    echo.
    pause
    exit /b 1
)

REM 创建必要的目录
if not exist "target\classes" mkdir target\classes

echo 正在编译Java源代码...
echo.

REM 编译所有Java文件
javac -encoding UTF-8 -d target\classes -cp "lib\*" src\main\java\com\hospital\*.java src\main\java\com\hospital\database\*.java src\main\java\com\hospital\model\*.java src\main\java\com\hospital\dao\*.java src\main\java\com\hospital\service\*.java src\main\java\com\hospital\ui\*.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo 编译失败！请检查错误信息。
    pause
    exit /b 1
)

echo.
echo 正在打包JAR文件...
echo.

REM 创建MANIFEST.MF
echo Manifest-Version: 1.0 > target\MANIFEST.MF
echo Main-Class: com.hospital.Main >> target\MANIFEST.MF
echo Class-Path: lib/sqlite-jdbc-3.42.0.0.jar lib/slf4j-simple-2.0.7.jar lib/slf4j-api-2.0.7.jar >> target\MANIFEST.MF
echo. >> target\MANIFEST.MF

REM 打包JAR
cd target\classes
jar cfm ..\appointment-system-1.0.0.jar ..\MANIFEST.MF com\hospital\*.class com\hospital\database\*.class com\hospital\model\*.class com\hospital\dao\*.class com\hospital\service\*.class com\hospital\ui\*.class
cd ..\..

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo 打包失败！
    pause
    exit /b 1
)

echo.
echo ========================================
echo   编译成功！
echo ========================================
echo.
echo JAR文件位置: target\appointment-system-1.0.0.jar
echo 依赖库位置: lib\
echo.
echo 现在可以运行 run.bat 启动系统
echo.
pause

