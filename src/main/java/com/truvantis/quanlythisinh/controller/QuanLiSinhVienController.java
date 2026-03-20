package com.truvantis.quanlythisinh.controller; // Định nghĩa package chứa Controller

import java.awt.event.ActionEvent; // Sự kiện hành động (nhấn nút, menu, ...)
import java.awt.event.ActionListener; // Interface lắng nghe ActionEvent
import java.util.List; // Danh sách động
import java.util.Map; // Map key/value

import com.truvantis.quanlythisinh.model.ThiSinh; // Model thí sinh
import com.truvantis.quanlythisinh.model.Tinh; // Model tỉnh
import com.truvantis.quanlythisinh.service.impl.ThiSinhService; // Service xử lý nghiệp vụ thí sinh
import com.truvantis.quanlythisinh.service.impl.TinhService; // Service xử lý nghiệp vụ tỉnh
import com.truvantis.quanlythisinh.view.impl.QuanLySinhVienView; // View giao diện chính

/**
 * Controller điều phối giữa View và Service (Model).
 *
 * <p>
 * Lắng nghe các sự kiện từ giao diện (View) và chuyển tiếp
 * các yêu cầu tới service tương ứng.
 * </p>
 */
public class QuanLiSinhVienController implements ActionListener {
    private QuanLySinhVienView view; // Tham chiếu đến View để điều khiển giao diện
    private ThiSinhService thiSinhService; // Service quản lý thí sinh
    private TinhService tinhService; // Service quản lý tỉnh

    /**
     * Khởi tạo Controller và đồng bộ dữ liệu ban đầu từ Service lên View.
     *
     * @param view đối tượng View sẽ được điều khiển bởi Controller
     */
    public QuanLiSinhVienController(QuanLySinhVienView view) {
        this.view = view; // Lưu tham chiếu View
        this.thiSinhService = new ThiSinhService(); // Khởi tạo service thí sinh
        this.tinhService = new TinhService(); // Khởi tạo service tỉnh

        // Khởi tạo giao diện với dữ liệu ban đầu
        // Lấy danh sách tỉnh từ Service để đổ vào combobox
        this.view.khoiTaoComboBoxTinh(this.tinhService.getAllTinh());
        // Lấy danh sách thí sinh từ Service để hiển thị trong bảng
        this.view.khoiTaoBangDuLieu(this.thiSinhService.getAllThiSinh());
    }

    /**
     * Xử lý sự kiện từ giao diện.
     *
     * <p>
     * Dựa vào {@code ActionCommand} của nguồn sự kiện để xác định
     * thao tác cần thực hiện (Thêm, Xóa, Tìm, ...).
     * </p>
     *
     * @param e sự kiện Action được phát ra từ UI
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Lấy tên lệnh từ nút bấm (ActionCommand)
        String cm = e.getActionCommand(); // Mã lệnh sẽ xác định hành động cần thực hiện
        // In ra console để debug xem nút đã hoạt động chưa
        System.out.println("Bạn vừa nhấn nút: " + cm); // In mã lệnh ra log

        // Điều hướng các phương thức xử lý trong View dựa trên lệnh nhận được
        switch (cm) { // Chọn khối xử lý tương ứng với mã lệnh
            case "THEM":
                // Lấy dữ liệu thí sinh từ form và gọi service thêm vào DB
                long maThiSinh = thiSinhService.insert(this.view.getThiSinhTuForm()); // Trả về mã mới hoặc -1 nếu lỗi

                if (maThiSinh != -1) {
                    // Thêm thành công: hiển thị thông báo và cập nhật form
                    this.view.hienThiThongBao("Thêm thí sinh thành công với mã: " + maThiSinh);
                    this.view.themHoacCapNhatThiSinh(thiSinhService.findById((int) maThiSinh));
                } else {
                    // Thêm thất bại: hiển thị thông báo lỗi
                    this.view.hienThiThongBao("Có lỗi xảy ra khi thêm thí sinh.");
                }

                break;
            // Trong lớp QuanLiSinhVienController, đoạn xử lý sự kiện "Xóa"
            case "XOA":
                // Xóa thí sinh: kiểm tra có hàng đang được chọn trên bảng hay không
                int row = this.view.getSelectedRowIndex(); // Lấy chỉ số hàng đang chọn

                if (row >= 0) {
                    // Hỏi người dùng xác nhận trước khi xóa
                    if (this.view.confirmAction("Bạn có chắc chắn muốn xóa thí sinh này không?")) {
                        // Lấy mã thí sinh từ form (hoặc hàng đang chọn)
                        int maThiSinh1 = this.view.layMaThiSinhTuForm();

                        // Gọi service để xóa thí sinh trong cơ sở dữ liệu
                        boolean isDeleted = thiSinhService.delete(maThiSinh1);

                        if (isDeleted) {
                            // Xóa thành công trong DB: cập nhật giao diện
                            this.view.removeRowFromTable(row);
                            this.view.showMessage("Đã xóa thí sinh thành công!");
                        } else {
                            // Xóa thất bại: hiển thị thông báo lỗi
                            this.view.showMessage("Lỗi: Không thể xóa thí sinh khỏi cơ sở dữ liệu.");
                        }
                    }
                } else {
                    // Chưa chọn thí sinh: yêu cầu chọn trước khi xóa
                    this.view.showMessage("Vui lòng chọn một thí sinh trong bảng để xóa!");
                }
                break;
            case "LUU":
                // Lưu dữ liệu hiện tại ra file (có thể là .csv hoặc định dạng khác)
                this.view.thucHienSaveFile();
                break;
            case "TIM":
                // Lấy điều kiện tìm kiếm (tỉnh hoặc mã thí sinh) từ giao diện
                Map.Entry<Tinh, String> criteria = this.view.layDuLieuTim();
                try {
                    // Gọi service tìm kiếm theo tên tỉnh hoặc mã thí sinh
                    List<ThiSinh> ketQuaTimKiem = thiSinhService.timKiemTheoTenTinhHoacMaThiSinh(
                            criteria.getKey().getTenTinh(),
                            Integer.parseInt(criteria.getValue()));
                    // Cập nhật lại bảng với kết quả tìm được
                    this.view.refreshTable(ketQuaTimKiem);
                } catch (Exception e1) {
                    // Nếu có lỗi xảy ra trong quá trình tìm kiếm thì hiện thông báo
                    this.view.showMessage("Có lỗi xảy ra khi tìm kiếm.");
                    e1.printStackTrace();
                }
                break;
            case "HUY_TIM":
                // Hủy tìm kiếm và hiển thị lại toàn bộ danh sách thí sinh
                this.view.huyTim();
                this.view.refreshTable(this.thiSinhService.getAllThiSinh());
                break;
            case "XOA_FORM":
                // Xóa toàn bộ dữ liệu trên form (reset form)
                this.view.xoaForm();
                break;
            case "VE_CHUNG_TOI":
                // Hiển thị thông tin giới thiệu về ứng dụng
                this.view.hienThiAbout();
                break;
            case "THOAT":
                // Thoát khỏi ứng dụng
                this.view.thoatKhoiChuongTrinh();
                break;
            case "LAM_MOI":
                // Làm mới dữ liệu dựa trên điều kiện tìm kiếm hiện tại (nếu có)
                Map.Entry<Tinh, String> criteria1 = this.view.layDuLieuTim(); // Lấy điều kiện tìm kiếm đang dùng
                try {
                    // Thực hiện tìm kiếm với điều kiện hiện tại để có dữ liệu mới nhất
                    List<ThiSinh> ketQuaTimKiem = thiSinhService.timKiemTheoTenTinhHoacMaThiSinh(
                            criteria1.getKey().getTenTinh(),
                            Integer.parseInt(criteria1.getValue())); // Chuyển chuỗi thành số nguyên
                    // Cập nhật bảng với kết quả tìm kiếm mới
                    this.view.refreshTable(ketQuaTimKiem);
                } catch (Exception e1) {
                    // Nếu xảy ra lỗi trong quá trình làm mới, hiển thị thông báo
                    this.view.showMessage("Có lỗi xảy ra.");
                    e1.printStackTrace();
                }
                break;
            case "CAP_NHAT":
                // Lấy dữ liệu thí sinh từ form để cập nhật
                ThiSinh ts = this.view.getThiSinhTuForm();

                // Gọi service để cập nhật thông tin thí sinh trong cơ sở dữ liệu
                boolean isUpdated = thiSinhService.update(ts);

                if (isUpdated) {
                    // Nếu cập nhật thành công, hiển thị thông báo và làm mới bảng
                    this.view.hienThiThongBao("Cập nhật thí sinh thành công!");
                    this.view.refreshTable(this.thiSinhService.getAllThiSinh());
                    // Xóa form sau khi cập nhật để tránh trùng lặp dữ liệu
                    this.view.xoaForm();
                } else {
                    // Nếu cập nhật thất bại thì hiển thị thông báo lỗi
                    this.view.hienThiThongBao("Có lỗi xảy ra khi cập nhật thí sinh.");
                }
                break;
            default:
                break;
        }
    }
}