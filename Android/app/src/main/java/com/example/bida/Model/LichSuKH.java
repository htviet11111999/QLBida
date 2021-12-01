package com.example.bida.Model;

import com.example.bida.Api.ApiService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSuKH {
    private int id;
    private int idkhachhang;
    private String noidung;
    private String thoigian;

    public LichSuKH(int id, int idkhachhang, String noidung, String thoigian) {
        this.id = id;
        this.idkhachhang = idkhachhang;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }
    public LichSuKH() {
        this.id = id;
        this.idkhachhang = idkhachhang;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdkhachhang() {
        return idkhachhang;
    }

    public void setIdkhachhang(int idkhachhang) {
        this.idkhachhang = idkhachhang;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }
    public static void ThemLichSu (int idkh,LichSuKH ls){
        ApiService.apiService.themLichSu_KH(idkh,ls)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                    }
                });
    }
}
