package com.example.atfood.EvenBus;

import com.example.atfood.Model.CuaHang;

public class SuaXoaEvent {
    CuaHang cuaHang;

    public SuaXoaEvent(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }

    public CuaHang getCuaHang() {
        return cuaHang;
    }

    public void setCuaHang(CuaHang cuaHang) {
        this.cuaHang = cuaHang;
    }
}
