package com.truvantis.quanlythisinh.view.impl;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.truvantis.quanlythisinh.controller.QuanLiSinhVienController;
import com.truvantis.quanlythisinh.model.ThiSinh;
import com.truvantis.quanlythisinh.model.Tinh;
import com.truvantis.quanlythisinh.utils.DateUtil;
import com.truvantis.quanlythisinh.view.QuanLySinhVienInterface;

public class QuanLySinhVienView extends JFrame implements QuanLySinhVienInterface {

    private DefaultTableModel modelTable;
    private JTable table;
    private JTextField txtMaTS, txtHoTen, txtNgaySinh, txtDiem1, txtDiem2, txtDiem3, txtTimMa;
    private JComboBox<String> cbQueQuan, cbTimQue;
    private JRadioButton rdNam, rdNu;

    private QuanLiSinhVienController controller;
    private List<Tinh> listTinh;

    public QuanLySinhVienView() {

        // --- Cấu hình chung ---
        setTitle("HỆ THỐNG QUẢN LÝ THÍ SINH");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);

        // Cài đặt Look and Feel hiện đại (nếu có thư viện)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        getContentPane().setLayout(new BorderLayout(10, 10));

        // Tạo một đối tượng Font chung để dùng lại (Size 18 hoặc 20 cho to)
        Font fontLabel = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontTextField = new Font("Segoe UI", Font.PLAIN, 16);

        // Định nghĩa chiều cao chuẩn cho tất cả ô nhập liệu
        int standardHeight = 35;

        // --- 2. Panel Tìm kiếm (North) ---
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        TitledBorder searchBorder = new TitledBorder("Bộ lọc tìm kiếm");
        searchBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlSearch.setBorder(searchBorder);

        // --- PHẦN QUÊ QUÁN ---
        JLabel lblTimQue = new JLabel("Quê quán:");
        lblTimQue.setFont(fontLabel);
        pnlSearch.add(lblTimQue);

        cbTimQue = new JComboBox<>(new String[] { "---Tất cả---", "Hà Nội", "TP.HCM", "Đà Nẵng" });
        cbTimQue.setFont(fontTextField);
        // CHỈNH Ở ĐÂY: Tăng chiều cao lên 35 để khớp với Font to
        cbTimQue.setPreferredSize(new Dimension(150, standardHeight));
        pnlSearch.add(cbTimQue);

        // --- PHẦN MÃ THÍ SINH ---
        JLabel lblTimMa = new JLabel("Mã thí sinh:");
        lblTimMa.setFont(fontLabel);
        pnlSearch.add(lblTimMa);

        txtTimMa = new JTextField(15);
        txtTimMa.setFont(fontTextField);
        // CHỈNH Ở ĐÂY: Đảm bảo JTextField cũng cao 35px
        txtTimMa.setPreferredSize(new Dimension(150, standardHeight));
        pnlSearch.add(txtTimMa);

        add(pnlSearch, BorderLayout.NORTH);

        // --- 3. Bảng dữ liệu (Center) ---
        // String[] columns = { "Mã TS", "Họ Tên", "Quê Quán", "Ngày Sinh", "Giới Tính",
        // "Môn 1", "Môn 2", "Môn 3" };
        // modelTable = new DefaultTableModel(columns, 0);
        // table = new JTable(modelTable);
        // table.setRowHeight(25);
        // table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // JScrollPane scrollPane = new JScrollPane(table);
        // scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        // add(scrollPane, BorderLayout.CENTER);
        // --- 3. Bảng dữ liệu (Center) ---
        String[] columns = { "Mã TS", "Họ Tên", "Quê Quán", "Ngày Sinh", "Giới Tính", "Môn 1", "Môn 2", "Môn 3" };
        modelTable = new DefaultTableModel(columns, 0);
        table = new JTable(modelTable);

        // Cấu hình FlatLaf cho bảng
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        // 1. Tăng chiều cao hàng để nội dung không bị "bí"
        table.setRowHeight(35); // Tăng từ 30 lên 35 để nhìn rõ hơn

        // 2. Tăng kích thước Font cho dữ liệu trong bảng
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font chữ to hơn (14px)

        // 3. (Tùy chọn) Cấu hình khoảng cách lề bên trong các ô
        table.setIntercellSpacing(new Dimension(5, 5));

        // Tăng cường giao diện FlatLaf
        table.putClientProperty("FlatLaf.style", "rowHeight: 30; showHorizontalLines: true; showVerticalLines: false;");

        // Căn giữa nội dung các cột cần thiết
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        // Cấu hình Header căn giữa
        JTableHeader header = table.getTableHeader();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Ép buộc Header áp dụng renderer mới
        header.setDefaultRenderer(headerRenderer);

        // Áp dụng cho các cột: Mã TS(0), Ngày Sinh(3), Giới Tính(4), Môn 1(5), Môn
        // 2(6), Môn 3(7)
        int[] centerCols = { 0, 3, 4, 5, 6, 7 };
        for (int colIndex : centerCols) {
            table.getColumnModel().getColumn(colIndex).setCellRenderer(centerRenderer);
        }

        // Thiết lập độ rộng ưu tiên (Cột họ tên chiếm nhiều nhất)
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        table.getColumnModel().getColumn(0).setPreferredWidth(50); // Mã TS thu nhỏ
        table.getColumnModel().getColumn(1).setPreferredWidth(200); // Họ tên mở rộng
        table.getColumnModel().getColumn(2).setPreferredWidth(120); // Quê quán

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // --- 4. Form Nhập liệu & Nút bấm (South) ---
        JPanel pnlBottom = new JPanel(new BorderLayout());

        // Form nhập liệu
        JPanel pnlForm = new JPanel(new GridLayout(4, 4, 15, 10));
        TitledBorder formBorder = new TitledBorder("Thông tin chi tiết");
        formBorder.setTitleFont(new Font("Segoe UI", Font.BOLD, 18));
        pnlForm.setBorder(formBorder);
        pnlForm.setBackground(Color.WHITE);

        // 1. Font và Layout
        Font fontLabelForm = new Font("Segoe UI", Font.PLAIN, 16);
        Font fontInputForm = new Font("Segoe UI", Font.PLAIN, 16);
        pnlForm.setLayout(new GridLayout(4, 4, 20, 15)); // 4 hàng, 4 cột, khoảng cách 20-15
        pnlForm.setBackground(Color.WHITE);

        // --- HÀNG 1: Mã Thí Sinh & Họ Tên ---
        JLabel lblMa = new JLabel("Mã Thí Sinh:");
        lblMa.setFont(fontLabelForm);
        pnlForm.add(lblMa); // Cột 1
        txtMaTS = new JTextField();
        txtMaTS.setFont(fontInputForm);
        txtMaTS.setEditable(false);
        txtMaTS.setFocusable(false);
        txtMaTS.setBackground(Color.LIGHT_GRAY);
        pnlForm.add(txtMaTS); // Cột 2

        JLabel lblTen = new JLabel("Họ Tên:");
        lblTen.setFont(fontLabelForm);
        pnlForm.add(lblTen); // Cột 3
        txtHoTen = new JTextField();
        txtHoTen.setFont(fontInputForm);
        pnlForm.add(txtHoTen); // Cột 4

        // --- HÀNG 2: Quê Quán & Ngày Sinh ---
        JLabel lblQue = new JLabel("Quê Quán:");
        lblQue.setFont(fontLabelForm);
        pnlForm.add(lblQue); // Cột 1
        cbQueQuan = new JComboBox<>(new String[] { "Hà Nội", "TP.HCM", "Đà Nẵng", "Cần Thơ", "Hải Phòng" });
        cbQueQuan.setFont(fontInputForm);
        cbQueQuan.setBackground(Color.WHITE);
        pnlForm.add(cbQueQuan); // Cột 2

        JLabel lblD1 = new JLabel("Điểm Môn 1:");
        lblD1.setFont(fontLabelForm);
        pnlForm.add(lblD1); // Cột 3
        txtDiem1 = new JTextField();
        txtDiem1.setFont(fontInputForm);
        pnlForm.add(txtDiem1); // Cột 4

        JLabel lblNgay = new JLabel("Ngày Sinh:");
        lblNgay.setFont(fontLabelForm);
        pnlForm.add(lblNgay); // Cột 3
        txtNgaySinh = new JTextField();
        txtNgaySinh.setFont(fontInputForm);
        pnlForm.add(txtNgaySinh); // Cột 4

        JLabel lblD2 = new JLabel("Điểm Môn 2:");
        lblD2.setFont(fontLabelForm);
        pnlForm.add(lblD2); // Cột 1
        txtDiem2 = new JTextField();
        txtDiem2.setFont(fontInputForm);
        pnlForm.add(txtDiem2); // Cột 2

        // --- HÀNG 3: Giới Tính & Điểm Môn 1 ---
        JLabel lblGioi = new JLabel("Giới Tính:");
        lblGioi.setFont(fontLabelForm);
        pnlForm.add(lblGioi); // Cột 1

        JPanel pnlGioiTinh = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pnlGioiTinh.setBackground(Color.WHITE);
        rdNam = new JRadioButton("Nam", true);
        rdNu = new JRadioButton("Nữ");
        rdNam.setFont(fontInputForm);
        rdNu.setFont(fontInputForm);
        rdNam.setBackground(Color.WHITE);
        rdNu.setBackground(Color.WHITE);
        ButtonGroup bg = new ButtonGroup();
        bg.add(rdNam);
        bg.add(rdNu);
        pnlGioiTinh.add(rdNam);
        pnlGioiTinh.add(rdNu);
        pnlForm.add(pnlGioiTinh); // Cột 2 (Mẹo: JPanel này chiếm đúng 1 ô lưới)

        // --- HÀNG 4: Điểm Môn 2 & Điểm Môn 3 ---

        JLabel lblD3 = new JLabel("Điểm Môn 3:");
        lblD3.setFont(fontLabelForm);
        pnlForm.add(lblD3); // Cột 3
        txtDiem3 = new JTextField();
        txtDiem3.setFont(fontInputForm);
        pnlForm.add(txtDiem3); // Cột 4

        // Panel chứa các nút điều khiển
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));

        pnlBottom.add(pnlForm, BorderLayout.CENTER);
        pnlBottom.add(pnlButtons, BorderLayout.SOUTH);
        pnlBottom.setBorder(new EmptyBorder(0, 10, 10, 10));

        add(pnlBottom, BorderLayout.SOUTH);

        this.controller = new QuanLiSinhVienController(this);

        JButton btnTim = createStyledButton("Tìm", this.controller, Color.decode("#2ecc71"));
        pnlSearch.add(btnTim);

        JButton btnHuy = createStyledButton("Hủy tìm", this.controller, Color.decode("#95a5a6"));
        pnlSearch.add(btnHuy);
        pnlButtons.add(createStyledButton("Thêm", this.controller, Color.decode("#3498db")));
        pnlButtons.add(createStyledButton("Xóa", this.controller, Color.decode("#e74c3c")));
        pnlButtons.add(createStyledButton("Cập Nhật", this.controller, Color.decode("#f1c40f")));
        pnlButtons.add(createStyledButton("Xóa Form", this.controller, Color.decode("#7f8c8d")));
        pnlButtons.add(createStyledButton("Làm mới", this.controller, Color.decode("#27ae60"))); // Gán action từ
                                                                                                 // Controller
        // --- 1. Thanh Menu ---
        setJMenuBar(createMenuBar(this.controller));
        setVisible(true);
    }

    // Hàm hỗ trợ tạo nút bấm đẹp
    private JButton createStyledButton(String text, ActionListener action, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(110, 35));
        btn.addActionListener(action);
        btn.setActionCommand(text);
        return btn;
    }

    private JMenuBar createMenuBar(ActionListener action) {
        JMenuBar mb = new JMenuBar();
        Font menuFont = new Font("Arial", Font.BOLD, 14);

        // Tạo khoảng đệm nhỏ, căn trái hoàn toàn
        Insets itemMargin = new Insets(2, 5, 2, 5); // top, left, bottom, right

        JMenu mFile = new JMenu("Tập tin");
        mFile.setFont(menuFont);
        // Bỏ khoảng cách thừa của Menu cha
        mFile.setMargin(new Insets(2, 2, 2, 2));

        String[] fileItems = { "Mở tập tin", "Lưu tập tin", "Separator", "Thoát" };
        for (String i : fileItems) {
            if (i.equals("Separator")) {
                mFile.addSeparator();
            } else {
                JMenuItem mi = new JMenuItem(i);
                mi.setFont(menuFont);
                mi.setMargin(itemMargin); // Quan trọng: Căn lề hẹp cho từng item
                mi.setIconTextGap(0); // Bỏ khoảng trống dành cho Icon
                mi.addActionListener(action);
                mi.setActionCommand(i);
                mFile.add(mi);
            }
        }

        JMenu mHelp = new JMenu("Trợ giúp");
        mHelp.setFont(menuFont);
        mHelp.setMargin(new Insets(2, 2, 2, 2));

        JMenuItem miAbout = new JMenuItem("Về chúng tôi");
        miAbout.setFont(menuFont);
        miAbout.setMargin(itemMargin);
        miAbout.setIconTextGap(0);
        miAbout.addActionListener(action);
        miAbout.setActionCommand("Về chúng tôi");
        mHelp.add(miAbout);

        mb.add(mFile);
        mb.add(mHelp);
        return mb;
    }

    public void khoiTaoComboBoxTinh(List<Tinh> listTinh) {
        this.listTinh = listTinh;
        cbQueQuan.removeAllItems();
        cbTimQue.removeAllItems();
        cbTimQue.addItem("---Tất cả---");
        for (Tinh t : listTinh) {
            cbQueQuan.addItem(t.getTenTinh());
            cbTimQue.addItem(t.getTenTinh());
        }

    }

    public void khoiTaoBangDuLieu(List<ThiSinh> list) {
        modelTable.setRowCount(0);
        for (ThiSinh ts : list) {
            modelTable.addRow(new Object[] {
                    ts.getMaThiSinh(), ts.getTenThiSinh(), ts.getQueQuan().getTenTinh(),
                    DateUtil.toString(ts.getNgaySinh()), (ts.isGioiTinh() ? "Nam" : "Nữ"),
                    ts.getDiemMon1(), ts.getDiemMon2(), ts.getDiemMon3()
            });
        }
    }

    // --- Override các phương thức Interface ---
    @Override
    public void xoaForm() {
        txtMaTS.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtDiem1.setText("");
        txtDiem2.setText("");
        txtDiem3.setText("");
        cbQueQuan.setSelectedIndex(0);
        rdNam.setSelected(true);
    }

    @Override
    public void thoatKhoiChuongTrinh() {
        if (JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát?") == JOptionPane.YES_OPTION)
            System.exit(0);
    }

    @Override
    public void themHoacCapNhatThiSinh(ThiSinh ts) {
        // Nếu truyền null, ta lấy từ Form
        ThiSinh thiSinh = (ts == null) ? getThiSinhTuForm() : ts;

        if (thiSinh != null) {
            // Logic: Nếu mã đã tồn tại trong bảng thì cập nhật, chưa có thì thêm mới
            modelTable.addRow(new Object[] {
                    thiSinh.getMaThiSinh(), thiSinh.getTenThiSinh(), thiSinh.getQueQuan(),
                    DateUtil.toString(thiSinh.getNgaySinh()), (thiSinh.isGioiTinh() ? "Nam" : "Nữ"),
                    thiSinh.getDiemMon1(), thiSinh.getDiemMon2(), thiSinh.getDiemMon3()
            });
            xoaForm();
        }
    }

    public ThiSinh getThiSinhTuForm() {
        try {
            // KHÔNG lấy mã từ txtMaTS nữa, mặc định là -1 cho thí sinh mới
            int maThiSinh = -1;

            // Nếu ô txtMaTS có chứa mã (trường hợp Cập Nhật), thì mới lấy
            String maHienTai = txtMaTS.getText().trim();
            if (!maHienTai.isEmpty()) {
                maThiSinh = Integer.parseInt(maHienTai);
            }

            String tenThiSinh = txtHoTen.getText().trim();
            String queQuan = cbQueQuan.getSelectedItem().toString();
            String ngaySinh = txtNgaySinh.getText().trim();
            boolean gioiTinh = rdNam.isSelected();

            float diemMon1 = Float.parseFloat(txtDiem1.getText().trim());
            float diemMon2 = Float.parseFloat(txtDiem2.getText().trim());
            float diemMon3 = Float.parseFloat(txtDiem3.getText().trim());

            return new ThiSinh(maThiSinh, tenThiSinh, new Tinh(0, queQuan),
                    DateUtil.toDate(ngaySinh), gioiTinh,
                    diemMon1, diemMon2, diemMon3);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Lỗi: Điểm phải là số!");
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void hienThiThongTinThiSinhDaChon() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaTS.setText(modelTable.getValueAt(row, 0).toString());
            txtHoTen.setText(modelTable.getValueAt(row, 1).toString());
            cbQueQuan.setSelectedItem(modelTable.getValueAt(row, 2).toString());
            txtNgaySinh.setText(modelTable.getValueAt(row, 3).toString());

            String gioiTinh = modelTable.getValueAt(row, 4).toString();
            if (gioiTinh.equals("Nam"))
                rdNam.setSelected(true);
            else
                rdNu.setSelected(true);

            txtDiem1.setText(modelTable.getValueAt(row, 5).toString());
            txtDiem2.setText(modelTable.getValueAt(row, 6).toString());
            txtDiem3.setText(modelTable.getValueAt(row, 7).toString());
        }
    }

    @Override
    public int getSelectedRowIndex() {
        return table.getSelectedRow();
    }

    @Override
    public boolean showConfirmDialog(String message) {
        int confirm = JOptionPane.showConfirmDialog(this, message, "Xác nhận", JOptionPane.YES_NO_OPTION);
        return confirm == JOptionPane.YES_OPTION;
    }

    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // Hàm này chỉ cập nhật giao diện sau khi Controller báo thành công
    @Override
    public void removeRowFromTable(int row) {
        modelTable.removeRow(row);
        xoaForm();
    }

    @Override
    public void hienThiAbout() {
        JOptionPane.showMessageDialog(this, "Phần mềm Quản lý Thí sinh v1.0\nTác giả: Truvantis", "Về chúng tôi",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public Map.Entry<Tinh, String> layDuLieuTim() {
        Tinh tinh = this.cbTimQue.getSelectedIndex() > 0 ? new Tinh(0, this.cbTimQue.getSelectedItem().toString())
                : new Tinh(0, "-1");
        String maTS = this.txtTimMa.getText().trim().isEmpty() ? "-1" : this.txtTimMa.getText().trim();
        return Map.entry(tinh, maTS);
    }

    @Override
    public void huyTim() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'huyTim'");
    }

    @Override
    public void thucHienSaveFile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'thucHienSaveFile'");
    }

    @Override
    public void thucHienOpenFile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'thucHienOpenFile'");
    }

    public void hienThiThongBao(String string) {
        JOptionPane.showMessageDialog(this, string);
    }

    public int layMaThiSinhTuForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            modelTable.getValueAt(row, 0).toString();
            return Integer.parseInt(modelTable.getValueAt(row, 0).toString());
        }
        throw new IllegalArgumentException("Không có thí sinh nào được chọn");
    }

    @Override
    public boolean confirmAction(String message) {
        int choice = JOptionPane.showConfirmDialog(
                this,
                message,
                "Xác nhận hành động",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE // Thêm icon cảnh báo để chuyên nghiệp hơn
        );
        return choice == JOptionPane.YES_OPTION;
    }

    @Override
    public void refreshTable(List<ThiSinh> danhSachThiSinh) {
        // Xóa sạch dữ liệu cũ trên bảng
        modelTable.setRowCount(0);
        // Đổ lại dữ liệu từ danh sách mới
        for (ThiSinh ts : danhSachThiSinh) {
            modelTable.addRow(new Object[] {
                    ts.getMaThiSinh(), ts.getTenThiSinh(), ts.getQueQuan().getTenTinh(),
                    DateUtil.toString(ts.getNgaySinh()), (ts.isGioiTinh() ? "Nam" : "Nữ"),
                    ts.getDiemMon1(), ts.getDiemMon2(), ts.getDiemMon3()
            });
        }
    }
}