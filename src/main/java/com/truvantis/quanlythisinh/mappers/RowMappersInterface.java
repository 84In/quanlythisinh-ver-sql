package com.truvantis.quanlythisinh.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMappersInterface<T> {
    T mapRow(ResultSet rs) throws SQLException;
}