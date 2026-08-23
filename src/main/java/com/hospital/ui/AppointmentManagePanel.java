package com.hospital.ui;

import com.hospital.model.Appointment;
import com.hospital.service.AppointmentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 预约管理面板
 */
public class AppointmentManagePanel extends JPanel {
    private AppointmentService service = new AppointmentService();
    private JTable table;
    private DefaultTableModel tableModel;

    public AppointmentManagePanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initComponents();
        loadData();
    }

    private void initComponents() {
        // 表格
        String[] columns = {"ID", "患者", "科室", "医生", "日期", "时间", "状态", "症状"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(100);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
        table.getColumnModel().getColumn(7).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton confirmButton = new JButton("确认预约");
        JButton cancelButton = new JButton("取消预约");
        JButton completeButton = new JButton("完成预约");
        JButton deleteButton = new JButton("删除预约");
        JButton refreshButton = new JButton("刷新");

        confirmButton.addActionListener(e -> updateStatus("confirmed", "确认"));
        cancelButton.addActionListener(e -> updateStatus("cancelled", "取消"));
        completeButton.addActionListener(e -> updateStatus("completed", "完成"));
        deleteButton.addActionListener(e -> deleteAppointment());
        refreshButton.addActionListener(e -> loadData());

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Appointment> appointments = service.getAllAppointments();

        for (Appointment apt : appointments) {
            Object[] row = {
                    apt.getId(),
                    apt.getPatientName(),
                    apt.getDepartmentName(),
                    apt.getDoctorName(),
                    apt.getAppointmentDate(),
                    apt.getAppointmentTime(),
                    apt.getStatusText(),
                    apt.getSymptoms()
            };
            tableModel.addRow(row);
        }
    }

    private void updateStatus(String status, String action) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择一条预约记录！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要" + action + "这条预约吗？",
                "确认",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = false;
            switch (status) {
                case "confirmed":
                    success = service.confirmAppointment(id);
                    break;
                case "cancelled":
                    success = service.cancelAppointment(id);
                    break;
                case "completed":
                    success = service.completeAppointment(id);
                    break;
            }

            if (success) {
                JOptionPane.showMessageDialog(this, action + "成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, action + "失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteAppointment() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请选择一条预约记录！", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (Integer) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除这条预约吗？",
                "确认删除",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (service.deleteAppointment(id)) {
                JOptionPane.showMessageDialog(this, "删除成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败！", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

