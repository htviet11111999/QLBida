package com.example.bida.fragment_nv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.MainActivity;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_NV;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.Kho;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemCTPhieuNhap extends AppCompatActivity {
    ArrayList<Kho> kho = new ArrayList<>();
    Kho kh = new Kho();
    Boolean kt = false;
    ImageView img_avatar , img_taihinh;
    Button btn_kt;
    EditText edt_tenvatpham, edt_dongia, edt_soluong;
    Button btn_Hoanthanh, btn_Thoat;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_ctphieunhap_nv);

        img_avatar = (ImageView) findViewById(R.id.addctpn_hinh);
        img_taihinh = (ImageView) findViewById(R.id.add_hinhbida_ctpn);
        edt_tenvatpham = (EditText) findViewById(R.id.addctpn_tenvatpham);
        edt_dongia = (EditText) findViewById(R.id.addctpn_dongia);
        edt_soluong = (EditText) findViewById(R.id.addctpn_soluong);
        btn_Hoanthanh = (Button) findViewById(R.id.addHoanthanh_ctpn);
        btn_Thoat = (Button) findViewById(R.id.addThoat_ctpn);
        btn_kt = (Button) findViewById(R.id.btn_kt);

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
                Intent intent = new Intent(ThemCTPhieuNhap.this, Menu_NV.class);
                intent.putExtra("number",Menu_NV.sdt);
                startActivity(intent);
            }
        });
        btn_kt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.layDSVatPham(QuanLyDSPhieuNhap.madiadiem)
                        .enqueue(new Callback<ArrayList<Kho>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                                kho = response.body();
                                for (int i =0 ; i< kho.size(); i++){
                                    if(kho.get(i).getTenvatpham().equals(edt_tenvatpham.getText().toString())==true){
                                        kh = kho.get(i);
                                        String photoImage = kho.get(i).getHinhanh();
                                        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                                        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                                        img_avatar.setImageBitmap(theImage);
                                        edt_dongia.setText(String.valueOf(kho.get(i).getDongia()));
                                        kt = true ;
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {
                                Toast.makeText(ThemCTPhieuNhap.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenvatpham = edt_tenvatpham.getText().toString().trim();
                String dongia = edt_dongia.getText().toString().trim();
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
                else{
                    CTPhieuNhap ct = new CTPhieuNhap();
                    ct.setIdphieunhap(ThongTin1PhieuNhap.id);
                    ct.setTenvatpham(edt_tenvatpham.getText().toString());
                    Bitmap bitmap = ((BitmapDrawable)img_avatar.getDrawable()).getBitmap();
                    ByteArrayOutputStream bas = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,bas);
                    byte[] imBytes=bas.toByteArray();
                    String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
                    String result = "data:image/png;base64,"+encodedImage;
                    ct.setHinhanh(result);
                    ct.setDongia( Integer.valueOf(edt_dongia.getText().toString().trim()));
                    ct.setSoluong(Integer.valueOf(edt_soluong.getText().toString().trim()));

                    ApiService.apiService.themCTPhieuNhap(ThongTin1PhieuNhap.id,ct)
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(ThemCTPhieuNhap.this, "Thêm chi tiết phiếu nhập thành công !", Toast.LENGTH_SHORT).show();
                                    if(kt == false){
                                        Kho k = new Kho();
                                        k.setTenvatpham(edt_tenvatpham.getText().toString());
                                        k.setDongia( Integer.valueOf(edt_dongia.getText().toString().trim()));
                                        k.setSoluong(Integer.valueOf(edt_soluong.getText().toString().trim()));
                                        k.setIddiadiem(QuanLyDSPhieuNhap.madiadiem);
                                        Bitmap bitmap = ((BitmapDrawable)img_avatar.getDrawable()).getBitmap();
                                        ByteArrayOutputStream bas = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bas);
                                        byte[] imBytes=bas.toByteArray();
                                        String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
                                        String result = "data:image/png;base64,"+encodedImage;
                                        k.setHinhanh(result);
                                        ApiService.apiService.themVatPham(k)
                                                .enqueue(new Callback<JsonObject>() {
                                                    @Override
                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                        Toast.makeText(ThemCTPhieuNhap.this, "Thêm vật phẩm vào kho thành công !", Toast.LENGTH_SHORT).show();
                                                        ThongTin1PhieuNhap.phieuNhap.setTienthanhtoan( ThongTin1PhieuNhap.phieuNhap.getTienthanhtoan()+ Integer.valueOf(edt_dongia.getText().toString().trim())*Integer.valueOf(edt_soluong.getText().toString().trim()));
                                                        ApiService.apiService.suaPhieuNhap(QuanLyDSPhieuNhap.madiadiem,ThongTin1PhieuNhap.phieuNhap.getId(),ThongTin1PhieuNhap.phieuNhap)
                                                                .enqueue(new Callback<JsonObject>() {
                                                                    @Override
                                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<JsonObject> call, Throwable t) {

                                                                    }
                                                                });
                                                        Intent intent = new Intent(ThemCTPhieuNhap.this, Menu_NV.class);
                                                        intent.putExtra("number",Menu_NV.sdt);
                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                                        Toast.makeText(ThemCTPhieuNhap.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    else{
                                        kh.setSoluong(kh.getSoluong() + Integer.valueOf(edt_soluong.getText().toString().trim()));
                                        ApiService.apiService.capnhatVatPham(QuanLyDSPhieuNhap.madiadiem, kh.getId(),kh)
                                                .enqueue(new Callback<JsonObject>() {
                                                    @Override
                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                        Toast.makeText(ThemCTPhieuNhap.this, "Thêm số lượng vào kho thành công !", Toast.LENGTH_SHORT).show();
                                                        ThongTin1PhieuNhap.phieuNhap.setTienthanhtoan( ThongTin1PhieuNhap.phieuNhap.getTienthanhtoan()+ kh.getDongia()*Integer.valueOf(edt_soluong.getText().toString().trim()));
                                                        ApiService.apiService.suaPhieuNhap(QuanLyDSPhieuNhap.madiadiem,ThongTin1PhieuNhap.phieuNhap.getId(),ThongTin1PhieuNhap.phieuNhap)
                                                                .enqueue(new Callback<JsonObject>() {
                                                                    @Override
                                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<JsonObject> call, Throwable t) {

                                                                    }
                                                                });
                                                        Intent intent = new Intent(ThemCTPhieuNhap.this, Menu_NV.class);
                                                        intent.putExtra("number",Menu_NV.sdt);
                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                                        Toast.makeText(ThemCTPhieuNhap.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(ThemCTPhieuNhap.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
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
}
