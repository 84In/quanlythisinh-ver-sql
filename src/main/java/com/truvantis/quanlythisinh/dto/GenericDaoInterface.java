package com.truvantis.quanlythisinh.dto;

import java.util.List;
import java.util.Optional;

import com.truvantis.quanlythisinh.mapper.RowMappersInterface;

/**
 * Interface chung cho các DAO, định nghĩa các phương thức cơ bản
 * để thao tác với cơ sở dữ liệu.
 *
 * @param <T> loại thực thể
 */
public interface GenericDaoInterface<T> {

        /**
         * Thực hiện truy vấn (SELECT) và trả về danh sách kết quả.
         *
         * @param sql       câu lệnh SQL
         * @param rowMapper ánh xạ mỗi dòng ResultSet sang đối tượng
         * @param params    tham số cho PreparedStatement
         * @return danh sách đối tượng kết quả
         */
        List<T> query(
                        String sql,
                        RowMappersInterface<T> rowMapper,
                        Object... params);

        /**
         * Thực hiện truy vấn và trả về một đối tượng (nếu có).
         *
         * @param sql       câu lệnh SQL
         * @param rowMapper ánh xạ mỗi dòng ResultSet sang đối tượng
         * @param params    tham số cho PreparedStatement
         * @return Optional chứa đối tượng nếu tìm thấy, ngược lại Optional.empty()
         */
        Optional<T> queryOne(
                        String sql,
                        RowMappersInterface<T> rowMapper,
                        Object... params);

        /**
         * Thực hiện câu lệnh UPDATE/DELETE.
         *
         * @param sql    câu lệnh SQL
         * @param params tham số cho PreparedStatement
         * @return số dòng bị ảnh hưởng
         */
        int update(
                        String sql,
                        Object... params);

        /**
         * Thực hiện câu lệnh INSERT và trả về khóa chính (generated key).
         *
         * @param sql    câu lệnh SQL
         * @param params tham số cho PreparedStatement
         * @return giá trị khóa chính sinh tự động (nếu có)
         */
        long insert(
                        String sql,
                        Object... params);

        /**
         * Thực hiện truy vấn COUNT.
         *
         * @param sql    câu lệnh SQL
         * @param params tham số cho PreparedStatement
         * @return số lượng bản ghi
         */
        long count(
                        String sql,
                        Object... params);
}
