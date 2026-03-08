package com.truvantis.quanlythisinh.view;

import java.util.List;

import com.truvantis.quanlythisinh.model.ThiSinh;

public interface QuanLySinhVienInterface {
    // --- Nhóm chức năng Cơ bản (CRUD) ---

    /** Xóa trắng các ô nhập liệu trên Form */
    void xoaForm();

    /** Thêm mới hoặc Cập nhật thông tin thí sinh */
    void themHoacCapNhatThiSinh(ThiSinh ts);

    /** Hiển thị thông tin thí sinh đang chọn lên các ô nhập liệu */
    void hienThiThongTinThiSinhDaChon();

    // Lấy thông tin từ giao diện
    int getSelectedRowIndex();

    int layMaThiSinhTuForm();

    // Các phương thức tương tác người dùng
    boolean confirmAction(String message);

    void showMessage(String message);

    // Các phương thức cập nhật giao diện
    void removeRowFromTable(int row);

    // --- Nhóm chức năng Tìm kiếm & Lọc ---

    /** Thực hiện tìm kiếm thí sinh theo các tiêu chí (Quê quán, Mã số...) */
    void thucHienTim();

    /** Hủy bộ lọc và hiển thị lại toàn bộ danh sách */
    void huyTim();

    // --- Nhóm chức năng Hệ thống (File) ---

    /** Hiển thị thông tin tác giả/phần mềm */
    void hienThiAbout();

    /** Thoát ứng dụng có xác nhận */
    void thoatKhoiChuongTrinh();

    /** Lưu dữ liệu xuống File (Binary/Text) */
    void thucHienSaveFile();

    /** Đọc dữ liệu từ File lên bảng */
    void thucHienOpenFile();

    boolean showConfirmDialog(String message);

    void refreshTable(List<ThiSinh> danhSachThiSinh);
}