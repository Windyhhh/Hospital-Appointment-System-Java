# 医院预约挂号系统 - PowerShell启动脚本

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  医院预约挂号系统" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 检查Java
Write-Host "检测Java环境..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version" | Select-Object -First 1
    Write-Host "✓ Java已安装: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ 未检测到Java" -ForegroundColor Red
    Write-Host "请先安装Java JDK 8或更高版本" -ForegroundColor Red
    Read-Host "按回车键退出"
    exit 1
}

Write-Host ""

# 检查JAR文件
$jarFile = "target\appointment-system-1.0.0.jar"
if (-not (Test-Path $jarFile)) {
    Write-Host "✗ 未找到编译好的JAR文件" -ForegroundColor Red
    Write-Host "请先编译项目" -ForegroundColor Yellow
    Read-Host "按回车键退出"
    exit 1
}

# 检查依赖库
$libs = @(
    "lib\sqlite-jdbc-3.42.0.0.jar",
    "lib\slf4j-api-2.0.7.jar",
    "lib\slf4j-simple-2.0.7.jar"
)

$allLibsExist = $true
foreach ($lib in $libs) {
    if (-not (Test-Path $lib)) {
        Write-Host "✗ 缺少依赖库: $lib" -ForegroundColor Red
        $allLibsExist = $false
    }
}

if (-not $allLibsExist) {
    Write-Host "请先编译项目以下载依赖库" -ForegroundColor Yellow
    Read-Host "按回车键退出"
    exit 1
}

# 启动系统
Write-Host "正在启动系统..." -ForegroundColor Green
Write-Host ""

$classpath = "$jarFile;lib\*"
java -cp $classpath com.hospital.Main

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "程序异常退出" -ForegroundColor Red
    Read-Host "按回车键退出"
}

