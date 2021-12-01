package com.example.bida.fragment_qtv;

import com.example.bida.Api.ApiService;
import com.example.bida.DangKy.GuiMaOTP_DangKyActivity;
import com.example.bida.DangKy.NhapThongTinDK_Activity;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.LichSuQTV;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.google.gson.JsonObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemTaiKhoan extends AppCompatActivity {
    ArrayList<TaiKhoan> data = new ArrayList<>();
    Boolean kt = false;
    ImageView img_avatar , img_taihinh;
    EditText edt_hoten, edt_sdt, edt_diachi, edt_matkhau;
    Button btn_Hoanthanh, btn_Thoat;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themtaikhoan_qltk);

        img_avatar = (ImageView) findViewById(R.id.addavatar_qltk);
        img_taihinh = (ImageView) findViewById(R.id.add_avatar_qltk);
        edt_hoten = (EditText) findViewById(R.id.add_hoten);
        edt_sdt = (EditText) findViewById(R.id.add_sdt);
        edt_diachi = (EditText) findViewById(R.id.add_diachi);
        edt_matkhau = (EditText) findViewById(R.id.add_matkhau);
        btn_Hoanthanh = (Button) findViewById(R.id.addHoanthanh_qltk);
        btn_Thoat = (Button) findViewById(R.id.addThoat_qltk);

        img_taihinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemTaiKhoan.this, Menu_QTV.class);
                intent.putExtra("number",Menu_QTV.sdt);
                startActivity(intent);
            }
        });

        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edt_hoten.getText().toString().trim();
                String sdt = edt_sdt.getText().toString().trim();
                String diachi = edt_diachi.getText().toString();
                String matkhau = edt_matkhau.getText().toString().trim();

                if(hoten.length()==0)
                {
                    edt_hoten.requestFocus();
                    edt_hoten.setError("Không được để trống !");

                }else if(sdt.length()==0)
                {
                    edt_sdt.requestFocus();
                    edt_sdt.setError("Không được để trống !");
                }else if(diachi.length()==0)
                {
                    edt_diachi.requestFocus();
                    edt_diachi.setError("Không được để trống !");
                }else if(matkhau.length()==0)
                {
                    edt_matkhau.requestFocus();
                    edt_matkhau.setError("Không được để trống !");
                }
                else{

                    ApiService.apiService.layDSTaiKhoan()
                            .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                                @Override
                                public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                                    data = response.body();

                                    for (int i =0;i < data.size(); i++){
//                                        String s = edt_nhapSDT.getText().toString().trim();
//                                        String s1 = s.substring(1);
//                                        String sdt = "+84"+s1;
                                        if (data.get(i).getSdt().equals(sdt) == true){
                                            edt_sdt.requestFocus();
                                            edt_sdt.setError("Số điện thoại đã đăng ký !");
                                            kt = true;
                                            break;
                                        }
                                    }
                                    if (kt == false){
                                        TaiKhoan taiKhoan = getTaiKhoan();
                                        ApiService.apiService.taoTaiKhoan(taiKhoan)
                                                .enqueue(new Callback<JsonObject>() {
                                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                                    @Override
                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                        Toast.makeText(ThemTaiKhoan.this, "Thêm tài khoản thành công !", Toast.LENGTH_SHORT).show();
                                                        String noidung = "Bạn đã thêm tài khoản mới "+taiKhoan.getHoten();
                                                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                        String thoigian = df.format(Calendar.getInstance().getTime());
                                                        LichSuQTV ls = new LichSuQTV();
                                                        ls.setNoidung(noidung);
                                                        ls.setThoigian(thoigian);
                                                        LichSuQTV.ThemLichSu(ls);
                                                        Intent intent = new Intent(ThemTaiKhoan.this, Menu_QTV.class);
                                                        intent.putExtra("number",Menu_QTV.sdt);
                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                                        Toast.makeText(ThemTaiKhoan.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                                    Toast.makeText(ThemTaiKhoan.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private TaiKhoan getTaiKhoan() {
        TaiKhoan tk = new TaiKhoan();
        tk.setHoten(edt_hoten.getText().toString().trim());
        tk.setSdt(edt_sdt.getText().toString().trim());
        tk.setDiachi(edt_diachi.getText().toString());
        tk.setMatkhau(edt_matkhau.getText().toString().trim());
        tk.setQuyen("CT");
        Bitmap bitmap = ((BitmapDrawable)img_avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bas);
        byte[] imBytes=bas.toByteArray();
        String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
        String result = "data:image/png;base64,"+encodedImage;
        tk.setHinhanh(result);
        return tk;
    }
}
