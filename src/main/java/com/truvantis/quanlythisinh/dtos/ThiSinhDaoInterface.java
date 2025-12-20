package com.truvantis.quanlythisinh.dtos;

import com.truvantis.quanlythisinh.models.ThiSinh;

import java.util.List;

public interface ThiSinhDaoInterface {

    void saveThiSinh(ThiSinh thiSinh);

    void updateThiSinh(ThiSinh thiSinh);

    void deleteThiSinh(int maThiSinh);

    ThiSinh findById(int maThiSinh);

    List<ThiSinh> findAll();

    List<ThiSinh> findByTen(String keyword);

    List<ThiSinh> findByTinh(int maTinh);
}
