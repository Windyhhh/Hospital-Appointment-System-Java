package com.hospital.service;

import com.hospital.dao.*;
import com.hospital.model.*;

import java.util.List;

/**
 * 预约服务类 - 业务逻辑层
 */
public class AppointmentService {
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    private PatientDAO patientDAO = new PatientDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private DepartmentDAO departmentDAO = new DepartmentDAO();

    /**
     * 获取所有预约
     */
    public List<Appointment> getAllAppointments() {
        return appointmentDAO.findAll();
    }

    /**
     * 根据患者ID获取预约
     */
    public List<Appointment> getAppointmentsByPatient(Integer patientId) {
        return appointmentDAO.findByPatientId(patientId);
    }

    /**
     * 根据医生ID获取预约
     */
    public List<Appointment> getAppointmentsByDoctor(Integer doctorId) {
        return appointmentDAO.findByDoctorId(doctorId);
    }

    /**
     * 根据日期获取预约
     */
    public List<Appointment> getAppointmentsByDate(String date) {
        return appointmentDAO.findByDate(date);
    }

    /**
     * 创建预约
     */
    public boolean createAppointment(Appointment appointment) {
        // 验证患者是否存在
        Patient patient = patientDAO.findById(appointment.getPatientId());
        if (patient == null) {
            return false;
        }

        // 验证医生是否存在
        Doctor doctor = doctorDAO.findById(appointment.getDoctorId());
        if (doctor == null) {
            return false;
        }

        // 验证科室是否存在
        Department department = departmentDAO.findById(appointment.getDepartmentId());
        if (department == null) {
            return false;
        }

        return appointmentDAO.insert(appointment);
    }

    /**
     * 确认预约
     */
    public boolean confirmAppointment(Integer appointmentId) {
        return appointmentDAO.updateStatus(appointmentId, "confirmed");
    }

    /**
     * 取消预约
     */
    public boolean cancelAppointment(Integer appointmentId) {
        return appointmentDAO.updateStatus(appointmentId, "cancelled");
    }

    /**
     * 完成预约
     */
    public boolean completeAppointment(Integer appointmentId) {
        return appointmentDAO.updateStatus(appointmentId, "completed");
    }

    /**
     * 删除预约
     */
    public boolean deleteAppointment(Integer appointmentId) {
        return appointmentDAO.delete(appointmentId);
    }

    /**
     * 获取所有患者
     */
    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }

    /**
     * 搜索患者
     */
    public List<Patient> searchPatients(String keyword) {
        return patientDAO.search(keyword);
    }

    /**
     * 添加患者
     */
    public Integer addPatient(Patient patient) {
        return patientDAO.insert(patient);
    }

    /**
     * 更新患者
     */
    public boolean updatePatient(Patient patient) {
        return patientDAO.update(patient);
    }

    /**
     * 删除患者
     */
    public boolean deletePatient(Integer patientId) {
        return patientDAO.delete(patientId);
    }

    /**
     * 获取所有科室
     */
    public List<Department> getAllDepartments() {
        return departmentDAO.findAll();
    }

    /**
     * 根据科室获取医生
     */
    public List<Doctor> getDoctorsByDepartment(Integer departmentId) {
        return doctorDAO.findByDepartmentId(departmentId);
    }

    /**
     * 获取所有医生
     */
    public List<Doctor> getAllDoctors() {
        return doctorDAO.findAll();
    }

    /**
     * 添加医生
     */
    public boolean addDoctor(Doctor doctor) {
        return doctorDAO.insert(doctor);
    }

    /**
     * 更新医生
     */
    public boolean updateDoctor(Doctor doctor) {
        return doctorDAO.update(doctor);
    }

    /**
     * 删除医生
     */
    public boolean deleteDoctor(Integer doctorId) {
        return doctorDAO.delete(doctorId);
    }

    /**
     * 添加科室
     */
    public boolean addDepartment(Department department) {
        return departmentDAO.insert(department);
    }

    /**
     * 更新科室
     */
    public boolean updateDepartment(Department department) {
        return departmentDAO.update(department);
    }

    /**
     * 删除科室
     */
    public boolean deleteDepartment(Integer departmentId) {
        return departmentDAO.delete(departmentId);
    }
}

