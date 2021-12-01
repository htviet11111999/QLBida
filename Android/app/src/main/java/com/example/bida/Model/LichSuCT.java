package com.example.bida.Model;

import com.example.bida.Api.ApiService;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSuCT {
    private int id;
    private int idchutiem;
    private String noidung;
    private String thoigian;

    public LichSuCT(int id, int idchutiem, String noidung, String thoigian) {
        this.id = id;
        this.idchutiem = idchutiem;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }
    public LichSuCT() {
        this.id = id;
        this.idchutiem = idchutiem;
        this.noidung = noidung;
        this.thoigian = thoigian;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdchutiem() {
        return idchutiem;
    }

    public void setIdchutiem(int idchutiem) {
        this.idchutiem = idchutiem;
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
    public static void ThemLichSu (int idct,LichSuCT ls){
        ApiService.apiService.themLichSu_CT(idct,ls)
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
