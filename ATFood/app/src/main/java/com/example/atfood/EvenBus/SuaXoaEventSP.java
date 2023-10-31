package com.example.atfood.EvenBus;

import com.example.atfood.Model.SanPham;

public class SuaXoaEventSP {
    SanPham sanPham;

    public SuaXoaEventSP(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
}
