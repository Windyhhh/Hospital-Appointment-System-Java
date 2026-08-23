package com.hospital.model;

/**
 * 患者实体类
 */
public class Patient {
    private Integer id;
    private String name;
    private String idCard;
    private String phone;
    private String gender;
    private Integer age;
    private String address;
    private String createdAt;

    public Patient() {
    }

    public Patient(Integer id, String name, String idCard, String phone, String gender, Integer age, String address) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.address = address;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}

