package com.hospital.model;

/**
 * 医生实体类
 */
public class Doctor {
    private Integer id;
    private String name;
    private Integer departmentId;
    private String departmentName;
    private String title;
    private String phone;
    private String specialty;
    private String createdAt;

    public Doctor() {
    }

    public Doctor(Integer id, String name, Integer departmentId, String title, String phone, String specialty) {
        this.id = id;
        this.name = name;
        this.departmentId = departmentId;
        this.title = title;
        this.phone = phone;
        this.specialty = specialty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return name + " - " + title;
    }
}

