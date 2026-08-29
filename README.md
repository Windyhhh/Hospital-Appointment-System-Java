<div align="center">

# 医院预约系统 | Hospital-Appointment-System-Java

### Java Swing + SQLite hospital appointment & registration system.

Patient / doctor / department management with appointment booking — a self-contained desktop app.

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-8+-007396?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Swing](https://img.shields.io/badge/GUI-Swing-orange)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![SQLite](https://img.shields.io/badge/SQLite-3-003B57?logo=sqlite&logoColor=white)](https://www.sqlite.org/)

</div>

---

**Hospital-Appointment-System-Java** is a desktop hospital appointment & registration system built with **Java Swing** and **SQLite** — managing patients, doctors and departments with appointment booking, all in one self-contained app.

> [!NOTE]
> 中文项目：Java Swing + SQLite 医院预约挂号系统——患者、医生/科室管理、预约挂号。

---

## Quickstart

```bash
git clone https://github.com/Windyhhh/Hospital-Appointment-System-Java.git
cd Hospital-Appointment-System-Java

# Run with the provided scripts
./run.sh          # Linux / macOS
run.bat           # Windows
run.ps1           # PowerShell
```

Jars (`sqlite-jdbc`, `slf4j`) are bundled in `lib/`; the DB is `hospital.db`.

---

## Features

- **Appointment booking** — register and manage appointments.
- **Patient / doctor / department** — full CRUD management.
- **Self-contained** — bundled deps, no external server needed.

---

## Project Structure

```
Hospital-Appointment-System-Java/
├── src/main/java/com/hospital/
│   ├── Main.java / SystemLauncher.java
│   ├── dao/               # AppointmentDAO, DepartmentDAO, DoctorDAO, PatientDAO
│   └── database/DatabaseManager.java
├── lib/                   # sqlite-jdbc, slf4j jars
├── pom.xml
└── run.sh / run.bat / run.ps1
```

---

## License

MIT — free to use, modify and distribute.
