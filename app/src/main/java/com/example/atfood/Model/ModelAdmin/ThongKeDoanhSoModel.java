package com.example.atfood.Model.ModelAdmin;

import java.util.List;

public class ThongKeDoanhSoModel {
    boolean success;
    String message;
    List<ThongKeDoanSo> result;

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

    public List<ThongKeDoanSo> getResult() {
        return result;
    }

    public void setResult(List<ThongKeDoanSo> result) {
        this.result = result;
    }
}
