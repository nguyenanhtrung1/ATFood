package com.example.atfood.Model;

import java.io.Serializable;

public class SanPham implements Serializable{
    int masanpham;
    String tensanpham;
    String giasanpham;
    String hinhanh;
    String mota;
    int macuahang;

    public int getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(int masanpham) {
        this.masanpham = masanpham;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public String getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(String giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getMacuahang() {
        return macuahang;
    }

    public void setMacuahang(int macuahang) {
        this.macuahang = macuahang;
    }
}
