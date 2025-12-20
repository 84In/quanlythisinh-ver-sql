package com.truvantis.quanlythisinh.dtos;

import com.truvantis.quanlythisinh.models.Tinh;

import java.util.List;

public interface TinhDaoInterface {

    //Luu tinh
    void saveTinh(Tinh tinh);

    //Xoa tinh
    void deleteTinh(Tinh tinh);

    //Cap nhat tinh
    void updateTinh(Tinh tinh);

    //Tim tinh theo ten
    Tinh findTinhByName(String name);

    //Tim tinh theo ma tinh
    Tinh findTinhById(int id);

    //Lay danh sach tinh
    List<Tinh> findAllTinh();

}
