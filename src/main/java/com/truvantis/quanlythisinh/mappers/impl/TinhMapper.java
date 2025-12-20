package com.truvantis.quanlythisinh.mappers.impl;

import com.truvantis.quanlythisinh.mappers.RowMappersInterface;
import com.truvantis.quanlythisinh.models.Tinh;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TinhMapper implements RowMappersInterface<Tinh> {


    @Override
    public Tinh mapRow(ResultSet rs) throws SQLException {
        return new Tinh(
                rs.getInt("ma_tinh"),
                rs.getString("ten_tinh"),
                rs.getTimestamp("created_at"),
                rs.getTimestamp("updated_at")
        );
    }
}
