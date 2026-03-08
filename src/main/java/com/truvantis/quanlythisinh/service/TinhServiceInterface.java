package com.truvantis.quanlythisinh.service;

import java.util.List;

import com.truvantis.quanlythisinh.model.Tinh;

public interface TinhServiceInterface {
    // Lấy toàn bộ danh sách từ Database
    List<Tinh> getAllTinh();

    // Thêm mới tỉnh
    boolean insert(Tinh tinh);

    // Cập nhật thông tin tỉnh
    boolean update(Tinh tinh);

    // Xóa theo ID
    boolean delete(int maTinh);

    // Tìm kiếm theo tên hoặc mã tỉnh
    // List<Tinh> search(String keyword);

    // Tìm kiếm theo tên tỉnh
    Tinh findByName(String name);
}
