package com.truvantis.quanlythisinh.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.truvantis.quanlythisinh.model.Tinh;
import com.truvantis.quanlythisinh.model.ThiSinh;

class ThiSinhServiceTest {

    @Test
    void tinhTongDiem_ShouldReturnSumOfAllThreeScores() {
        ThiSinhService service = new ThiSinhService();
        ThiSinh ts = new ThiSinh(1, "Nguyen Van A", new Tinh(1, "Ha Noi"), new Date(), true, 7.5f, 8.0f, 9.0f);

        float sum = service.tinhTongDiem(ts);

        assertEquals(24.5f, sum, 0.0001f, "Tổng điểm phải bằng tổng 3 môn");
    }

    @Test
    void tinhTongDiem_ShouldThrowWhenThiSinhIsNull() {
        ThiSinhService service = new ThiSinhService();
        assertThrows(IllegalArgumentException.class, () -> service.tinhTongDiem(null));
    }
}
