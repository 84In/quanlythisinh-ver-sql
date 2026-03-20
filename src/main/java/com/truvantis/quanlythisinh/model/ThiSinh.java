package com.truvantis.quanlythisinh.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Đại diện cho thực thể Thí Sinh trong hệ thống.
 *
 * Lớp này chứa toàn bộ thông tin nghiệp vụ của một thí sinh,
 * bao gồm dữ liệu cá nhân, điểm số và các mốc thời gian hệ thống.
 *
 * Việc implement {@link Serializable} cho phép đối tượng
 * được lưu trữ hoặc truyền tải khi cần thiết.
 */
public class ThiSinh implements Serializable {

    /** Mã định danh duy nhất của thí sinh */
    private int maThiSinh;

    /** Họ và tên thí sinh */
    private String tenThiSinh;

    /** Quê quán (tỉnh/thành) của thí sinh */
    private Tinh queQuan;

    /** Ngày sinh của thí sinh */
    private Date ngaySinh;

    /** Giới tính (true: nam, false: nữ) */
    private boolean gioiTinh;

    /** Điểm môn thứ nhất */
    private float diemMon1;

    /** Điểm môn thứ hai */
    private float diemMon2;

    /** Điểm môn thứ ba */
    private float diemMon3;

    /** Thời điểm tạo bản ghi */
    private Timestamp createdAt;

    /** Thời điểm cập nhật bản ghi gần nhất */
    private Timestamp updatedAt;

    /**
     * Constructor khởi tạo đầy đủ thông tin nghiệp vụ của thí sinh.
     *
     * @param maThiSinh  mã thí sinh
     * @param tenThiSinh tên thí sinh
     * @param queQuan    tỉnh/thành quê quán
     * @param ngaySinh   ngày sinh
     * @param gioiTinh   giới tính
     * @param diemMon1   điểm môn 1
     * @param diemMon2   điểm môn 2
     * @param diemMon3   điểm môn 3
     */
    public ThiSinh(int maThiSinh,
            String tenThiSinh,
            Tinh queQuan,
            Date ngaySinh,
            boolean gioiTinh,
            float diemMon1,
            float diemMon2,
            float diemMon3) {
        this.maThiSinh = maThiSinh;
        this.tenThiSinh = tenThiSinh;
        this.queQuan = queQuan;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diemMon1 = diemMon1;
        this.diemMon2 = diemMon2;
        this.diemMon3 = diemMon3;
    }

    public int getMaThiSinh() {
        return maThiSinh;
    }

    public void setMaThiSinh(int maThiSinh) {
        this.maThiSinh = maThiSinh;
    }

    public String getTenThiSinh() {
        return tenThiSinh;
    }

    public void setTenThiSinh(String tenThiSinh) {
        this.tenThiSinh = tenThiSinh;
    }

    public Tinh getQueQuan() {
        return queQuan;
    }

    public void setQueQuan(Tinh queQuan) {
        this.queQuan = queQuan;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public float getDiemMon1() {
        return diemMon1;
    }

    public void setDiemMon1(float diemMon1) {
        this.diemMon1 = diemMon1;
    }

    public float getDiemMon2() {
        return diemMon2;
    }

    public void setDiemMon2(float diemMon2) {
        this.diemMon2 = diemMon2;
    }

    public float getDiemMon3() {
        return diemMon3;
    }

    public void setDiemMon3(float diemMon3) {
        this.diemMon3 = diemMon3;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
