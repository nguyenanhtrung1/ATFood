package com.example.atfood.Model;

import com.example.atfood.Activity.CuaHangActivity;

import java.util.List;

public class CuaHangModel {
    boolean success;
    String message;
    List<CuaHang> result;

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

    public List<CuaHang> getResult() {
        return result;
    }

    public void setResult(List<CuaHang> result) {
        this.result = result;
    }
}
