package com.truvantis.quanlythisinh.dto.impl;

import com.truvantis.quanlythisinh.dto.TinhDaoInterface;
import com.truvantis.quanlythisinh.mapper.impl.TinhMapper;
import com.truvantis.quanlythisinh.model.Tinh;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) cho thực thể {@link Tinh}.
 * Lớp này chịu trách nhiệm thao tác với bảng {@code tinh} trong cơ sở dữ liệu,
 * bao gồm các chức năng: thêm, sửa, xóa và truy vấn tỉnh/thành.
 * Được triển khai theo mô hình Singleton nhằm đảm bảo chỉ tồn tại
 * một instance duy nhất trong suốt vòng đời ứng dụng.
 */
public class TinhDao extends GenericDao<Tinh> implements TinhDaoInterface {

    /**
     * Instance duy nhất của {@code TinhDao} (Singleton).
     */
    private static TinhDao instance;

    /**
     * Constructor riêng nhằm ngăn việc khởi tạo đối tượng
     * {@code TinhDao} từ bên ngoài lớp.
     */
    private TinhDao() {
    }

    /**
     * Trả về instance duy nhất của {@code TinhDao}.
     * Phương thức được đồng bộ hóa để đảm bảo an toàn
     * trong môi trường đa luồng.
     *
     * @return instance của {@code TinhDao}
     */
    public static synchronized TinhDao getInstance() {
        if (instance == null) {
            instance = new TinhDao();
        }
        return instance;
    }

    /**
     * Lưu mới một tỉnh/thành vào cơ sở dữ liệu.
     *
     * @param tinh đối tượng {@link Tinh} cần lưu
     */
    @Override
    public void saveTinh(Tinh tinh) {
        String sql = """
                    INSERT INTO Tinh(maTinh, tenTinh)
                    VALUES (?, ?)
                """;
        insert(sql, tinh.getMaTinh(), tinh.getTenTinh());
    }

    /**
     * Xóa một tỉnh/thành khỏi cơ sở dữ liệu.
     *
     * @param tinh đối tượng {@link Tinh} cần xóa
     */
    @Override
    public void deleteTinh(Tinh tinh) {
        String sql = "DELETE FROM Tinh WHERE maTinh = ?";
        update(sql, tinh.getMaTinh());
    }

    /**
     * Cập nhật thông tin tỉnh/thành.
     *
     * @param tinh đối tượng {@link Tinh} chứa dữ liệu mới
     */
    @Override
    public void updateTinh(Tinh tinh) {
        String sql = """
                    UPDATE Tinh
                    SET tenTinh = ?
                    WHERE maTinh = ?
                """;
        update(sql, tinh.getTenTinh(), tinh.getMaTinh());
    }

    /**
     * Tìm tỉnh/thành theo tên chính xác.
     *
     * @param name tên tỉnh/thành
     * @return {@link Tinh} nếu tồn tại, ngược lại trả về {@code null}
     */
    @Override
    public Tinh findTinhByName(String name) {
        String sql = "SELECT maTinh, tenTinh, createdAt, updatedAt FROM Tinh WHERE tenTinh = ?";
        Optional<Tinh> tinh = queryOne(sql, new TinhMapper(), name);
        return tinh.orElse(null);
    }

    /**
     * Tìm tỉnh/thành theo mã tỉnh.
     *
     * @param id mã tỉnh
     * @return {@link Tinh} nếu tồn tại, ngược lại trả về {@code null}
     */
    @Override
    public Tinh findTinhById(int id) {
        String sql = "SELECT maTinh, tenTinh, createdAt, updatedAt FROM Tinh WHERE maTinh = ?";
        Optional<Tinh> tinh = queryOne(sql, new TinhMapper(), id);
        return tinh.orElse(null);
    }

    /**
     * Lấy danh sách toàn bộ tỉnh/thành.
     *
     * @return danh sách {@link Tinh}, sắp xếp theo tên tỉnh
     */
    @Override
    public List<Tinh> findAllTinh() {
        String sql = "SELECT maTinh, tenTinh, createdAt, updatedAt FROM Tinh ORDER BY tenTinh";
        return query(sql, new TinhMapper());
    }
}
