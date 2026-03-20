package com.truvantis.quanlythisinh.service.impl;

import java.util.ArrayList;

import com.truvantis.quanlythisinh.dto.impl.ThiSinhDao;
import com.truvantis.quanlythisinh.model.ThiSinh;
import com.truvantis.quanlythisinh.model.Tinh;
import com.truvantis.quanlythisinh.service.ThiSinhServiceInterface;

/**
 * Service xử lý nghiệp vụ liên quan đến thí sinh.
 *
 * <p>
 * Đóng vai trò trung gian giữa controller và DAO, cung cấp các phương thức
 * truy vấn/ghi dữ liệu và logic xử lý trước khi lưu vào cơ sở dữ liệu.
 * </p>
 */
public class ThiSinhService implements ThiSinhServiceInterface {

    private ThiSinhDao thiSinhDao;
    private TinhService tinhService;

    public ThiSinhService() {
        this.thiSinhDao = ThiSinhDao.getInstance();
        this.tinhService = new TinhService();
    }

    /**
     * Lấy toàn bộ thí sinh từ cơ sở dữ liệu.
     *
     * @return danh sách thí sinh.
     */
    @Override
    public ArrayList<ThiSinh> getAllThiSinh() {
        return (ArrayList<ThiSinh>) thiSinhDao.findAll();
    }

    /**
     * Thêm mới một thí sinh.
     *
     * <p>
     * Trước khi lưu, phương thức tìm tỉnh tương ứng để đảm bảo tham chiếu
     * đúng {@link Tinh} trong cơ sở dữ liệu.
     * </p>
     *
     * @param ts thí sinh cần thêm
     * @return mã thí sinh mới (generated key) hoặc -1 nếu có lỗi.
     */
    @Override
    public long insert(ThiSinh ts) {

        Tinh tinh = tinhService.findByName(ts.getQueQuan().getTenTinh());
        try {
            ts.setQueQuan(tinh);
            return thiSinhDao.saveThiSinh(ts);
        } catch (Exception e) {
            e.printStackTrace();
            return -1; // Trả về -1 nếu có lỗi xảy ra
        }
    }

    /**
     * Cập nhật thông tin thí sinh.
     *
     * @param ts thí sinh cần cập nhật
     * @return {@code true} nếu cập nhật thành công, {@code false} nếu có lỗi.
     */
    @Override
    public boolean update(ThiSinh ts) {
        Tinh tinh = tinhService.findByName(ts.getQueQuan().getTenTinh());
        try {
            ts.setQueQuan(tinh);
            thiSinhDao.updateThiSinh(ts);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    /**
     * Xóa thí sinh theo mã.
     *
     * @param maThiSinh mã thí sinh cần xóa
     * @return luôn trả về {@code true} (hiện tại chưa kiểm tra lỗi chi tiết).
     */
    @Override
    public boolean delete(int maThiSinh) {
        thiSinhDao.deleteThiSinh(maThiSinh);
        return true;
    }

    /**
     * Tìm kiếm thí sinh theo tỉnh (hoặc tất cả) và/hoặc mã thí sinh.
     *
     * <p>
     * Nếu {@code tenTinh} là "-1" thì sẽ tìm theo mã thí sinh duy nhất.
     * Nếu {@code maThiSinh} là -1 thì sẽ tìm theo tỉnh (hoặc tất cả nếu tên tỉnh là
     * "-1").
     * </p>
     *
     * @param tenTinh   tên tỉnh hoặc "-1" để không lọc theo tỉnh
     * @param maThiSinh mã thí sinh hoặc -1 để không lọc theo mã
     * @return danh sách thí sinh thỏa mãn điều kiện
     */
    @Override
    public ArrayList<ThiSinh> timKiemTheoTenTinhHoacMaThiSinh(String tenTinh, int maThiSinh) {
        if (tenTinh.equals("-1")) {
            tenTinh = null;
        }
        Integer maTS = maThiSinh == -1 ? null : maThiSinh;
        return (ArrayList<ThiSinh>) thiSinhDao.findByTinhVaMaThiSinh(tenTinh, maTS);
    }

    /**
     * Tính tổng điểm 3 môn của thí sinh.
     *
     * <p>
     * Hiện tại chưa được sử dụng trong giao diện, phương thức này giữ lại
     * để mở rộng sau này (ví dụ: tính điểm xét tuyển).
     * </p>
     *
     * @param ts thí sinh cần tính tổng điểm
     * @return tổng điểm 3 môn
     */
    @Override
    public float tinhTongDiem(ThiSinh ts) {
        throw new UnsupportedOperationException("Unimplemented method 'tinhTongDiem'");
    }

    /**
     * Lấy thí sinh theo mã.
     *
     * @param maThiSinh mã thí sinh
     * @return đối tượng {@link ThiSinh} nếu tồn tại, hoặc {@code null}
     */
    @Override
    public ThiSinh findById(int maThiSinh) {
        return thiSinhDao.findById(maThiSinh);
    }

}
