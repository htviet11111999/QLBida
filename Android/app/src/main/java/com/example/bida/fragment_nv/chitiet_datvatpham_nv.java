package com.example.bida.fragment_nv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Adapter.ChiTietPhieuNhapAdapter;
import com.example.bida.Adapter.datvatphamAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Model.Booking;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.Model.Kho;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class chitiet_datvatpham_nv extends AppCompatActivity {
    private ListView lvDanhSach;
    Button btn_them , btn_thoat;
    ArrayList<CTBooking> data = new ArrayList<>();
    ArrayList<Kho> kho = new ArrayList<>();
    Kho kh = new Kho();
    Booking booking = chitiet_Booking_nv.booking;
    datvatphamAdapter adapter = null;
    int idvatpham;
    int iddiadiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_datvatpham_booking_nv);
        setControl();
        setEvent();
    }
    public void setControl() {
        lvDanhSach = (ListView)findViewById(R.id.listDatVatPham);
        btn_them = (Button) findViewById(R.id.ThemVatPham);
        btn_thoat = (Button) findViewById(R.id.ThoatDatVatPham);
    }
    public void setEvent() {
        if (chitiet_Booking_nv.booking.getTrangthai() != 3 ) btn_them.setVisibility(View.GONE);
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chitiet_datvatpham_nv.this, chitiet_Booking_nv.class);
                intent.putExtra("id", chitiet_Booking_nv.id);
                startActivity(intent);
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chitiet_datvatpham_nv.this, them_ctbooking_nv.class);
                startActivity(intent);
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
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(chitiet_datvatpham_nv.this);
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
                                                                                                Toast.makeText(chitiet_datvatpham_nv.this, "Xóa chi tiết Booking thành công !", Toast.LENGTH_SHORT).show();
                                                                                                ApiService.apiService.layDSCTBooking(chitiet_Booking_nv.id)
                                                                                                        .enqueue(new Callback<ArrayList<CTBooking>>() {
                                                                                                            @Override
                                                                                                            public void onResponse(Call<ArrayList<CTBooking>> call, Response<ArrayList<CTBooking>> response) {
                                                                                                                data = response.body();
                                                                                                                adapter = new datvatphamAdapter(chitiet_datvatpham_nv.this, R.layout.dat_vatpham_kh, data);
                                                                                                                lvDanhSach.setAdapter(adapter);
                                                                                                                adapter.notifyDataSetChanged();
                                                                                                            }
                                                                                                            @Override
                                                                                                            public void onFailure(Call<ArrayList<CTBooking>> call, Throwable t) {
                                                                                                                Toast.makeText(chitiet_datvatpham_nv.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        });
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                                                                Toast.makeText(chitiet_datvatpham_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(chitiet_datvatpham_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        });
                return false;
            }
        });
        ApiService.apiService.layDSCTBooking(chitiet_Booking_nv.id)
                .enqueue(new Callback<ArrayList<CTBooking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CTBooking>> call, Response<ArrayList<CTBooking>> response) {
                        data = response.body();
                        adapter = new datvatphamAdapter(chitiet_datvatpham_nv.this, R.layout.chitiet_datvatpham_booking_nv, data);
                        lvDanhSach.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<CTBooking>> call, Throwable t) {
                        Toast.makeText(chitiet_datvatpham_nv.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
