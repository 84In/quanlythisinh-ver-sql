package com.truvantis.quanlythisinh.dtos.impl;

import com.truvantis.quanlythisinh.dtos.TinhDaoInterface;
import com.truvantis.quanlythisinh.mappers.impl.TinhMapper;
import com.truvantis.quanlythisinh.models.Tinh;

import java.util.List;
import java.util.Optional;

public class TinhDao extends GenericDao<Tinh> implements TinhDaoInterface {

    private static TinhDao instance;

    private TinhDao() {
    }

    public static synchronized TinhDao getInstance() {
        if (instance == null) {
            instance = new TinhDao();
        }
        return instance;
    }

    // ================= SAVE =================
    @Override
    public void saveTinh(Tinh tinh) {
        String sql = """
            INSERT INTO tinh(ma_tinh, ten_tinh)
            VALUES (?, ?)
        """;
        insert(sql, tinh.getMaTinh(), tinh.getTenTinh());
    }

    // ================= DELETE =================
    @Override
    public void deleteTinh(Tinh tinh) {
        String sql = "DELETE FROM tinh WHERE ma_tinh = ?";
        update(sql, tinh.getMaTinh());
    }

    // ================= UPDATE =================
    @Override
    public void updateTinh(Tinh tinh) {
        String sql = """
            UPDATE tinh
            SET ten_tinh = ?
            WHERE ma_tinh = ?
        """;
        update(sql, tinh.getTenTinh(), tinh.getMaTinh());
    }

    // ================= FIND BY NAME =================
    @Override
    public Tinh findTinhByName(String name) {
        String sql = "SELECT * FROM tinh WHERE ten_tinh = ?";
        Optional<Tinh> tinh = queryOne(sql, new TinhMapper(), name);
        return tinh.orElse(null);
    }

    // ================= FIND BY ID =================
    @Override
    public Tinh findTinhById(int id) {
        String sql = "SELECT * FROM tinh WHERE ma_tinh = ?";
        Optional<Tinh> tinh = queryOne(sql, new TinhMapper(), id);
        return tinh.orElse(null);
    }

    // ================= FIND ALL =================
    @Override
    public List<Tinh> findAllTinh() {
        String sql = "SELECT * FROM tinh ORDER BY ten_tinh";
        return query(sql, new TinhMapper());
    }
}
