package com.truvantis.quanlythisinh.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class Tinh implements Serializable {

    private int maTinh;
    private String tenTinh;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Tinh() {
    }

    public Tinh(int maTinh, String tenTinh) {
        this.maTinh = maTinh;
        this.tenTinh = tenTinh;
    }

    public Tinh(int maTinh, String tenTinh, Timestamp createdAt, Timestamp updatedAt) {
        this.maTinh = maTinh;
        this.tenTinh = tenTinh;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getMaTinh() {
        return maTinh;
    }

    public void setMaTinh(int maTinh) {
        this.maTinh = maTinh;
    }

    public String getTenTinh() {
        return tenTinh;
    }

    public void setTenTinh(String tenTinh) {
        this.tenTinh = tenTinh;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tinh)) return false;
        Tinh tinh = (Tinh) o;
        return maTinh == tinh.maTinh;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maTinh);
    }

    @Override
    public String toString() {
        return tenTinh;
    }
}
