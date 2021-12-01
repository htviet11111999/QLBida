package com.example.bida.fragment_nv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_NV;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Kho;
import com.example.bida.Model.LichSuNV;
import com.example.bida.R;
import com.example.bida.fragment_qtv.SuaTaiKhoan;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaVatPham extends AppCompatActivity {
    ImageView img_avatar , img_taihinh;
    EditText edt_tenvatpham, edt_dongia, edt_soluong;
    Button btn_Hoanthanh, btn_Thoat;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capnhatthongtin_qlk_nv);

        img_avatar = (ImageView) findViewById(R.id.updatehinh_qlk);
        img_taihinh = (ImageView) findViewById(R.id.update_hinh_qlk);
        edt_tenvatpham = (EditText) findViewById(R.id.update_tenvatpham);
        edt_dongia = (EditText) findViewById(R.id.update_dongia);
        edt_soluong = (EditText) findViewById(R.id.update_soluong_qlk);
        btn_Hoanthanh = (Button) findViewById(R.id.updateHoanthanh_qlk);
        btn_Thoat = (Button) findViewById(R.id.updateThoat_qlk);

        img_taihinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        ApiService.apiService.lay1VatPham(QuanLyKho.madiadiem, ChiTietVatPham.id)
                .enqueue(new Callback<Kho>() {
                    @Override
                    public void onResponse(Call<Kho> call, Response<Kho> response) {
                        Kho b = response.body();
                        edt_tenvatpham.setText(b.getTenvatpham());
                        edt_dongia.setText(String.valueOf(b.getDongia()));
                        edt_soluong.setText(String.valueOf(b.getSoluong()));
                        String photoImage = b.getHinhanh();
                        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        img_avatar.setImageBitmap(theImage);
                    }

                    @Override
                    public void onFailure(Call<Kho> call, Throwable t) {
                        Toast.makeText(SuaVatPham.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuaVatPham.this, Menu_NV.class);
                intent.putExtra("number",Menu_NV.sdt);
                startActivity(intent);
            }
        });
        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenvatpham = edt_tenvatpham.getText().toString();
                String dongia= edt_dongia.getText().toString().trim();
                String soluong = edt_soluong.getText().toString();

                if(tenvatpham.length()==0)
                {
                    edt_tenvatpham.requestFocus();
                    edt_tenvatpham.setError("Không được để trống !");

                }else if(dongia.length()==0)
                {
                    edt_dongia.requestFocus();
                    edt_dongia.setError("Không được để trống !");
                }else if(soluong.length()==0)
                {
                    edt_soluong.requestFocus();
                    edt_soluong.setError("Không được để trống !");
                }
                else {
                    Kho k = getVatPham();
                    ApiService.apiService.capnhatVatPham(QuanLyKho.madiadiem, ChiTietVatPham.id,k)
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(SuaVatPham.this, "Cập nhật thông tin thành công !", Toast.LENGTH_SHORT).show();
                                    String noidung = "Bạn đã cập nhật vật phẩm : "+k.getTenvatpham();
                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                    LichSuNV ls = new LichSuNV();
                                    ls.setIdnhanvien(Menu_NV.manhanvien);
                                    ls.setNoidung(noidung);
                                    ls.setThoigian(thoigian);
                                    LichSuNV.ThemLichSu(Menu_NV.manhanvien,ls);
                                    Intent intent = new Intent(SuaVatPham.this, Menu_NV.class);
                                    intent.putExtra("number",Menu_NV.sdt);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(SuaVatPham.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
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
    private Kho getVatPham (){
        Kho b= new Kho();
        b.setTenvatpham(edt_tenvatpham.getText().toString());
        b.setDongia(Integer.valueOf(edt_dongia.getText().toString().trim()));
        b.setSoluong(Integer.valueOf(edt_soluong.getText().toString().trim()));
        Bitmap bitmap = ((BitmapDrawable)img_avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bas);
        byte[] imBytes=bas.toByteArray();
        String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
        String result = "data:image/png;base64,"+encodedImage;
        b.setHinhanh(result);
        return b;
    }
}
