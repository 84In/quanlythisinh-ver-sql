package com.truvantis.quanlythisinh.dto;

import java.util.List;

import com.truvantis.quanlythisinh.model.ThiSinh;

/**
 * Interface định nghĩa các thao tác truy cập dữ liệu cho thực thể
 * {@link ThiSinh}.
 */
public interface ThiSinhDaoInterface {

    /**
     * Lưu mới một thí sinh.
     *
     * @param thiSinh đối tượng thí sinh cần lưu
     * @return id được tạo tự động của thí sinh
     */
    long saveThiSinh(ThiSinh thiSinh);

    /**
     * Cập nhật thông tin của thí sinh.
     *
     * @param thiSinh đối tượng thí sinh đã được chỉnh sửa
     */
    void updateThiSinh(ThiSinh thiSinh);

    /**
     * Xóa thí sinh theo mã.
     *
     * @param maThiSinh mã thí sinh cần xóa
     */
    void deleteThiSinh(int maThiSinh);

    /**
     * Tìm thí sinh theo mã.
     *
     * @param maThiSinh mã thí sinh
     * @return đối tượng thí sinh nếu tồn tại, hoặc {@code null}
     */
    ThiSinh findById(int maThiSinh);

    /**
     * Lấy danh sách toàn bộ thí sinh.
     *
     * @return danh sách thí sinh
     */
    List<ThiSinh> findAll();

    /**
     * Tìm thí sinh theo tên (phần chứa).
     *
     * @param keyword từ khóa tìm kiếm
     * @return danh sách thí sinh phù hợp
     */
    List<ThiSinh> findByTen(String keyword);

    /**
     * Lấy danh sách thí sinh theo tỉnh.
     *
     * @param maTinh mã tỉnh
     * @return danh sách thí sinh thuộc tỉnh
     */
    List<ThiSinh> findByTinh(int maTinh);

    /**
     * Tìm kiếm thí sinh theo tỉnh và/hoặc mã thí sinh.
     *
     * @param tenTinh   tên tỉnh (có thể là null để không lọc theo tỉnh)
     * @param maThiSinh mã thí sinh (có thể là null để không lọc theo mã)
     * @return danh sách thí sinh phù hợp
     */
    List<ThiSinh> findByTinhVaMaThiSinh(String tenTinh, Integer maThiSinh);
}
