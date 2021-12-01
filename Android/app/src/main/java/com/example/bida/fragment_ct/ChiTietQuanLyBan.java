package com.example.bida.fragment_ct;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.Model.LichSuCT;
import com.example.bida.R;
import com.example.bida.fragment_qtv.ChiTietQuanLyDiaDiem;
import com.example.bida.fragment_qtv.ChiTietQuanLyTaiKhoan;
import com.example.bida.fragment_qtv.SuaDiaDiem;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietQuanLyBan extends AppCompatActivity {
    public  static  int id;
    ArrayList<Booking> da = new ArrayList<>();
    BanBida ban = new BanBida();
    ImageView img_avatar;
    TextView tv_tenban, tv_gia, tv_soluong;
    Button btn_sua, btn_xoa, btn_thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_quanlyban_ct);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        img_avatar = (ImageView) findViewById(R.id.avatar_qlban);
        tv_tenban = (TextView) findViewById(R.id.tenban_chitietban);
        tv_gia = (TextView) findViewById(R.id.gia_chitietban);
        tv_soluong = (TextView) findViewById(R.id.soluong_chitietban);
        btn_sua = (Button) findViewById(R.id.sua_qlban);
        btn_xoa = (Button) findViewById(R.id.xoa_qlban);
        btn_thoat = (Button) findViewById(R.id.thoat_qlban);
        ApiService.apiService.layDSBookingToan()
                .enqueue(new Callback<ArrayList<Booking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                        da = response.body();
                        for (int i = 0; i< da.size();i++){
                            if(da.get(i).getIdban()==id){
                                btn_xoa.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                    }
                });
        ApiService.apiService.lay1BanBida(Home_CT.d.getId(),id)
                .enqueue(new Callback<BanBida>() {
                    @Override
                    public void onResponse(Call<BanBida> call, Response<BanBida> response) {
                        BanBida b = response.body();
                        ban = b;
                        String photoImage = b.getHinhanh();
                        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        img_avatar.setImageBitmap(theImage);
                        tv_tenban.setText(String.format("Tên bàn : %s", b.getTenban()));
                        tv_gia.setText(String.format("Giá : %s", b.getGia()));
                        tv_soluong.setText(String.format("Số lượng : %s", b.getSoluong()));

                    }

                    @Override
                    public void onFailure(Call<BanBida> call, Throwable t) {
                        Toast.makeText(ChiTietQuanLyBan.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietQuanLyBan.this, SuaBan.class);
                startActivity(intent);
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ChiTietQuanLyBan.this);
                builder1.setMessage("Bạn chắc chắn xóa bàn này chứ ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {

                                ApiService.apiService.xoaBan(Home_CT.d.getId(), id)
                                        .enqueue(new Callback<JsonObject>() {
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                Toast.makeText(ChiTietQuanLyBan.this, "Xóa bàn thành công !", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                                String noidung = "Bạn đã xóa bàn "+ban.getTenban();
                                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                String thoigian = df.format(Calendar.getInstance().getTime());
                                                LichSuCT ls = new LichSuCT();
                                                ls.setIdchutiem(Menu_CT.machutiem);
                                                ls.setNoidung(noidung);
                                                ls.setThoigian(thoigian);
                                                LichSuCT.ThemLichSu(Menu_CT.machutiem,ls);
                                                Intent intent = new Intent(ChiTietQuanLyBan.this, Menu_CT.class);
                                                intent.putExtra("number",Menu_CT.sdt);
                                                startActivity(intent);
                                            }
                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                Toast.makeText(ChiTietQuanLyBan.this, "Xóa tài khoản thất bại !", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(ChiTietQuanLyBan.this, Menu_CT.class);
                intent.putExtra("number", Menu_CT.sdt);
                startActivity(intent);
            }
        });

    }
}
