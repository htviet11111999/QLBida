package com.example.bida.ChatBot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.KNN.DVAnUong;
import com.example.bida.KNN.RongRai;
import com.example.bida.Menu_KH;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DichVu extends AppCompatActivity {
    Button btn_tieptuc, btn_thoat;
    RadioGroup rd_dichvu;
    TextView ketqua;
    String sdt ;

    public ArrayList<DVAnUong> kt = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot_dichvuanuong);
        Intent intent = getIntent();
        sdt= intent.getStringExtra("number");

        ketqua = (TextView) findViewById(R.id.ketqua_dvanuong);
        rd_dichvu = (RadioGroup) findViewById(R.id.rd_dvanuong);
        btn_tieptuc = (Button) findViewById(R.id.btn_tt_dvanuong);
        btn_thoat = (Button) findViewById(R.id.btn_thoatdvanuong);

        rd_dichvu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.radio_daydu:
                        ApiService.apiService.layDSDichVu()
                                .enqueue(new Callback<ArrayList<DVAnUong>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<DVAnUong>> call, Response<ArrayList<DVAnUong>> response) {
                                        kt = response.body();
                                        int max = kt.get(0).getSoluong();
                                        for (int l = 1; l < kt.size(); l++) {
                                            if (max < kt.get(l).getSoluong()) {
                                                max = kt.get(l).getSoluong();
                                            }
                                        }
                                        DVAnUong p1 = new DVAnUong();
                                        p1.setSoluong(max);
                                        String kq1 = KNN(kt,kt.size(),p1);
                                        if (kq1.equals("") == true){
                                            ketqua.setText("Hiện không có tiệm nào phù hợp !");
                                        }
                                        else
                                        ketqua.setText("Tiệm Bida phù hợp với mong muốn của bạn là:  "+kq1);
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<DVAnUong>> call, Throwable t) {

                                    }
                                });
                        break;
                    case R.id.radio_bt:
                        ApiService.apiService.layDSDichVu()
                                .enqueue(new Callback<ArrayList<DVAnUong>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<DVAnUong>> call, Response<ArrayList<DVAnUong>> response) {
                                        kt = response.body();
                                        int tong = 0;
                                        int dem = 0;
                                        for (int z = 0; z < kt.size(); z++)
                                        {
                                            tong = tong + kt.get(z).getSoluong();
                                            dem = dem + 1;
                                        }
                                        Log.e("TB",""+tong/dem);
                                        DVAnUong p2 = new DVAnUong();
                                        p2.setSoluong(tong/dem);
                                        String kq2 = KNN(kt,kt.size(),p2);
                                        if (kq2.equals("") == true){
                                            ketqua.setText("Hiện không có tiệm nào phù hợp !");
                                        }
                                        else
                                        ketqua.setText("Tiệm Bida phù hợp với mong muốn của bạn là:  "+kq2);
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<DVAnUong>> call, Throwable t) {

                                    }
                                });
                        break;
                    case R.id.radio_khongqt:
                        ApiService.apiService.layDSDichVu()
                                .enqueue(new Callback<ArrayList<DVAnUong>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<DVAnUong>> call, Response<ArrayList<DVAnUong>> response) {
                                        kt = response.body();
                                        DVAnUong p = new DVAnUong();
                                        p.setSoluong(0);
                                        String kq = KNN(kt,kt.size(),p);
                                        if (kq.equals("") == true){
                                            ketqua.setText("Hiện không có tiệm nào phù hợp !");
                                        }
                                        else
                                        ketqua.setText("Tiệm Bida phù hợp với mong muốn của bạn là:  "+kq);
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<DVAnUong>> call, Throwable t) {

                                    }
                                });
                        break;
                }
            }
        });

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.xoaDichVu()
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                ApiService.apiService.xoaRongRai()
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                ApiService.apiService.xoaSLNguoi()
                                                        .enqueue(new Callback<JsonObject>() {
                                                            @Override
                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                ApiService.apiService.xoaBaiGiuXe()
                                                                        .enqueue(new Callback<JsonObject>() {
                                                                            @Override
                                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                                Intent intent = new Intent(DichVu.this, Menu_KH.class);
                                                                                intent.putExtra("number", sdt);
                                                                                startActivity(intent);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                                                            }
                                                                        });

                                                            }

                                                            @Override
                                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                                            }
                                                        });

                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                            }
                                        });

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                            }
                        });

            }
        });
        btn_tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ApiService.apiService.xoaDichVu()
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                ApiService.apiService.xoaRongRai()
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                ApiService.apiService.xoaSLNguoi()
                                                        .enqueue(new Callback<JsonObject>() {
                                                            @Override
                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                ApiService.apiService.xoaBaiGiuXe()
                                                                        .enqueue(new Callback<JsonObject>() {
                                                                            @Override
                                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                                Intent intent = new Intent(DichVu.this, MainChatBot.class);
                                                                                intent.putExtra("number", sdt);
                                                                                startActivity(intent);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                                                            }
                                                                        });

                                                            }

                                                            @Override
                                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                                            }
                                                        });

                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                            }
                                        });

                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                            }
                        });


            }
        });

    }
    public String KNN(ArrayList<DVAnUong> r, int n, DVAnUong p) {
        for (int i = 0; i < n; i++) {
            r.get(i).setKhoangcach(Math.sqrt((r.get(i).getSoluong() - p.getSoluong()) * (r.get(i).getSoluong() - p.getSoluong()) + 0));
        }
        for (int i = 0; i < n; i++) {
            Log.e("KCCC", "" +r.get(i).getTentiem()+" == "+ r.get(i).getKhoangcach());
        }
        double min = r.get(0).getKhoangcach();
        String ten= r.get(0).getTentiem();
            for (int i = 1; i < n; i++) {
                if (min > r.get(i).getKhoangcach()) {
                    ten = r.get(i).getTentiem();
                }
            }


        return ten;
    }
}
