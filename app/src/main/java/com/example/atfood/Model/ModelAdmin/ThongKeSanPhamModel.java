package com.example.atfood.Model.ModelAdmin;

import java.util.List;

public class ThongKeSanPhamModel {
    boolean success;
    String message;
    List<ThongKeSanPham> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ThongKeSanPham> getResult() {
        return result;
    }

    public void setResult(List<ThongKeSanPham> result) {
        this.result = result;
    }
}
