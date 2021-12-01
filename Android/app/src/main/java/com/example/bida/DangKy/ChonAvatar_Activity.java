package com.example.bida.DangKy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.MainActivity;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChonAvatar_Activity extends AppCompatActivity {
    ArrayList <TaiKhoan> data = new ArrayList<>();
    TaiKhoan tk = new TaiKhoan();
    int ma;
    String sdt;
    String matkhau;
    ImageView avatar;
    Button btn_ChonHinh, btn_BoQua, btn_Xong;

    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_avatar_dangky);

        Intent intent = getIntent();
        sdt = intent.getStringExtra("number");
        matkhau = intent.getStringExtra("matkhau");

        avatar =(ImageView)findViewById(R.id.avatar);
        btn_ChonHinh = (Button) findViewById(R.id.btnTaiHinh);
        btn_BoQua = (Button) findViewById(R.id.btnBoQua);
        btn_Xong = (Button) findViewById(R.id.btnXong);

        btn_ChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });
        btn_Xong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaiKhoan taik = gettaikhoan();
                ApiService.apiService.capnhatTaiKhoan(ma,taik)
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                Toast.makeText(ChonAvatar_Activity.this, "Chọn avatar thành công !", Toast.LENGTH_SHORT).show();
                                Toast.makeText(ChonAvatar_Activity.this, "Hoàn tất đăng ký !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChonAvatar_Activity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Toast.makeText(ChonAvatar_Activity.this, "Chọn avatar thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        btn_BoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaiKhoan t = gettaikhoan_chuachonhinh();
                ApiService.apiService.capnhatTaiKhoan(ma,t)
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                Toast.makeText(ChonAvatar_Activity.this, "Hoàn tất đăng ký !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChonAvatar_Activity.this, MainActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Toast.makeText(ChonAvatar_Activity.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        });
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
                avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private TaiKhoan gettaikhoan() {
        ApiService.apiService.layDSTaiKhoan()
                .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                        data = response.body();
                        for(int i =0; i< data.size();i++)
                        {
                            if(data.get(i).getSdt().equals(sdt) == true){
                                ma = data.get(i).getId();
                                TaiKhoan taikhoan = new TaiKhoan();
                                //String s = edt_nhapSDT.getText().toString().trim();
                                //String s1 = s.substring(1);
                                // String sdt = "+84"+s1;
                                taikhoan.setSdt(data.get(i).getSdt());
                                taikhoan.setHoten(data.get(i).getHoten());
                                taikhoan.setDiachi(data.get(i).getDiachi());
                                taikhoan.setQuyen("KH");
                                taikhoan.setMatkhau(matkhau);
                                Bitmap bitmap = ((BitmapDrawable)avatar.getDrawable()).getBitmap();
                                ByteArrayOutputStream bas = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG,100,bas);
                                byte[] imBytes=bas.toByteArray();
                                String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
                                String result = "data:image/png;base64,"+encodedImage;
                                taikhoan.setHinhanh(result);
                                tk =taikhoan;
                                break;
                            }
                            else continue;
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {

                    }
                });
        return tk;
    }

    private TaiKhoan gettaikhoan_chuachonhinh() {
        ApiService.apiService.layDSTaiKhoan()
                .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                        data = response.body();
                        for(int i =0; i< data.size();i++)
                        {
                            if(data.get(i).getSdt().equals(sdt) == true){
                                ma = data.get(i).getId();
                                TaiKhoan taikhoan = new TaiKhoan();
                                //String s = edt_nhapSDT.getText().toString().trim();
                                //String s1 = s.substring(1);
                                // String sdt = "+84"+s1;
                                taikhoan.setSdt(data.get(i).getSdt());
                                taikhoan.setHoten(data.get(i).getHoten());
                                taikhoan.setDiachi(data.get(i).getDiachi());
                                taikhoan.setQuyen("KH");
                                taikhoan.setMatkhau(matkhau);
                                taikhoan.setHinhanh("");
                                tk =taikhoan;
                                break;
                            }
                            else continue;
                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {

                    }
                });
        return tk;
    }

}
