package com.example.bida.fragment_nv;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bida.Adapter.ChiTietPhieuNhapAdapter;
import com.example.bida.Adapter.datvatphamAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_NV;
import com.example.bida.Model.Booking;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.Model.Kho;
import com.example.bida.R;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class datvatpham_nv extends AppCompatActivity {
    private ListView lvDanhSach;
    Button btn_them, btn_hoanthanh;
    ArrayList<CTBooking> data = new ArrayList<>();
    ArrayList<Kho> kho = new ArrayList<>();
    Kho kh = new Kho();
    datvatphamAdapter adapter = null;
    public static Booking booking = datbooking_nv.b;
    int idvatpham;
    int iddiadiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ds_vatpham_nv);
        setControl();
        setEvent();
    }
    public void setControl() {
        lvDanhSach = (ListView)findViewById(R.id.listCTBooking_nv);
        btn_them = (Button) findViewById(R.id.ThemCTBooking_nv);
        btn_hoanthanh = (Button) findViewById(R.id.HoanThanhThemCTBooking_nv);
    }
    public void setEvent() {
        int ma;
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(datvatpham_nv.this, them_ctbooking_nv.class);
                startActivity(intent);
            }
        });
        btn_hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(datvatpham_nv.this, Menu_NV.class);
                intent.putExtra("number",Menu_NV.sdt);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        ma= intent.getIntExtra("ma", 0);
        ApiService.apiService.layDSCTBooking(ma)
                .enqueue(new Callback<ArrayList<CTBooking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CTBooking>> call, Response<ArrayList<CTBooking>> response) {
                        data = response.body();
                        adapter = new datvatphamAdapter(datvatpham_nv.this, R.layout.ds_vatpham_nv, data);
                        lvDanhSach.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<CTBooking>> call, Throwable t) {
                        Toast.makeText(datvatpham_nv.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        lvDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ApiService.apiService.layDSVatPham(QuanLyDSBooking.madiadiem)
                        .enqueue(new Callback<ArrayList<Kho>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                                kho = response.body();
                                for (int i =0 ; i< kho.size(); i++){
                                    if(kho.get(i).getTenvatpham().equals(data.get(pos).getTenvatpham())==true){
                                        kh = kho.get(i);
                                        idvatpham = kho.get(i).getId();
                                        iddiadiem = kho.get(i).getIddiadiem();
                                        break;
                                    }
                                }
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(datvatpham_nv.this);
                                builder1.setMessage("Bạn chắc chắn muốn xóa chứ ?");
                                builder1.setCancelable(true);
                                builder1.setPositiveButton(
                                        "Đồng ý",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int i) {
                                                kh.setSoluong(kh.getSoluong() + data.get(pos).getSoluong());
                                                ApiService.apiService.capnhatVatPham(iddiadiem,idvatpham,kh)
                                                        .enqueue(new Callback<JsonObject>() {
                                                            @Override
                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                booking.setTienthanhtoan(booking.getTienthanhtoan()-data.get(pos).getDongia()* data.get(pos).getSoluong());
                                                                booking.setGiochoithat("");
                                                                ApiService.apiService.suaBooking(iddiadiem,data.get(pos).getIdbooking(),booking)
                                                                        .enqueue(new Callback<JsonObject>() {
                                                                            @Override
                                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                                ApiService.apiService.xoaCTBooking(data.get(pos).getId())
                                                                                        .enqueue(new Callback<JsonObject>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                                                Toast.makeText(datvatpham_nv.this, "Xóa chi tiết Booking thành công !", Toast.LENGTH_SHORT).show();
                                                                                                ApiService.apiService.layDSCTBooking(ma)
                                                                                                        .enqueue(new Callback<ArrayList<CTBooking>>() {
                                                                                                            @Override
                                                                                                            public void onResponse(Call<ArrayList<CTBooking>> call, Response<ArrayList<CTBooking>> response) {
                                                                                                                data = response.body();
                                                                                                                adapter = new datvatphamAdapter(datvatpham_nv.this, R.layout.dat_vatpham_kh, data);
                                                                                                                lvDanhSach.setAdapter(adapter);
                                                                                                                adapter.notifyDataSetChanged();
                                                                                                            }
                                                                                                            @Override
                                                                                                            public void onFailure(Call<ArrayList<CTBooking>> call, Throwable t) {
                                                                                                                Toast.makeText(datvatpham_nv.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        });
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                                                                Toast.makeText(datvatpham_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<JsonObject> call, Throwable t) {

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
                                        "Thoát",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                            @Override
                            public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {
                                Toast.makeText(datvatpham_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        });
                return false;
            }
        });
    }
}