package com.truvantis.quanlythisinh.dtos;

import com.truvantis.quanlythisinh.mappers.RowMappersInterface;

import java.util.List;
import java.util.Optional;

public interface GenericDaoInterface<T> {

    List<T> query(
            String sql,
            RowMappersInterface<T> rowMapper,
            Object... params
    );

    Optional<T> queryOne(
            String sql,
            RowMappersInterface<T> rowMapper,
            Object... params
    );

    int update(
            String sql,
            Object... params
    );

    long insert(
            String sql,
            Object... params
    );

    long count(
            String sql,
            Object... params
    );
}
