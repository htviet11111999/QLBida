package com.example.bida.fragment_nv;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_NV;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Kho;
import com.example.bida.Model.LichSuNV;
import com.example.bida.Model.PhieuNhap;
import com.example.bida.R;
import com.example.bida.fragment_qtv.ChiTietQuanLyDiaDiem;
import com.example.bida.fragment_qtv.ChiTietQuanLyTaiKhoan;
import com.example.bida.fragment_qtv.SuaDiaDiem;
import com.google.android.gms.common.api.Api;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTin1PhieuNhap extends AppCompatActivity {
    public  static  int id;
    public  static  PhieuNhap phieuNhap;
    TextView tv_ngaylap, tv_hotennv, tv_tienthanhtoan, tv_trangthai;
    Button btn_thanhtoan, btn_chitiet, btn_xoa, btn_thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtin_phieunhap_nv);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        tv_ngaylap = (TextView) findViewById(R.id.ngaylap_phieunhap);
        tv_hotennv = (TextView) findViewById(R.id.hotennhanvien_phieunhap);
        tv_tienthanhtoan = (TextView) findViewById(R.id.tienthanhtoan_phieunhap);
        tv_trangthai = (TextView) findViewById(R.id.trangthai_phieunhap);
        btn_xoa = (Button) findViewById(R.id.xoa_phieunhap);
        btn_thoat = (Button) findViewById(R.id.thoat_phieunhap);
        btn_thanhtoan = (Button) findViewById(R.id.thanhtoan_phieunhap);
        btn_chitiet = (Button) findViewById(R.id.chitiet_phieunhap);
        ApiService.apiService.lay1PhieuNhap(QuanLyDSPhieuNhap.madiadiem,id)
                .enqueue(new Callback<PhieuNhap>() {
                    @Override
                    public void onResponse(Call<PhieuNhap> call, Response<PhieuNhap> response) {
                        phieuNhap = response.body();
                        tv_ngaylap.setText(String.format("Ngày lập : %s", phieuNhap.getNgay()));
                        tv_hotennv.setText(String.format("Họ tên nhân viên lập : %s", phieuNhap.getTennhanvien()));
                        tv_tienthanhtoan.setText(String.format("Tiền thanh toán : %s", phieuNhap.getTienthanhtoan()));
                        if(phieuNhap.getTienthanhtoan() != 0 && phieuNhap.getTrangthai() == 0 || phieuNhap.getTrangthai() == 1) btn_xoa.setVisibility(View.GONE);
                        String tt ;
                        if (phieuNhap.getTrangthai() == 0 ) tt ="Chưa thanh toán";
                        else{
                            tt = "Đã thanh toán";
                            btn_thanhtoan.setVisibility(View.GONE);
                        }
                        tv_trangthai.setText(String.format("Trạng thái : %s", tt));
                    }
                    @Override
                    public void onFailure(Call<PhieuNhap> call, Throwable t) {
                        Toast.makeText(ThongTin1PhieuNhap.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ThongTin1PhieuNhap.this);
                builder1.setMessage("Bạn chắc chắn muốn thanh toán phiếu này chứ ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                phieuNhap.setTrangthai(1);
                                ApiService.apiService.suaPhieuNhap(QuanLyDSPhieuNhap.madiadiem,phieuNhap.getId(),phieuNhap)
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                Toast.makeText(ThongTin1PhieuNhap.this, "Thanh toán thành công !", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                                Intent intent = new Intent(ThongTin1PhieuNhap.this, Menu_NV.class);
                                                intent.putExtra("number",Menu_NV.sdt);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                Toast.makeText(ThongTin1PhieuNhap.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
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
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ThongTin1PhieuNhap.this);
                builder1.setMessage("Bạn chắc chắn xóa phiếu nhập này chứ ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {

                                ApiService.apiService.xoaPhieuNhap(QuanLyDSPhieuNhap.madiadiem,phieuNhap.getId())
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                Toast.makeText(ThongTin1PhieuNhap.this, "Xóa phiếu nhập thành công !", Toast.LENGTH_SHORT).show();
                                                String noidung = "Bạn đã xóa 1 phiếu nhập";
                                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                String thoigian = df.format(Calendar.getInstance().getTime());
                                                LichSuNV ls = new LichSuNV();
                                                ls.setIdnhanvien(Menu_NV.manhanvien);
                                                ls.setNoidung(noidung);
                                                ls.setThoigian(thoigian);
                                                LichSuNV.ThemLichSu(Menu_NV.manhanvien,ls);
                                                dialog.cancel();
                                                Intent intent = new Intent(ThongTin1PhieuNhap.this, Menu_NV.class);
                                                intent.putExtra("number",Menu_NV.sdt);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                Toast.makeText(ThongTin1PhieuNhap.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
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

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTin1PhieuNhap.this, Menu_NV.class);
                intent.putExtra("number", Menu_NV.sdt);
                startActivity(intent);
            }
        });
        btn_chitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThongTin1PhieuNhap.this, ChiTietPhieuNhap.class);
                startActivity(intent);
            }
        });

    }
}
