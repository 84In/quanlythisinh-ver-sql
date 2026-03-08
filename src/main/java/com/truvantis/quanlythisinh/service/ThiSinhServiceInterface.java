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
    ArrayList<ThiSinh> search(String keyword, int maTinh);

    // Tính toán logic: Ví dụ tính tổng điểm 3 môn
    float tinhTongDiem(ThiSinh ts);

    // Lấy thông tin thí sinh theo ID
    ThiSinh findById(int maThiSinh);
}