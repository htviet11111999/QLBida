package com.example.bida.fragment_nv;

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
import com.example.bida.fragment_kh.ChiTietDiaDiem_KH;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class them_ctbooking_nv extends AppCompatActivity {
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
    public static Booking booking = datbooking_nv.b;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_ctbooking_nv);
        spn_tenvp = (Spinner) findViewById(R.id.spn_tenvatpham_nv);
        img_avatar = (ImageView) findViewById(R.id.themBooking_hinh_nv);
        tv_dongia = (TextView) findViewById(R.id.themBooking_dongia_nv);
        tv_slcl = (TextView) findViewById(R.id.themBooking_slcl_nv);
        edt_soluong = (EditText) findViewById(R.id.themBooking_soluong_nv);
        btn_Hoanthanh = (Button) findViewById(R.id.addHoanthanh_themBooking_nv);
        btn_Thoat = (Button) findViewById(R.id.addThoat_themBooking_nv);

        ApiService.apiService.layDSVatPham(QuanLyDSBooking.madiadiem)
                .enqueue(new Callback<ArrayList<Kho>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                        kho = response.body();
                        ArrayList<String> tenvatpham = new ArrayList<String>();
                        for (int i = 0; i < kho.size(); i++) {
                            tenvatpham.add(kho.get(i).getTenvatpham());
                        }
                        ArrayAdapter spinnerAdapter = new ArrayAdapter(them_ctbooking_nv.this, android.R.layout.simple_spinner_item, tenvatpham);
                        spn_tenvp.setAdapter(spinnerAdapter);
                        spn_tenvp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int i, long id) {
                                //?????i s??? postion l?? v??? tr?? ph???n t??? trong list Data
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
                                Toast.makeText(them_ctbooking_nv.this, "Vui l??ng ch???n v???t ph???m !", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {
                        Toast.makeText(them_ctbooking_nv.this, "G???i API th???t b???i !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(them_ctbooking_nv.this, datvatpham_nv.class);
                intent.putExtra("ma", datbooking_nv.b.getId());
                startActivity(intent);
            }
        });

        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soluong = edt_soluong.getText().toString();

                if (soluong.length() == 0) {
                    edt_soluong.requestFocus();
                    edt_soluong.setError("Kh??ng ???????c ????? tr???ng !");
                } else if (Integer.valueOf(soluong) < 0) {
                    edt_soluong.requestFocus();
                    edt_soluong.setError("S??? l?????ng kh??ng h???p l??? !");
                } else if (Integer.valueOf(soluong) > Integer.valueOf(tv_slcl.getText().toString())) {
                    edt_soluong.requestFocus();
                    edt_soluong.setError("S??? l?????ng trong kho kh??ng ????? !");
                } else {
                    ApiService.apiService.layDSBooking(QuanLyDSBooking.madiadiem)
                            .enqueue(new Callback<ArrayList<Booking>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                                    data = response.body();
                                    Log.e("Detail",""+data.size());
                                    for (int i = 0 ; i<data.size(); i++){
                                        if ((data.get(i).getTenkhachhang().equals(datbooking_nv.b.getTenkhachhang())) && (data.get(i).getTrangthai()==0)){
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
                                            ApiService.apiService.themCTBooking(datbooking_nv.b.getId(), ct)
                                                    .enqueue(new Callback<JsonObject>() {
                                                        @Override
                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                            Toast.makeText(them_ctbooking_nv.this, "Th??m chi ti???t Booking th??nh c??ng !", Toast.LENGTH_SHORT).show();
                                                            kh.setSoluong(kh.getSoluong() - Integer.valueOf(edt_soluong.getText().toString().trim()));
                                                            ApiService.apiService.capnhatVatPham(QuanLyDSBooking.madiadiem, kh.getId(), kh)
                                                                    .enqueue(new Callback<JsonObject>() {
                                                                        @Override
                                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                            Toast.makeText(them_ctbooking_nv.this, "C???p nh???t s??? l?????ng trong kho th??nh c??ng !", Toast.LENGTH_SHORT).show();
                                                                            datbooking_nv.b.setTienthanhtoan(datbooking_nv.b.getTienthanhtoan() + kh.getDongia() * Integer.valueOf(edt_soluong.getText().toString().trim()));
                                                                            datbooking_nv.b.setGiochoithat("");
                                                                            ApiService.apiService.suaBooking(QuanLyDSBooking.madiadiem, IDBOOKING, datbooking_nv.b)
                                                                                    .enqueue(new Callback<JsonObject>() {
                                                                                        @Override
                                                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                                            Intent intent = new Intent(them_ctbooking_nv.this, datvatpham_nv.class);
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
                                                                            Toast.makeText(them_ctbooking_nv.this, "G???i API th???t b???i !", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    });

                                                        }

                                                        @Override
                                                        public void onFailure(Call<JsonObject> call, Throwable t) {
                                                            Toast.makeText(them_ctbooking_nv.this, "G???i API th???t b???i !", Toast.LENGTH_SHORT).show();
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
