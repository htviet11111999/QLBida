package com.example.bida.fragment_kh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_KH;
import com.example.bida.Model.Booking;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.Kho;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemCTBooking_vatpham extends AppCompatActivity {
    ArrayList<Kho> kho = new ArrayList<>();
    ArrayList<Booking> data = new ArrayList<>();
    public static int IDBOOKING;
    Kho kh = new Kho();
    ImageView img_avatar;
    Spinner spn_tenvp;
    TextView tv_dongia, tv_slcl;
    EditText edt_soluong;
    Button btn_Hoanthanh, btn_Thoat;
    int idphatvat;
    String tenvp;
    public static CTBooking ct;
    public static Booking booking = DatBan_KH.boo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_ctbooking_kh);
        spn_tenvp = (Spinner) findViewById(R.id.spn_tenvatpham);
        img_avatar = (ImageView) findViewById(R.id.themBooking_hinh);
        tv_dongia = (TextView) findViewById(R.id.themBooking_dongia);
        tv_slcl = (TextView) findViewById(R.id.themBooking_slcl);
        edt_soluong = (EditText) findViewById(R.id.themBooking_soluong);
        btn_Hoanthanh = (Button) findViewById(R.id.addHoanthanh_themBooking);
        btn_Thoat = (Button) findViewById(R.id.addThoat_themBooking);

        ApiService.apiService.layDSVatPham(ChiTietDiaDiem_KH.id)
                .enqueue(new Callback<ArrayList<Kho>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                        kho = response.body();
                        ArrayList<String> tenvatpham = new ArrayList<String>();
                        for (int i = 0; i < kho.size(); i++) {
                            tenvatpham.add(kho.get(i).getTenvatpham());
                        }
                        ArrayAdapter spinnerAdapter = new ArrayAdapter(ThemCTBooking_vatpham.this, android.R.layout.simple_spinner_item, tenvatpham);
                        spn_tenvp.setAdapter(spinnerAdapter);
                        spn_tenvp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int i, long id) {
                                //đối số postion là vị trí phần tử trong list Data
                                kh = kho.get(i);
                                idphatvat = kho.get(i).getId();
                                tenvp = kho.get(i).getTenvatpham();
                                String photoImage = kho.get(i).getHinhanh();
                                photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                                byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                                ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                                img_avatar.setImageBitmap(theImage);
                                tv_dongia.setText(String.valueOf(kho.get(i).getDongia()));
                                tv_slcl.setText(String.valueOf(kho.get(i).getSoluong()));
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {
                                Toast.makeText(ThemCTBooking_vatpham.this, "Vui lòng chọn vật phẩm !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {
                        Toast.makeText(ThemCTBooking_vatpham.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemCTBooking_vatpham.this, DatVatPham.class);
                intent.putExtra("ma",IDBOOKING);
                startActivity(intent);
            }
        });

        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soluong = edt_soluong.getText().toString();

                if (soluong.length() == 0) {
                    edt_soluong.requestFocus();
                    edt_soluong.setError("Không được để trống !");
                } else if (Integer.valueOf(soluong) < 0) {
                    edt_soluong.requestFocus();
                    edt_soluong.setError("Số lượng không hợp lệ !");
                } else if (Integer.valueOf(soluong) > Integer.valueOf(tv_slcl.getText().toString())) {
                    edt_soluong.requestFocus();
                    edt_soluong.setError("Số lượng trong kho không đủ !");
                } else {
                    ApiService.apiService.layDSBooking(ChiTietDiaDiem_KH.id)
                            .enqueue(new Callback<ArrayList<Booking>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                                    data = response.body();
                                    for (int i = 0 ; i<data.size(); i++){
                                        if ((data.get(i).getSdt().equals(Menu_KH.sdt)) && (data.get(i).getTrangthai()==2)){
                                            IDBOOKING = data.get(i).getId();
                                            ct = new CTBooking();
                                            ct.setIdbooking(IDBOOKING);
                                            ct.setIdvatpham(idphatvat);
                                            ct.setTenvatpham(tenvp);
                                            ct.setSoluong(Integer.valueOf(edt_soluong.getText().toString()));
                                            ct.setDongia(Integer.valueOf(tv_dongia.getText().toString()));
                                            Bitmap bitmap = ((BitmapDrawable) img_avatar.getDrawable()).getBitmap();
                                            ByteArrayOutputStream bas = new ByteArrayOutputStream();
                                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bas);
                                            byte[] imBytes = bas.toByteArray();
                                            String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
                                            String result = "data:image/png;base64," + encodedImage;
                                            ct.setHinhanh(result);
                                            ApiService.apiService.themCTBooking(ChiTietDiaDiem_KH.idBooking, ct)
                                                    .enqueue(new Callback<JsonObject>() {
                                                        @Override
                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                            Toast.makeText(ThemCTBooking_vatpham.this, "Thêm chi tiết Booking thành công !", Toast.LENGTH_SHORT).show();
                                                            kh.setSoluong(kh.getSoluong() - Integer.valueOf(edt_soluong.getText().toString().trim()));
                                                            ApiService.apiService.capnhatVatPham(ChiTietDiaDiem_KH.id, kh.getId(), kh)
                                                                    .enqueue(new Callback<JsonObject>() {
                                                                        @Override
                                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                            Toast.makeText(ThemCTBooking_vatpham.this, "Cập nhật số lượng trong kho thành công !", Toast.LENGTH_SHORT).show();
                                                                            DatBan_KH.boo.setTienthanhtoan(DatBan_KH.boo.getTienthanhtoan() + kh.getDongia() * Integer.valueOf(edt_soluong.getText().toString().trim()));
                                                                            DatBan_KH.boo.setGiochoithat("");
                                                                            ApiService.apiService.suaBooking(ChiTietDiaDiem_KH.id, IDBOOKING, DatBan_KH.boo)
                                                                                    .enqueue(new Callback<JsonObject>() {
                                                                                        @Override
                                                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                                            Intent intent = new Intent(ThemCTBooking_vatpham.this, DatVatPham.class);
                                                                                            intent.putExtra("ma",IDBOOKING);
                                                                                            startActivity(intent);
                                                                                        }

                                                                                        @Override
                                                                                        public void onFailure(Call<JsonObject> call, Throwable t) {

                                                                                        }
                                                                                    });
                                                                        }
                                                                        @Override
                                                                        public void onFailure(Call<JsonObject> call, Throwable t) {
                                                                            Toast.makeText(ThemCTBooking_vatpham.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

                                                        }

                                                        @Override
                                                        public void onFailure(Call<JsonObject> call, Throwable t) {
                                                            Toast.makeText(ThemCTBooking_vatpham.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                            break;
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                                }
                            });

                }

            }
        });
    }
}
