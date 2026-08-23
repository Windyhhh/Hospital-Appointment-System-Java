# 🏥 Hospital Appointment System (Java) | Java 医院预约挂号管理系统

> **Complete hospital appointment management system built with Java Swing and SQLite. Features patient management, doctor scheduling, department management, appointment booking, and cancellation. Cross-platform with Maven build.**
>
> 基于 Java Swing 和 SQLite 的完整医院预约挂号管理系统。功能包括患者管理、医生排班、科室管理、预约挂号和取消。跨平台，Maven 构建。

---

## 🌟 Features | 核心特性

- **Patient Management** — Register, edit, delete patient records
- **Doctor Management** — Manage doctor information and specialties
- **Department Management** — Organize hospital departments
- **Appointment Booking** — Book appointments with specific doctors
- **Appointment Cancellation** — Cancel existing appointments
- **Appointment Management** — View, filter, manage all appointments
- **SQLite Database** — Embedded database, no external server needed
- **Java Swing UI** — Cross-platform desktop GUI
- **Maven Build** — Standard Maven project structure
- **Cross-Platform** — Windows (.bat), Linux/Mac (.sh), PowerShell (.ps1) scripts

---

## 📁 Project Structure | 项目结构

```
Hospital-Appointment-System-Java/
├── src/main/java/com/hospital/
│   ├── Main.java                          # Main entry point
│   ├── SystemLauncher.java                # System launcher
│   ├── model/
│   │   ├── Patient.java                   # Patient entity
│   │   ├── Doctor.java                    # Doctor entity
│   │   ├── Department.java                # Department entity
│   │   └── Appointment.java               # Appointment entity
│   ├── dao/
│   │   ├── PatientDAO.java                # Patient data access
│   │   ├── DoctorDAO.java                 # Doctor data access
│   │   ├── DepartmentDAO.java             # Department data access
│   │   └── AppointmentDAO.java            # Appointment data access
│   ├── service/
│   │   └── AppointmentService.java        # Business logic
│   ├── database/
│   │   └── DatabaseManager.java           # SQLite database manager
│   └── ui/
│       ├── AppointmentPanel.java          # Appointment booking UI
│       ├── AppointmentManagePanel.java    # Appointment management UI
│       ├── DepartmentPanel.java           # Department management UI
│       └── ... (more UI panels)
├── lib/
│   ├── sqlite-jdbc-3.42.0.0.jar           # SQLite JDBC driver
│   ├── slf4j-api-2.0.7.jar                # SLF4J API
│   └── slf4j-simple-2.0.7.jar             # SLF4J simple binding
├── hospital.db                             # SQLite database file
├── pom.xml                                 # Maven configuration
├── dependency-reduced-pom.xml
├── compile.bat                             # Windows compile script
├── compile-simple.bat                      # Simple compile script
├── compile.ps1                             # PowerShell compile script
├── run.bat                                 # Windows run script
├── run.sh                                  # Linux/Mac run script
├── run.ps1                                 # PowerShell run script
├── README.md
├── README-PowerShell.md
└── .gitignore
```

---

## 🚀 Quick Start | 快速开始

### Option 1: Maven | 使用 Maven

```bash
# Compile
mvn clean package

# Run
java -jar target/hospital-appointment-system.jar
```

### Option 2: Scripts | 使用脚本

```bash
# Windows
compile.bat
run.bat

# Linux/Mac
chmod +x run.sh
./run.sh

# PowerShell
.\compile.ps1
.\run.ps1
```

### Option 3: Manual | 手动编译

```bash
# Compile
javac -cp "lib/*" -d bin src/main/java/com/hospital/**/*.java

# Run
java -cp "bin:lib/*" com.hospital.Main
```

---

## 🗄️ Database Schema | 数据库架构

### patients | 患者表

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER PK | Patient ID |
| name | TEXT | Patient name |
| gender | TEXT | Gender |
| age | INTEGER | Age |
| phone | TEXT | Phone number |
| id_card | TEXT | ID card number |

### doctors | 医生表

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER PK | Doctor ID |
| name | TEXT | Doctor name |
| department_id | INTEGER FK | Department ID |
| title | TEXT | Professional title |
| specialty | TEXT | Specialty |
| schedule | TEXT | Working schedule |

### departments | 科室表

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER PK | Department ID |
| name | TEXT | Department name |
| description | TEXT | Description |
| location | TEXT | Location |

### appointments | 预约表

| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER PK | Appointment ID |
| patient_id | INTEGER FK | Patient ID |
| doctor_id | INTEGER FK | Doctor ID |
| department_id | INTEGER FK | Department ID |
| appointment_date | DATE | Appointment date |
| appointment_time | TIME | Appointment time |
| status | TEXT | Status (booked/cancelled/completed) |
| create_time | TIMESTAMP | Creation time |

---

## 🔧 Tech Stack | 技术栈

| Component | Technology |
|-----------|------------|
| **Language** | Java 8+ |
| **UI Framework** | Java Swing |
| **Database** | SQLite (embedded) |
| **JDBC Driver** | sqlite-jdbc 3.42.0.0 |
| **Logging** | SLF4J + slf4j-simple |
| **Build Tool** | Maven |
| **Architecture** | MVC (Model-View-Controller) + DAO pattern |

---

## 📚 References | 参考文献

1. **Oracle.** (2024). *Java Swing Tutorial.*
2. **SQLite.** (2024). *SQLite Documentation.* https://www.sqlite.org/docs.html
3. **Apache Maven.** (2024). *Maven Getting Started Guide.*
4. **Fowler, M.** (2002). *Patterns of Enterprise Application Architecture.* (DAO Pattern)

---

## 📄 License | 许可证

MIT License.

---

<div align="center">

**Built with 🏥 for healthcare management systems**

[GitHub](https://github.com/Windyhhh/Hospital-Appointment-System-Java)

</div>
