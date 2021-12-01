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
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.Kho;
import com.example.bida.Model.LichSuNV;
import com.example.bida.R;
import com.example.bida.fragment_qtv.ChiTietQuanLyDiaDiem;
import com.example.bida.fragment_qtv.ChiTietQuanLyTaiKhoan;
import com.example.bida.fragment_qtv.SuaDiaDiem;
import com.google.android.gms.common.api.Api;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietVatPham extends AppCompatActivity {
    public  static  int id;
    ArrayList<CTBooking> data = new ArrayList<>();
    ImageView img_avatar;
    TextView tv_tenvatpham, tv_dongia, tv_soluong;
    Button btn_sua, btn_xoa, btn_thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_quanlykho_nv);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        ApiService.apiService.layDSCTBookingToan()
                .enqueue(new Callback<ArrayList<CTBooking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CTBooking>> call, Response<ArrayList<CTBooking>> response) {
                        data= response.body();
                        for(int i = 0 ;i<data.size();i++){
                            if(data.get(i).getIdvatpham() == id){
                                btn_xoa.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<CTBooking>> call, Throwable t) {

                    }
                });
        img_avatar = (ImageView) findViewById(R.id.avatar_qlk);
        tv_tenvatpham = (TextView) findViewById(R.id.tenvatpham_chitietk);
        tv_dongia = (TextView) findViewById(R.id.dongia_chitietk);
        tv_soluong = (TextView) findViewById(R.id.soluong_chitietk);
        btn_sua = (Button) findViewById(R.id.sua_qlk);
        btn_xoa = (Button) findViewById(R.id.xoa_qlk);
        btn_thoat = (Button) findViewById(R.id.thoat_qlk);

        ApiService.apiService.lay1VatPham(QuanLyKho.madiadiem,id)
                .enqueue(new Callback<Kho>() {
                    @Override
                    public void onResponse(Call<Kho> call, Response<Kho> response) {
                        Kho k = response.body();
                        String photoImage = k.getHinhanh();
                        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        img_avatar.setImageBitmap(theImage);
                        tv_tenvatpham.setText(String.format("Tên vật phẩm : %s", k.getTenvatpham()));
                        tv_dongia.setText(String.format("Đơn giá : %s", k.getDongia()));
                        tv_soluong.setText(String.format("Số lượng : %s", k.getSoluong()));

                    }

                    @Override
                    public void onFailure(Call<Kho> call, Throwable t) {
                        Toast.makeText(ChiTietVatPham.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietVatPham.this, SuaVatPham.class);
                startActivity(intent);
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ChiTietVatPham.this);
                builder1.setMessage("Bạn chắc chắn xóa vật phẩm này chứ ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {

                                ApiService.apiService.xoaVatPham(QuanLyKho.madiadiem,id)
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                Toast.makeText(ChiTietVatPham.this, "Xóa vật phẩm thành công !", Toast.LENGTH_SHORT).show();
                                                String noidung = "Bạn đã xóa vật phẩm : "+tv_tenvatpham.getText().toString();
                                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                String thoigian = df.format(Calendar.getInstance().getTime());
                                                LichSuNV ls = new LichSuNV();
                                                ls.setIdnhanvien(Menu_NV.manhanvien);
                                                ls.setNoidung(noidung);
                                                ls.setThoigian(thoigian);
                                                LichSuNV.ThemLichSu(Menu_NV.manhanvien,ls);
                                                dialog.cancel();
                                                Intent intent = new Intent(ChiTietVatPham.this, Menu_NV.class);
                                                intent.putExtra("number",Menu_NV.sdt);
                                                startActivity(intent);
                                            }
                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                Toast.makeText(ChiTietVatPham.this, "Xóa vật phẩm thất bại !", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(ChiTietVatPham.this, Menu_NV.class);
                intent.putExtra("number", Menu_NV.sdt);
                startActivity(intent);
            }
        });

    }
}
