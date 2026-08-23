# 医院预约挂号系统 - PowerShell编译脚本

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  编译医院预约挂号系统" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 检查Maven
Write-Host "检测编译工具..." -ForegroundColor Yellow
$useMaven = $false

try {
    $mvnVersion = mvn --version 2>&1 | Select-String "Apache Maven" | Select-Object -First 1
    Write-Host "✓ 检测到Maven: $mvnVersion" -ForegroundColor Green
    $useMaven = $true
} catch {
    Write-Host "○ 未检测到Maven，将使用javac编译" -ForegroundColor Yellow
}

Write-Host ""

if ($useMaven) {
    # 使用Maven编译
    Write-Host "使用Maven编译项目..." -ForegroundColor Green
    Write-Host ""
    
    mvn clean package -DskipTests
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "✓ 编译成功！" -ForegroundColor Green
    } else {
        Write-Host ""
        Write-Host "✗ 编译失败" -ForegroundColor Red
        Read-Host "按回车键退出"
        exit 1
    }
} else {
    # 使用javac编译
    Write-Host "使用javac编译项目..." -ForegroundColor Green
    Write-Host ""
    
    # 创建目录
    if (-not (Test-Path "lib")) {
        New-Item -ItemType Directory -Path "lib" | Out-Null
    }
    if (-not (Test-Path "target\classes")) {
        New-Item -ItemType Directory -Path "target\classes" -Force | Out-Null
    }
    
    # 下载依赖
    Write-Host "下载依赖库..." -ForegroundColor Yellow
    
    $libs = @{
        "lib\sqlite-jdbc-3.42.0.0.jar" = "https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.42.0.0/sqlite-jdbc-3.42.0.0.jar"
        "lib\slf4j-api-2.0.7.jar" = "https://repo1.maven.org/maven2/org/slf4j/slf4j-api/2.0.7/slf4j-api-2.0.7.jar"
        "lib\slf4j-simple-2.0.7.jar" = "https://repo1.maven.org/maven2/org/slf4j/slf4j-simple/2.0.7/slf4j-simple-2.0.7.jar"
    }
    
    foreach ($lib in $libs.GetEnumerator()) {
        if (-not (Test-Path $lib.Key)) {
            Write-Host "  下载 $($lib.Key)..." -ForegroundColor Cyan
            try {
                Invoke-WebRequest -Uri $lib.Value -OutFile $lib.Key -TimeoutSec 60
                Write-Host "  ✓ 完成" -ForegroundColor Green
            } catch {
                Write-Host "  ✗ 下载失败: $($_.Exception.Message)" -ForegroundColor Red
                Read-Host "按回车键退出"
                exit 1
            }
        } else {
            Write-Host "  ✓ $($lib.Key) 已存在" -ForegroundColor Green
        }
    }
    
    Write-Host ""
    Write-Host "编译Java源文件..." -ForegroundColor Yellow
    
    # 查找所有Java文件
    $javaFiles = Get-ChildItem -Path "src\main\java" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }
    
    if ($javaFiles.Count -eq 0) {
        Write-Host "✗ 未找到Java源文件" -ForegroundColor Red
        Read-Host "按回车键退出"
        exit 1
    }
    
    Write-Host "  找到 $($javaFiles.Count) 个Java文件" -ForegroundColor Cyan
    
    # 编译
    $classpath = "lib\*"
    javac -encoding UTF-8 -d target\classes -cp $classpath -sourcepath src\main\java $javaFiles
    
    if ($LASTEXITCODE -ne 0) {
        Write-Host ""
        Write-Host "✗ 编译失败" -ForegroundColor Red
        Read-Host "按回车键退出"
        exit 1
    }
    
    Write-Host "  ✓ 编译完成" -ForegroundColor Green
    Write-Host ""
    Write-Host "创建JAR文件..." -ForegroundColor Yellow
    
    # 创建MANIFEST
    $manifest = @"
Manifest-Version: 1.0
Main-Class: com.hospital.Main
Class-Path: lib/sqlite-jdbc-3.42.0.0.jar lib/slf4j-simple-2.0.7.jar lib/slf4j-api-2.0.7.jar

"@
    $manifest | Out-File -FilePath "target\MANIFEST.MF" -Encoding ASCII
    
    # 创建JAR
    Push-Location target\classes
    jar cfm ..\appointment-system-1.0.0.jar ..\MANIFEST.MF com\hospital\*.class com\hospital\database\*.class com\hospital\model\*.class com\hospital\dao\*.class com\hospital\service\*.class com\hospital\ui\*.class
    Pop-Location
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "  ✓ JAR文件创建成功" -ForegroundColor Green
        Write-Host ""
        Write-Host "✓ 编译成功！" -ForegroundColor Green
    } else {
        Write-Host ""
        Write-Host "✗ JAR创建失败" -ForegroundColor Red
        Read-Host "按回车键退出"
        exit 1
    }
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  编译完成！" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "下一步：运行 .\run.ps1 启动系统" -ForegroundColor Yellow
Write-Host ""

Read-Host "按回车键退出"

