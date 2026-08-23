package com.hospital.ui;

import com.hospital.model.Patient;
import com.hospital.service.AppointmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 患者管理面板
 */
public class PatientPanel extends JPanel {
    private AppointmentService service = new AppointmentService();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public PatientPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
        loadData();
    }

    private void initComponents() {
        // 搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.add(new JLabel("搜索:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> searchPatients());
        searchPanel.add(searchButton);
        JButton showAllButton = new JButton("显示全部");
        showAllButton.addActionListener(e -> loadData());
        searchPanel.add(showAllButton);

        add(searchPanel, BorderLayout.NORTH);

        // 表格
        String[] columns = {"ID", "姓名", "身份证号", "手机号", "性别", "年龄", "地址"};
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

        JButton addButton = new JButton("添加患者");
        JButton editButton = new JButton("编辑患者");
        JButton deleteButton = new JButton("删除患者");
        JButton refreshButton = new JButton("刷新");

        addButton.addActionListener(e -> showPatientDialog(null));
        editButton.addActionListener(e -> editPatient());
        deleteButton.addActionListener(e -> deletePatient());
        refreshButton.addActionListener(e -> loadData());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Patient> patients = service.getAllPatients();

        for (Patient patient : patients) {
            Object[] row = {
                    patient.getId(),
                    patient.getName(),
                    patient.getIdCard(),
                    patient.getPhone(),
                    patient.getGender(),
                    patient.getAge(),
                    patient.getAddress()
            };
            tableModel.addRow(row);
        }
    }

    private void searchPatients() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadData();
            return;
        }

        tableModel.setRowCount(0);
        List<Patient> patients = service.searchPatients(keyword);

        for (Patient patient : patients) {
            Object[] row = {
                    patient.getId(),
                    patient.getName(),
                    patient.getIdCard(),
                    patient.getPhone(),
                    patient.getGender(),
                    patient.getAge(),
                    patient.getAddress()
            };
            tableModel.addRow(row);
        }
    }

    private void editPatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择一条患者记录！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        Patient patient = new Patient();
        patient.setId(id);
        patient.setName((String) tableModel.getValueAt(selectedRow, 1));
        patient.setIdCard((String) tableModel.getValueAt(selectedRow, 2));
        patient.setPhone((String) tableModel.getValueAt(selectedRow, 3));
        patient.setGender((String) tableModel.getValueAt(selectedRow, 4));
        patient.setAge((Integer) tableModel.getValueAt(selectedRow, 5));
        patient.setAddress((String) tableModel.getValueAt(selectedRow, 6));

        showPatientDialog(patient);
    }

    private void deletePatient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择一条患者记录！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除这条患者记录吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.deletePatient(id)) {
                JOptionPane.showMessageDialog(this, "删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showPatientDialog(Patient patient) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                patient == null ? "添加患者" : "编辑患者", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(15);
        JTextField idCardField = new JTextField(15);
        JTextField phoneField = new JTextField(15);
        JComboBox<String> genderComboBox = new JComboBox<>(new String[]{"男", "女"});
        JTextField ageField = new JTextField(15);
        JTextField addressField = new JTextField(15);

        if (patient != null) {
            nameField.setText(patient.getName());
            idCardField.setText(patient.getIdCard());
            phoneField.setText(patient.getPhone());
            genderComboBox.setSelectedItem(patient.getGender());
            ageField.setText(String.valueOf(patient.getAge()));
            addressField.setText(patient.getAddress());
        }

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("姓名:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("身份证号:"), gbc);
        gbc.gridx = 1;
        panel.add(idCardField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("手机号:"), gbc);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("性别:"), gbc);
        gbc.gridx = 1;
        panel.add(genderComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("年龄:"), gbc);
        gbc.gridx = 1;
        panel.add(ageField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("地址:"), gbc);
        gbc.gridx = 1;
        panel.add(addressField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");

        saveButton.addActionListener(e -> {
            try {
                Patient p = patient == null ? new Patient() : patient;
                p.setName(nameField.getText().trim());
                p.setIdCard(idCardField.getText().trim());
                p.setPhone(phoneField.getText().trim());
                p.setGender((String) genderComboBox.getSelectedItem());
                p.setAge(Integer.parseInt(ageField.getText().trim()));
                p.setAddress(addressField.getText().trim());

                boolean success;
                if (patient == null) {
                    success = service.addPatient(p) != null;
                } else {
                    success = service.updatePatient(p);
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

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}

