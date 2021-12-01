package com.example.bida.fragment_kh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_KH;
import com.example.bida.Model.Booking;
import com.example.bida.Model.DiaDiem;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDiaDiem_KH extends AppCompatActivity {
    TextView tv_tentiem, tv_diachi,  tv_trangthai,tv_ghichu,tv_hotenchu;
    Button btn_booking,  btn_thoat;
    ArrayList<Booking> data = new ArrayList<>();
    ArrayList<Booking> booking = new ArrayList<>();
    public static int id;
    public static int idBooking;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietthongtin_diadiem_kh);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        tv_tentiem = (TextView) findViewById(R.id.ten_chitietQldd_kh);
        tv_diachi = (TextView) findViewById(R.id.diachi_chitietQldd_kh);
        tv_trangthai = (TextView) findViewById(R.id.trangthai_chitietQldd_kh);
        tv_ghichu = (TextView) findViewById(R.id.ghichu_chitietQldd_kh);
        tv_hotenchu = (TextView) findViewById(R.id.hotenchu_chitietQldd_kh);
        btn_booking = (Button) findViewById(R.id.booking_qldd_kh);
        btn_thoat = (Button) findViewById(R.id.thoat_qldd_kh);

        ApiService.apiService.layDSBookingToan()
                .enqueue(new Callback<ArrayList<Booking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                        booking = response.body();
                        Calendar calendar = Calendar.getInstance();
                        int ngayhientai = calendar.get(Calendar.DATE);
                        int thanghientai  = calendar.get(Calendar.MONTH) + 1;
                        int namhientai  = calendar.get(Calendar.YEAR);
                        int giohientai = 0;
                        int phuthientai = calendar.get(Calendar.MINUTE);
                        int td = calendar.get(Calendar.AM_PM);
                        if(td == 1){
                            giohientai = calendar.get(Calendar.HOUR) + 12;
                        }
                        else giohientai =calendar.get(Calendar.HOUR);
                        for (int i =0 ; i< booking.size();i++){
                            String strArrtmp[]=booking.get(i).getNgaychoi().split("/");
                            int ngay=Integer.parseInt(strArrtmp[0]);
                            int thang=Integer.parseInt(strArrtmp[1]);
                            int nam=Integer.parseInt(strArrtmp[2]);

                            String s = booking.get(i).getGiochoi()+"";
                            String strArr[]=s.split(":");
                            String sau=(strArr[1]);
                            String ss[]= sau.split(" ");
                            int phut = Integer.parseInt(ss[0]);
                            String tdd = ss[1];
                            Log.e("TDD",""+tdd);
                            int gio = 0;
                            if(tdd.equals("PM")==true ){
                                gio = Integer.parseInt(strArr[0]) + 12;
                            }
                            else gio = Integer.parseInt(strArr[0]);
                            Log.e("TG",""+gio+"/"+phut);
                            if (booking.get(i).getTrangthai() == 2 && ngayhientai == ngay && thanghientai == thang && namhientai == nam && Math.abs(gio*60+phut - giohientai*60+phuthientai) <=30   ){

                                btn_booking.setVisibility(View.GONE);
                                Toast.makeText(ChiTietDiaDiem_KH.this, "Bạn đã đặt lịch chơi trong thời điểm này rồi, vui lòng chơi theo lịch đã đặt trước hoặc hủy lịch đã đặt để đặt booking mới !", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                    }
                });

                        ApiService.apiService.lay1DiaDiem(id)
                .enqueue(new Callback<DiaDiem>() {
                    @Override
                    public void onResponse(Call<DiaDiem> call, Response<DiaDiem> response) {
                        DiaDiem d = response.body();
                        tv_tentiem.setText(String.format("Tên tiệm : %s", d.getTen()));
                        tv_diachi.setText(String.format("Địa chỉ : %s", d.getDiachi()));
                        String tt = "";
                        if (d.getTrangthai() == 0) tt = "Còn bàn";
                        else{
                            tt = "Hết bàn";
                            btn_booking.setVisibility(View.GONE);
                        }
                        tv_trangthai.setText(String.format("Trạng thái : %s", tt));
                        tv_ghichu.setText(String.format("Ghi chú : %s", d.getGhichu()));
                        tv_hotenchu.setText(String.format("Họ tên chủ : %s", d.getHotenchu()));
                    }

                    @Override
                    public void onFailure(Call<DiaDiem> call, Throwable t) {
                        Toast.makeText(ChiTietDiaDiem_KH.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietDiaDiem_KH.this, Menu_KH.class);
                intent.putExtra("number", Menu_KH.sdt);
                startActivity(intent);
            }
        });
        btn_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.layDSBooking(ChiTietDiaDiem_KH.id)
                        .enqueue(new Callback<ArrayList<Booking>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                                data = response.body();
                                for (int i = 0 ; i<data.size(); i++){
                                    if ((data.get(i).getSdt().equals(Menu_KH.sdt)) && (data.get(i).getTrangthai()==0)){
                                        idBooking = data.get(i).getId();
                                        break;
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                            }
                        });
                Intent intent = new Intent(ChiTietDiaDiem_KH.this, DatBan_KH.class);
                startActivity(intent);
            }
        });
    }
}
