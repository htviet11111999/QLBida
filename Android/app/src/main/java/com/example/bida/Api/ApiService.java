package com.example.bida.Api;


import com.example.bida.KNN.BGiuXe;
import com.example.bida.KNN.DVAnUong;
import com.example.bida.KNN.RongRai;
import com.example.bida.KNN.SLNguoi;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.Kho;
import com.example.bida.Model.LichSuCT;
import com.example.bida.Model.LichSuKH;
import com.example.bida.Model.LichSuNV;
import com.example.bida.Model.LichSuQTV;
import com.example.bida.Model.NhanVien;
import com.example.bida.Model.PhieuNhap;
import com.example.bida.Model.TaiKhoan;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    //KNN_KhongGian
    @GET("khonggian")
    Call<ArrayList<RongRai>> layDSRongRai();
    @POST("khonggian")
    Call<JsonObject> themRongRai (
            @Body RongRai r
    );
    @DELETE("khonggian")
    Call<JsonObject> xoaRongRai ();
    //KNN_DichVuAnUong
    @GET("dichvu")
    Call<ArrayList<DVAnUong>> layDSDichVu();
    @POST("dichvu")
    Call<JsonObject> themDichVu (
            @Body DVAnUong r
    );
    @DELETE("dichvu")
    Call<JsonObject> xoaDichVu ();

    //KNN_SoLuongNguoi
    @GET("slnguoi")
    Call<ArrayList<SLNguoi>> layDSSLNguoi();
    @POST("slnguoi")
    Call<JsonObject> themSLNguoi (
            @Body SLNguoi r
    );
    @DELETE("slnguoi")
    Call<JsonObject> xoaSLNguoi ();

    //KNN_BaiGiuXe
    @GET("baigiuxe")
    Call<ArrayList<BGiuXe>> layDSBaiGiuXe();
    @POST("baigiuxe")
    Call<JsonObject> themBaiGiuXe (
            @Body BGiuXe r
    );
    @DELETE("baigiuxe")
    Call<JsonObject> xoaBaiGiuXe ();


    //L???ch s??? qu???n tr??? vi??n
    @GET("lichsu_qtv")
    Call<ArrayList<LichSuQTV>> layDSLS_QTV();
    @POST("lichsu_qtv")
    Call<JsonObject> themLichSu_QTV (
            @Body LichSuQTV lichSuQTV
    );
    @DELETE("lichsu_qtv")
    Call<JsonObject> xoaLichSu_QTV ();
    //L???ch s??? ch??? ti???m
    @GET("lichsu_ct/{idchutiem}")
    Call<ArrayList<LichSuCT>> layDSLS_CT(@Path("idchutiem") int idchutiem);
    @POST("lichsu_ct/{idchutiem}")
    Call<JsonObject> themLichSu_CT (
            @Path("idchutiem") int idchutiem,
            @Body LichSuCT lichSuCT
    );
    @DELETE("lichsu_ct/{idchutiem}")
    Call<JsonObject> xoaLichSu_CT (@Path("idchutiem") int idchutiem);
    //L???ch s??? nh??n vi??n
    @GET("lichsu_nv/{idnhanvien}")
    Call<ArrayList<LichSuNV>> layDSLS_NV(@Path("idnhanvien") int idnhanvien);
    @POST("lichsu_nv/{idnhanvien}")
    Call<JsonObject> themLichSu_NV (
            @Path("idnhanvien") int idnhanvien,
            @Body LichSuNV lichSuNV
    );
    @DELETE("lichsu_nv/{idnhanvien}")
    Call<JsonObject> xoaLichSu_NV (@Path("idnhanvien") int idnhanvien);
    //L???ch s??? kh??ch h??ng
    @GET("lichsu_kh/{idkhachhang}")
    Call<ArrayList<LichSuKH>> layDSLS_KH(@Path("idkhachhang") int idkhachhang);
    @POST("lichsu_kh/{idkhachhang}")
    Call<JsonObject> themLichSu_KH (
            @Path("idkhachhang") int idkhachhang,
            @Body LichSuKH lichSuKH
    );
    @DELETE("lichsu_kh/{idkhachhang}")
    Call<JsonObject> xoaLichSu_KH (@Path("idkhachhang") int idkhachhang);
    //????ng k??
    @POST("taikhoan")
    Call<JsonObject> taoTaiKhoan (
            @Body TaiKhoan taikhoan
    );
    @PUT("taikhoan/{id}")
    Call<JsonObject> capnhatTaiKhoan (
            @Path("id") int id,
            @Body TaiKhoan taiKhoan
    );
    //T??i kho???n
    @GET("taikhoan")
    Call<ArrayList<TaiKhoan>> layDSTaiKhoan();
    @GET("taikhoan/{id}")
    Call<TaiKhoan> lay1TaiKhoan(@Path("id") int id);
    @DELETE("taikhoan/{id}")
    Call<JsonObject> xoaTaiKhoan (@Path("id") int id);
    //?????a ??i???m
    @GET("diadiem")
    Call<ArrayList<DiaDiem>> layDSDiaDiem();
    @GET("diadiem/{id}")
    Call<DiaDiem> lay1DiaDiem(@Path("id") int id);
    @PUT("diadiem/{id}")
    Call<JsonObject> capnhatDiaDiem (
            @Path("id") int id,
            @Body DiaDiem diaDiem
    );
    @POST("diadiem")
    Call<JsonObject> taoDiaDiem (
            @Body DiaDiem diaDiem
    );
    @PATCH("taikhoan/{id}")
    Call<JsonObject> capnhatSohuu (
            @Path("id") int id
    );
    //B??n Bida
    @GET("ban/{id}")
    Call<BanBida> lay1BanBidatheoID(@Path("id") int id);
    @PUT("ban/{id}")
    Call<JsonObject> capnhatBantheoID (
            @Path("id") int id,
            @Body BanBida banBida
    );

    @GET("banbida/{iddiadiem}")
    Call<ArrayList<BanBida>> layDSBanBida(@Path("iddiadiem") int id);
    @POST("banbida")
    Call<JsonObject> themBanBida(@Body BanBida banBida);
    @GET("banbida/{iddiadiem}/{id}")
    Call<BanBida> lay1BanBida(@Path("iddiadiem") int iddiadiem, @Path("id") int id);
    @PUT("banbida/{iddiadiem}/{id}")
    Call<JsonObject> capnhatBan (
            @Path("iddiadiem") int iddiadiem, @Path("id") int id,
            @Body BanBida banBida
    );
    @DELETE("banbida/{iddiadiem}/{id}")
    Call<JsonObject> xoaBan (
            @Path("iddiadiem") int iddiadiem, @Path("id") int id
    );
    //Nh??n vi??n
    @GET("nhanvien/{iddiadiem}")
    Call<ArrayList<NhanVien>> layDSNhanVien(@Path("iddiadiem") int id);
    @POST("nhanvien")
    Call<JsonObject> themNhanVien(@Body NhanVien nhanVien);
    @GET("nhanvien/{iddiadiem}/{id}")
    Call<NhanVien> lay1NhanVien(@Path("iddiadiem") int iddiadiem, @Path("id") int id);
    @PUT("nhanvien/{iddiadiem}/{id}")
    Call<JsonObject> capnhatNhanVien (
            @Path("iddiadiem") int iddiadiem, @Path("id") int id,
            @Body NhanVien nhanVien
    );
    @DELETE("nhanvien/{iddiadiem}/{id}")
    Call<JsonObject> xoaNhanVien (
            @Path("iddiadiem") int iddiadiem, @Path("id") int id
    );
    //L???y nh??n vi??n theo id
    @GET("nhanvien")
    Call<ArrayList<NhanVien>> layDSNVToan();
    //Kho
    @GET("kho/{iddiadiem}")
    Call<ArrayList<Kho>> layDSVatPham(@Path("iddiadiem") int id);
    @POST("kho")
    Call<JsonObject> themVatPham(@Body Kho kho);
    @GET("kho/{iddiadiem}/{id}")
    Call<Kho> lay1VatPham(@Path("iddiadiem") int iddiadiem, @Path("id") int id);
    @PUT("kho/{iddiadiem}/{id}")
    Call<JsonObject> capnhatVatPham (
            @Path("iddiadiem") int iddiadiem, @Path("id") int id,
            @Body Kho kho
    );
    @DELETE("kho/{iddiadiem}/{id}")
    Call<JsonObject> xoaVatPham (
            @Path("iddiadiem") int iddiadiem, @Path("id") int id
    );
    //Phi???u nh???p
    @GET("phieunhap/{iddiadiem}")
    Call<ArrayList<PhieuNhap>> layDSPhieuNhap(@Path("iddiadiem") int id);
    @GET("phieunhap/{iddiadiem}/{id}")
    Call<PhieuNhap> lay1PhieuNhap(@Path("iddiadiem") int iddiadiem, @Path("id") int id);
    @POST("phieunhap")
    Call<JsonObject> themPhieuNhap(@Body PhieuNhap phieunhap);
    @PUT("phieunhap/{iddiadiem}/{id}")
    Call<JsonObject> suaPhieuNhap (
            @Path("iddiadiem") int iddiadiem, @Path("id") int id,
            @Body PhieuNhap phieunhap
    );
    @DELETE("phieunhap/{iddiadiem}/{id}")
    Call<JsonObject> xoaPhieuNhap (
            @Path("iddiadiem") int iddiadiem, @Path("id") int id
    );
    //Chi ti???t phi???u nh???p
    @GET("ctphieunhap/{idphieunhap}")
    Call<ArrayList<CTPhieuNhap>> layDSCTPhieuNhap(@Path("idphieunhap") int id);
    @POST("ctphieunhap/{idphieunhap}")
    Call<JsonObject> themCTPhieuNhap(@Path("idphieunhap") int id, @Body CTPhieuNhap ctpn);
    @DELETE("ctphieunhap/{id}")
    Call<JsonObject> xoaCTPhieuNhap (
            @Path("id") int id
    );
    //Booking
    @GET("booking")
    Call<ArrayList<Booking>> layDSBookingToan();
    @GET("booking/{iddiadiem}")
    Call<ArrayList<Booking>> layDSBooking(@Path("iddiadiem") int id);
    @GET("booking/{iddiadiem}/{id}")
    Call<Booking> lay1Booking(@Path("iddiadiem") int iddiadiem, @Path("id") int id);
    @POST("booking")
    Call<JsonObject> themBooking(@Body Booking booking);
    @PUT("booking/{iddiadiem}/{id}")
    Call<JsonObject> suaBooking (
            @Path("iddiadiem") int iddiadiem, @Path("id") int id,
            @Body Booking booking
    );
    @PUT("booking/{id}")
    Call<JsonObject> suaBookingXacthuc (
            @Path("id") int id,
            @Body Booking booking
    );
    //Chi ti???t Booking
    @GET("ctbooking/{idbooking}")
    Call<ArrayList<CTBooking>> layDSCTBooking(@Path("idbooking") int id);
    @GET("ctbooking")
    Call<ArrayList<CTBooking>> layDSCTBookingToan();
    @POST("ctbooking/{idbooking}")
    Call<JsonObject> themCTBooking(@Path("idbooking") int id, @Body CTBooking ctbooking);
    @PUT("ctbooking/{idbooking}")
    Call<JsonObject> suaCTBooking (
            @Path("idbooking") int idbooking,
            @Body CTBooking ctbooking
    );
    @DELETE("ctbooking/{idbooking}")
    Call<JsonObject> xoaCTBooking (
            @Path("idbooking") int id
    );

}
