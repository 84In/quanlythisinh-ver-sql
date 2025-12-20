package com.truvantis.quanlythisinh.dtos;

import com.truvantis.quanlythisinh.models.Tinh;

import java.util.List;

public interface TinhDaoInterface {

    void saveTinh(Tinh tinh);
    void deleteTinh(Tinh tinh);
    void updateTinh(Tinh tinh);
    Tinh findTinhByName(String name);
    Tinh findTinhById(int id);
    List<Tinh> findAllTinh();

}
