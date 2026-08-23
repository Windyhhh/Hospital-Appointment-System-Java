package com.hospital.ui;

import com.hospital.model.Department;
import com.hospital.model.Doctor;
import com.hospital.service.AppointmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 医生管理面板
 */
public class DoctorPanel extends JPanel {
    private AppointmentService service = new AppointmentService();
    private JTable table;
    private DefaultTableModel tableModel;

    public DoctorPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
        loadData();
    }

    private void initComponents() {
        // 表格
        String[] columns = {"ID", "姓名", "科室", "职称", "手机号", "专长"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("添加医生");
        JButton editButton = new JButton("编辑医生");
        JButton deleteButton = new JButton("删除医生");
        JButton refreshButton = new JButton("刷新");

        addButton.addActionListener(e -> showDoctorDialog(null));
        editButton.addActionListener(e -> editDoctor());
        deleteButton.addActionListener(e -> deleteDoctor());
        refreshButton.addActionListener(e -> loadData());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Doctor> doctors = service.getAllDoctors();

        for (Doctor doctor : doctors) {
            Object[] row = {
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getDepartmentName(),
                    doctor.getTitle(),
                    doctor.getPhone(),
                    doctor.getSpecialty()
            };
            tableModel.addRow(row);
        }
    }

    private void editDoctor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择一条医生记录！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        List<Doctor> doctors = service.getAllDoctors();
        Doctor doctor = doctors.stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null);

        if (doctor != null) {
            showDoctorDialog(doctor);
        }
    }

    private void deleteDoctor() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择一条医生记录！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除这条医生记录吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.deleteDoctor(id)) {
                JOptionPane.showMessageDialog(this, "删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showDoctorDialog(Doctor doctor) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                doctor == null ? "添加医生" : "编辑医生", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(15);
        JComboBox<Department> departmentComboBox = new JComboBox<>();
        JTextField titleField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JTextField specialtyField = new JTextField(15);

        // 加载科室列表
        List<Department> departments = service.getAllDepartments();
        for (Department dept : departments) {
            departmentComboBox.addItem(dept);
        }

        if (doctor != null) {
            nameField.setText(doctor.getName());
            titleField.setText(doctor.getTitle());
            phoneField.setText(doctor.getPhone());
            specialtyField.setText(doctor.getSpecialty());

            // 选择对应的科室
            for (int i = 0; i < departmentComboBox.getItemCount(); i++) {
                if (departmentComboBox.getItemAt(i).getId().equals(doctor.getDepartmentId())) {
                    departmentComboBox.setSelectedIndex(i);
                    break;
                }
            }
        }

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("姓名:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("科室:"), gbc);
        gbc.gridx = 1;
        panel.add(departmentComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("职称:"), gbc);
        gbc.gridx = 1;
        panel.add(titleField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("手机号:"), gbc);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("专长:"), gbc);
        gbc.gridx = 1;
        panel.add(specialtyField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");

        saveButton.addActionListener(e -> {
            try {
                Doctor d = doctor == null ? new Doctor() : doctor;
                d.setName(nameField.getText().trim());
                d.setDepartmentId(((Department) departmentComboBox.getSelectedItem()).getId());
                d.setTitle(titleField.getText().trim());
                d.setPhone(phoneField.getText().trim());
                d.setSpecialty(specialtyField.getText().trim());

                boolean success;
                if (doctor == null) {
                    success = service.addDoctor(d);
                } else {
                    success = service.updateDoctor(d);
                }

                if (success) {
                    JOptionPane.showMessageDialog(dialog, "保存成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(dialog, "保存失败！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "输入错误：" + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}

