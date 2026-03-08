package com.truvantis.quanlythisinh.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface định nghĩa chuẩn ánh xạ (mapping) dữ liệu
 * từ {@link ResultSet} sang đối tượng domain.
 *
 * Mỗi implementation chịu trách nhiệm chuyển đổi
 * một dòng dữ liệu trong {@code ResultSet} thành
 * một đối tượng cụ thể.
 *
 * @param <T> kiểu đối tượng đích sau khi ánh xạ
 */
public interface RowMappersInterface<T> {

    /**
     * Ánh xạ một dòng dữ liệu từ {@link ResultSet}
     * sang đối tượng kiểu {@code T}.
     *
     * @param rs {@link ResultSet} đang trỏ tới dòng cần ánh xạ
     * @return đối tượng đã được ánh xạ
     * @throws SQLException nếu xảy ra lỗi truy cập dữ liệu
     */
    T mapRow(ResultSet rs) throws SQLException;
}