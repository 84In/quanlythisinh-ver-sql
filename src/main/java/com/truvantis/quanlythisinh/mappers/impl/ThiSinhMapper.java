package com.truvantis.quanlythisinh.mappers.impl;

import com.truvantis.quanlythisinh.mappers.RowMappersInterface;
import com.truvantis.quanlythisinh.models.ThiSinh;
import com.truvantis.quanlythisinh.models.Tinh;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThiSinhMapper implements RowMappersInterface<ThiSinh> {

    @Override
    public ThiSinh mapRow(ResultSet rs) throws SQLException {
        Tinh tinh = new Tinh(
                rs.getInt("ma_tinh"),
                rs.getString("ten_tinh")
        );

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

        ts.setCreatedAt(rs.getTimestamp("created_at"));
        ts.setUpdatedAt(rs.getTimestamp("updated_at"));

        return ts;
    }
}
