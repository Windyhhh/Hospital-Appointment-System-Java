package com.hospital.model;

/**
 * 预约实体类
 */
public class Appointment {
    private Integer id;
    private Integer patientId;
    private String patientName;
    private Integer doctorId;
    private String doctorName;
    private Integer departmentId;
    private String departmentName;
    private String appointmentDate;
    private String appointmentTime;
    private String status; // pending, confirmed, cancelled, completed
    private String symptoms;
    private String createdAt;

    public Appointment() {
    }

    public Appointment(Integer id, Integer patientId, Integer doctorId, Integer departmentId,
                       String appointmentDate, String appointmentTime, String status, String symptoms) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.departmentId = departmentId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = status;
        this.symptoms = symptoms;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStatusText() {
        switch (status) {
            case "pending":
                return "待确认";
            case "confirmed":
                return "已确认";
            case "cancelled":
                return "已取消";
            case "completed":
                return "已完成";
            default:
                return status;
        }
    }
}

