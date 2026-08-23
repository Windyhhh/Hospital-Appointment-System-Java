package com.hospital.dao;

import com.hospital.database.DatabaseManager;
import com.hospital.model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 科室数据访问对象
 */
public class DepartmentDAO {
    private DatabaseManager dbManager = DatabaseManager.getInstance();

    /**
     * 获取所有科室
     */
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM department ORDER BY id";

        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                departments.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }

    /**
     * 根据ID查找科室
     */
    public Department findById(Integer id) {
        String sql = "SELECT * FROM department WHERE id = ?";

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
     * 添加科室
     */
    public boolean insert(Department department) {
        String sql = "INSERT INTO department (name, description) VALUES (?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department.getName());
            pstmt.setString(2, department.getDescription());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新科室
     */
    public boolean update(Department department) {
        String sql = "UPDATE department SET name = ?, description = ? WHERE id = ?";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department.getName());
            pstmt.setString(2, department.getDescription());
            pstmt.setInt(3, department.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除科室
     */
    public boolean delete(Integer id) {
        String sql = "DELETE FROM department WHERE id = ?";

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
    private Department mapResultSet(ResultSet rs) throws SQLException {
        Department dept = new Department();
        dept.setId(rs.getInt("id"));
        dept.setName(rs.getString("name"));
        dept.setDescription(rs.getString("description"));
        dept.setCreatedAt(rs.getString("created_at"));
        return dept;
    }
}

