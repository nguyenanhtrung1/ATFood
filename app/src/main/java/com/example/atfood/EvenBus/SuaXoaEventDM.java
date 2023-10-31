package com.example.atfood.EvenBus;

import com.example.atfood.Model.LoaiSp;

public class SuaXoaEventDM {
    LoaiSp loaiSp;

    public SuaXoaEventDM(LoaiSp loaiSp) {
        this.loaiSp = loaiSp;
    }

    public LoaiSp getLoaiSp() {
        return loaiSp;
    }

    public void setLoaiSp(LoaiSp loaiSp) {
        this.loaiSp = loaiSp;
    }
}
