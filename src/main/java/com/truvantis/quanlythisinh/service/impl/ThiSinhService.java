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
    public ArrayList<ThiSinh> search(String keyword, int maTinh) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    @Override
    public float tinhTongDiem(ThiSinh ts) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tinhTongDiem'");
    }

    @Override
    public ThiSinh findById(int maThiSinh) {
        return thiSinhDao.findById(maThiSinh);
    }

}
