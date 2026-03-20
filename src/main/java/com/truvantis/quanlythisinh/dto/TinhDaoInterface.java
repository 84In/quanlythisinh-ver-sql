package com.truvantis.quanlythisinh.dto;

import java.util.List;

import com.truvantis.quanlythisinh.model.Tinh;

/**
 * Interface định nghĩa các thao tác truy cập dữ liệu cho thực thể {@link Tinh}.
 */
public interface TinhDaoInterface {

    /**
     * Lưu mới một tỉnh/thành.
     *
     * @param tinh đối tượng tỉnh/thành cần lưu
     */
    void saveTinh(Tinh tinh);

    /**
     * Xóa tỉnh/thành.
     *
     * @param tinh đối tượng tỉnh/thành cần xóa
     */
    void deleteTinh(Tinh tinh);

    /**
     * Cập nhật thông tin tỉnh/thành.
     *
     * @param tinh đối tượng tỉnh/thành cần cập nhật
     */
    void updateTinh(Tinh tinh);

    /**
     * Tìm tỉnh/thành theo tên.
     *
     * @param name tên tỉnh/thành
     * @return đối tượng {@link Tinh} nếu tồn tại, hoặc {@code null}
     */
    Tinh findTinhByName(String name);

    /**
     * Tìm tỉnh/thành theo mã.
     *
     * @param id mã tỉnh
     * @return đối tượng {@link Tinh} nếu tồn tại, hoặc {@code null}
     */
    Tinh findTinhById(int id);

    /**
     * Lấy danh sách toàn bộ tỉnh/thành.
     *
     * @return danh sách {@link Tinh}
     */
    List<Tinh> findAllTinh();

}
