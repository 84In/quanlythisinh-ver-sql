package com.truvantis.quanlythisinh.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class TinhTest {

    @Test
    void toString_ShouldReturnTenTinh() {
        Tinh t = new Tinh(42, "Da Nang");
        assertEquals("Da Nang", t.toString());
    }

    @Test
    void equalsAndHashCode_ShouldBeBasedOnMaTinh() {
        Tinh t1 = new Tinh(10, "Hue");
        Tinh t2 = new Tinh(10, "Hue");
        Tinh t3 = new Tinh(11, "Da Nang");

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());

        assertNotEquals(t1, t3);
    }
}
