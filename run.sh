#!/bin/bash

echo "========================================"
echo "  医院预约挂号管理系统"
echo "========================================"
echo ""

# 检查是否存在编译好的JAR文件
if [ -f "target/appointment-system-1.0.0.jar" ]; then
    echo "正在启动系统..."
    java -cp "target/appointment-system-1.0.0.jar:lib/*" com.hospital.Main
else
    echo "未找到编译好的JAR文件！"
    echo "请先运行编译脚本编译项目"
    echo ""
    exit 1
fi

