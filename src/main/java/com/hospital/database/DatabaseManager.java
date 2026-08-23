package com.hospital.database;

import java.sql.*;

/**
 * 数据库管理器 - 使用SQLite数据库
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private static final String DB_URL = "jdbc:sqlite:hospital.db";

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * 获取数据库连接
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * 初始化数据库表结构
     */
    public void initDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // 创建科室表
            stmt.execute("CREATE TABLE IF NOT EXISTS department (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL UNIQUE," +
                    "description TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            // 创建医生表
            stmt.execute("CREATE TABLE IF NOT EXISTS doctor (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "department_id INTEGER NOT NULL," +
                    "title TEXT," +
                    "phone TEXT," +
                    "specialty TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (department_id) REFERENCES department(id)" +
                    ")");

            // 创建患者表
            stmt.execute("CREATE TABLE IF NOT EXISTS patient (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "id_card TEXT UNIQUE," +
                    "phone TEXT NOT NULL," +
                    "gender TEXT," +
                    "age INTEGER," +
                    "address TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");

            // 创建预约表
            stmt.execute("CREATE TABLE IF NOT EXISTS appointment (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "patient_id INTEGER NOT NULL," +
                    "doctor_id INTEGER NOT NULL," +
                    "department_id INTEGER NOT NULL," +
                    "appointment_date TEXT NOT NULL," +
                    "appointment_time TEXT NOT NULL," +
                    "status TEXT DEFAULT 'pending'," +
                    "symptoms TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY (patient_id) REFERENCES patient(id)," +
                    "FOREIGN KEY (doctor_id) REFERENCES doctor(id)," +
                    "FOREIGN KEY (department_id) REFERENCES department(id)" +
                    ")");

            // 插入初始数据
            insertInitialData(conn);

            System.out.println("数据库初始化成功！");

        } catch (SQLException e) {
            System.err.println("数据库初始化失败: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 插入初始测试数据
     */
    private void insertInitialData(Connection conn) throws SQLException {
        // 检查是否已有数据
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM department")) {
            if (rs.next() && rs.getInt(1) > 0) {
                return; // 已有数据，不再插入
            }
        }

        // 插入科室数据
        String[] departments = {
                "('内科', '内科疾病诊疗')",
                "('外科', '外科手术及治疗')",
                "('儿科', '儿童疾病诊疗')",
                "('妇产科', '妇科及产科诊疗')",
                "('骨科', '骨骼疾病诊疗')",
                "('眼科', '眼部疾病诊疗')",
                "('耳鼻喉科', '耳鼻喉疾病诊疗')",
                "('皮肤科', '皮肤疾病诊疗')"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String dept : departments) {
                stmt.execute("INSERT INTO department (name, description) VALUES " + dept);
            }
        }

        // 插入医生数据
        String[] doctors = {
                "('张医生', 1, '主任医师', '13800138001', '心血管内科')",
                "('李医生', 1, '副主任医师', '13800138002', '消化内科')",
                "('王医生', 2, '主任医师', '13800138003', '普通外科')",
                "('赵医生', 3, '主治医师', '13800138004', '儿童保健')",
                "('刘医生', 4, '主任医师', '13800138005', '妇科')",
                "('陈医生', 5, '副主任医师', '13800138006', '骨折治疗')",
                "('杨医生', 6, '主治医师', '13800138007', '白内障')",
                "('周医生', 7, '主任医师', '13800138008', '鼻炎治疗')"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String doctor : doctors) {
                stmt.execute("INSERT INTO doctor (name, department_id, title, phone, specialty) VALUES " + doctor);
            }
        }

        System.out.println("初始数据插入成功！");
    }
}

