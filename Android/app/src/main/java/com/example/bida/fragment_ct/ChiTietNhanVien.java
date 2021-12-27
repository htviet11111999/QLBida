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
import com.example.bida.Model.NhanVien;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.example.bida.fragment_qtv.ChiTietQuanLyDiaDiem;
import com.example.bida.fragment_qtv.ChiTietQuanLyTaiKhoan;
import com.example.bida.fragment_qtv.SuaDiaDiem;
import com.google.android.gms.common.api.Api;
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

public class ChiTietNhanVien extends AppCompatActivity {
    public  static  int id;
    ArrayList<TaiKhoan> data = new ArrayList<>();
    ArrayList<Booking> da = new ArrayList<>();
    public static NhanVien nv = new NhanVien();
    ImageView img_avatar;
    TextView tv_hotennv, tv_sdt, tv_diachi, tv_gioitinh;
    Button btn_taotk, btn_sua, btn_xoa, btn_thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_quanlynhanvien_ct);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        img_avatar = (ImageView) findViewById(R.id.avatar_qlnhanvien);
        tv_hotennv = (TextView) findViewById(R.id.hoten_chitietQlnv);
        tv_sdt = (TextView) findViewById(R.id.sdt_chitietQlnv);
        tv_diachi = (TextView) findViewById(R.id.diachi_chitietQlnv);
        tv_gioitinh = (TextView) findViewById(R.id.gioitinh_chitietQlnv);
        btn_taotk = (Button) findViewById(R.id.taotk_qlnv);
        btn_sua = (Button) findViewById(R.id.sua_qlnv);
        btn_xoa = (Button) findViewById(R.id.xoa_qlnv);
        btn_thoat = (Button) findViewById(R.id.thoat_qlnv);
        ApiService.apiService.layDSBookingToan()
                .enqueue(new Callback<ArrayList<Booking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                        da = response.body();
                        for (int i = 0; i< da.size();i++){
                            if(da.get(i).getIdnhanvien()==id){
                                btn_xoa.setVisibility(View.GONE);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                    }
                });
        ApiService.apiService.layDSTaiKhoan()
                .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                        data = response.body();
                        for (int i =0; i< data.size(); i++){
                            String sd = data.get(i).getSdt();
                            if (sd != null) {
                                if (nv.getSdt().equals(sd) == true) {
                                    btn_taotk.setVisibility(View.GONE);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                        Toast.makeText(ChiTietNhanVien.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        ApiService.apiService.lay1NhanVien(Home_CT.d.getId(),id)
                .enqueue(new Callback<NhanVien>() {
                    @Override
                    public void onResponse(Call<NhanVien> call, Response<NhanVien> response) {
                        nv = response.body();
                        String photoImage = nv.getHinhanh();
                        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        img_avatar.setImageBitmap(theImage);
                        tv_hotennv.setText(String.format("Họ tên : %s", nv.getHoten()));
                        tv_sdt.setText(String.format("Số điện thoại : %s", nv.getSdt()));
                        tv_diachi.setText(String.format("Địa chỉ : %s", nv.getDiachi()));
                        String gt;
                        if (nv.getGioitinh()==1) gt ="Nam";
                        else gt ="Nữ";
                        tv_gioitinh.setText(String.format("Giới tính : %s", gt));
                    }

                    @Override
                    public void onFailure(Call<NhanVien> call, Throwable t) {
                        Toast.makeText(ChiTietNhanVien.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietNhanVien.this, SuaNhanVien.class);
                startActivity(intent);
            }
        });

        btn_taotk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietNhanVien.this, TaoTaiKhoanNV.class);
                startActivity(intent);
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ChiTietNhanVien.this);
                builder1.setMessage("Bạn chắc chắn xóa nhân viên này chứ ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {

                                ApiService.apiService.xoaNhanVien(Home_CT.d.getId(),id)
                                        .enqueue(new Callback<JsonObject>() {
                                            @RequiresApi(api = Build.VERSION_CODES.O)
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                Toast.makeText(ChiTietNhanVien.this, "Xóa nhân viên thành công !", Toast.LENGTH_SHORT).show();
                                                dialog.cancel();
                                                String noidung = "Bạn đã xóa nhân viên "+nv.getHoten();
                                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                String thoigian = df.format(Calendar.getInstance().getTime());
                                                LichSuCT ls = new LichSuCT();
                                                ls.setIdchutiem(Menu_CT.machutiem);
                                                ls.setNoidung(noidung);
                                                ls.setThoigian(thoigian);
                                                LichSuCT.ThemLichSu(Menu_CT.machutiem,ls);
                                                Intent intent = new Intent(ChiTietNhanVien.this, Menu_CT.class);
                                                intent.putExtra("number",Menu_CT.sdt);
                                                startActivity(intent);
                                            }
                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                Toast.makeText(ChiTietNhanVien.this, "Xóa nhân viên thất bại !", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(ChiTietNhanVien.this, Menu_CT.class);
                intent.putExtra("number", Menu_CT.sdt);
                startActivity(intent);
            }
        });

    }
}
