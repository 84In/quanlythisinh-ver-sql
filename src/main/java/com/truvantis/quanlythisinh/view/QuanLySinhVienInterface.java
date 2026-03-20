package com.truvantis.quanlythisinh.view;

import java.util.List;
import java.util.Map;

import com.truvantis.quanlythisinh.model.ThiSinh;
import com.truvantis.quanlythisinh.model.Tinh;

/**
 * Giao diện chung định nghĩa các hành vi của View quản lý thí sinh.
 *
 * <p>
 * Mục đích là tách rời phần hiển thị và logic xử lý, giúp Controller có thể
 * thao tác mà không phụ thuộc trực tiếp vào Swing.
 * </p>
 */
public interface QuanLySinhVienInterface {
    // --- Nhóm chức năng Cơ bản (CRUD) ---

    /** Xóa trắng các ô nhập liệu trên Form */
    void xoaForm();

    /** Thêm mới hoặc Cập nhật thông tin thí sinh */
    void themHoacCapNhatThiSinh(ThiSinh ts);

    /** Hiển thị thông tin thí sinh đang chọn lên các ô nhập liệu */
    void hienThiThongTinThiSinhDaChon();

    // Lấy thông tin từ giao diện
    /**
     * Lấy chỉ số dòng đang được chọn trên bảng.
     *
     * @return chỉ số dòng (row) đang chọn, hoặc -1 nếu không có dòng nào.
     */
    int getSelectedRowIndex();

    /**
     * Lấy mã thí sinh từ dòng đang chọn (từ bảng).
     *
     * @return mã thí sinh
     * @throws IllegalArgumentException nếu không có dòng nào được chọn.
     */
    int layMaThiSinhTuForm();

    // Các phương thức tương tác người dùng

    /**
     * Hiển thị hộp thoại xác nhận và trả về kết quả lựa chọn.
     *
     * @param message nội dung thông báo
     * @return {@code true} nếu người dùng chọn đồng ý, {@code false} ngược lại
     */
    boolean confirmAction(String message);

    /**
     * Hiển thị hộp thoại thông báo đơn giản.
     *
     * @param message nội dung thông báo
     */
    void showMessage(String message);

    // Các phương thức cập nhật giao diện

    /**
     * Xóa một dòng khỏi bảng và cập nhật giao diện.
     *
     * @param row chỉ số dòng cần xóa
     */
    void removeRowFromTable(int row);

    // --- Nhóm chức năng Tìm kiếm & Lọc ---

    /** Thực hiện tìm kiếm thí sinh theo các tiêu chí (Quê quán, Mã số...) */
    Map.Entry<Tinh, String> layDuLieuTim();

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

    /**
     * Hiển thị hộp thoại xác nhận (Yes/No) với icon cảnh báo.
     *
     * @param message nội dung thông báo
     * @return {@code true} nếu người dùng chọn YES
     */
    boolean showConfirmDialog(String message);

    /**
     * Làm mới dữ liệu trong bảng theo danh sách thí sinh mới.
     *
     * @param danhSachThiSinh danh sách thí sinh mới
     */
    void refreshTable(List<ThiSinh> danhSachThiSinh);
}