package com.truvantis.quanlythisinh.dto.impl;

import com.truvantis.quanlythisinh.dto.GenericDaoInterface;
import com.truvantis.quanlythisinh.mapper.RowMappersInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GenericDao<T> implements GenericDaoInterface<T> {

    // Duong dan ket noi co so du lieu
    private static final String URL = "jdbc:mysql://localhost:3306/quanlythisinh?useSSL=false&serverTimezone=Asia/Ho_Chi_Minh";

    // User truy cap co so du lieu
    private static final String USER = "root";

    // Mat khay truy cap co so du lieu
    private static final String PASSWORD = "";

    static {
        try {
            // Khoi tao class noi ket
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Bat loi khi co loi trong khoi tao class noi ket
            throw new RuntimeException("Không load được MySQL Driver", e);
        }
    }

    // Khoi tao noi ket
    protected Connection getConnection() throws SQLException {
        // Tra ve noi ket voi co so du lieu
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // ================= QUERY LIST =================
    @Override
    public List<T> query(String sql, RowMappersInterface<T> rowMapper, Object... params) {
        List<T> results = new ArrayList<>();

        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setParameters(ps, params);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    results.add(rowMapper.mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query failed: " + sql, e);
        }

        return results;
    }

    // ================= QUERY ONE =================
    @Override
    public Optional<T> queryOne(String sql, RowMappersInterface<T> rowMapper, Object... params) {
        List<T> list = query(sql, rowMapper, params);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    // ================= UPDATE / DELETE =================
    @Override
    public int update(String sql, Object... params) {
        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setParameters(ps, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Update failed: " + sql, e);
        }
    }

    // ================= INSERT =================
    @Override
    public long insert(String sql, Object... params) {
        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            conn.setAutoCommit(false);
            setParameters(ps, params);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                conn.commit();
                return rs.next() ? rs.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Insert failed: " + sql, e);
        }
    }

    // ================= COUNT =================
    @Override
    public long count(String sql, Object... params) {
        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            setParameters(ps, params);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getLong(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Count failed: " + sql, e);
        }
    }

    // ================= PARAM =================
    private void setParameters(PreparedStatement ps, Object... params)
            throws SQLException {

        if (params == null)
            return;

        for (int i = 0; i < params.length; i++) {
            Object p = params[i];
            int idx = i + 1;

            if (p == null) {
                ps.setObject(idx, null);
            } else if (p instanceof Integer) {
                ps.setInt(idx, (Integer) p);
            } else if (p instanceof Long) {
                ps.setLong(idx, (Long) p);
            } else if (p instanceof String) {
                ps.setString(idx, (String) p);
            } else if (p instanceof Boolean) {
                ps.setBoolean(idx, (Boolean) p);
            } else if (p instanceof Float) {
                ps.setFloat(idx, (Float) p);
            } else if (p instanceof Double) {
                ps.setDouble(idx, (Double) p);
            } else if (p instanceof java.util.Date) {
                ps.setDate(idx, new java.sql.Date(((java.util.Date) p).getTime()));
            } else if (p instanceof Timestamp) {
                ps.setTimestamp(idx, (Timestamp) p);
            } else {
                ps.setObject(idx, p);
            }
        }
    }
}
