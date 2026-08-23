package com.hospital;

import com.hospital.database.DatabaseManager;
import com.hospital.ui.MainFrame;

import javax.swing.*;

/**
 * 医院预约挂号管理系统 - 主入口
 */
public class Main {
    public static void main(String[] args) {
        try {
            // 设置系统外观
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 初始化数据库
        DatabaseManager.getInstance().initDatabase();

        // 启动GUI
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}

