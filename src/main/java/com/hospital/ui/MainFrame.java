package com.hospital.ui;

import javax.swing.*;
import java.awt.*;

/**
 * 主窗口
 */
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("医院预约挂号管理系统");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // 添加各个功能面板
        tabbedPane.addTab("预约挂号", new AppointmentPanel());
        tabbedPane.addTab("预约管理", new AppointmentManagePanel());
        tabbedPane.addTab("患者管理", new PatientPanel());
        tabbedPane.addTab("医生管理", new DoctorPanel());
        tabbedPane.addTab("科室管理", new DepartmentPanel());

        add(tabbedPane);
    }
}

