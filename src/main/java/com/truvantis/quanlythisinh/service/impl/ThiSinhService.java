package com.truvantis.quanlythisinh.service.impl;

import java.util.ArrayList;

import com.truvantis.quanlythisinh.dto.impl.ThiSinhDao;
import com.truvantis.quanlythisinh.model.ThiSinh;
import com.truvantis.quanlythisinh.model.Tinh;
import com.truvantis.quanlythisinh.service.ThiSinhServiceInterface;

public class ThiSinhService implements ThiSinhServiceInterface {

    private ThiSinhDao thiSinhDao;
    private TinhService tinhService;

    public ThiSinhService() {
        this.thiSinhDao = ThiSinhDao.getInstance();
        this.tinhService = new TinhService();
    }

    @Override
    public ArrayList<ThiSinh> getAllThiSinh() {
        return (ArrayList<ThiSinh>) thiSinhDao.findAll();
    }

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

    @Override
    public boolean update(ThiSinh ts) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(int maThiSinh) {
        thiSinhDao.deleteThiSinh(maThiSinh);
        return true;
    }

    @Override
    public ArrayList<ThiSinh> timKiemTheoTenTinhHoacMaThiSinh(String tenTinh, int maThiSinh) {
        if (tenTinh.equals("-1")) {
            tenTinh = null;
        }
        Integer maTS = maThiSinh == -1 ? null : maThiSinh;
        return (ArrayList<ThiSinh>) thiSinhDao.findByTinhVaMaThiSinh(tenTinh, maTS);
    }

    @Override
    public float tinhTongDiem(ThiSinh ts) {
        if (ts == null) {
            throw new IllegalArgumentException("ThiSinh must not be null");
        }
        // Tính tổng điểm của 3 môn (giả định hệ điểm thang 10)
        return ts.getDiemMon1() + ts.getDiemMon2() + ts.getDiemMon3();
    }

    @Override
    public ThiSinh findById(int maThiSinh) {
        return thiSinhDao.findById(maThiSinh);
    }

}
