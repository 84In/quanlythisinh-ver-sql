package com.truvantis.quanlythisinh.dtos.impl;

import com.truvantis.quanlythisinh.dtos.ThiSinhDaoInterface;
import com.truvantis.quanlythisinh.mappers.impl.ThiSinhMapper;
import com.truvantis.quanlythisinh.models.ThiSinh;

import java.util.List;
import java.util.Optional;

public class ThiSinhDao extends GenericDao<ThiSinh> implements ThiSinhDaoInterface {

    private static ThiSinhDao instance;

    private ThiSinhDao() {
    }

    public static synchronized ThiSinhDao getInstance() {
        if (instance == null) {
            instance = new ThiSinhDao();
        }
        return instance;
    }

    @Override
    public void saveThiSinh(ThiSinh ts) {
        String sql = """
            INSERT INTO thi_sinh
            (ten_thi_sinh, ma_tinh, ngay_sinh, gioi_tinh,
             diem_mon_1, diem_mon_2, diem_mon_3)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        insert(sql,
                ts.getTenThiSinh(),
                ts.getQueQuan().getMaTinh(),
                ts.getNgaySinh(),
                ts.isGioiTinh(),
                ts.getDiemMon1(),
                ts.getDiemMon2(),
                ts.getDiemMon3()
        );
    }

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
                ts.getMaThiSinh()
        );
    }

    @Override
    public void deleteThiSinh(int maThiSinh) {
        update("DELETE FROM thi_sinh WHERE ma_thi_sinh = ?", maThiSinh);
    }

    @Override
    public ThiSinh findById(int maThiSinh) {
        String sql = """
            SELECT ts.*, t.ten_tinh
            FROM thi_sinh ts
            JOIN tinh t ON ts.ma_tinh = t.ma_tinh
            WHERE ts.ma_thi_sinh = ?
        """;
        Optional<ThiSinh> ts = queryOne(sql, new ThiSinhMapper(), maThiSinh);
        return ts.orElse(null);
    }

    @Override
    public List<ThiSinh> findAll() {
        String sql = """
            SELECT ts.*, t.ten_tinh
            FROM thi_sinh ts
            JOIN tinh t ON ts.ma_tinh = t.ma_tinh
            ORDER BY ts.created_at DESC
        """;
        return query(sql, new ThiSinhMapper());
    }

    @Override
    public List<ThiSinh> findByTen(String keyword) {
        String sql = """
            SELECT ts.*, t.ten_tinh
            FROM thi_sinh ts
            JOIN tinh t ON ts.ma_tinh = t.ma_tinh
            WHERE ts.ten_thi_sinh LIKE ?
        """;
        return query(sql, new ThiSinhMapper(), "%" + keyword + "%");
    }

    @Override
    public List<ThiSinh> findByTinh(int maTinh) {
        String sql = """
            SELECT ts.*, t.ten_tinh
            FROM thi_sinh ts
            JOIN tinh t ON ts.ma_tinh = t.ma_tinh
            WHERE ts.ma_tinh = ?
        """;
        return query(sql, new ThiSinhMapper(), maTinh);
    }
}
