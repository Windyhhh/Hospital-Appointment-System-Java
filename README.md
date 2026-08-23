# 🏥 Java 医院预约挂号系统 | Hospital Appointment System (Java)

> **基于 SSM + MySQL 的医院在线预约挂号系统——患者在线挂号、医生排班管理、科室分诊、缴费支付，完整的医院信息化解决方案。**
>
> *Hospital online appointment system based on SSM + MySQL — patient online registration, doctor scheduling, department triage, payment, complete hospital informatization solution.*

---

## ⭐ 核心卖点 | Why Star This

| 卖点 | Feature | 一句话 |
|------|---------|--------|
| 🏥 **完整业务流程** | Full Workflow | 挂号→就诊→缴费→取药全流程覆盖 |
| 👨‍⚕️ **多角色管理** | Multi-Role | 患者、医生、护士、管理员多角色权限 |
| 📅 **智能排班** | Smart Scheduling | 医生排班管理，号源自动分配 |
| 🏥 **科室分诊** | Department Triage | 多级科室分类，智能推荐科室 |
| 💳 **在线支付** | Online Payment | 模拟支付流程，支持多种支付方式 |

---

## 🏆 技术栈 | Tech Stack

![Java](https://img.shields.io/badge/Java-8+-blue?logo=openjdk)
![Spring](https://img.shields.io/badge/Spring-5.0+-green?logo=spring)
![SpringMVC](https://img.shields.io/badge/SpringMVC-5.0+-green?logo=spring)
![MyBatis](https://img.shields.io/badge/MyBatis-3.5+-red?logo=mybatis)
![MySQL](https://img.shields.io/badge/MySQL-5.7+-blue?logo=mysql)
![Bootstrap](https://img.shields.io/badge/Bootstrap-4.0+-purple?logo=bootstrap)
![Maven](https://img.shields.io/badge/Maven-3.6+-orange?logo=apachemaven)

---

## 📊 系统模块 | System Modules

| 模块 | 功能 | 角色 |
|------|------|------|
| 👤 用户管理 | 注册、登录、个人信息、密码修改 | 所有用户 |
| 🏥 科室管理 | 科室分类、科室介绍、科室查询 | 管理员 |
| 👨‍⚕️ 医生管理 | 医生信息、职称、擅长领域 | 管理员/医生 |
| 📅 排班管理 | 医生排班、号源管理、停诊处理 | 管理员/医生 |
| 📝 预约挂号 | 科室选择、医生选择、时间选择、挂号 | 患者 |
| 🏥 就诊管理 | 叫号、就诊记录、处方开具 | 医生/护士 |
| 💊 药品管理 | 药品信息、库存、价格 | 管理员/药师 |
| 💳 缴费管理 | 挂号费、诊疗费、药费缴纳 | 患者/收费员 |
| 📊 统计报表 | 挂号量、就诊量、收入统计 | 管理员 |

---

## 🚀 快速开始 | Quick Start

```bash
# 1. 克隆项目
git clone https://github.com/Windyhhh/Hospital-Appointment-System-Java.git
cd Hospital-Appointment-System-Java

# 2. 导入数据库
mysql -u root -p < sql/hospital.sql

# 3. 修改配置
# 修改 src/main/resources/jdbc.properties 中的数据库连接信息
# 修改 src/main/resources/db.properties

# 4. 编译打包
mvn clean package -DskipTests

# 5. 部署到 Tomcat
cp target/hospital.war $TOMCAT_HOME/webapps/
$TOMCAT_HOME/bin/startup.sh

# 6. 访问系统
# 前台: http://localhost:8080/hospital
# 后台: http://localhost:8080/hospital/admin

# 测试账号:
# 患者: patient / 123456
# 医生: doctor / 123456
# 管理员: admin / 123456
```

---

## 📂 项目结构 | Project Structure

```
Hospital-Appointment-System-Java/
├── src/main/
│   ├── java/com/hospital/
│   │   ├── controller/        # 控制器层
│   │   │   ├── UserController.java
│   │   │   ├── DepartmentController.java
│   │   │   ├── DoctorController.java
│   │   │   ├── ScheduleController.java
│   │   │   ├── AppointmentController.java
│   │   │   ├── PrescriptionController.java
│   │   │   ├── PaymentController.java
│   │   │   └── AdminController.java
│   │   ├── service/           # 业务层
│   │   │   ├── UserService.java
│   │   │   ├── DepartmentService.java
│   │   │   ├── DoctorService.java
│   │   │   ├── ScheduleService.java
│   │   │   ├── AppointmentService.java
│   │   │   ├── PrescriptionService.java
│   │   │   └── PaymentService.java
│   │   ├── mapper/            # 持久层 (MyBatis)
│   │   │   ├── UserMapper.java
│   │   │   ├── DepartmentMapper.java
│   │   │   ├── DoctorMapper.java
│   │   │   ├── ScheduleMapper.java
│   │   │   ├── AppointmentMapper.java
│   │   │   ├── PrescriptionMapper.java
│   │   │   └── PaymentMapper.java
│   │   ├── pojo/              # 实体类
│   │   │   ├── User.java
│   │   │   ├── Department.java
│   │   │   ├── Doctor.java
│   │   │   ├── Schedule.java
│   │   │   ├── Appointment.java
│   │   │   ├── Prescription.java
│   │   │   ├── Medicine.java
│   │   │   └── Payment.java
│   │   ├── util/              # 工具类
│   │   │   ├── MD5Util.java
│   │   │   ├── DateUtil.java
│   │   │   ├── PageUtil.java
│   │   │   └── MailUtil.java
│   │   ├── interceptor/       # 拦截器
│   │   │   ├── LoginInterceptor.java
│   │   │   └── RoleInterceptor.java
│   │   └── exception/         # 异常处理
│   │       └── GlobalExceptionHandler.java
│   ├── resources/
│   │   ├── spring/            # Spring 配置
│   │   │   ├── applicationContext.xml
│   │   │   ├── spring-mvc.xml
│   │   │   └── mybatis-config.xml
│   │   ├── mapper/            # MyBatis 映射文件
│   │   │   ├── UserMapper.xml
│   │   │   ├── DepartmentMapper.xml
│   │   │   └── ...
│   │   ├── jdbc.properties    # 数据库配置
│   │   ├── log4j.properties   # 日志配置
│   │   └── validation.properties # 验证配置
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── web.xml
│       │   ├── applicationContext.xml
│       │   └── spring-mvc.xml
│       ├── static/
│       │   ├── css/
│       │   ├── js/
│       │   ├── images/
│       │   └── plugins/       # Bootstrap, jQuery, etc.
│       └── pages/
│           ├── front/         # 前台页面
│           │   ├── index.jsp
│           │   ├── login.jsp
│           │   ├── register.jsp
│           │   ├── department.jsp
│           │   ├── doctor.jsp
│           │   ├── appointment.jsp
│           │   └── user_center.jsp
│           ├── doctor/        # 医生端页面
│           │   ├── dashboard.jsp
│           │   ├── schedule.jsp
│           │   ├── patient_list.jsp
│           │   └── prescription.jsp
│           └── admin/         # 管理员页面
│               ├── dashboard.jsp
│               ├── user_manage.jsp
│               ├── department_manage.jsp
│               ├── doctor_manage.jsp
│               ├── schedule_manage.jsp
│               └── statistics.jsp
├── sql/
│   └── hospital.sql           # 数据库脚本
├── pom.xml                    # Maven 配置
└── README.md
```

---

## 🔬 核心功能 | Core Features

### 预约挂号流程 | Appointment Workflow

```
患者端:
  1. 选择科室 (内科/外科/儿科/...)
  2. 选择医生 (查看医生信息、擅长领域、评分)
  3. 选择日期和时段 (上午/下午/晚上)
  4. 确认挂号信息 (科室、医生、时间、费用)
  5. 支付挂号费
  6. 挂号成功，生成挂号单

医生端:
  1. 查看当日挂号列表
  2. 叫号 (按挂号顺序)
  3. 问诊，填写就诊记录
  4. 开具处方 (药品、用量、天数)
  5. 结束就诊

患者端:
  1. 查看就诊记录和处方
  2. 缴费 (药费、检查费)
  3. 取药
  4. 评价医生
```

### 医生排班 | Doctor Scheduling

```
排班规则:
  - 每个医生每周固定排班 (周一上午、周二下午...)
  - 每个时段号源数量有限 (如上午 30 号)
  - 支持临时停诊 (提前通知已挂号患者)
  - 支持加号 (医生手动增加号源)

数据库设计:
  schedule表: id, doctor_id, date, time_slot, total_count, remaining_count, status
  
  time_slot: 1=上午, 2=下午, 3=晚上
  status: 1=正常, 0=停诊

号源分配:
  - 患者挂号时，remaining_count - 1
  - 患者取消挂号时，remaining_count + 1
  - remaining_count = 0 时，该时段不可挂号
```

### 权限控制 | Access Control

```
角色权限矩阵:

功能              患者  医生  护士  收费员  管理员
─────────────────────────────────────────────────
个人信息管理       ✅    ✅    ✅     ✅      ✅
科室查询           ✅    ✅    ✅     ✅      ✅
医生查询           ✅    ✅    ✅     ✅      ✅
预约挂号           ✅    ❌    ❌     ❌      ✅
就诊记录查看       ✅    ✅    ✅     ❌      ✅
处方开具           ❌    ✅    ❌     ❌      ❌
叫号管理           ❌    ✅    ✅     ❌      ❌
缴费收费           ❌    ❌    ❌     ✅      ✅
药品管理           ❌    ❌    ✅     ❌      ✅
排班管理           ❌    ✅    ❌     ❌      ✅
用户管理           ❌    ❌    ❌     ❌      ✅
统计报表           ❌    ❌    ❌     ❌      ✅

实现方式:
  - Spring MVC 拦截器 (LoginInterceptor + RoleInterceptor)
  - 基于角色的访问控制 (RBAC)
  - 前端页面按钮级权限控制
```

### 数据库设计 | Database Design

#### 核心表结构

```sql
-- 用户表
CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    real_name VARCHAR(50),
    gender TINYINT,
    phone VARCHAR(20),
    email VARCHAR(100),
    role TINYINT NOT NULL COMMENT '1=患者, 2=医生, 3=护士, 4=收费员, 5=管理员',
    status TINYINT DEFAULT 1,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 科室表
CREATE TABLE department (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    parent_id INT DEFAULT 0 COMMENT '父科室ID, 0为一级科室',
    description TEXT,
    location VARCHAR(100),
    sort_order INT DEFAULT 0,
    status TINYINT DEFAULT 1
);

-- 医生表
CREATE TABLE doctor (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    department_id INT NOT NULL,
    title VARCHAR(50) COMMENT '职称: 主任医师/副主任医师/主治医师/住院医师',
    specialty VARCHAR(200) COMMENT '擅长领域',
    introduction TEXT,
    avatar VARCHAR(255),
    rating DECIMAL(2,1) DEFAULT 5.0 COMMENT '评分',
    consultation_fee DECIMAL(10,2) DEFAULT 0 COMMENT '挂号费',
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (department_id) REFERENCES department(id)
);

-- 排班表
CREATE TABLE schedule (
    id INT PRIMARY KEY AUTO_INCREMENT,
    doctor_id INT NOT NULL,
    schedule_date DATE NOT NULL,
    time_slot TINYINT NOT NULL COMMENT '1=上午, 2=下午, 3=晚上',
    total_count INT DEFAULT 30,
    remaining_count INT DEFAULT 30,
    status TINYINT DEFAULT 1 COMMENT '1=正常, 0=停诊',
    FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    UNIQUE KEY uk_doctor_date_slot (doctor_id, schedule_date, time_slot)
);

-- 挂号表
CREATE TABLE appointment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    schedule_id INT NOT NULL,
    appointment_no INT NOT NULL COMMENT '挂号序号',
    status TINYINT DEFAULT 1 COMMENT '1=已挂号, 2=已就诊, 3=已取消, 4=已过期',
    fee DECIMAL(10,2) NOT NULL,
    pay_status TINYINT DEFAULT 0 COMMENT '0=未支付, 1=已支付, 2=已退款',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES user(id),
    FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    FOREIGN KEY (schedule_id) REFERENCES schedule(id)
);

-- 就诊记录表
CREATE TABLE medical_record (
    id INT PRIMARY KEY AUTO_INCREMENT,
    appointment_id INT NOT NULL,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    chief_complaint TEXT COMMENT '主诉',
    present_illness TEXT COMMENT '现病史',
    physical_examination TEXT COMMENT '体格检查',
    diagnosis TEXT COMMENT '诊断',
    advice TEXT COMMENT '医嘱',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointment(id)
);

-- 处方表
CREATE TABLE prescription (
    id INT PRIMARY KEY AUTO_INCREMENT,
    medical_record_id INT NOT NULL,
    doctor_id INT NOT NULL,
    patient_id INT NOT NULL,
    total_amount DECIMAL(10,2) DEFAULT 0,
    status TINYINT DEFAULT 1 COMMENT '1=未缴费, 2=已缴费, 3=已取药, 4=已作废',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medical_record_id) REFERENCES medical_record(id)
);

-- 处方明细表
CREATE TABLE prescription_item (
    id INT PRIMARY KEY AUTO_INCREMENT,
    prescription_id INT NOT NULL,
    medicine_id INT NOT NULL,
    quantity INT NOT NULL,
    dosage VARCHAR(100) COMMENT '用量',
    frequency VARCHAR(50) COMMENT '频次',
    days INT COMMENT '天数',
    price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (prescription_id) REFERENCES prescription(id),
    FOREIGN KEY (medicine_id) REFERENCES medicine(id)
);

-- 药品表
CREATE TABLE medicine (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    specification VARCHAR(100) COMMENT '规格',
    manufacturer VARCHAR(100) COMMENT '生产厂家',
    unit VARCHAR(20) COMMENT '单位',
    price DECIMAL(10,2) NOT NULL,
    stock INT DEFAULT 0,
    category VARCHAR(50) COMMENT '分类: 处方药/非处方药',
    status TINYINT DEFAULT 1
);

-- 缴费表
CREATE TABLE payment (
    id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    appointment_id INT,
    prescription_id INT,
    amount DECIMAL(10,2) NOT NULL,
    payment_type TINYINT COMMENT '1=挂号费, 2=诊疗费, 3=药费, 4=检查费',
    payment_method TINYINT COMMENT '1=现金, 2=微信, 3=支付宝, 4=医保',
    status TINYINT DEFAULT 1 COMMENT '1=已支付, 2=已退款',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES user(id)
);
```

---

## 📊 系统特色 | System Features

### 智能科室推荐 | Smart Department Recommendation

```
根据患者症状描述，推荐相关科室:

1. 关键词匹配: 症状关键词与科室标签匹配
2. 历史数据: 基于历史就诊数据的科室推荐
3. 多级分诊: 一级科室 → 二级科室 → 医生

示例:
  症状: "头痛、发烧、咳嗽" → 推荐: 呼吸内科 / 全科
  症状: "胃痛、反酸" → 推荐: 消化内科
  症状: "皮肤瘙痒、红疹" → 推荐: 皮肤科
```

### 医生评价系统 | Doctor Rating System

```
患者就诊后可对医生进行评价:

评价维度:
  - 医术水平 (1-5星)
  - 服务态度 (1-5星)
  - 就诊环境 (1-5星)
  - 等待时间 (1-5星)
  - 文字评价

医生评分:
  - 综合评分 = 各维度加权平均
  - 评价数量影响排名
  - 新评价权重更高 (时间衰减)

展示:
  - 医生列表页显示评分和评价数
  - 医生详情页显示评分分布和评价列表
  - 科室排名按平均评分排序
```

### 统计报表 | Statistics & Reports

```
管理员后台统计报表:

1. 挂号统计:
   - 日/周/月挂号量趋势
   - 各科室挂号量占比
   - 各医生挂号量排名
   - 挂号时段分布

2. 就诊统计:
   - 就诊率 (就诊数/挂号数)
   - 取消率 (取消数/挂号数)
   - 平均就诊时长
   - 科室就诊量对比

3. 收入统计:
   - 日/周/月收入趋势
   - 收入构成 (挂号费/药费/检查费)
   - 各科室收入排名
   - 支付方式分布

4. 药品统计:
   - 药品销量排名
   - 库存预警
   - 药品收入占比
   - 处方药品分布
```

---

## 🎯 应用场景 | Use Cases

- 🏥 **小型医院/诊所**：社区医院、私人诊所的信息化管理
- 🏫 **教学实践**：高校软件工程/医学信息学的课程设计和毕业设计
- 🎓 **SSM 框架学习**：Spring + SpringMVC + MyBatis 框架的学习案例
- 💼 **求职项目**：Java 后端开发的项目经验展示
- 🏢 **企业内训**：企业新员工的 Java Web 开发培训
- 🔧 **二次开发**：基于本系统进行功能扩展和定制化开发

---

## 📚 技术要点 | Technical Highlights

- **SSM 框架整合**：Spring + SpringMVC + MyBatis 经典整合
- **RESTful API**：前后端分离的 API 设计
- **RBAC 权限控制**：基于角色的访问控制
- **分页查询**：MyBatis 分页插件
- **文件上传**：头像、检查报告等文件上传
- **邮件通知**：挂号成功、停诊通知等邮件发送
- **数据校验**：前端 + 后端双重数据校验
- **异常处理**：全局异常处理器
- **日志记录**：Log4j 日志框架
- **连接池**：Druid 数据库连接池
- **缓存**：Redis 缓存热点数据

---

## 📄 License

MIT License — 自由使用、修改和分发。

---

> 💡 **SSM + MySQL 的医院预约挂号完整系统，Star ⭐ 支持开源医疗信息化！**
