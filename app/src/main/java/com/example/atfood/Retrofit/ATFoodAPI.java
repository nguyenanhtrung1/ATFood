package com.example.atfood.Retrofit;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import com.example.atfood.Model.ChucNang;
import com.example.atfood.Model.ChucNangModel;
import com.example.atfood.Model.CuaHangModel;
import com.example.atfood.Model.DonHangModel;
import com.example.atfood.Model.KetQuaModel;
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
            @Field("sodienthoai") String sodienthoai,
            @Field("vaitro") int vaitro,
            @Field("uid") String uid
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("taikhoan") String taikhoan,
            @Field("matkhau") String matkhau,
            @Field("vaitro") int vaitro
    );
    @GET("getchucnang.php")
    Observable<ChucNangModel> getChucNang();
    @GET("getloaisp.php")
    Observable<LoaiSpModel> getLoaiSp();
    @GET("getqlicuahang.php")
    Observable<CuaHangModel> getQliCuaHang();


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
    @POST("timkiemsanpham.php")
    @FormUrlEncoded
    Observable<SanPhamModel> timKiemSPTrongCH(
            @Field("search") String search,
            @Field("macuahang") int macuahang
    );
    @POST("timkiemcuahang.php")
    @FormUrlEncoded
    Observable<CuaHangModel> timKiemCH(
            @Field("search") String search
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> taoDonHang(
            @Field("manguoidung") int manguoidung,
            @Field("tennguoidathang") String tennguoidathang,
            @Field("sodienthoai") String sodienthoai,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("tongtien") String tongtien,
            @Field("chitiet") String chitiet
    );
    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("manguoidung") int manguoidung
    );

    @POST("getngaydathang.php")
    @FormUrlEncoded
    Observable<DonHangModel> getNgayDatHang(
            @Field("madonhang") int madonhang
    );
    @POST("themcuahang.php")
    @FormUrlEncoded
    Observable<KetQuaModel> themCuaHang(
            @Field("tencuahang") String tencuahang,
            @Field("diachi") String diachi,
            @Field("hinhanh") String hinhanh,
            @Field("danhmuc") int danhmuc
    );
    @POST("suacuahang.php")
    @FormUrlEncoded
    Observable<KetQuaModel> suaCuaHang(
            @Field("tencuahang") String tencuahang,
            @Field("diachi") String diachi,
            @Field("hinhanh") String hinhanh,
            @Field("danhmuc") int danhmuc,
            @Field("macuahang") int macuahang
    );
    @POST("xoacuahang.php")
    @FormUrlEncoded
    Observable<KetQuaModel> xoaCuaHang(
            @Field("macuahang") int macuahang
    );
    @POST("themsanpham.php")
    @FormUrlEncoded
    Observable<KetQuaModel> themSanPham(
            @Field("tensanpham") String tensanpham,
            @Field("giasanpham") String giasanpham,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("macuahang") int macuahang
    );
    @POST("xoasanpham.php")
    @FormUrlEncoded
    Observable<KetQuaModel> xoaSanPham(
            @Field("masanpham") int masanpham
    );
    @POST("suasanpham.php")
    @FormUrlEncoded
    Observable<KetQuaModel> suaSanPham(
            @Field("tensanpham") String tensanpham,
            @Field("giasanpham") String giasanpham,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("macuahang") int macuahang,
            @Field("masanpham") int masanpham
    );
    @Multipart
    @POST("taianhlen.php")
    Call<KetQuaModel> uploadFile(@Part MultipartBody.Part file);
}
