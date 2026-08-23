# 医院预约挂号系统 - PowerShell版使用指南

## 🎯 快速开始

你的电脑已经安装了 **Java 17**，可以直接运行系统！

### 最简单的运行方式

在PowerShell中执行：

```powershell
java -cp "target/appointment-system-1.0.0.jar;lib/*" com.hospital.Main
```

就这么简单！

---

## 📋 为什么bat脚本打不开？

PowerShell和传统的CMD有一些差异，bat脚本在PowerShell中可能无法正常执行。

**解决方案：**
1. 使用PowerShell脚本（.ps1文件）
2. 使用Java启动器（SystemLauncher.java）
3. 直接使用Java命令（最可靠）

---

## 🚀 三种运行方式

### 方式1：直接用Java命令（推荐⭐⭐⭐⭐⭐）

```powershell
# 进入项目目录
cd "C:\Users\32517\Desktop\10月29"

# 运行系统
java -cp "target/appointment-system-1.0.0.jar;lib/*" com.hospital.Main
```

**优点：**
- 最直接、最可靠
- 不依赖任何脚本
- 适用于所有环境

### 方式2：使用Java启动器（推荐⭐⭐⭐⭐）

```powershell
# 显示菜单
java SystemLauncher.java

# 或直接运行
java SystemLauncher.java run

# 检测环境
java SystemLauncher.java check

# 编译项目
java SystemLauncher.java compile

# 打包移植文件
java SystemLauncher.java package
```

**优点：**
- 功能全面
- 跨平台
- 自动检测环境

### 方式3：使用PowerShell脚本

```powershell
# 运行系统
.\run.ps1

# 编译项目
.\compile.ps1
```

**注意：** 如果提示无法运行脚本，需要修改执行策略：

```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

---

## 🔧 如果需要重新编译

### 使用Maven（如果已安装）

```powershell
mvn clean package -DskipTests
```

### 使用PowerShell编译脚本

```powershell
.\compile.ps1
```

### 使用Java启动器

```powershell
java SystemLauncher.java compile
```

---

## 📦 移植到其他电脑

### 方法1：使用Java启动器打包

```powershell
java SystemLauncher.java package
```

会创建 `hospital-system-portable` 文件夹，包含所有必需文件。

### 方法2：手动复制文件

复制以下文件到目标电脑：

```
必需文件：
├── target/appointment-system-1.0.0.jar
├── lib/sqlite-jdbc-3.42.0.0.jar
├── lib/slf4j-api-2.0.7.jar
└── lib/slf4j-simple-2.0.7.jar

可选文件：
├── hospital.db（保留数据）
├── run.ps1
└── SystemLauncher.java
```

在目标电脑上运行：

```powershell
java -cp "target/appointment-system-1.0.0.jar;lib/*" com.hospital.Main
```

---

## 💡 创建桌面快捷方式

1. 右键桌面 -> 新建 -> 快捷方式
2. 位置输入：
   ```
   java -cp "C:\Users\32517\Desktop\10月29\target\appointment-system-1.0.0.jar;C:\Users\32517\Desktop\10月29\lib\*" com.hospital.Main
   ```
3. 名称：医院预约挂号系统
4. 完成

**注意：** 路径要改成实际的完整路径。

---

## 📁 文件说明

### PowerShell脚本
- `run.ps1` - 启动系统
- `compile.ps1` - 编译项目

### Java工具
- `SystemLauncher.java` - Java版启动器（推荐）

### 文档
- `启动说明-PowerShell版.txt` - PowerShell版详细说明
- `README-PowerShell.md` - 本文件
- `使用说明.txt` - 系统使用说明
- `快速参考.txt` - 快速参考卡片

### 传统bat脚本（可能在PowerShell中无法使用）
- `run.bat`
- `compile.bat`
- `一键安装环境.bat`
- 等等...

---

## ❓ 常见问题

### Q: 为什么双击bat文件没反应？
**A:** PowerShell对bat脚本的支持有限。建议：
- 使用 `.\run.ps1`
- 或直接用Java命令
- 或使用 `java SystemLauncher.java`

### Q: 提示"无法加载，因为在此系统上禁止运行脚本"？
**A:** 执行以下命令修改执行策略：
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

或者直接使用Java命令，不需要修改策略。

### Q: 最简单的运行方式是什么？
**A:** 直接在PowerShell中执行：
```powershell
java -cp "target/appointment-system-1.0.0.jar;lib/*" com.hospital.Main
```

### Q: 如何检查系统是否正常？
**A:** 运行环境检测：
```powershell
java SystemLauncher.java check
```

### Q: 移植到其他电脑需要什么？
**A:** 目标电脑需要：
- Java 8 或更高版本（你的是Java 17，完全兼容）
- 复制4个必需文件（1个jar + 3个lib）

### Q: 如何备份数据？
**A:** 复制 `hospital.db` 文件即可。

---

## 🎁 系统功能

1. **预约挂号** - 患者信息登记、选择科室医生、预约时间
2. **预约管理** - 查看、确认、取消、完成预约
3. **患者管理** - 增删改查、搜索功能
4. **医生管理** - 增删改查医生信息
5. **科室管理** - 增删改查科室信息

---

## ⚙️ 系统要求

- **Java版本：** Java 8 或更高版本（你的Java 17完全满足）
- **操作系统：** Windows 7/10/11、Linux、Mac
- **磁盘空间：** 约50MB

---

## 🔥 技术特点

✅ **无需安装数据库** - 使用SQLite文件数据库  
✅ **跨平台** - Windows/Linux/Mac都支持  
✅ **易于移植** - 复制几个文件即可  
✅ **数据易备份** - 单个db文件包含所有数据  
✅ **兼容性强** - 支持Java 8-21  

---

## 📞 需要帮助？

1. 查看 `启动说明-PowerShell版.txt`
2. 查看 `使用说明.txt`
3. 运行 `java SystemLauncher.java check` 检测环境

---

## 🎊 现在就开始使用吧！

在PowerShell中执行：

```powershell
java -cp "target/appointment-system-1.0.0.jar;lib/*" com.hospital.Main
```

或者：

```powershell
java SystemLauncher.java
```

祝使用愉快！🎉

---

**版本：** 1.0.0  
**更新日期：** 2024-10-29  
**技术栈：** Java 8 + SQLite + Swing  
**你的Java版本：** Java 17.0.12 ✓

