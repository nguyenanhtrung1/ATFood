package com.example.atfood.Retrofit;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

import com.example.atfood.Model.ChucNang;
import com.example.atfood.Model.ChucNangModel;
import com.example.atfood.Model.CuaHangModel;
import com.example.atfood.Model.LoaiSpModel;
import com.example.atfood.Model.SanPham;
import com.example.atfood.Model.SanPhamModel;
import com.example.atfood.Model.UserModel;

public interface ATFoodAPI {
    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("taikhoan") String taikhoan,
            @Field("matkhau") String matkhau,
            @Field("tennguoidung") String tennguoidung,
            @Field("sodienthoai") String sodienthoai
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("taikhoan") String taikhoan,
            @Field("matkhau") String matkhau
    );
    @GET("getchucnang.php")
    Observable<ChucNangModel> getChucNang();
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();
    @POST("getcuahang.php")
    @FormUrlEncoded
    Observable<CuaHangModel> getCuaHang1(
            @Field("danhmuc") int danhmuc
    );
    @POST("getsanpham.php")
    @FormUrlEncoded
    Observable<SanPhamModel> getSanPham(
            @Field("macuahang") int macuahang
    );

    @POST("searchsp.php")
    @FormUrlEncoded
    Observable<SanPhamModel> timKiemSP(
            @Field("search") String search
    );
}
