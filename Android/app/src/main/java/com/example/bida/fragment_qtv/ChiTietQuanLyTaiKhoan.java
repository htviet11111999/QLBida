package com.example.bida.fragment_qtv;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bida.Api.ApiService;
import com.example.bida.MainActivity;
import com.example.bida.Menu_KH;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.LichSuQTV;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.google.android.gms.common.api.Api;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietQuanLyTaiKhoan extends AppCompatActivity {
    ArrayList <TaiKhoan> data = new ArrayList<>();
    int id;
    ImageView img_avatar;
    TextView tv_hoten, tv_sdt, tv_diachi, tv_matkhau, tv_quyen;
    Button btn_sua, btn_xoa, btn_thoat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_quanlytaikhoan_qtv);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        img_avatar = (ImageView) findViewById(R.id.avatar_qltk);
        tv_hoten = (TextView) findViewById(R.id.hoten_chitietQltk);
        tv_sdt = (TextView) findViewById(R.id.sdt_chitietQltk);
        tv_diachi = (TextView) findViewById(R.id.diachi_chitietQltk);
        tv_matkhau = (TextView) findViewById(R.id.matkhau_chitietQltk);
        tv_quyen = (TextView) findViewById(R.id.quyen_chitietQltk);
        btn_sua = (Button) findViewById(R.id.sua_qltk);
        btn_xoa = (Button) findViewById(R.id.xoa_qltk);
        btn_thoat = (Button) findViewById(R.id.thoat_qltk);

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietQuanLyTaiKhoan.this, Menu_QTV.class);
                intent.putExtra("number",Menu_QTV.sdt);
                startActivity(intent);
            }
        });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietQuanLyTaiKhoan.this, SuaTaiKhoan.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });

        ApiService.apiService.layDSTaiKhoan()
                .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                        data = response.body();
                        for(int i =0 ;i< data.size();i++) {
                            if (data.get(i).getId() == id && data.get(i).getSohuutiem() == 1 ){
                                btn_xoa.setVisibility(View.GONE);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                        Toast.makeText(ChiTietQuanLyTaiKhoan.this, "X??a t??i kho???n th???t b???i, v?? ch??? ti???m ??ang s??? h???u ti???m Bida !", Toast.LENGTH_SHORT).show();
                    }
                });

        btn_xoa.setOnClickListener(new View.OnClickListener() {
            //C?? ??i???u ki???n ????? x??a
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(ChiTietQuanLyTaiKhoan.this);
                builder1.setMessage("B???n ch???c ch???n x??a t??i kho???n n??y ch??? ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "?????ng ??",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                ApiService.apiService.xoaLichSu_KH(id)
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                ApiService.apiService.xoaTaiKhoan(id)
                                                        .enqueue(new Callback<JsonObject>() {
                                                            @Override
                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                Log.e("KQ",""+response.body());
                                                                Toast.makeText(ChiTietQuanLyTaiKhoan.this, "X??a t??i kho???n th??nh c??ng !", Toast.LENGTH_SHORT).show();
                                                                String noidung = "B???n ???? x??a t??i kho???n "+ tv_hoten.getText().toString();
                                                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                                String thoigian = df.format(Calendar.getInstance().getTime());
                                                                LichSuQTV ls = new LichSuQTV();
                                                                ls.setNoidung(noidung);
                                                                ls.setThoigian(thoigian);
                                                                LichSuQTV.ThemLichSu(ls);
                                                                dialog.cancel();
                                                                Intent intent = new Intent(ChiTietQuanLyTaiKhoan.this, Menu_QTV.class);
                                                                intent.putExtra("number",Menu_QTV.sdt);
                                                                startActivity(intent);

                                                            }

                                                            @Override
                                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                                Toast.makeText(ChiTietQuanLyTaiKhoan.this, "X??a t??i kho???n th???t b???i !", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                            }
                                        });




                            }
                        });

                builder1.setNegativeButton(
                        "Tho??t",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });


        ApiService.apiService.lay1TaiKhoan(id)
                .enqueue(new Callback<TaiKhoan>() {
                    @Override
                    public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                        TaiKhoan tk = response.body();
                        String photoImage = tk.getHinhanh();
                        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        img_avatar.setImageBitmap(theImage);
                        tv_hoten.setText(String.format("H??? v?? t??n : %s", tk.getHoten()));
                        tv_sdt.setText(String.format("S??? ??i???n tho???i : %s", tk.getSdt()));
                        tv_diachi.setText(String.format("?????a ch??? : %s", tk.getDiachi()));
                        tv_matkhau.setText(String.format("M???t kh???u : %s", tk.getMatkhau()));
                        String quyen = "";
                        if (tk.getQuyen().equals("QTV")) {
                            quyen = "Qu???n tr??? vi??n";
                        } else if (tk.getQuyen().equals("KH")) {
                            quyen = "Kh??ch h??ng";
                        } else if (tk.getQuyen().equals("CT")) {
                            quyen = "Ch??? ti???m";
                        } else if (tk.getQuyen().equals("NV")) {
                            quyen = "Nh??n vi??n";
                        }
                        tv_quyen.setText(String.format("Quy???n : %s", quyen));
                    }

                    @Override
                    public void onFailure(Call<TaiKhoan> call, Throwable t) {
                        Toast.makeText(ChiTietQuanLyTaiKhoan.this, "G???i API th???t b???i !", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

