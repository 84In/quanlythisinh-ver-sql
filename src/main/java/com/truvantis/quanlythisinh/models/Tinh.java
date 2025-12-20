package com.truvantis.quanlythisinh.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Đại diện cho thực thể Tỉnh/Thành trong hệ thống.
 *
 * Lớp này được sử dụng để biểu diễn thông tin tỉnh/thành,
 * đồng thời đóng vai trò là đối tượng tham chiếu
 * trong các thực thể khác như {@link ThiSinh}.
 *
 * Việc implement {@link Serializable} cho phép đối tượng
 * được lưu trữ hoặc truyền tải khi cần thiết.
 */
public class Tinh implements Serializable {

    /** Mã định danh duy nhất của tỉnh */
    private int maTinh;

    /** Tên tỉnh/thành */
    private String tenTinh;

    /** Thời điểm tạo bản ghi */
    private Timestamp createdAt;

    /** Thời điểm cập nhật bản ghi gần nhất */
    private Timestamp updatedAt;

    /**
     * Constructor mặc định.
     * <p>
     * Phục vụ cho việc khởi tạo rỗng hoặc ánh xạ dữ liệu.
     * </p>
     */
    public Tinh() {
    }

    /**
     * Constructor khởi tạo thông tin cơ bản của tỉnh/thành.
     *
     * @param maTinh  mã tỉnh
     * @param tenTinh tên tỉnh/thành
     */
    public Tinh(int maTinh, String tenTinh) {
        this.maTinh = maTinh;
        this.tenTinh = tenTinh;
    }

    /**
     * Constructor khởi tạo đầy đủ thông tin tỉnh/thành.
     *
     * @param maTinh    mã tỉnh
     * @param tenTinh   tên tỉnh/thành
     * @param createdAt thời điểm tạo
     * @param updatedAt thời điểm cập nhật
     */
    public Tinh(int maTinh, String tenTinh, Timestamp createdAt, Timestamp updatedAt) {
        this.maTinh = maTinh;
        this.tenTinh = tenTinh;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getMaTinh() {
        return maTinh;
    }

    public void setMaTinh(int maTinh) {
        this.maTinh = maTinh;
    }

    public String getTenTinh() {
        return tenTinh;
    }

    public void setTenTinh(String tenTinh) {
        this.tenTinh = tenTinh;
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

    /**
     * So sánh hai đối tượng {@link Tinh} dựa trên mã tỉnh.
     *
     * @param o đối tượng cần so sánh
     * @return {@code true} nếu cùng mã tỉnh, ngược lại {@code false}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tinh)) return false;
        Tinh tinh = (Tinh) o;
        return maTinh == tinh.maTinh;
    }

    /**
     * Sinh mã hash dựa trên mã tỉnh.
     *
     * @return hash code của đối tượng
     */
    @Override
    public int hashCode() {
        return Objects.hash(maTinh);
    }

    /**
     * Trả về tên tỉnh/thành.
     * Hữu ích khi hiển thị trong {@code JComboBox},
     * {@code JList} hoặc log/debug.
     *
     * @return tên tỉnh/thành
     */
    @Override
    public String toString() {
        return tenTinh;
    }
}
