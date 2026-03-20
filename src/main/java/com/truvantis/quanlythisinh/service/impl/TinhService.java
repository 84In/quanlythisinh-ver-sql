package com.truvantis.quanlythisinh.service.impl;

import java.util.List;

import com.truvantis.quanlythisinh.dto.impl.TinhDao;
import com.truvantis.quanlythisinh.model.Tinh;
import com.truvantis.quanlythisinh.service.TinhServiceInterface;

/**
 * Service xử lý logic nghiệp vụ liên quan đến tỉnh/thành.
 *
 * <p>
 * Đóng vai trò trung gian giữa Controller và DAO, đảm bảo phân tách rõ
 * trách nhiệm giữa phần hiển thị và truy cập dữ liệu.
 * </p>
 */
public class TinhService implements TinhServiceInterface {

    private TinhDao tinhDao;

    public TinhService() {
        this.tinhDao = TinhDao.getInstance();
    }

    /**
     * Chèn mới tỉnh/thành vào cơ sở dữ liệu.
     *
     * <p>
     * Hiện tại chưa có yêu cầu thêm tỉnh mới từ giao diện, nên phương thức này
     * chưa được cài đặt.
     * </p>
     *
     * @param tinh tỉnh/thành cần thêm
     * @return {@code true} nếu thêm thành công, {@code false} nếu không.
     */
    @Override
    public boolean insert(Tinh tinh) {
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    /**
     * Cập nhật thông tin tỉnh/thành.
     *
     * <p>
     * Hiện tại chưa có giao diện cập nhật tỉnh nên phương thức được giữ nguyên để
     * mở rộng sau này.
     * </p>
     *
     * @param tinh tỉnh/thành cần cập nhật
     * @return {@code true} nếu cập nhật thành công, {@code false} nếu không.
     */
    @Override
    public boolean update(Tinh tinh) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    /**
     * Xóa tỉnh/thành theo mã.
     *
     * @param maTinh mã tỉnh cần xóa
     * @return {@code true} nếu xóa thành công, {@code false} nếu không.
     */
    @Override
    public boolean delete(int maTinh) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    /**
     * Tìm tỉnh/thành theo tên.
     *
     * @param name tên tỉnh/thành cần tìm
     * @return đối tượng {@link Tinh} nếu tìm thấy, hoặc {@code null} nếu không.
     */
    @Override
    public Tinh findByName(String name) {
        Tinh tinh = tinhDao.findTinhByName(name);
        if (tinh == null) {
            System.out.println("Không tìm thấy tỉnh với tên: " + name);
            return null;
        }
        return tinh;
    }

    /**
     * Lấy danh sách toàn bộ tỉnh/thành trong cơ sở dữ liệu.
     *
     * @return danh sách các {@link Tinh}.
     */
    @Override
    public List<Tinh> getAllTinh() {
        return tinhDao.findAllTinh();
    }

}
