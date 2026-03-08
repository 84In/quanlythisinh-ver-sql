package com.truvantis.quanlythisinh.service.impl;

import java.util.List;

import com.truvantis.quanlythisinh.dto.impl.TinhDao;
import com.truvantis.quanlythisinh.model.Tinh;
import com.truvantis.quanlythisinh.service.TinhServiceInterface;

public class TinhService implements TinhServiceInterface {

    private TinhDao tinhDao;

    public TinhService() {
        this.tinhDao = TinhDao.getInstance();
    }

    @Override
    public boolean insert(Tinh tinh) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public boolean update(Tinh tinh) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public boolean delete(int maTinh) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Tinh findByName(String name) {
        // TODO Auto-generated method stub
        Tinh tinh = tinhDao.findTinhByName(name);
        if (tinh == null) {
            System.out.println("Không tìm thấy tỉnh với tên: " + name);
            return null;
        }
        return tinh;
    }

    @Override
    public List<Tinh> getAllTinh() {
        // TODO Auto-generated method stub
        return tinhDao.findAllTinh();
    }

}
