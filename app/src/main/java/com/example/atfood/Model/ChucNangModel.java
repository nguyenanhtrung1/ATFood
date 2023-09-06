package com.example.atfood.Model;

import java.util.List;

public class ChucNangModel {
    boolean success;
    String message;
    List<ChucNang> result;

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

    public List<ChucNang> getResult() {
        return result;
    }

    public void setResult(List<ChucNang> result) {
        this.result = result;
    }
}
