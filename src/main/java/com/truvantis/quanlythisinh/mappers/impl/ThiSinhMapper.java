package com.truvantis.quanlythisinh.mappers.impl;

import com.truvantis.quanlythisinh.mappers.RowMappersInterface;
import com.truvantis.quanlythisinh.models.ThiSinh;
import com.truvantis.quanlythisinh.models.Tinh;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper ánh xạ dữ liệu từ {@link ResultSet}
 * sang đối tượng {@link ThiSinh}.
 *
 * Mapper này xử lý dữ liệu được lấy từ câu truy vấn
 * có join giữa bảng {@code thi_sinh} và {@code tinh},
 * đồng thời khởi tạo đầy đủ các thông tin liên quan.
 */
public class ThiSinhMapper implements RowMappersInterface<ThiSinh> {

    /**
     * Ánh xạ một dòng hiện tại trong {@link ResultSet}
     * sang đối tượng {@link ThiSinh}.
     *
     * <p>
     * Bao gồm:
     * <ul>
     *   <li>Thông tin thí sinh</li>
     *   <li>Thông tin tỉnh (được ánh xạ sang đối tượng {@link Tinh})</li>
     *   <li>Thời gian tạo và cập nhật</li>
     * </ul>
     * </p>
     *
     * @param rs {@link ResultSet} đang trỏ tới dòng cần ánh xạ
     * @return đối tượng {@link ThiSinh} đã được khởi tạo đầy đủ
     * @throws SQLException nếu xảy ra lỗi khi đọc dữ liệu
     */
    @Override
    public ThiSinh mapRow(ResultSet rs) throws SQLException {

        // Ánh xạ thông tin tỉnh
        Tinh tinh = new Tinh(
                rs.getInt("ma_tinh"),
                rs.getString("ten_tinh")
        );

        // Ánh xạ thông tin thí sinh
        ThiSinh ts = new ThiSinh(
                rs.getInt("ma_thi_sinh"),
                rs.getString("ten_thi_sinh"),
                tinh,
                rs.getDate("ngay_sinh"),
                rs.getBoolean("gioi_tinh"),
                rs.getFloat("diem_mon_1"),
                rs.getFloat("diem_mon_2"),
                rs.getFloat("diem_mon_3")
        );

        // Thiết lập thời gian tạo và cập nhật
        ts.setCreatedAt(rs.getTimestamp("created_at"));
        ts.setUpdatedAt(rs.getTimestamp("updated_at"));

        return ts;
    }
}
