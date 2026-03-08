package com.truvantis.quanlythisinh.dto;

import java.util.List;

import com.truvantis.quanlythisinh.model.Tinh;

public interface TinhDaoInterface {

    // Luu tinh
    void saveTinh(Tinh tinh);

    // Xoa tinh
    void deleteTinh(Tinh tinh);

    // Cap nhat tinh
    void updateTinh(Tinh tinh);

    // Tim tinh theo ten
    Tinh findTinhByName(String name);

    // Tim tinh theo ma tinh
    Tinh findTinhById(int id);

    // Lay danh sach tinh
    List<Tinh> findAllTinh();

}
