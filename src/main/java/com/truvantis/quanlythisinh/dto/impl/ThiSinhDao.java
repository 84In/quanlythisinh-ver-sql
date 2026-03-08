package com.truvantis.quanlythisinh.dto.impl;

import com.truvantis.quanlythisinh.dto.ThiSinhDaoInterface;
import com.truvantis.quanlythisinh.mapper.impl.ThiSinhMapper;
import com.truvantis.quanlythisinh.model.ThiSinh;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) cho thực thể {@link ThiSinh}.
 * Lớp này chịu trách nhiệm thao tác trực tiếp với bảng {@code thi_sinh}
 * trong cơ sở dữ liệu, bao gồm: thêm, sửa, xóa và truy vấn dữ liệu.
 * Được cài đặt theo mô hình Singleton nhằm đảm bảo chỉ tồn tại
 * một instance duy nhất trong suốt vòng đời ứng dụng.
 */
public class ThiSinhDao extends GenericDao<ThiSinh> implements ThiSinhDaoInterface {

    /**
     * Instance duy nhất của {@code ThiSinhDao} (Singleton).
     */
    private static ThiSinhDao instance;

    /**
     * Constructor riêng nhằm ngăn việc khởi tạo đối tượng
     * từ bên ngoài lớp.
     */
    private ThiSinhDao() {
    }

    /**
     * Trả về instance duy nhất của {@code ThiSinhDao}.
     * Phương thức được đồng bộ hóa (synchronized) để đảm bảo
     * an toàn trong môi trường đa luồng.
     *
     * @return instance của {@code ThiSinhDao}
     */
    public static synchronized ThiSinhDao getInstance() {
        if (instance == null) {
            instance = new ThiSinhDao();
        }
        return instance;
    }

    /**
     * Lưu mới một thí sinh vào cơ sở dữ liệu.
     *
     * @param ts đối tượng {@link ThiSinh} cần lưu
     */
    @Override
    public long saveThiSinh(ThiSinh ts) {
        String sql = """
                    INSERT INTO ThiSinh
                    (tenThiSinh, maTinh, ngaySinh, gioiTinh,
                     diemMon1, diemMon2, diemMon3)
                    VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
        return insert(sql,
                ts.getTenThiSinh(),
                ts.getQueQuan().getMaTinh(),
                ts.getNgaySinh(),
                ts.isGioiTinh(),
                ts.getDiemMon1(),
                ts.getDiemMon2(),
                ts.getDiemMon3());
    }

    /**
     * Cập nhật thông tin một thí sinh đã tồn tại.
     *
     * @param ts đối tượng {@link ThiSinh} chứa dữ liệu mới
     */
    @Override
    public void updateThiSinh(ThiSinh ts) {
        String sql = """
                    UPDATE thi_sinh SET
                        ten_thi_sinh = ?,
                        ma_tinh = ?,
                        ngay_sinh = ?,
                        gioi_tinh = ?,
                        diem_mon_1 = ?,
                        diem_mon_2 = ?,
                        diem_mon_3 = ?
                    WHERE ma_thi_sinh = ?
                """;
        update(sql,
                ts.getTenThiSinh(),
                ts.getQueQuan().getMaTinh(),
                ts.getNgaySinh(),
                ts.isGioiTinh(),
                ts.getDiemMon1(),
                ts.getDiemMon2(),
                ts.getDiemMon3(),
                ts.getMaThiSinh());
    }

    /**
     * Xóa một thí sinh theo mã thí sinh.
     *
     * @param maThiSinh mã thí sinh cần xóa
     */
    @Override
    public void deleteThiSinh(int maThiSinh) {
        update("DELETE FROM thiSinh WHERE maThiSinh = ?", maThiSinh);
    }

    /**
     * Tìm thí sinh theo mã thí sinh.
     *
     * @param maThiSinh mã thí sinh
     * @return đối tượng {@link ThiSinh} nếu tồn tại, ngược lại trả về {@code null}
     */
    @Override
    public ThiSinh findById(int maThiSinh) {
        String sql = """
                    SELECT ts.*, t.tenTinh
                    FROM thiSinh ts
                    JOIN tinh t ON ts.maTinh = t.maTinh
                    WHERE ts.maThiSinh = ?
                """;
        Optional<ThiSinh> ts = queryOne(sql, new ThiSinhMapper(), maThiSinh);
        return ts.orElse(null);
    }

    /**
     * Lấy danh sách toàn bộ thí sinh.
     *
     * @return danh sách {@link ThiSinh}, sắp xếp theo thời gian tạo giảm dần
     */
    @Override
    public List<ThiSinh> findAll() {
        String sql = """
                    SELECT ts.*, t.tenTinh
                    FROM thiSinh ts
                    JOIN tinh t ON ts.maTinh = t.maTinh
                    ORDER BY ts.maThiSinh ASC
                """;
        return query(sql, new ThiSinhMapper());
    }

    /**
     * Tìm kiếm thí sinh theo tên (gần đúng).
     *
     * @param keyword từ khóa tìm kiếm
     * @return danh sách {@link ThiSinh} phù hợp
     */
    @Override
    public List<ThiSinh> findByTen(String keyword) {
        String sql = """
                    SELECT ts.*, t.tenTinh
                    FROM thiSinh ts
                    JOIN tinh t ON ts.maTinh = t.maTinh
                    WHERE ts.tenThiSinh LIKE ?
                """;
        return query(sql, new ThiSinhMapper(), "%" + keyword + "%");
    }

    /**
     * Lấy danh sách thí sinh theo tỉnh.
     *
     * @param maTinh mã tỉnh
     * @return danh sách {@link ThiSinh} thuộc tỉnh tương ứng
     */
    @Override
    public List<ThiSinh> findByTinh(int maTinh) {
        String sql = """
                    SELECT ts.*, t.tenTinh
                    FROM thiSinh ts
                    JOIN tinh t ON ts.maTinh = t.maTinh
                    WHERE ts.maTinh = ?
                """;
        return query(sql, new ThiSinhMapper(), maTinh);
    }
}
