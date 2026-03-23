package com.truvantis.quanlythisinh.controller; // Định nghĩa package chứa Controller

import java.awt.event.ActionEvent; // Sự kiện hành động (nhấn nút, menu, ...)
import java.awt.event.ActionListener; // Interface lắng nghe ActionEvent
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List; // Danh sách động
import java.util.Map; // Map key/value

import javax.swing.SwingWorker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
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
            case "TIM":
                // Lấy điều kiện tìm kiếm (tỉnh hoặc mã thí sinh) từ giao diện
                Map.Entry<Tinh, String> criteria = this.view.layDuLieuTim();
                try {
                    int maTim = -1;
                    if (criteria.getValue() != null && !criteria.getValue().trim().isEmpty()) {
                        maTim = Integer.parseInt(criteria.getValue().trim());
                    }
                    // Gọi service tìm kiếm theo tên tỉnh hoặc mã thí sinh
                    List<ThiSinh> ketQuaTimKiem = thiSinhService.timKiemTheoTenTinhHoacMaThiSinh(
                            criteria.getKey().getTenTinh(),
                            maTim);
                    // Cập nhật lại bảng với kết quả tìm được
                    this.view.refreshTable(ketQuaTimKiem);
                } catch (NumberFormatException ex) {
                    this.view.showMessage("Mã thí sinh phải là số nguyên hợp lệ.");
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
                    int maTim1 = -1;
                    if (criteria1.getValue() != null && !criteria1.getValue().trim().isEmpty()) {
                        maTim1 = Integer.parseInt(criteria1.getValue().trim());
                    }
                    // Thực hiện tìm kiếm với điều kiện hiện tại để có dữ liệu mới nhất
                    List<ThiSinh> ketQuaTimKiem = thiSinhService.timKiemTheoTenTinhHoacMaThiSinh(
                            criteria1.getKey().getTenTinh(),
                            maTim1);
                    // Cập nhật bảng với kết quả tìm kiếm mới
                    this.view.refreshTable(ketQuaTimKiem);
                } catch (NumberFormatException ex) {
                    this.view.showMessage("Mã thí sinh phải là số nguyên hợp lệ.");
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
            case "XUAT_FILE":
                // Mở dialog chọn file để xuất
                String filePath = this.view.showExportDialog();
                if (filePath != null) {
                    ArrayList<ThiSinh> ketQuaTimKiem = null;
                    try {
                        Map.Entry<Tinh, String> criteria2 = this.view.layDuLieuTim(); // Lấy điều kiện tìm kiếm đang
                                                                                      // dùng
                        try {
                            // Thực hiện tìm kiếm với điều kiện hiện tại để có dữ liệu mới nhất
                            ketQuaTimKiem = thiSinhService.timKiemTheoTenTinhHoacMaThiSinh(
                                    criteria2.getKey().getTenTinh(),
                                    Integer.parseInt(criteria2.getValue())); // Chuyển chuỗi thành số nguyên
                            // Cập nhật bảng với kết quả tìm kiếm mới

                        } catch (Exception e1) {
                            // Nếu xảy ra lỗi trong quá trình làm mới, hiển thị thông báo
                            this.view.showMessage("Có lỗi xảy ra.");
                            e1.printStackTrace();
                        }
                        thiSinhService.exportToFile(filePath, ketQuaTimKiem);
                        this.view.showMessage("Xuất file thành công!");
                    } catch (Exception e3) {
                        this.view.showMessage("Lỗi khi xuất file: " + e3.getMessage());
                    }
                }
                break;
            case "NHAP_FILE":
                // Mở dialog chọn file để nhập
                String importFilePath = this.view.showImportDialog();
                if (importFilePath != null) {
                    // Chạy import bất đồng bộ với progress
                    SwingWorker<Void, Integer> importWorker = new SwingWorker<Void, Integer>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            // Đếm số dòng để set max progress
                            int totalLines = 0;
                            if (importFilePath.toLowerCase().endsWith(".csv")) {
                                try (LineNumberReader reader = new LineNumberReader(
                                        new FileReader(importFilePath))) {
                                    reader.skip(Long.MAX_VALUE);
                                    totalLines = reader.getLineNumber() - 2; // Trừ 2 dòng header
                                }
                            } else if (importFilePath.toLowerCase().endsWith(".xlsx")) {
                                try (FileInputStream fis = new FileInputStream(importFilePath);
                                        Workbook workbook = new XSSFWorkbook(fis)) {
                                    Sheet sheet = workbook.getSheetAt(0);
                                    totalLines = sheet.getLastRowNum() - 2; // Trừ 3 dòng header (0-based)
                                }
                            }
                            publish(0); // Bắt đầu

                            // Import với progress
                            if (importFilePath.toLowerCase().endsWith(".csv")) {
                                // Import CSV
                                try (CSVReader reader = new CSVReader(
                                        new FileReader(importFilePath))) {
                                    SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
                                            "dd/MM/yyyy");
                                    String[] line;
                                    int lineIndex = 0;
                                    int processed = 0;

                                    while ((line = reader.readNext()) != null) {
                                        lineIndex++;
                                        if (lineIndex <= 2)
                                            continue; // Bỏ qua 2 dòng đầu

                                        if (line.length >= 8) {
                                            ThiSinh ts = thiSinhService.parseThiSinhFromData(line,
                                                    dateFormat);
                                            if (ts != null) {
                                                ThiSinh existing = thiSinhService
                                                        .findById(ts.getMaThiSinh());
                                                if (existing != null) {
                                                    thiSinhService.update(ts);
                                                } else {
                                                    thiSinhService.insert(ts);
                                                }
                                            }
                                        }
                                        processed++;
                                        if (totalLines > 0 && processed % 10 == 0) { // Publish mỗi 10 dòng
                                            publish(Math.min(processed * 100 / totalLines, 99));
                                        }
                                    }
                                    publish(100);
                                }
                            } else if (importFilePath.toLowerCase().endsWith(".xlsx")) {
                                // Import XLSX
                                try (FileInputStream fis = new FileInputStream(importFilePath);
                                        Workbook workbook = new XSSFWorkbook(
                                                fis)) {
                                    Sheet sheet = workbook.getSheetAt(0);
                                    SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
                                            "dd/MM/yyyy");
                                    int processed = 0;

                                    for (int i = 3; i <= sheet.getLastRowNum(); i++) {
                                        Row row = sheet.getRow(i);
                                        if (row != null) {
                                            String[] data = new String[8];
                                            for (int j = 0; j < 8; j++) {
                                                Cell cell = row.getCell(j);
                                                data[j] = cell != null ? cell.toString() : "";
                                            }
                                            ThiSinh ts = thiSinhService.parseThiSinhFromData(data,
                                                    dateFormat);
                                            if (ts != null) {
                                                ThiSinh existing = thiSinhService
                                                        .findById(ts.getMaThiSinh());
                                                if (existing != null) {
                                                    thiSinhService.update(ts);
                                                } else {
                                                    thiSinhService.insert(ts);
                                                }
                                            }
                                        }
                                        processed++;
                                        if (totalLines > 0 && processed % 10 == 0) {
                                            publish(Math.min(processed * 100 / totalLines, 99));
                                        }
                                    }
                                    publish(100);
                                }
                            }
                            return null;
                        }

                        @Override
                        protected void process(List<Integer> chunks) {
                            // Cập nhật progress
                            for (int progress : chunks) {
                                view.updateProgress(progress);
                            }
                        }

                        @Override
                        protected void done() {
                            view.hideProgressDialog();
                            try {
                                get(); // Kiểm tra exception
                                view.refreshTable(thiSinhService.getAllThiSinh());
                                view.showMessage("Nhập file thành công!");
                            } catch (Exception e) {
                                view.showMessage("Lỗi khi nhập file: " + e.getMessage());
                            }
                        }
                    };
                    importWorker.execute();
                    this.view.showProgressDialog();
                }
                break;
            default:
                break;
        }
    }

}