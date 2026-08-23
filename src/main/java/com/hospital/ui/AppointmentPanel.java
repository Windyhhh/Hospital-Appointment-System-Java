package com.hospital.ui;

import com.hospital.model.*;
import com.hospital.service.AppointmentService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 预约挂号面板
 */
public class AppointmentPanel extends JPanel {
    private AppointmentService service = new AppointmentService();

    private JTextField patientNameField;
    private JTextField patientPhoneField;
    private JTextField patientIdCardField;
    private JComboBox<String> genderComboBox;
    private JTextField ageField;
    private JTextField addressField;

    private JComboBox<Department> departmentComboBox;
    private JComboBox<Doctor> doctorComboBox;
    private JTextField dateField;
    private JComboBox<String> timeComboBox;
    private JTextArea symptomsArea;

    public AppointmentPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
        loadDepartments();
    }

    private void initComponents() {
        // 主面板
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // 患者信息面板
        JPanel patientPanel = createPatientInfoPanel();
        mainPanel.add(patientPanel);

        // 预约信息面板
        JPanel appointmentInfoPanel = createAppointmentInfoPanel();
        mainPanel.add(appointmentInfoPanel);

        add(mainPanel, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton submitButton = new JButton("提交预约");
        JButton resetButton = new JButton("重置");

        submitButton.setPreferredSize(new Dimension(120, 35));
        resetButton.setPreferredSize(new Dimension(120, 35));

        submitButton.addActionListener(e -> submitAppointment());
        resetButton.addActionListener(e -> resetForm());

        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createPatientInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("患者信息"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 姓名
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("姓名:"), gbc);
        gbc.gridx = 1;
        patientNameField = new JTextField(15);
        panel.add(patientNameField, gbc);

        // 手机号
        gbc.gridx = 2;
        panel.add(new JLabel("手机号:"), gbc);
        gbc.gridx = 3;
        patientPhoneField = new JTextField(15);
        panel.add(patientPhoneField, gbc);

        // 身份证号
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("身份证号:"), gbc);
        gbc.gridx = 1;
        patientIdCardField = new JTextField(15);
        panel.add(patientIdCardField, gbc);

        // 性别
        gbc.gridx = 2;
        panel.add(new JLabel("性别:"), gbc);
        gbc.gridx = 3;
        genderComboBox = new JComboBox<>(new String[]{"男", "女"});
        panel.add(genderComboBox, gbc);

        // 年龄
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("年龄:"), gbc);
        gbc.gridx = 1;
        ageField = new JTextField(15);
        panel.add(ageField, gbc);

        // 地址
        gbc.gridx = 2;
        panel.add(new JLabel("地址:"), gbc);
        gbc.gridx = 3;
        addressField = new JTextField(15);
        panel.add(addressField, gbc);

        return panel;
    }

    private JPanel createAppointmentInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("预约信息"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 科室
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("科室:"), gbc);
        gbc.gridx = 1;
        departmentComboBox = new JComboBox<>();
        departmentComboBox.addActionListener(e -> loadDoctors());
        panel.add(departmentComboBox, gbc);

        // 医生
        gbc.gridx = 2;
        panel.add(new JLabel("医生:"), gbc);
        gbc.gridx = 3;
        doctorComboBox = new JComboBox<>();
        panel.add(doctorComboBox, gbc);

        // 日期
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("预约日期:"), gbc);
        gbc.gridx = 1;
        dateField = new JTextField(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE), 15);
        panel.add(dateField, gbc);

        // 时间
        gbc.gridx = 2;
        panel.add(new JLabel("预约时间:"), gbc);
        gbc.gridx = 3;
        String[] times = {"08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00"};
        timeComboBox = new JComboBox<>(times);
        panel.add(timeComboBox, gbc);

        // 症状描述
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("症状描述:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3;
        symptomsArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(symptomsArea);
        panel.add(scrollPane, gbc);

        return panel;
    }

    private void loadDepartments() {
        List<Department> departments = service.getAllDepartments();
        departmentComboBox.removeAllItems();
        for (Department dept : departments) {
            departmentComboBox.addItem(dept);
        }
    }

    private void loadDoctors() {
        Department selectedDept = (Department) departmentComboBox.getSelectedItem();
        if (selectedDept != null) {
            List<Doctor> doctors = service.getDoctorsByDepartment(selectedDept.getId());
            doctorComboBox.removeAllItems();
            for (Doctor doctor : doctors) {
                doctorComboBox.addItem(doctor);
            }
        }
    }

    private void submitAppointment() {
        try {
            // 验证输入
            if (patientNameField.getText().trim().isEmpty() ||
                    patientPhoneField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写患者姓名和手机号！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (departmentComboBox.getSelectedItem() == null ||
                    doctorComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "请选择科室和医生！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 创建或更新患者信息
            Patient patient = new Patient();
            patient.setName(patientNameField.getText().trim());
            patient.setPhone(patientPhoneField.getText().trim());
            patient.setIdCard(patientIdCardField.getText().trim());
            patient.setGender((String) genderComboBox.getSelectedItem());
            patient.setAge(ageField.getText().isEmpty() ? 0 : Integer.parseInt(ageField.getText()));
            patient.setAddress(addressField.getText().trim());

            Integer patientId = service.addPatient(patient);
            if (patientId == null) {
                JOptionPane.showMessageDialog(this, "患者信息保存失败！", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 创建预约
            Appointment appointment = new Appointment();
            appointment.setPatientId(patientId);
            appointment.setDoctorId(((Doctor) doctorComboBox.getSelectedItem()).getId());
            appointment.setDepartmentId(((Department) departmentComboBox.getSelectedItem()).getId());
            appointment.setAppointmentDate(dateField.getText());
            appointment.setAppointmentTime((String) timeComboBox.getSelectedItem());
            appointment.setStatus("pending");
            appointment.setSymptoms(symptomsArea.getText().trim());

            if (service.createAppointment(appointment)) {
                JOptionPane.showMessageDialog(this, "预约成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "预约失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "操作失败：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void resetForm() {
        patientNameField.setText("");
        patientPhoneField.setText("");
        patientIdCardField.setText("");
        genderComboBox.setSelectedIndex(0);
        ageField.setText("");
        addressField.setText("");
        dateField.setText(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        timeComboBox.setSelectedIndex(0);
        symptomsArea.setText("");
    }
}

