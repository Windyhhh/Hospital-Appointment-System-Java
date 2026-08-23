package com.hospital.dao;

import com.hospital.database.DatabaseManager;
import com.hospital.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 患者数据访问对象
 */
public class PatientDAO {
    private DatabaseManager dbManager = DatabaseManager.getInstance();

    /**
     * 获取所有患者
     */
    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient ORDER BY id DESC";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patients.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    /**
     * 根据ID查找患者
     */
    public Patient findById(Integer id) {
        String sql = "SELECT * FROM patient WHERE id = ?";

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
     * 根据手机号查找患者
     */
    public Patient findByPhone(String phone) {
        String sql = "SELECT * FROM patient WHERE phone = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phone);
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
     * 搜索患者（按姓名或手机号）
     */
    public List<Patient> search(String keyword) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE name LIKE ? OR phone LIKE ? ORDER BY id DESC";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String pattern = "%" + keyword + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                patients.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    /**
     * 添加患者
     */
    public Integer insert(Patient patient) {
        String sql = "INSERT INTO patient (name, id_card, phone, gender, age, address) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getIdCard());
            pstmt.setString(3, patient.getPhone());
            pstmt.setString(4, patient.getGender());
            pstmt.setInt(5, patient.getAge());
            pstmt.setString(6, patient.getAddress());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 更新患者
     */
    public boolean update(Patient patient) {
        String sql = "UPDATE patient SET name = ?, id_card = ?, phone = ?, gender = ?, age = ?, address = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getIdCard());
            pstmt.setString(3, patient.getPhone());
            pstmt.setString(4, patient.getGender());
            pstmt.setInt(5, patient.getAge());
            pstmt.setString(6, patient.getAddress());
            pstmt.setInt(7, patient.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除患者
     */
    public boolean delete(Integer id) {
        String sql = "DELETE FROM patient WHERE id = ?";

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
    private Patient mapResultSet(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setName(rs.getString("name"));
        patient.setIdCard(rs.getString("id_card"));
        patient.setPhone(rs.getString("phone"));
        patient.setGender(rs.getString("gender"));
        patient.setAge(rs.getInt("age"));
        patient.setAddress(rs.getString("address"));
        patient.setCreatedAt(rs.getString("created_at"));
        return patient;
    }
}

