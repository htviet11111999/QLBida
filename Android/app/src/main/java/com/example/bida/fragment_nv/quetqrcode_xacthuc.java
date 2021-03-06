package com.example.bida.fragment_nv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Adapter.QuanLyDSPhieuNhapAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_NV;
import com.example.bida.Model.Booking;
import com.example.bida.Model.NhanVien;
import com.example.bida.Model.PhieuNhap;
import com.example.bida.R;
import com.google.gson.JsonObject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class quetqrcode_xacthuc extends AppCompatActivity {
    public  static  int id;
    ArrayList<NhanVien> nv = new ArrayList<>();
    public static int madiadiem;
    public static int idnhanvientiem;
    public static String hotennhanvien;
    ArrayList<Booking> b = new ArrayList<>();
    public  static Booking booking;
    TextView tv_kh, tv_ngaylap,  tv_tienthanhtoan,tv_ngaychoi, tv_giochoi, tv_trangthai, tv_stt;
    Button btn_quetqr, btn_xacthuc, btn_thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quetqrcode_nv);

        tv_kh = (TextView) findViewById(R.id.tenkh_bookingxt);
        tv_ngaylap = (TextView) findViewById(R.id.ngaylap_bookingxt);
        tv_tienthanhtoan = (TextView) findViewById(R.id.tienthanhtoan_bookingxt);
        tv_ngaychoi = (TextView) findViewById(R.id.ngaychoi_bookingxt);
        tv_giochoi = (TextView) findViewById(R.id.giochoi_bookingxt);
        tv_trangthai = (TextView) findViewById(R.id.trangthai_bookingxt);
        tv_stt = (TextView) findViewById(R.id.stt);
        btn_quetqr = (Button) findViewById(R.id.quetqr);
        btn_xacthuc = (Button) findViewById(R.id.xacthuc);
        btn_thoat = (Button) findViewById(R.id.thoat);

        ApiService.apiService.layDSNVToan()
                .enqueue(new Callback<ArrayList<NhanVien>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NhanVien>> call, Response<ArrayList<NhanVien>> response) {
                        nv = response.body();
                        for (int i =0; i< nv.size(); i++){
                            if (nv.get(i).getSdt().equals(Menu_NV.sdt)==true){
                                madiadiem = nv.get(i).getIddiadiem();
                                idnhanvientiem = nv.get(i).getId();
                                hotennhanvien = nv.get(i).getHoten();
                                break;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<NhanVien>> call, Throwable t) {
                        Toast.makeText(quetqrcode_xacthuc.this,"G???i API th???t b???i !",Toast.LENGTH_SHORT).show();
                    }
                });

        ApiService.apiService.layDSBookingToan()
                .enqueue(new Callback<ArrayList<Booking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                        b = response.body();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                    }
                });
        final IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        btn_quetqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentIntegrator.initiateScan();
            }
        });
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(quetqrcode_xacthuc.this, Menu_NV.class);
                intent.putExtra("number",Menu_NV.sdt);
                startActivity(intent);
            }
        });

        btn_xacthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Booking boo = booking;
                boo.setIdnhanvien(idnhanvientiem);
                boo.setTennhanvien(hotennhanvien);
                boo.setGiochoithat("");
                boo.setTrangthai(0);
                ApiService.apiService.suaBookingXacthuc(boo.getId(), boo)
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                Toast.makeText(quetqrcode_xacthuc.this, "X??c th???c booking th??nh c??ng !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(quetqrcode_xacthuc.this, Menu_NV.class);
                                intent.putExtra("number", Menu_NV.sdt);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                            }
                        });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null) {
            if (result.getContents() == null){
                Toast.makeText(this,"Cancelled", Toast.LENGTH_SHORT).show();
            }else {
                Log.e("SCAN",""+result.getContents());
                id = Integer.parseInt(result.getContents());
                int mabooking = Integer.parseInt(result.getContents());
                for (int i = 0 ; i < b.size(); i++){
                    if (b.get(i).getId() == mabooking && b.get(i).getIddiadiem() == Menu_NV.idd){
                        booking = b.get(i);
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
                            tv_stt.setText("???? h???t th???i gian ch??i !");
                            btn_xacthuc.setVisibility(View.GONE);
                            btn_quetqr.setVisibility(View.GONE);
                        }
                        else if (ngay < ngayhientai && thang == thanghientai && nam == namhientai){
                            tv_stt.setText("???? h???t th???i gian ch??i !");
                            btn_xacthuc.setVisibility(View.GONE);
                            btn_quetqr.setVisibility(View.GONE);
                        }
                        else if (ngay > ngayhientai && thang == thanghientai && nam == namhientai){
                            tv_stt.setText("Ch??a ?????n th???i gian ch??i !");
                            btn_xacthuc.setVisibility(View.GONE);
                            btn_quetqr.setVisibility(View.GONE);
                        }
                        tv_kh.setText(String.format("Kh??ch h??ng : %s", booking.getTenkhachhang()));
                        tv_ngaylap.setText(String.format("Ng??y l???p : %s", booking.getNgay()));
                        tv_tienthanhtoan.setText(String.format("Ti???n thanh to??n : %s", booking.getTienthanhtoan()));
                        tv_ngaychoi.setText(String.format("Ng??y ch??i : %s", booking.getNgaychoi()));
                        tv_giochoi.setText(String.format("Gi??? b???t ?????u ch??i : %s", booking.getGiochoi()));
                        String tt ;
                        if (booking.getTrangthai() == 0 ) {
                            tt = "Ch??a thanh to??n";
                            tv_trangthai.setTextColor(Color.parseColor("#D80C0C"));
                        }
                        else if (booking.getTrangthai() == 2 ) {
                            tt = "Ch??a x??c nh???n";
                            tv_trangthai.setTextColor(Color.parseColor("#F3DB0B"));
                        }
                        else if (booking.getTrangthai() == 3) {
                            tt = "??ang ch??i...";
                            tv_trangthai.setTextColor(Color.parseColor("#0FC6DF"));
                        }
                        else {
                            tt = "???? thanh to??n";
                            tv_trangthai.setTextColor(Color.parseColor("#04FF00"));
                        }
                        tv_trangthai.setText(String.format("Tr???ng th??i : %s", tt));
                        break;
                    }
                    else  tv_trangthai.setText("Kh??ch h??ng hi???n t???i kh??ng ????ng k?? ch??i ??? ti???m n??y !");
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
