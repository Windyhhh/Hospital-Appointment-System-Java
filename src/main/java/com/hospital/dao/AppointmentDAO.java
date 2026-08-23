package com.hospital.dao;

import com.hospital.database.DatabaseManager;
import com.hospital.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 预约数据访问对象
 */
public class AppointmentDAO {
    private DatabaseManager dbManager = DatabaseManager.getInstance();

    /**
     * 获取所有预约
     */
    public List<Appointment> findAll() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.*, " +
                "p.name as patient_name, " +
                "d.name as doctor_name, " +
                "dept.name as department_name " +
                "FROM appointment a " +
                "LEFT JOIN patient p ON a.patient_id = p.id " +
                "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                "LEFT JOIN department dept ON a.department_id = dept.id " +
                "ORDER BY a.appointment_date DESC, a.appointment_time DESC";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                appointments.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    /**
     * 根据患者ID查找预约
     */
    public List<Appointment> findByPatientId(Integer patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.*, " +
                "p.name as patient_name, " +
                "d.name as doctor_name, " +
                "dept.name as department_name " +
                "FROM appointment a " +
                "LEFT JOIN patient p ON a.patient_id = p.id " +
                "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                "LEFT JOIN department dept ON a.department_id = dept.id " +
                "WHERE a.patient_id = ? " +
                "ORDER BY a.appointment_date DESC, a.appointment_time DESC";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                appointments.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    /**
     * 根据医生ID查找预约
     */
    public List<Appointment> findByDoctorId(Integer doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.*, " +
                "p.name as patient_name, " +
                "d.name as doctor_name, " +
                "dept.name as department_name " +
                "FROM appointment a " +
                "LEFT JOIN patient p ON a.patient_id = p.id " +
                "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                "LEFT JOIN department dept ON a.department_id = dept.id " +
                "WHERE a.doctor_id = ? " +
                "ORDER BY a.appointment_date DESC, a.appointment_time DESC";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, doctorId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                appointments.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    /**
     * 根据日期查找预约
     */
    public List<Appointment> findByDate(String date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.*, " +
                "p.name as patient_name, " +
                "d.name as doctor_name, " +
                "dept.name as department_name " +
                "FROM appointment a " +
                "LEFT JOIN patient p ON a.patient_id = p.id " +
                "LEFT JOIN doctor d ON a.doctor_id = d.id " +
                "LEFT JOIN department dept ON a.department_id = dept.id " +
                "WHERE a.appointment_date = ? " +
                "ORDER BY a.appointment_time";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                appointments.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    /**
     * 添加预约
     */
    public boolean insert(Appointment appointment) {
        String sql = "INSERT INTO appointment (patient_id, doctor_id, department_id, appointment_date, " +
                "appointment_time, status, symptoms) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setInt(3, appointment.getDepartmentId());
            pstmt.setString(4, appointment.getAppointmentDate());
            pstmt.setString(5, appointment.getAppointmentTime());
            pstmt.setString(6, appointment.getStatus());
            pstmt.setString(7, appointment.getSymptoms());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新预约状态
     */
    public boolean updateStatus(Integer id, String status) {
        String sql = "UPDATE appointment SET status = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除预约
     */
    public boolean delete(Integer id) {
        String sql = "DELETE FROM appointment WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 映射结果集到实体对象
     */
    private Appointment mapResultSet(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getInt("id"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setPatientName(rs.getString("patient_name"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setDoctorName(rs.getString("doctor_name"));
        appointment.setDepartmentId(rs.getInt("department_id"));
        appointment.setDepartmentName(rs.getString("department_name"));
        appointment.setAppointmentDate(rs.getString("appointment_date"));
        appointment.setAppointmentTime(rs.getString("appointment_time"));
        appointment.setStatus(rs.getString("status"));
        appointment.setSymptoms(rs.getString("symptoms"));
        appointment.setCreatedAt(rs.getString("created_at"));
        return appointment;
    }
}

