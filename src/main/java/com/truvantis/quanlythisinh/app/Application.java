package com.truvantis.quanlythisinh.app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.truvantis.quanlythisinh.view.impl.QuanLySinhVienView;

/**
 * Entry point của ứng dụng Quản lý thí sinh.
 *
 * <p>
 * Chỉ chịu trách nhiệm khởi tạo và hiển thị giao diện người dùng trong
 * Swing Event Dispatch Thread (EDT).
 * </p>
 */
public class Application {
    /**
     * Điểm vào của chương trình.
     *
     * <p>
     * Khởi tạo và hiển thị giao diện người dùng trong Swing Event Dispatch Thread
     * (EDT).
     * </p>
     *
     * @param args tham số dòng lệnh (không sử dụng)
     */
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
