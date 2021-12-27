package com.example.bida.ChatBot;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.KNN.BGiuXe;
import com.example.bida.KNN.DVAnUong;
import com.example.bida.KNN.RongRai;
import com.example.bida.KNN.SLNguoi;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.Kho;
import com.example.bida.R;
import com.google.gson.JsonObject;
import java.util.ArrayList;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainChatBot extends AppCompatActivity  {
    public ArrayList<BanBida> databan = new ArrayList<>();
    public ArrayList<DiaDiem> data = new ArrayList<>();
    public ArrayList<Kho> datakho = new ArrayList<>();
    public ArrayList<Booking> databooking = new ArrayList<>();
    Button btn_khonggian, btn_dichvuanuong, btn_soluongnguoichoi, btn_baigiuxe;



    String sdt;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbot);

        Intent intent = getIntent();
        sdt = intent.getStringExtra("number");

        btn_khonggian = (Button) findViewById(R.id.btn_KhongGian);
        btn_dichvuanuong = (Button) findViewById(R.id.btn_DvAnUong);
        btn_soluongnguoichoi = (Button) findViewById(R.id.btn_SLNguoiChoi);
        btn_baigiuxe = (Button) findViewById(R.id.btn_BaiGiuXe);


        ApiService.apiService.layDSDiaDiem()
                .enqueue(new Callback<ArrayList<DiaDiem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<DiaDiem>> call, Response<ArrayList<DiaDiem>> response) {
                        data = response.body();
                        for(int i = 0 ; i< data.size(); i++){
                            RongRai r = new RongRai(data.get(i).getTen(),0,0.0);
                            ApiService.apiService.layDSBanBida(data.get(i).getId())
                                    .enqueue(new Callback<ArrayList<BanBida>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<BanBida>> call, Response<ArrayList<BanBida>> response) {
                                            int soluong = 0 ;
                                            databan = response.body();
                                            for(int i = 0; i< databan.size(); i++){
                                                soluong = soluong + databan.get(i).getSoluong();
                                            }
                                            r.setSoluong(soluong);
                                            ApiService.apiService.themRongRai(r)
                                                    .enqueue(new Callback<JsonObject>() {
                                                        @Override
                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                        }

                                                        @Override
                                                        public void onFailure(Call<JsonObject> call, Throwable t) {

                                                        }
                                                    });
                                        }
                                        @Override
                                        public void onFailure(Call<ArrayList<BanBida>> call, Throwable t) {

                                        }
                                    });

                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<DiaDiem>> call, Throwable t) {
                        Toast.makeText(MainChatBot.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });

        ApiService.apiService.layDSDiaDiem()
                .enqueue(new Callback<ArrayList<DiaDiem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<DiaDiem>> call, Response<ArrayList<DiaDiem>> response) {
                        data = response.body();
                        for(int i = 0 ; i< data.size(); i++){
                            SLNguoi r = new SLNguoi(data.get(i).getTen(),0,0.0);
                            ApiService.apiService.layDSBooking(data.get(i).getId())
                                    .enqueue(new Callback<ArrayList<Booking>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                                            databooking = response.body();
                                            r.setSoluong(databooking.size());
                                            ApiService.apiService.themSLNguoi(r)
                                                    .enqueue(new Callback<JsonObject>() {
                                                        @Override
                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                        }

                                                        @Override
                                                        public void onFailure(Call<JsonObject> call, Throwable t) {

                                                        }
                                                    });
                                        }
                                        @Override
                                        public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                                        }
                                    });

                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<DiaDiem>> call, Throwable t) {
                        Toast.makeText(MainChatBot.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        ApiService.apiService.layDSDiaDiem()
                .enqueue(new Callback<ArrayList<DiaDiem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<DiaDiem>> call, Response<ArrayList<DiaDiem>> response) {
                        data = response.body();
                        for(int i = 0 ; i< data.size(); i++){
                            DVAnUong r = new DVAnUong(data.get(i).getTen(),0,0.0);
                            ApiService.apiService.layDSVatPham(data.get(i).getId())
                                    .enqueue(new Callback<ArrayList<Kho>>() {
                                        @Override
                                        public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                                            int soluong = 0 ;
                                            datakho = response.body();
                                            for(int i = 0; i< datakho.size(); i++){
                                                soluong = soluong + datakho.get(i).getSoluong();
                                            }
                                            r.setSoluong(soluong);
                                            ApiService.apiService.themDichVu(r)
                                                    .enqueue(new Callback<JsonObject>() {
                                                        @Override
                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                        }

                                                        @Override
                                                        public void onFailure(Call<JsonObject> call, Throwable t) {

                                                        }
                                                    });
                                        }
                                        @Override
                                        public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {

                                        }
                                    });

                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<DiaDiem>> call, Throwable t) {
                        Toast.makeText(MainChatBot.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        ApiService.apiService.layDSDiaDiem()
                .enqueue(new Callback<ArrayList<DiaDiem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<DiaDiem>> call, Response<ArrayList<DiaDiem>> response) {
                        data = response.body();
                        for(int i = 0 ; i< data.size(); i++){
                            BGiuXe r = new BGiuXe(data.get(i).getTen(),0,0.0);
                            r.setSoluong(data.get(i).getBaigiuxe());
                            ApiService.apiService.themBaiGiuXe(r)
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

                    @Override
                    public void onFailure(Call<ArrayList<DiaDiem>> call, Throwable t) {
                        Toast.makeText(MainChatBot.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });

        btn_khonggian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainChatBot.this, KhongGian.class);
                intent.putExtra("number", sdt);
                startActivity(intent);
            }
        });
        btn_dichvuanuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainChatBot.this, DichVu.class);
                intent.putExtra("number", sdt);
                startActivity(intent);
            }
        });
        btn_soluongnguoichoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainChatBot.this, SLNguoiChoi.class);
                intent.putExtra("number", sdt);
                startActivity(intent);
            }
        });
        btn_baigiuxe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainChatBot.this, BaiGiuXe.class);
                intent.putExtra("number", sdt);
                startActivity(intent);
            }
        });

    }

}
