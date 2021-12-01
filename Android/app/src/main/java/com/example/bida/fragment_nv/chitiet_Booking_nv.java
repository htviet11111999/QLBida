package com.example.bida.fragment_nv;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Adapter.datvatphamAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_NV;
import com.example.bida.Model.Booking;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.PhieuNhap;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class chitiet_Booking_nv extends AppCompatActivity {
    public  static  int id;
    public  static Booking booking;
    TextView tv_kh, tv_ngaylap, tv_hotennv, tv_tienthanhtoan,tv_ngaychoi, tv_giochoi, tv_trangthai;
    Button btn_batdauchoi, btn_thanhtoan, btn_chitiet, btn_xoa, btn_thoat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_booking_nv);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        tv_kh = (TextView) findViewById(R.id.tenkh_booking);
        tv_ngaylap = (TextView) findViewById(R.id.ngaylap_booking);
        tv_hotennv = (TextView) findViewById(R.id.hotennhanvien_booking);
        tv_tienthanhtoan = (TextView) findViewById(R.id.tienthanhtoan_booking);
        tv_ngaychoi = (TextView) findViewById(R.id.ngaychoi_booking);
        tv_giochoi = (TextView) findViewById(R.id.giochoi_booking);
        tv_trangthai = (TextView) findViewById(R.id.trangthai_booking);
        btn_batdauchoi = (Button) findViewById(R.id.batdauchoi_booking);
        btn_xoa = (Button) findViewById(R.id.xoa_booking);
        btn_thoat = (Button) findViewById(R.id.thoat_booking);
        btn_thanhtoan = (Button) findViewById(R.id.thanhtoan_booking);
        btn_chitiet = (Button) findViewById(R.id.chitiet_booking);
        ApiService.apiService.lay1Booking(QuanLyDSBooking.madiadiem,id)
                .enqueue(new Callback<Booking>() {
                    @Override
                    public void onResponse(Call<Booking> call, Response<Booking> response) {
                        booking = response.body();
                        if(booking.getTrangthai() == 4){
                            btn_batdauchoi.setVisibility(View.GONE);
                            btn_thanhtoan.setVisibility(View.GONE);
                            btn_xoa.setVisibility(View.GONE);
                        }
                        String strArrtmp[]=booking.getNgaychoi().split("/");
                        int ngay=Integer.parseInt(strArrtmp[0]);
                        int thang=Integer.parseInt(strArrtmp[1]);
                        int nam=Integer.parseInt(strArrtmp[2]);
                        String s = booking.getGiochoi()+"";
                        String strArr[]=s.split(":");
                        int gio=Integer.parseInt(strArr[0]);
                        String sau=(strArr[1]);
                        String ss[]= sau.split(" ");
                        int phut = Integer.parseInt(ss[0]);
                        int thoigiandat = gio * 60 + phut;
                        Log.e("ngay dat",""+ngay+"/"+thang+"/"+nam);
                        Log.e("gio dat",""+gio+":"+phut+" "+thoigiandat);

                        Calendar calendar = Calendar.getInstance();
                        int ngayhientai = calendar.get(Calendar.DATE);
                        int thanghientai  = calendar.get(Calendar.MONTH) + 1;
                        int namhientai  = calendar.get(Calendar.YEAR);
                        int giohientai = calendar.get(Calendar.HOUR);
                        int phuthientai = calendar.get(Calendar.MINUTE);
                        Log.e("ngay hien tai",""+ngayhientai+"/"+thanghientai+"/"+namhientai);
                        int thoigianhientai = giohientai *60 + phuthientai;
                        Log.e("gio hien tai",""+giohientai+":"+phuthientai+" "+thoigianhientai);

                        int thoigian = Math.abs(thoigianhientai - thoigiandat);
                        if (ngay == ngayhientai && thang == thanghientai && nam == namhientai && gio <= giohientai && thoigian > 30){
                            Toast.makeText(chitiet_Booking_nv.this, "Đã hết thời gian chơi !", Toast.LENGTH_SHORT).show();
                            btn_batdauchoi.setVisibility(View.GONE);
                            btn_thanhtoan.setVisibility(View.GONE);
                        }
                        tv_kh.setText(String.format("Khách hàng : %s", booking.getTenkhachhang()));
                        tv_ngaylap.setText(String.format("Ngày lập : %s", booking.getNgay()));
                        tv_hotennv.setText(String.format("Họ tên nhân viên lập : %s", booking.getTennhanvien()));
                        tv_tienthanhtoan.setText(String.format("Tiền thanh toán : %s", booking.getTienthanhtoan()));
                        tv_ngaychoi.setText(String.format("Ngày chơi : %s", booking.getNgaychoi()));
                        tv_giochoi.setText(String.format("Giờ bắt đầu chơi : %s", booking.getGiochoi()));
                        if(booking.getTienthanhtoan() != 0 && (booking.getTrangthai() == 1 ||booking.getTrangthai() == 2||booking.getTrangthai() == 0)) btn_xoa.setVisibility(View.GONE);
                        if(booking.getTrangthai() == 1 ||booking.getTrangthai() == 2 ) btn_thanhtoan.setVisibility(View.GONE);
                        if (booking.getTrangthai() == 2) btn_batdauchoi.setVisibility(View.GONE);
                        if(booking.getTrangthai() == 3 ) {
                            btn_xoa.setVisibility(View.GONE);
                            btn_batdauchoi.setVisibility(View.GONE);
                        }
                        String tt ;
                        if (booking.getTrangthai() == 0 ) {
                            tt = "Chưa thanh toán";
                            tv_trangthai.setTextColor(Color.parseColor("#D80C0C"));
                        }
                        else if (booking.getTrangthai() == 2 ) {
                            tt = "Chưa xác nhận";
                            tv_trangthai.setTextColor(Color.parseColor("#F3DB0B"));
                        }
                        else if (booking.getTrangthai() == 3) {
                            tt = "Đang chơi...";
                            tv_trangthai.setTextColor(Color.parseColor("#10DF19"));
                        }
                        else if (booking.getTrangthai() == 4) {
                            tt = "Đã hủy";
                            tv_trangthai.setTextColor(Color.parseColor("#FF0000"));
                        }
                        else {
                            tt = "Đã thanh toán";
                            tv_trangthai.setTextColor(Color.parseColor("#FFBB86FC"));
                        }
                        tv_trangthai.setText(String.format("Trạng thái : %s", tt));
                    }
                    @Override
                    public void onFailure(Call<Booking> call, Throwable t) {
                        Toast.makeText(chitiet_Booking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });

        btn_batdauchoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strArrtmp[]=booking.getNgaychoi().split("/");
                int ngay=Integer.parseInt(strArrtmp[0]);
                int thang=Integer.parseInt(strArrtmp[1]);
                int nam=Integer.parseInt(strArrtmp[2]);
                String s = booking.getGiochoi()+"";
                String strArr[]=s.split(":");
                int gio=Integer.parseInt(strArr[0]);
                String sau=(strArr[1]);
                String ss[]= sau.split(" ");
                int phut = Integer.parseInt(ss[0]);
                int thoigiandat = gio * 60 + phut;
                Log.e("ngay dat",""+ngay+"/"+thang+"/"+nam);
                Log.e("gio dat",""+gio+":"+phut+" "+thoigiandat);

                Calendar calendar = Calendar.getInstance();
                int ngayhientai = calendar.get(Calendar.DATE);
                int thanghientai  = calendar.get(Calendar.MONTH) + 1;
                int namhientai  = calendar.get(Calendar.YEAR);
                int giohientai = calendar.get(Calendar.HOUR);
                int phuthientai = calendar.get(Calendar.MINUTE);
                Log.e("ngay hien tai",""+ngayhientai+"/"+thanghientai+"/"+namhientai);
                int thoigianhientai = giohientai *60 + phuthientai;
                Log.e("gio hien tai",""+giohientai+":"+phuthientai+" "+thoigianhientai);

                int thoigian = Math.abs(thoigianhientai - thoigiandat);
                if (thoigian <= 30){
                    Booking b = booking;
                    Calendar cal=Calendar.getInstance();;
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String date = df.format(Calendar.getInstance().getTime());
                    b.setNgaychoi(date);
                    SimpleDateFormat dft= null;
                    dft =new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    //đưa lên giao diện
                    b.setGio(dft.format(cal.getTime()));
                    b.setTrangthai(3);
                    b.setGiochoithat("");
                    ApiService.apiService.suaBooking(QuanLyDSBooking.madiadiem,booking.getId(),b)
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(chitiet_Booking_nv.this, "Đã bắt đầu chơi !", Toast.LENGTH_SHORT).show();
                                    btn_batdauchoi.setVisibility(View.GONE);
                                    ApiService.apiService.lay1Booking(QuanLyDSBooking.madiadiem,id)
                                            .enqueue(new Callback<Booking>() {
                                                @Override
                                                public void onResponse(Call<Booking> call, Response<Booking> response) {
                                                    booking = response.body();
                                                    tv_kh.setText(String.format("Khách hàng : %s", booking.getTenkhachhang()));
                                                    tv_ngaylap.setText(String.format("Ngày lập : %s", booking.getNgay()));
                                                    tv_hotennv.setText(String.format("Họ tên nhân viên lập : %s", booking.getTennhanvien()));
                                                    tv_tienthanhtoan.setText(String.format("Tiền thanh toán : %s", booking.getTienthanhtoan()));
                                                    tv_ngaychoi.setText(String.format("Ngày chơi : %s", booking.getNgaychoi()));
                                                    tv_giochoi.setText(String.format("Giờ bắt đầu chơi : %s", booking.getGiochoi()));
                                                    if(booking.getTienthanhtoan() != 0) btn_xoa.setVisibility(View.GONE);
                                                    if(booking.getTrangthai() == 1 ||booking.getTrangthai() == 2 ) btn_thanhtoan.setVisibility(View.GONE);
                                                    if(booking.getTrangthai() == 3 ) btn_batdauchoi.setVisibility(View.GONE);
                                                    String tt ;
                                                    if (booking.getTrangthai() == 0 ) {
                                                        tt = "Chưa thanh toán";
                                                        tv_trangthai.setTextColor(Color.parseColor("#D80C0C"));
                                                    }
                                                    else if (booking.getTrangthai() == 2 ) {
                                                        tt = "Chưa xác nhận";
                                                        tv_trangthai.setTextColor(Color.parseColor("#F3DB0B"));
                                                    }
                                                    else if (booking.getTrangthai() == 3) {
                                                        tt = "Đang chơi...";
                                                        tv_trangthai.setTextColor(Color.parseColor("#10DF19"));
                                                    }
                                                    else if (booking.getTrangthai() == 4) {
                                                        tt = "Đã hủy";
                                                        tv_trangthai.setTextColor(Color.parseColor("#FF0000"));
                                                    }
                                                    else {
                                                        tt = "Đã thanh toán";
                                                        tv_trangthai.setTextColor(Color.parseColor("#FFBB86FC"));
                                                    }
                                                    tv_trangthai.setText(String.format("Trạng thái : %s", tt));
                                                }
                                                @Override
                                                public void onFailure(Call<Booking> call, Throwable t) {
                                                    Toast.makeText(chitiet_Booking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(chitiet_Booking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    Booking b = booking;
                    b.setTrangthai(3);
                    b.setGiochoithat("");
                    ApiService.apiService.suaBooking(QuanLyDSBooking.madiadiem,booking.getId(),b)
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(chitiet_Booking_nv.this, "Đã bắt đầu chơi !", Toast.LENGTH_SHORT).show();
                                    btn_batdauchoi.setVisibility(View.GONE);
                                    ApiService.apiService.lay1Booking(QuanLyDSBooking.madiadiem,id)
                                            .enqueue(new Callback<Booking>() {
                                                @Override
                                                public void onResponse(Call<Booking> call, Response<Booking> response) {
                                                    booking = response.body();
                                                    tv_kh.setText(String.format("Khách hàng : %s", booking.getTenkhachhang()));
                                                    tv_ngaylap.setText(String.format("Ngày lập : %s", booking.getNgay()));
                                                    tv_hotennv.setText(String.format("Họ tên nhân viên lập : %s", booking.getTennhanvien()));
                                                    tv_tienthanhtoan.setText(String.format("Tiền thanh toán : %s", booking.getTienthanhtoan()));
                                                    tv_ngaychoi.setText(String.format("Ngày chơi : %s", booking.getNgaychoi()));
                                                    tv_giochoi.setText(String.format("Giờ bắt đầu chơi : %s", booking.getGiochoi()));

                                                    if(booking.getTienthanhtoan() != 0) btn_xoa.setVisibility(View.GONE);
                                                    if(booking.getTrangthai() == 1 ||booking.getTrangthai() == 2 || booking.getTrangthai() == 4 ) btn_thanhtoan.setVisibility(View.GONE);
                                                    if(booking.getTrangthai() == 3 || booking.getTrangthai() == 4 ) btn_batdauchoi.setVisibility(View.GONE);
                                                    String tt ;
                                                    if (booking.getTrangthai() == 0 ) {
                                                        tt = "Chưa thanh toán";
                                                        tv_trangthai.setTextColor(Color.parseColor("#D80C0C"));
                                                    }
                                                    else if (booking.getTrangthai() == 2 ) {
                                                        tt = "Chưa xác nhận";
                                                        tv_trangthai.setTextColor(Color.parseColor("#F3DB0B"));
                                                    }
                                                    else if (booking.getTrangthai() == 3) {
                                                        tt = "Đang chơi...";
                                                        tv_trangthai.setTextColor(Color.parseColor("#10DF19"));
                                                    }
                                                    else if (booking.getTrangthai() == 4) {
                                                        tt = "Đã hủy";
                                                        tv_trangthai.setTextColor(Color.parseColor("#FF0000"));
                                                    }
                                                    else {
                                                        tt = "Đã thanh toán";
                                                        tv_trangthai.setTextColor(Color.parseColor("#FFBB86FC"));
                                                    }
                                                    tv_trangthai.setText(String.format("Trạng thái : %s", tt));
                                                }
                                                @Override
                                                public void onFailure(Call<Booking> call, Throwable t) {
                                                    Toast.makeText(chitiet_Booking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(chitiet_Booking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(chitiet_Booking_nv.this);
                builder1.setMessage("Bạn chắc chắn muốn thanh toán booking này chứ ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                Booking b = booking;
                                b.setTrangthai(1);
                                Calendar cal=Calendar.getInstance();;
                                SimpleDateFormat dft= null;
                                dft =new SimpleDateFormat("hh:mm a", Locale.getDefault());
                                //đưa lên giao diện
                                b.setGiochoithat(dft.format(cal.getTime()));
                                String s = booking.getGiochoi()+"";
                                String strArr[]=s.split(":");
                                int gio=Integer.parseInt(strArr[0]);
                                String sau=(strArr[1]);
                                String ss[]= sau.split(" ");
                                int phut = Integer.parseInt(ss[0]);
                                int thoigiandat = gio * 60 + phut;

                                String s1 = b.getGiochoithat()+"";
                                String strArr1[]=s1.split(":");
                                int gio1=Integer.parseInt(strArr1[0]);
                                String sau1=(strArr[1]);
                                String ss1[]= sau1.split(" ");
                                int phut1 = Integer.parseInt(ss1[0]);
                                int thoigiandat1 = gio1 * 60 + phut1;

                                int thoigian = Math.abs(thoigiandat1 - thoigiandat);
                                int thanhtien = (thoigian * booking.getTienthanhtoan())/booking.getGiaban();
                                b.setTienthanhtoan(thanhtien);

                                //đưa lên giao diện

                                ApiService.apiService.suaBooking(QuanLyDSBooking.madiadiem,booking.getId(),b)
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                Toast.makeText(chitiet_Booking_nv.this, "Đã bắt đầu chơi !", Toast.LENGTH_SHORT).show();
                                                btn_batdauchoi.setVisibility(View.GONE);
                                                ApiService.apiService.lay1Booking(QuanLyDSBooking.madiadiem,id)
                                                        .enqueue(new Callback<Booking>() {
                                                            @Override
                                                            public void onResponse(Call<Booking> call, Response<Booking> response) {
                                                                Toast.makeText(chitiet_Booking_nv.this, "Thanh toán thành công !", Toast.LENGTH_SHORT).show();
                                                                dialog.cancel();
                                                                Intent intent = new Intent(chitiet_Booking_nv.this, Menu_NV.class);
                                                                intent.putExtra("number",Menu_NV.sdt);
                                                                startActivity(intent);
                                                            }
                                                            @Override
                                                            public void onFailure(Call<Booking> call, Throwable t) {
                                                                Toast.makeText(chitiet_Booking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                Toast.makeText(chitiet_Booking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        });

                builder1.setNegativeButton(
                        "Thoát",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        btn_chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chitiet_Booking_nv.this, chitiet_datvatpham_nv.class);
                startActivity(intent);
            }
        });

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chitiet_Booking_nv.this, Menu_NV.class);
                intent.putExtra("number", Menu_NV.sdt);
                startActivity(intent);
            }
        });


    }
}
