package com.truvantis.quanlythisinh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tiện ích xử lý ngày tháng (chuyển đổi giữa {@link java.util.Date} và chuỗi).
 *
 * <p>
 * Định dạng mặc định là {@code dd/MM/yyyy} (chiều ngày trước).
 * </p>
 */
public class DateUtil {
    // Định dạng ngày tháng chuẩn Việt Nam
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Chuyển từ String (người dùng nhập) sang java.util.Date
     */
    public static Date toDate(String dateStr) {
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            // Nếu sai định dạng, trả về null hoặc báo lỗi
            System.err.println("Lỗi định dạng ngày: " + dateStr);
            return null;
        }
    }

    /**
     * Chuyển từ java.util.Date sang String (để hiển thị lên bảng/form)
     */
    public static String toString(Date date) {
        if (date == null)
            return "";
        return sdf.format(date);
    }

    /**
     * Chuyển từ java.util.Date sang java.sql.Date (để lưu vào MySQL)
     */
    public static java.sql.Date toSqlDate(Date utilDate) {
        if (utilDate == null)
            return null;
        return new java.sql.Date(utilDate.getTime());
    }

    /**
     * Chuyển từ java.sql.Date sang java.util.Date (khi lấy từ Database lên)
     */
    public static Date fromSqlDate(java.sql.Date sqlDate) {
        if (sqlDate == null)
            return null;
        return new Date(sqlDate.getTime());
    }
}