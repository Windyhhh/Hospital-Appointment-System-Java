package com.hospital.dao;

import com.hospital.database.DatabaseManager;
import com.hospital.model.Doctor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 医生数据访问对象
 */
public class DoctorDAO {
    private DatabaseManager dbManager = DatabaseManager.getInstance();

    /**
     * 获取所有医生
     */
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT d.*, dept.name as department_name " +
                "FROM doctor d " +
                "LEFT JOIN department dept ON d.department_id = dept.id " +
                "ORDER BY d.id";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                doctors.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    /**
     * 根据科室ID查找医生
     */
    public List<Doctor> findByDepartmentId(Integer departmentId) {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT d.*, dept.name as department_name " +
                "FROM doctor d " +
                "LEFT JOIN department dept ON d.department_id = dept.id " +
                "WHERE d.department_id = ? " +
                "ORDER BY d.id";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                doctors.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctors;
    }

    /**
     * 根据ID查找医生
     */
    public Doctor findById(Integer id) {
        String sql = "SELECT d.*, dept.name as department_name " +
                "FROM doctor d " +
                "LEFT JOIN department dept ON d.department_id = dept.id " +
                "WHERE d.id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 添加医生
     */
    public boolean insert(Doctor doctor) {
        String sql = "INSERT INTO doctor (name, department_id, title, phone, specialty) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctor.getName());
            pstmt.setInt(2, doctor.getDepartmentId());
            pstmt.setString(3, doctor.getTitle());
            pstmt.setString(4, doctor.getPhone());
            pstmt.setString(5, doctor.getSpecialty());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新医生
     */
    public boolean update(Doctor doctor) {
        String sql = "UPDATE doctor SET name = ?, department_id = ?, title = ?, phone = ?, specialty = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, doctor.getName());
            pstmt.setInt(2, doctor.getDepartmentId());
            pstmt.setString(3, doctor.getTitle());
            pstmt.setString(4, doctor.getPhone());
            pstmt.setString(5, doctor.getSpecialty());
            pstmt.setInt(6, doctor.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除医生
     */
    public boolean delete(Integer id) {
        String sql = "DELETE FROM doctor WHERE id = ?";

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
    private Doctor mapResultSet(ResultSet rs) throws SQLException {
        Doctor doctor = new Doctor();
        doctor.setId(rs.getInt("id"));
        doctor.setName(rs.getString("name"));
        doctor.setDepartmentId(rs.getInt("department_id"));
        doctor.setDepartmentName(rs.getString("department_name"));
        doctor.setTitle(rs.getString("title"));
        doctor.setPhone(rs.getString("phone"));
        doctor.setSpecialty(rs.getString("specialty"));
        doctor.setCreatedAt(rs.getString("created_at"));
        return doctor;
    }
}

