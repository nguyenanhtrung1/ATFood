package com.example.atfood.Model.ModelAdmin;

import java.util.List;

public class ThongKeDonHangModel {
    boolean success;
    String message;
    List<ThongKeDonHang> result;

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

    public List<ThongKeDonHang> getResult() {
        return result;
    }

    public void setResult(List<ThongKeDonHang> result) {
        this.result = result;
    }
}
