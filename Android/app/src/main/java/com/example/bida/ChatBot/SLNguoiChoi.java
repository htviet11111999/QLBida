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
import com.example.bida.KNN.RongRai;
import com.example.bida.KNN.SLNguoi;
import com.example.bida.Menu_KH;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SLNguoiChoi extends AppCompatActivity {
    Button btn_tieptuc, btn_thoat;
    RadioGroup rd_slnguoi;
    TextView ketqua;
    String sdt ;

    public ArrayList<SLNguoi> kt = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot_soluongnguoi);

        Intent intent = getIntent();
        sdt= intent.getStringExtra("number");

        ketqua = (TextView) findViewById(R.id.ketqua_slnguoi);
        rd_slnguoi = (RadioGroup) findViewById(R.id.rd_slnguoi);
        btn_tieptuc = (Button) findViewById(R.id.btn_tt_slnguoi);
        btn_thoat = (Button) findViewById(R.id.btn_thoatslnguoi);

        rd_slnguoi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i){
                    case R.id.radio_dongduc:
                        ApiService.apiService.layDSSLNguoi()
                                .enqueue(new Callback<ArrayList<SLNguoi>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<SLNguoi>> call, Response<ArrayList<SLNguoi>> response) {
                                        kt = response.body();
                                        int max = kt.get(0).getSoluong();
                                        for (int l = 1; l < kt.size(); l++) {
                                            if (max < kt.get(l).getSoluong()) {
                                                max = kt.get(l).getSoluong();
                                            }
                                        }
                                        SLNguoi p1 = new SLNguoi();
                                        p1.setSoluong(max);
                                        String kq1 = KNN(kt,kt.size(),p1);
                                        if (kq1.equals("") == true){
                                            ketqua.setText("Hiện không có tiệm nào phù hợp !");
                                        }
                                        else
                                        ketqua.setText("Tiệm Bida phù hợp với mong muốn của bạn là:  "+kq1);
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<SLNguoi>> call, Throwable t) {

                                    }
                                });
                        break;
                    case R.id.radio_vuavua:
                        ApiService.apiService.layDSSLNguoi()
                                .enqueue(new Callback<ArrayList<SLNguoi>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<SLNguoi>> call, Response<ArrayList<SLNguoi>> response) {
                                        kt = response.body();
                                        int tong = 0;
                                        int dem = 0;
                                        for (int z = 0; z < kt.size(); z++)
                                        {
                                            tong = tong + kt.get(z).getSoluong();
                                            dem = dem + 1;
                                        }
                                        Log.e("TB",""+tong/dem);
                                        SLNguoi p2 = new SLNguoi();
                                        p2.setSoluong(tong/dem);
                                        String kq2 = KNN(kt,kt.size(),p2);
                                        if (kq2.equals("") == true){
                                            ketqua.setText("Hiện không có tiệm nào phù hợp !");
                                        }
                                        else
                                        ketqua.setText("Tiệm Bida phù hợp với mong muốn của bạn là:  "+kq2);
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<SLNguoi>> call, Throwable t) {

                                    }
                                });
                        break;
                    case R.id.radio_it:
                        ApiService.apiService.layDSSLNguoi()
                                .enqueue(new Callback<ArrayList<SLNguoi>>() {
                                    @Override
                                    public void onResponse(Call<ArrayList<SLNguoi>> call, Response<ArrayList<SLNguoi>> response) {
                                        kt = response.body();
                                        SLNguoi p = new SLNguoi();
                                        p.setSoluong(0);
                                        String kq = KNN(kt,kt.size(),p);
                                        if (kq.equals("") == true){
                                            ketqua.setText("Hiện không có tiệm nào phù hợp !");
                                        }
                                        else
                                        ketqua.setText("Tiệm Bida phù hợp với mong muốn của bạn là:  "+kq);
                                    }

                                    @Override
                                    public void onFailure(Call<ArrayList<SLNguoi>> call, Throwable t) {

                                    }
                                });
                        break;
                }
            }
        });

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.xoaRongRai()
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                ApiService.apiService.xoaDichVu()
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
                                                                                Intent intent = new Intent(SLNguoiChoi.this, Menu_KH.class);
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
                ApiService.apiService.xoaRongRai()
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                ApiService.apiService.xoaDichVu()
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
                                                                                Intent intent = new Intent(SLNguoiChoi.this, MainChatBot.class);
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
    public String KNN(ArrayList<SLNguoi> r, int n, SLNguoi p) {
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
