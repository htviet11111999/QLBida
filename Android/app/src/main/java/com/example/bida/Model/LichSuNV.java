package com.example.bida.Model;

import com.example.bida.Api.ApiService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSuNV {
    private int id;
    private int idnhanvien;
    private String noidung;
    private String thoigian;

    public LichSuNV(int id, int idnhanvien, String noidung, String thoigian) {
        this.id = id;
        this.idnhanvien = idnhanvien;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }
    public LichSuNV() {
        this.id = id;
        this.idnhanvien = idnhanvien;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdnhanvien() {
        return idnhanvien;
    }

    public void setIdnhanvien(int idnhanvien) {
        this.idnhanvien = idnhanvien;
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
    public static void ThemLichSu (int idnv,LichSuNV ls){
        ApiService.apiService.themLichSu_NV(idnv,ls)
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
