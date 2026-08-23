package com.hospital;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * 系统启动器 - Java版本
 * 用于检测环境、编译和运行医院预约挂号系统
 */
public class SystemLauncher {
    
    private static final String JAR_FILE = "target/appointment-system-1.0.0.jar";
    private static final String MAIN_CLASS = "com.hospital.Main";
    private static final String[] REQUIRED_LIBS = {
        "lib/sqlite-jdbc-3.42.0.0.jar",
        "lib/slf4j-api-2.0.7.jar",
        "lib/slf4j-simple-2.0.7.jar"
    };
    
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  医院预约挂号系统 - 启动器");
        System.out.println("========================================");
        System.out.println();
        
        if (args.length > 0) {
            String command = args[0].toLowerCase();
            switch (command) {
                case "check":
                case "检测":
                    checkEnvironment();
                    break;
                case "run":
                case "运行":
                    runSystem();
                    break;
                case "compile":
                case "编译":
                    compileProject();
                    break;
                case "package":
                case "打包":
                    packageProject();
                    break;
                default:
                    showHelp();
            }
        } else {
            showMenu();
        }
    }
    
    private static void showMenu() {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n请选择操作：");
            System.out.println("1. 检测环境");
            System.out.println("2. 运行系统");
            System.out.println("3. 编译项目");
            System.out.println("4. 打包移植文件");
            System.out.println("5. 帮助");
            System.out.println("0. 退出");
            System.out.print("\n请输入选项 (0-5): ");
            
            String choice = scanner.nextLine().trim();
            System.out.println();
            
            switch (choice) {
                case "1":
                    checkEnvironment();
                    break;
                case "2":
                    runSystem();
                    return; // 运行系统后退出菜单
                case "3":
                    compileProject();
                    break;
                case "4":
                    packageProject();
                    break;
                case "5":
                    showHelp();
                    break;
                case "0":
                    System.out.println("再见！");
                    return;
                default:
                    System.out.println("无效选项，请重新选择");
            }
        }
    }
    
    private static void checkEnvironment() {
        System.out.println("========================================");
        System.out.println("  环境检测");
        System.out.println("========================================");
        System.out.println();
        
        int passCount = 0;
        int failCount = 0;
        
        // 检测Java版本
        System.out.println("[1/4] Java环境");
        System.out.println("----------------------------------------");
        String javaVersion = System.getProperty("java.version");
        String javaHome = System.getProperty("java.home");
        System.out.println("✓ Java已安装");
        System.out.println("  版本: " + javaVersion);
        System.out.println("  路径: " + javaHome);
        passCount++;
        System.out.println();
        
        // 检测Maven
        System.out.println("[2/4] Maven环境（可选）");
        System.out.println("----------------------------------------");
        if (commandExists("mvn")) {
            System.out.println("✓ Maven已安装");
            passCount++;
        } else {
            System.out.println("○ Maven未安装（可选）");
            System.out.println("  说明: 可以使用javac直接编译");
        }
        System.out.println();
        
        // 检测项目文件
        System.out.println("[3/4] 项目文件");
        System.out.println("----------------------------------------");
        boolean projectOk = true;
        
        if (Files.exists(Paths.get("pom.xml"))) {
            System.out.println("✓ pom.xml");
        } else {
            System.out.println("✗ pom.xml 缺失");
            projectOk = false;
        }
        
        if (Files.exists(Paths.get("src/main/java/com/hospital/Main.java"))) {
            System.out.println("✓ 源代码文件");
        } else {
            System.out.println("✗ 源代码文件缺失");
            projectOk = false;
        }
        
        if (projectOk) {
            passCount++;
        } else {
            failCount++;
        }
        System.out.println();
        
        // 检测编译状态
        System.out.println("[4/4] 编译状态");
        System.out.println("----------------------------------------");
        if (Files.exists(Paths.get(JAR_FILE))) {
            System.out.println("✓ JAR文件已编译");
            try {
                long size = Files.size(Paths.get(JAR_FILE));
                System.out.println("  大小: " + size + " 字节");
            } catch (IOException e) {
                // ignore
            }
            passCount++;
        } else {
            System.out.println("✗ JAR文件未编译");
            System.out.println("  建议: 选择菜单中的'编译项目'");
            failCount++;
        }
        
        // 检测依赖库
        boolean allLibsExist = true;
        for (String lib : REQUIRED_LIBS) {
            if (!Files.exists(Paths.get(lib))) {
                allLibsExist = false;
                break;
            }
        }
        
        if (allLibsExist) {
            System.out.println("✓ 依赖库文件");
        } else {
            System.out.println("✗ 依赖库文件缺失");
            System.out.println("  建议: 选择菜单中的'编译项目'");
        }
        System.out.println();
        
        // 总结
        System.out.println("========================================");
        System.out.println("  检测结果");
        System.out.println("========================================");
        System.out.println("通过项: " + passCount);
        System.out.println("失败项: " + failCount);
        System.out.println();
        
        if (failCount == 0) {
            System.out.println("✓ 环境检测通过！系统可以正常运行");
        } else {
            System.out.println("⚠ 发现 " + failCount + " 个问题");
            System.out.println("建议先编译项目");
        }
    }
    
    private static void runSystem() {
        System.out.println("========================================");
        System.out.println("  启动系统");
        System.out.println("========================================");
        System.out.println();
        
        // 检查JAR文件是否存在
        if (!Files.exists(Paths.get(JAR_FILE))) {
            System.out.println("✗ 错误：未找到编译好的JAR文件");
            System.out.println("请先编译项目（选择菜单中的'编译项目'）");
            return;
        }
        
        // 检查依赖库
        for (String lib : REQUIRED_LIBS) {
            if (!Files.exists(Paths.get(lib))) {
                System.out.println("✗ 错误：缺少依赖库 " + lib);
                System.out.println("请先编译项目");
                return;
            }
        }
        
        System.out.println("正在启动医院预约挂号系统...");
        System.out.println();
        
        try {
            // 构建classpath
            String separator = System.getProperty("os.name").toLowerCase().contains("win") ? ";" : ":";
            StringBuilder classpath = new StringBuilder(JAR_FILE);
            for (String lib : REQUIRED_LIBS) {
                classpath.append(separator).append(lib);
            }
            
            // 启动系统
            ProcessBuilder pb = new ProcessBuilder(
                "java",
                "-cp",
                classpath.toString(),
                MAIN_CLASS
            );
            pb.inheritIO();
            Process process = pb.start();
            
            // 等待进程结束
            int exitCode = process.waitFor();
            
            if (exitCode != 0) {
                System.out.println("\n程序异常退出，退出码: " + exitCode);
            }
            
        } catch (Exception e) {
            System.out.println("✗ 启动失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void compileProject() {
        System.out.println("========================================");
        System.out.println("  编译项目");
        System.out.println("========================================");
        System.out.println();
        
        // 先尝试使用Maven
        if (commandExists("mvn")) {
            System.out.println("检测到Maven，使用Maven编译...");
            System.out.println();
            
            try {
                ProcessBuilder pb = new ProcessBuilder("mvn", "clean", "package", "-DskipTests");
                pb.inheritIO();
                Process process = pb.start();
                int exitCode = process.waitFor();
                
                if (exitCode == 0) {
                    System.out.println("\n✓ 编译成功！");
                } else {
                    System.out.println("\n✗ 编译失败");
                }
                return;
            } catch (Exception e) {
                System.out.println("Maven编译失败: " + e.getMessage());
            }
        }
        
        // 使用javac编译
        System.out.println("使用javac编译...");
        System.out.println("此功能需要先下载依赖库");
        System.out.println("建议安装Maven或手动下载依赖库到lib目录");
    }
    
    private static void packageProject() {
        System.out.println("========================================");
        System.out.println("  打包移植文件");
        System.out.println("========================================");
        System.out.println();
        
        if (!Files.exists(Paths.get(JAR_FILE))) {
            System.out.println("✗ 错误：未找到编译好的JAR文件");
            System.out.println("请先编译项目");
            return;
        }
        
        String packageDir = "hospital-system-portable";
        
        try {
            // 创建打包目录
            Files.createDirectories(Paths.get(packageDir, "target"));
            Files.createDirectories(Paths.get(packageDir, "lib"));
            
            // 复制文件
            System.out.println("正在复制文件...");
            Files.copy(Paths.get(JAR_FILE), 
                      Paths.get(packageDir, JAR_FILE), 
                      StandardCopyOption.REPLACE_EXISTING);
            
            for (String lib : REQUIRED_LIBS) {
                if (Files.exists(Paths.get(lib))) {
                    Files.copy(Paths.get(lib),
                              Paths.get(packageDir, lib),
                              StandardCopyOption.REPLACE_EXISTING);
                }
            }
            
            // 复制文档
            copyIfExists("使用说明.txt", packageDir);
            copyIfExists("README.md", packageDir);
            
            // 创建启动脚本
            createRunScript(packageDir);
            
            System.out.println("\n✓ 打包完成！");
            System.out.println("打包目录: " + packageDir);
            System.out.println("\n将此文件夹复制到目标电脑即可运行");
            
        } catch (IOException e) {
            System.out.println("✗ 打包失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createRunScript(String dir) throws IOException {
        // Windows脚本
        String runBat = "@echo off\r\n" +
                       "chcp 65001 >nul\r\n" +
                       "echo 正在启动医院预约挂号系统...\r\n" +
                       "echo.\r\n" +
                       "java -cp \"target\\appointment-system-1.0.0.jar;lib\\*\" com.hospital.Main\r\n" +
                       "if %ERRORLEVEL% NEQ 0 (\r\n" +
                       "    echo.\r\n" +
                       "    echo 程序运行出错！\r\n" +
                       "    pause\r\n" +
                       ")\r\n";
        Files.write(Paths.get(dir, "run.bat"), runBat.getBytes("UTF-8"));
        
        // Linux/Mac脚本
        String runSh = "#!/bin/bash\n" +
                      "echo \"正在启动医院预约挂号系统...\"\n" +
                      "echo \"\"\n" +
                      "java -cp \"target/appointment-system-1.0.0.jar:lib/*\" com.hospital.Main\n";
        Files.write(Paths.get(dir, "run.sh"), runSh.getBytes("UTF-8"));
        
        System.out.println("✓ 已创建启动脚本");
    }
    
    private static void copyIfExists(String file, String targetDir) {
        try {
            if (Files.exists(Paths.get(file))) {
                Files.copy(Paths.get(file),
                          Paths.get(targetDir, file),
                          StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            // ignore
        }
    }
    
    private static boolean commandExists(String command) {
        try {
            ProcessBuilder pb = new ProcessBuilder(command, "--version");
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.PIPE);
            Process process = pb.start();
            process.waitFor();
            return process.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static void showHelp() {
        System.out.println("========================================");
        System.out.println("  帮助信息");
        System.out.println("========================================");
        System.out.println();
        System.out.println("使用方法：");
        System.out.println("  java SystemLauncher.java           - 显示菜单");
        System.out.println("  java SystemLauncher.java check     - 检测环境");
        System.out.println("  java SystemLauncher.java run       - 运行系统");
        System.out.println("  java SystemLauncher.java compile   - 编译项目");
        System.out.println("  java SystemLauncher.java package   - 打包移植文件");
        System.out.println();
        System.out.println("快速开始：");
        System.out.println("1. java SystemLauncher.java check    - 检查环境");
        System.out.println("2. java SystemLauncher.java run      - 运行系统");
        System.out.println();
    }
}

