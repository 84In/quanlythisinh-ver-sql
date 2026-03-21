package com.truvantis.quanlythisinh.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
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
        if (ts == null) {
            throw new IllegalArgumentException("ThiSinh must not be null");
        }
        // Tính tổng điểm của 3 môn (giả định hệ điểm thang 10)
        return ts.getDiemMon1() + ts.getDiemMon2() + ts.getDiemMon3();
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

    @Override
    public void exportToFile(String filePath, ArrayList<ThiSinh> ketQuaTimKiem) {
        // Kiểm tra file có tồn tại hay không
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Không thể tạo file: " + filePath, e);
            }
        }

        // Kiểm tra đuôi file để xuất đúng mẫu
        if (filePath.toLowerCase().endsWith(".csv")) {
            exportToCSV(filePath, ketQuaTimKiem);
        } else if (filePath.toLowerCase().endsWith(".xlsx")) {
            exportToXLSX(filePath, ketQuaTimKiem);
        } else {
            throw new IllegalArgumentException("Định dạng file không được hỗ trợ. Chỉ hỗ trợ .csv và .xlsx");
        }
    }

    private void exportToCSV(String filePath, List<ThiSinh> thiSinhList) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            // Ghi heading lớn (mặc dù CSV không hỗ trợ font lớn, nhưng ghi như text)
            writer.writeNext(new String[] { "DANH SÁCH THÍ SINH" });

            // Ghi header
            String[] header = { "MaThiSinh", "TenThiSinh", "QueQuan", "NgaySinh", "GioiTinh", "DiemMon1", "DiemMon2",
                    "DiemMon3" };
            writer.writeNext(header);

            // Định dạng ngày
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Ghi dữ liệu từng thí sinh
            for (ThiSinh ts : thiSinhList) {
                String[] data = {
                        String.valueOf(ts.getMaThiSinh()),
                        ts.getTenThiSinh(),
                        ts.getQueQuan() != null ? ts.getQueQuan().getTenTinh() : "",
                        ts.getNgaySinh() != null ? dateFormat.format(ts.getNgaySinh()) : "",
                        ts.isGioiTinh() ? "Nam" : "Nu",
                        ts.getDiemMon1() == 0.0f ? "" : String.valueOf(ts.getDiemMon1()),
                        ts.getDiemMon2() == 0.0f ? "" : String.valueOf(ts.getDiemMon2()),
                        ts.getDiemMon3() == 0.0f ? "" : String.valueOf(ts.getDiemMon3())
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xuất file CSV: " + filePath, e);
        }
    }

    private void exportToXLSX(String filePath, List<ThiSinh> thiSinhList) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("DanhSachThiSinh");

            // Tạo font lớn cho heading
            Font headingFont = workbook.createFont();
            headingFont.setFontHeightInPoints((short) 16);
            headingFont.setBold(true);

            CellStyle headingStyle = workbook.createCellStyle();
            headingStyle.setFont(headingFont);
            headingStyle.setAlignment(HorizontalAlignment.CENTER);

            // Merge cells cho heading
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 7)); // Merge từ cột 0 đến 7

            // Ghi heading
            Row headingRow = sheet.createRow(0);
            Cell headingCell = headingRow.createCell(0);
            headingCell.setCellValue("DANH SÁCH THÍ SINH");
            headingCell.setCellStyle(headingStyle);

            // Ghi header ở dòng 2
            Row headerRow = sheet.createRow(2);
            String[] headers = { "MaThiSinh", "TenThiSinh", "QueQuan", "NgaySinh", "GioiTinh", "DiemMon1", "DiemMon2",
                    "DiemMon3" };
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.MEDIUM);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Định dạng ngày
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            CellStyle cellStyle = workbook.createCellStyle();
            DataFormat df = workbook.createDataFormat();
            cellStyle.setDataFormat(df.getFormat("0.00"));
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);

            CellStyle style = workbook.createCellStyle();

            // Kẻ đường viền
            style.setBorderTop(BorderStyle.THIN);
            style.setBorderBottom(BorderStyle.THIN);
            style.setBorderLeft(BorderStyle.THIN);
            style.setBorderRight(BorderStyle.THIN);

            // Chỉnh màu đường viền (mặc định là đen)
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());

            // Ghi dữ liệu từ dòng 3
            int rowNum = 3;
            for (ThiSinh ts : thiSinhList) {
                Row row = sheet.createRow(rowNum++);
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(ts.getMaThiSinh());
                cell0.setCellStyle(style);
                Cell cell1 = row.createCell(1);
                cell1.setCellValue(ts.getTenThiSinh());
                cell1.setCellStyle(style);
                Cell cell2 = row.createCell(2);
                cell2.setCellValue(ts.getQueQuan() != null ? ts.getQueQuan().getTenTinh() : "");
                cell2.setCellStyle(style);
                Cell cell3 = row.createCell(3);
                cell3.setCellValue(ts.getNgaySinh() != null ? dateFormat.format(ts.getNgaySinh()) : "");
                cell3.setCellStyle(style);
                Cell cell4 = row.createCell(4);
                cell4.setCellValue(ts.isGioiTinh() ? "Nam" : "Nu");
                cell4.setCellStyle(style);
                Cell cell5 = row.createCell(5);
                cell5.setCellValue(ts.getDiemMon1() == 0.0f ? 0 : ts.getDiemMon1());
                cell5.setCellStyle(cellStyle);
                Cell cell6 = row.createCell(6);
                cell6.setCellValue(ts.getDiemMon2() == 0.0f ? 0 : ts.getDiemMon2());
                cell6.setCellStyle(cellStyle);
                Cell cell7 = row.createCell(7);
                cell7.setCellValue(ts.getDiemMon3() == 0.0f ? 0 : ts.getDiemMon3());
                cell7.setCellStyle(cellStyle);
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi xuất file XLSX: " + filePath, e);
        }
    }

    @Override
    public void importFromFile(String filePath) {
        // Kiểm tra file có tồn tại hay không
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File không tồn tại: " + filePath);
        }

        // Kiểm tra đuôi file để nhập đúng mẫu
        if (filePath.toLowerCase().endsWith(".csv")) {
            importFromCSV(filePath);
        } else if (filePath.toLowerCase().endsWith(".xlsx")) {
            importFromXLSX(filePath);
        } else {
            throw new IllegalArgumentException("Định dạng file không được hỗ trợ. Chỉ hỗ trợ .csv và .xlsx");
        }
    }

    private void importFromCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = reader.readAll();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Bỏ qua dòng đầu (heading) và header (dòng 1 và 2)
            for (int i = 2; i < lines.size(); i++) {
                String[] data = lines.get(i);
                if (data.length >= 8) {
                    ThiSinh ts = parseThiSinhFromData(data, dateFormat);
                    if (ts != null) {
                        // Kiểm tra nếu đã tồn tại thì update, ngược lại insert
                        ThiSinh existing = findById(ts.getMaThiSinh());
                        if (existing != null) {
                            update(ts);
                        } else {
                            insert(ts);
                        }
                    }
                }
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi nhập file CSV: " + filePath, e);
        }
    }

    private void importFromXLSX(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Bỏ qua dòng 0 (heading), 1 (trống?), 2 (header), bắt đầu từ dòng 3
            for (int i = 3; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    String[] data = new String[8];
                    for (int j = 0; j < 8; j++) {
                        Cell cell = row.getCell(j);
                        if (cell != null) {
                            data[j] = cell.toString();
                        } else {
                            data[j] = "";
                        }
                    }
                    ThiSinh ts = parseThiSinhFromData(data, dateFormat);
                    if (ts != null) {
                        // Kiểm tra nếu đã tồn tại thì update, ngược lại insert
                        ThiSinh existing = findById(ts.getMaThiSinh());
                        if (existing != null) {
                            update(ts);
                        } else {
                            insert(ts);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi nhập file XLSX: " + filePath, e);
        }
    }

    public ThiSinh parseThiSinhFromData(String[] data, SimpleDateFormat dateFormat) {
        try {
            ThiSinh ts = new ThiSinh();
            ts.setMaThiSinh(Integer.parseInt(data[0].trim()));
            ts.setTenThiSinh(data[1].trim());

            // Tìm tỉnh
            Tinh tinh = tinhService.findByName(data[2].trim());
            if (tinh == null) {
                // Nếu không tìm thấy, bỏ qua hoặc tạo mới? Ở đây bỏ qua
                System.out
                        .println("Không tìm thấy tỉnh: " + data[2].trim() + ", bỏ qua thí sinh: " + ts.getTenThiSinh());
                return null;
            }
            ts.setQueQuan(tinh);

            // Parse ngày sinh
            if (!data[3].trim().isEmpty()) {
                ts.setNgaySinh(dateFormat.parse(data[3].trim()));
            }

            // Giới tính
            ts.setGioiTinh(data[4].trim().equalsIgnoreCase("Nam"));

            // Điểm
            ts.setDiemMon1(data[5].trim().isEmpty() ? 0.0f : Float.parseFloat(data[5].trim()));
            ts.setDiemMon2(data[6].trim().isEmpty() ? 0.0f : Float.parseFloat(data[6].trim()));
            ts.setDiemMon3(data[7].trim().isEmpty() ? 0.0f : Float.parseFloat(data[7].trim()));

            return ts;
        } catch (NumberFormatException | ParseException e) {
            // Bỏ qua dòng không hợp lệ
            System.out.println("Dữ liệu không hợp lệ, bỏ qua: " + String.join(", ", data));
            return null;
        }
    }

}
