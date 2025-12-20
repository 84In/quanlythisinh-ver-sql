package com.truvantis.quanlythisinh.dtos;

import com.truvantis.quanlythisinh.mappers.RowMappersInterface;

import java.util.List;
import java.util.Optional;

public interface GenericDaoInterface<T> {

    //Query danh sach
    List<T> query(
            String sql,
            RowMappersInterface<T> rowMapper,
            Object... params
    );

    //Query 1 ca the
    Optional<T> queryOne(
            String sql,
            RowMappersInterface<T> rowMapper,
            Object... params
    );

    //Cap nhat thong tin
    int update(
            String sql,
            Object... params
    );

    //Them moi
    long insert(
            String sql,
            Object... params
    );

    //Dem so luong
    long count(
            String sql,
            Object... params
    );
}
