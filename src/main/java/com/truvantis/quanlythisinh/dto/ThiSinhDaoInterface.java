package com.truvantis.quanlythisinh.dto;

import java.util.List;

import com.truvantis.quanlythisinh.model.ThiSinh;

public interface ThiSinhDaoInterface {

    // Luu thi sinh
    long saveThiSinh(ThiSinh thiSinh);

    // Cap nhat thi sinh
    void updateThiSinh(ThiSinh thiSinh);

    // Xoa thi sinh
    void deleteThiSinh(int maThiSinh);

    // Tim kiem thi sinh theo ma thi sinh
    ThiSinh findById(int maThiSinh);

    // Lay danh sach thi sinh
    List<ThiSinh> findAll();

    // lay danh sach thi sinh theo ten
    List<ThiSinh> findByTen(String keyword);

    // Lay danh sach theo ma tinh
    List<ThiSinh> findByTinh(int maTinh);
}
