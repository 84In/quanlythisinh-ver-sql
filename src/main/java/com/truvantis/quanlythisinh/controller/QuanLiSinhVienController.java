package com.truvantis.quanlythisinh.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import com.truvantis.quanlythisinh.model.ThiSinh;
import com.truvantis.quanlythisinh.model.Tinh;
import com.truvantis.quanlythisinh.service.impl.ThiSinhService;
import com.truvantis.quanlythisinh.service.impl.TinhService;
import com.truvantis.quanlythisinh.view.impl.QuanLySinhVienView;

public class QuanLiSinhVienController implements ActionListener {
    private QuanLySinhVienView view;
    private ThiSinhService thiSinhService;
    private TinhService tinhService;

    // Constructor để nhận đối tượng View, giúp Controller điều khiển được giao diện
    public QuanLiSinhVienController(QuanLySinhVienView view) {
        this.view = view;
        this.thiSinhService = new ThiSinhService();
        this.tinhService = new TinhService();

        // Lấy danh sách tỉnh từ Service
        this.view.khoiTaoComboBoxTinh(this.tinhService.getAllTinh());
        this.view.khoiTaoBangDuLieu(this.thiSinhService.getAllThiSinh());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lấy tên lệnh từ nút bấm (ActionCommand)
        String cm = e.getActionCommand();

        // In ra console để debug xem nút đã hoạt động chưa
        System.out.println("Bạn vừa nhấn nút: " + cm);

        // Điều hướng các phương thức xử lý trong View
        switch (cm) {
            case "Thêm":
                // Gọi phương thức xử lý logic thêm/cập nhật từ View

                // this.view.themHoacCapNhatThiSinh(this.view.layThongTinTuForm());
                long maThiSinh = thiSinhService.insert(this.view.getThiSinhTuForm());

                if (maThiSinh != -1) {
                    this.view.hienThiThongBao("Thêm thí sinh thành công với mã: " + maThiSinh);
                    this.view.themHoacCapNhatThiSinh(thiSinhService.findById((int) maThiSinh));
                } else {
                    this.view.hienThiThongBao("Có lỗi xảy ra khi thêm thí sinh.");
                }

                break;
            // Trong lớp QuanLiSinhVienController, đoạn xử lý sự kiện "Xóa"
            case "Xóa":
                // 1. Kiểm tra xem người dùng đã chọn dòng nào trong bảng chưa
                int row = this.view.getSelectedRowIndex();

                if (row >= 0) {
                    // 2. Sử dụng Interface để gọi hộp thoại xác nhận từ View
                    // Việc này giúp Controller không bị phụ thuộc vào JOptionPane (Swing)
                    if (this.view.confirmAction("Bạn có chắc chắn muốn xóa thí sinh này không?")) {

                        // 3. Lấy mã thí sinh từ form thông qua View
                        int maThiSinh1 = this.view.layMaThiSinhTuForm();

                        // 4. Gọi Service để xử lý logic xóa trong Database
                        // Giả sử delete trả về boolean thành công hay thất bại
                        boolean isDeleted = thiSinhService.delete(maThiSinh1);

                        if (isDeleted) {
                            // 5. Nếu xóa thành công trong DB, mới xóa trên giao diện
                            this.view.removeRowFromTable(row);
                            this.view.showMessage("Đã xóa thí sinh thành công!");
                        } else {
                            this.view.showMessage("Lỗi: Không thể xóa thí sinh khỏi cơ sở dữ liệu.");
                        }
                    }
                } else {
                    // 6. Thông báo nếu chưa chọn thí sinh
                    this.view.showMessage("Vui lòng chọn một thí sinh trong bảng để xóa!");
                }
                break;
            case "Lưu":
                this.view.thucHienSaveFile();
                break;
            case "Tìm":
                Map.Entry<Tinh, String> criteria = this.view.layDuLieuTim();
                try {
                    List<ThiSinh> ketQuaTimKiem = thiSinhService.timKiemTheoTenTinhHoacMaThiSinh(
                            criteria.getKey().getTenTinh(),
                            Integer.parseInt(criteria.getValue()));
                    this.view.refreshTable(ketQuaTimKiem);
                } catch (Exception e1) {
                    this.view.showMessage("Có lỗi xảy ra khi tìm kiếm.");
                    e1.printStackTrace();
                }
                break;
            case "Hủy Tìm":
                this.view.huyTim();
                break;
            case "Xóa Form":
                this.view.xoaForm();
                break;
            case "Về chúng tôi":
                this.view.hienThiAbout();
                break;
            case "Thoát":
                this.view.thoatKhoiChuongTrinh();
                break;
            case "Làm mới":
                // 1. Lấy dữ liệu mới nhất từ Database thông qua Service
                List<ThiSinh> danhSachMoi = thiSinhService.getAllThiSinh();

                // 2. Bảo View đổ lại dữ liệu
                this.view.refreshTable(danhSachMoi);

                // 3. Thông báo cho người dùng
                this.view.showMessage("Đã cập nhật dữ liệu mới nhất!");
                break;
            default:
                break;
        }
    }
}