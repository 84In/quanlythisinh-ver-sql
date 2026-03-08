package com.truvantis.quanlythisinh.mapper.impl;

import com.truvantis.quanlythisinh.mapper.RowMappersInterface;
import com.truvantis.quanlythisinh.model.Tinh;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper ánh xạ dữ liệu từ {@link ResultSet}
 * sang đối tượng {@link Tinh}.
 *
 * Chịu trách nhiệm chuyển đổi một dòng dữ liệu
 * của bảng {@code tinh} thành đối tượng domain tương ứng.
 */
public class TinhMapper implements RowMappersInterface<Tinh> {

    /**
     * Ánh xạ một dòng hiện tại trong {@link ResultSet}
     * sang đối tượng {@link Tinh}.
     *
     * @param rs {@link ResultSet} đang trỏ tới dòng cần ánh xạ
     * @return đối tượng {@link Tinh} đã được khởi tạo
     * @throws SQLException nếu xảy ra lỗi khi đọc dữ liệu
     */
    @Override
    public Tinh mapRow(ResultSet rs) throws SQLException {
        return new Tinh(
                rs.getInt("maTinh"),
                rs.getString("tenTinh"),
                rs.getTimestamp("createdAt"),
                rs.getTimestamp("updatedAt"));
    }
}
