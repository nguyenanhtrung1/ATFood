package com.example.atfood.Model;

import java.io.Serializable;

public class CuaHang  implements Serializable {
    int macuahang;
    String tencuahang;
    String diachi;
    String hinhanh;
    int danhmuc;

    public int getMacuahang() {
        return macuahang;
    }

    public void setMacuahang(int macuahang) {
        this.macuahang = macuahang;
    }

    public String getTencuahang() {
        return tencuahang;
    }

    public void setTencuahang(String tencuahang) {
        this.tencuahang = tencuahang;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getDanhmuc() {
        return danhmuc;
    }

    public void setDanhmuc(int danhmuc) {
        this.danhmuc = danhmuc;
    }
}
