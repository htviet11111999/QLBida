package com.example.bida.Model;

import com.example.bida.Api.ApiService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSuQTV {
    private int id;
    private String noidung;
    private String thoigian;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public LichSuQTV(int id, String noidung, String thoigian) {
        this.id = id;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }
    public LichSuQTV() {
        this.id = id;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }
    public static void ThemLichSu (LichSuQTV ls){
        ApiService.apiService.themLichSu_QTV(ls)
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
