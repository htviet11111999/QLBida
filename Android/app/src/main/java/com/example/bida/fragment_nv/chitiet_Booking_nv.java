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
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.PhieuNhap;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.NumberFormat;
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
    TextView tv_kh, tv_ngaylap, tv_hotennv, tv_tienthanhtoan,tv_ngaychoi, tv_giochoi, tv_trangthai, tv_tienthuc;
    Button btn_batdauchoi, btn_thanhtoan, btn_chitiet, btn_thoat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_booking_nv);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        tv_kh = (TextView) findViewById(R.id.tenkh_booking);
        tv_tienthuc = (TextView) findViewById(R.id.tienthuc_booking);
        tv_ngaylap = (TextView) findViewById(R.id.ngaylap_booking);
        tv_hotennv = (TextView) findViewById(R.id.hotennhanvien_booking);
        tv_tienthanhtoan = (TextView) findViewById(R.id.tienthanhtoan_booking);
        tv_ngaychoi = (TextView) findViewById(R.id.ngaychoi_booking);
        tv_giochoi = (TextView) findViewById(R.id.giochoi_booking);
        tv_trangthai = (TextView) findViewById(R.id.trangthai_booking);
        btn_batdauchoi = (Button) findViewById(R.id.batdauchoi_booking);
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
//                        if (ngay == ngayhientai && thang == thanghientai && nam == namhientai && gio <= giohientai && thoigian > 30){
//                            Toast.makeText(chitiet_Booking_nv.this, "Đã hết thời gian chơi !", Toast.LENGTH_SHORT).show();
//                            btn_batdauchoi.setVisibility(View.GONE);
//                            btn_thanhtoan.setVisibility(View.GONE);
//                        }
                        tv_kh.setText(String.format("Khách hàng : %s", booking.getTenkhachhang()));
                        tv_ngaylap.setText(String.format("Ngày lập : %s", booking.getNgay()));
                        tv_hotennv.setText(String.format("Họ tên nhân viên lập : %s", booking.getTennhanvien()));
                        double vnd = Math.round(booking.getTienthanhtoan() * 10) / 10 ;

                        // tạo 1 NumberFormat để định dạng tiền tệ theo tiêu chuẩn của Việt Nam
                        // đơn vị tiền tệ của Việt Nam là đồng
                        Locale localeVN = new Locale("vi", "VN");
                        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                        String str1 = currencyVN.format(vnd);
                        tv_tienthanhtoan.setText(String.format("Tiền dịch vụ : %s", str1));
                        tv_ngaychoi.setText(String.format("Ngày chơi : %s", booking.getNgaychoi()));
                        tv_giochoi.setText(String.format("Giờ bắt đầu chơi : %s", booking.getGiochoi()));
                        if(booking.getTrangthai() == 1 ||booking.getTrangthai() == 2 ) btn_thanhtoan.setVisibility(View.GONE);
                        if(booking.getTrangthai()==1){
                            btn_batdauchoi.setVisibility(View.GONE);
                        }
                        if (booking.getTrangthai() == 2) btn_batdauchoi.setVisibility(View.GONE);
                        if(booking.getTrangthai() == 3 ) {
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
                            tv_trangthai.setTextColor(Color.parseColor("#0FC6DF"));
                        }
                        else if (booking.getTrangthai() == 4) {
                            tt = "Đã hủy";
                            tv_trangthai.setTextColor(Color.parseColor("#FF6F00"));
                        }
                        else {
                            tt = "Đã thanh toán";
                            tv_trangthai.setTextColor(Color.parseColor("#04FF00"));
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
                String sau=(strArr[1]);
                String ss[]= sau.split(" ");
                int phut = Integer.parseInt(ss[0]);

                String tdd = ss[1];
                int gio = 0;
                if(tdd.equals("PM")==true ){
                    gio = Integer.parseInt(strArr[0]) + 12;
                }
                else gio = Integer.parseInt(strArr[0]);
                int thoigiandat = gio * 60 + phut;
                Log.e("ngay dat",""+ngay+"/"+thang+"/"+nam);
                Log.e("gio dat",""+gio+":"+phut+" "+thoigiandat);

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
                Log.e("ngay hien tai",""+ngayhientai+"/"+thanghientai+"/"+namhientai);
                int thoigianhientai = giohientai *60 + phuthientai;
                Log.e("gio hien tai",""+giohientai+":"+phuthientai+" "+thoigianhientai);

                int thoigian = thoigianhientai - thoigiandat;
                if (ngay==ngayhientai && thang ==  thanghientai && nam == namhientai && giohientai<=gio && thoigian < 0)  Toast.makeText(chitiet_Booking_nv.this, "Chưa đến giờ chưa của khách này !", Toast.LENGTH_SHORT).show();
                else if (ngay==ngayhientai && thang ==  thanghientai && nam == namhientai && giohientai<=gio && thoigian > 30)  Toast.makeText(chitiet_Booking_nv.this, "Đã hết giờ chơi !", Toast.LENGTH_SHORT).show();
                else {
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
                                                    double vnd = Math.round(booking.getTienthanhtoan() * 10) / 10 ;

                                                    // tạo 1 NumberFormat để định dạng tiền tệ theo tiêu chuẩn của Việt Nam
                                                    // đơn vị tiền tệ của Việt Nam là đồng
                                                    Locale localeVN = new Locale("vi", "VN");
                                                    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                                                    String str1 = currencyVN.format(vnd);
                                                    tv_tienthanhtoan.setText(String.format("Tiền dịch vụ : %s", str1));
                                                    tv_ngaychoi.setText(String.format("Ngày chơi : %s", booking.getNgaychoi()));
                                                    tv_giochoi.setText(String.format("Giờ bắt đầu chơi : %s", booking.getGiochoi()));
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
                                                        tv_trangthai.setTextColor(Color.parseColor("#0FC6DF"));
                                                    }
                                                    else if (booking.getTrangthai() == 4) {
                                                        tt = "Đã hủy";
                                                        tv_trangthai.setTextColor(Color.parseColor("#FF6F00"));
                                                    }
                                                    else {
                                                        tt = "Đã thanh toán";
                                                        tv_trangthai.setTextColor(Color.parseColor("#04FF00"));
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

//                else {
//                    Booking b = booking;
//                    b.setTrangthai(3);
//                    b.setGiochoithat("");
//                    ApiService.apiService.suaBooking(QuanLyDSBooking.madiadiem,booking.getId(),b)
//                            .enqueue(new Callback<JsonObject>() {
//                                @Override
//                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                                    Toast.makeText(chitiet_Booking_nv.this, "Đã bắt đầu chơi !", Toast.LENGTH_SHORT).show();
//                                    btn_batdauchoi.setVisibility(View.GONE);
//                                    ApiService.apiService.lay1Booking(QuanLyDSBooking.madiadiem,id)
//                                            .enqueue(new Callback<Booking>() {
//                                                @Override
//                                                public void onResponse(Call<Booking> call, Response<Booking> response) {
//                                                    booking = response.body();
//                                                    tv_kh.setText(String.format("Khách hàng : %s", booking.getTenkhachhang()));
//                                                    tv_ngaylap.setText(String.format("Ngày lập : %s", booking.getNgay()));
//                                                    tv_hotennv.setText(String.format("Họ tên nhân viên lập : %s", booking.getTennhanvien()));
//                                                    double vnd = Math.round(booking.getTienthanhtoan() * 10) / 10 ;
//
//                                                    // tạo 1 NumberFormat để định dạng tiền tệ theo tiêu chuẩn của Việt Nam
//                                                    // đơn vị tiền tệ của Việt Nam là đồng
//                                                    Locale localeVN = new Locale("vi", "VN");
//                                                    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
//                                                    String str1 = currencyVN.format(vnd);
//                                                    tv_tienthanhtoan.setText(String.format("Tiền dịch vụ : %s", str1));
//                                                    tv_ngaychoi.setText(String.format("Ngày chơi : %s", booking.getNgaychoi()));
//                                                    tv_giochoi.setText(String.format("Giờ bắt đầu chơi : %s", booking.getGiochoi()));
//
//                                                    if(booking.getTrangthai() == 1 ||booking.getTrangthai() == 2 || booking.getTrangthai() == 4 ) btn_thanhtoan.setVisibility(View.GONE);
//                                                    if(booking.getTrangthai() == 3 || booking.getTrangthai() == 4 ) btn_batdauchoi.setVisibility(View.GONE);
//                                                    String tt ;
//                                                    if (booking.getTrangthai() == 0 ) {
//                                                        tt = "Chưa thanh toán";
//                                                        tv_trangthai.setTextColor(Color.parseColor("#D80C0C"));
//                                                    }
//                                                    else if (booking.getTrangthai() == 2 ) {
//                                                        tt = "Chưa xác nhận";
//                                                        tv_trangthai.setTextColor(Color.parseColor("#F3DB0B"));
//                                                    }
//                                                    else if (booking.getTrangthai() == 3) {
//                                                        tt = "Đang chơi...";
//                                                        tv_trangthai.setTextColor(Color.parseColor("#0FC6DF"));
//                                                    }
//                                                    else if (booking.getTrangthai() == 4) {
//                                                        tt = "Đã hủy";
//                                                        tv_trangthai.setTextColor(Color.parseColor("#FF6F00"));
//                                                    }
//                                                    else {
//                                                        tt = "Đã thanh toán";
//                                                        tv_trangthai.setTextColor(Color.parseColor("#04FF00"));
//                                                    }
//                                                    tv_trangthai.setText(String.format("Trạng thái : %s", tt));
//                                                }
//                                                @Override
//                                                public void onFailure(Call<Booking> call, Throwable t) {
//                                                    Toast.makeText(chitiet_Booking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<JsonObject> call, Throwable t) {
//                                    Toast.makeText(chitiet_Booking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                }
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
                                SimpleDateFormat d= null;
                                d =new SimpleDateFormat("hh:mm a", Locale.getDefault());
                                //đưa lên giao diện
                                b.setGiochoithat(d.format(cal.getTime()));
                                String s = booking.getGiochoi()+"";
                                String strArr[]=s.split(":");
                                int gio=0;
                                String sau=(strArr[1]);
                                String ss[]= sau.split(" ");
                                int phut = Integer.parseInt(ss[0]);
                                String ttd = ss[1];
                                if(ttd.equals("PM")==true){
                                    gio = Integer.parseInt(strArr[0]) + 12;
                                }
                                else gio = Integer.parseInt(strArr[0]);
                                int thoigiandat = gio * 60 + phut;

                                Calendar calendar = Calendar.getInstance();

                                int giohientai = calendar.get(Calendar.HOUR);
                                int phuthientai = calendar.get(Calendar.MINUTE);
                                int td = calendar.get(Calendar.AM_PM);
                                String tt ="";
                                if(td == 1){
                                    tt = "PM";
                                }
                                else tt = "AM";
                                b.setGiochoithat(giohientai+":"+phuthientai+" "+tt);


                                String s1 = b.getGiochoithat()+"";
                                String strArr1[]=s1.split(":");
                                int gio1=0;
                                String sau1=(strArr1[1]);
                                String ss1[]= sau1.split(" ");
                                int phut1 = Integer.parseInt(ss1[0]);
                                String ttd0 = ss1[1];
                                if(ttd0.equals("PM")==true){
                                    gio1 = Integer.parseInt(strArr1[0]) + 12;
                                }
                                else gio1 = Integer.parseInt(strArr1[0]);
                                int thoigiandat1 = gio1 * 60 + phut1;

                                int thoigian = Math.abs(thoigiandat1 - thoigiandat);

                                double thanhtien = ((double)((double)thoigian/60) * (double)booking.getGiaban())+booking.getTienthanhtoan();
                                Log.e("ThGG","t/g dat"+gio+":"+phut+" "+thoigiandat);
                                Log.e("ThGG","t/g ket thuc"+gio1+":"+phut1+" "+thoigiandat1);
                                Log.e("ThGG",""+booking.getGiaban());
                                Log.e("Thời gian chia",""+(double)((double)thoigian/60));
                                Log.e("tg chưa cộng",""+((double)((double)thoigian/60) * (double)booking.getGiaban()));
                                Log.e("Thành tiền",""+thanhtien);
                                double vnd = Math.round(thanhtien * 10) / 10 ;

                                // tạo 1 NumberFormat để định dạng tiền tệ theo tiêu chuẩn của Việt Nam
                                // đơn vị tiền tệ của Việt Nam là đồng
                                Locale localeVN = new Locale("vi", "VN");
                                NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                                String str1 = currencyVN.format(vnd);
                                tv_tienthuc.setText("Số tiền cần phải thanh toán là: "+str1);
                                b.setTienthanhtoan(thanhtien);
                                ApiService.apiService.lay1BanBidatheoID(b.getIdban())
                                        .enqueue(new Callback<BanBida>() {
                                            @Override
                                            public void onResponse(Call<BanBida> call, Response<BanBida> response) {
                                                BanBida ban = response.body();
                                                ban.setSoluong(ban.getSoluong()+1);
                                                ApiService.apiService.capnhatBantheoID(ban.getId(),ban)
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
                                            public void onFailure(Call<BanBida> call, Throwable t) {

                                            }
                                        });
                                //đưa lên giao diện

                                ApiService.apiService.suaBooking(QuanLyDSBooking.madiadiem,booking.getId(),b)
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
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
                intent.putExtra("ma", id);
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
