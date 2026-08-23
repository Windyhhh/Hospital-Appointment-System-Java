package com.hospital.ui;

import com.hospital.model.Department;
import com.hospital.service.AppointmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 科室管理面板
 */
public class DepartmentPanel extends JPanel {
    private AppointmentService service = new AppointmentService();
    private JTable table;
    private DefaultTableModel tableModel;

    public DepartmentPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
        loadData();
    }

    private void initComponents() {
        // 表格
        String[] columns = {"ID", "科室名称", "描述"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(300);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("添加科室");
        JButton editButton = new JButton("编辑科室");
        JButton deleteButton = new JButton("删除科室");
        JButton refreshButton = new JButton("刷新");

        addButton.addActionListener(e -> showDepartmentDialog(null));
        editButton.addActionListener(e -> editDepartment());
        deleteButton.addActionListener(e -> deleteDepartment());
        refreshButton.addActionListener(e -> loadData());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Department> departments = service.getAllDepartments();

        for (Department dept : departments) {
            Object[] row = {
                    dept.getId(),
                    dept.getName(),
                    dept.getDescription()
            };
            tableModel.addRow(row);
        }
    }

    private void editDepartment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择一条科室记录！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        Department department = new Department();
        department.setId(id);
        department.setName((String) tableModel.getValueAt(selectedRow, 1));
        department.setDescription((String) tableModel.getValueAt(selectedRow, 2));

        showDepartmentDialog(department);
    }

    private void deleteDepartment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择一条科室记录！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除这条科室记录吗？\n注意：删除科室可能会影响相关的医生和预约记录！",
                "确认删除",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.deleteDepartment(id)) {
                JOptionPane.showMessageDialog(this, "删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showDepartmentDialog(Department department) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                department == null ? "添加科室" : "编辑科室", true);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(20);
        JTextField descField = new JTextField(20);

        if (department != null) {
            nameField.setText(department.getName());
            descField.setText(department.getDescription());
        }

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("科室名称:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("描述:"), gbc);
        gbc.gridx = 1;
        panel.add(descField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton saveButton = new JButton("保存");
        JButton cancelButton = new JButton("取消");

        saveButton.addActionListener(e -> {
            try {
                Department d = department == null ? new Department() : department;
                d.setName(nameField.getText().trim());
                d.setDescription(descField.getText().trim());

                boolean success;
                if (department == null) {
                    success = service.addDepartment(d);
                } else {
                    success = service.updateDepartment(d);
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

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}

