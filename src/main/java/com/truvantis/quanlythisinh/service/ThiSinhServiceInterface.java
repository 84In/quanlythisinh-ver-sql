package com.truvantis.quanlythisinh.service;

import com.truvantis.quanlythisinh.model.ThiSinh;
import java.util.ArrayList;

public interface ThiSinhServiceInterface {
    // Lấy toàn bộ danh sách từ Database
    ArrayList<ThiSinh> getAllThiSinh();

    // Thêm mới thí sinh
    long insert(ThiSinh ts);

    // Cập nhật thông tin
    boolean update(ThiSinh ts);

    // Xóa theo ID
    boolean delete(int maThiSinh);

    // Tìm kiếm theo tên hoặc mã tỉnh
    ArrayList<ThiSinh> timKiemTheoTenTinhHoacMaThiSinh(String tenTinh, int maThiSinh);

    // Tính toán logic: Ví dụ tính tổng điểm 3 môn
    float tinhTongDiem(ThiSinh ts);

    // Lấy thông tin thí sinh theo ID
    ThiSinh findById(int maThiSinh);

    // Xuất danh sách thí sinh ra file (có thể là .csv, .xlsx hoặc định dạng khác)
    void exportToFile(String filePath, ArrayList<ThiSinh> thiSinhList);

    // Nhập danh sách thí sinh từ file (có thể là .csv, .xlsx hoặc định dạng khác)
    void importFromFile(String filePath);
}