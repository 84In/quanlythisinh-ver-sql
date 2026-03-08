package com.truvantis.quanlythisinh.app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.truvantis.quanlythisinh.view.impl.QuanLySinhVienView;

public class Application {
    public static void main(String[] args) {
        // Chạy giao diện trong luồng sự kiện của Swing (đảm bảo an toàn và mượt mà)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // 1. (Tùy chọn) Thiết lập giao diện theo hệ điều hành cho đẹp
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                    // 2. Khởi tạo View
                    new QuanLySinhVienView();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
