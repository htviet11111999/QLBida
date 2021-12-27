package com.example.bida.fragment_qtv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.DiaDiem;
import com.example.bida.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietQuanLyDiaDiem extends AppCompatActivity {
    int id;
    TextView tv_tentiem, tv_diachi, tv_kinhdo, tv_vido, tv_trangthai,tv_ghichu,tv_hotenchu, tv_baigiuxe;
    Button btn_sua,  btn_thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_quanlydiadiem_qtv);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        tv_tentiem = (TextView) findViewById(R.id.ten_chitietQldd);
        tv_diachi = (TextView) findViewById(R.id.diachi_chitietQldd);
        tv_kinhdo = (TextView) findViewById(R.id.kinhdo_chitietQldd);
        tv_vido = (TextView) findViewById(R.id.vido_chitietQldd);
        tv_trangthai = (TextView) findViewById(R.id.trangthai_chitietQldd);
        tv_ghichu = (TextView) findViewById(R.id.ghichu_chitietQldd);
        tv_hotenchu = (TextView) findViewById(R.id.hotenchu_chitietQldd);
        tv_baigiuxe = (TextView) findViewById(R.id.baigiuxe_chitietQldd);
        btn_sua = (Button) findViewById(R.id.sua_qldd);
        btn_thoat = (Button) findViewById(R.id.thoat_qldd);

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietQuanLyDiaDiem.this, Menu_QTV.class);
                intent.putExtra("number",Menu_QTV.sdt);
                startActivity(intent);
            }
        });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietQuanLyDiaDiem.this, SuaDiaDiem.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        ApiService.apiService.lay1DiaDiem(id)
                .enqueue(new Callback<DiaDiem>() {
                    @Override
                    public void onResponse(Call<DiaDiem> call, Response<DiaDiem> response) {
                        DiaDiem d = response.body();
                        tv_tentiem.setText(String.format("Tên tiệm : %s", d.getTen()));
                        tv_diachi.setText(String.format("Địa chỉ : %s", d.getDiachi()));
                        tv_kinhdo.setText(String.format("Kinh độ : %s", d.getKinhdo()));
                        tv_vido.setText(String.format("Vĩ độ : %s", d.getVido()));
                        String tt = "";
                        if (d.getTrangthai() == 0) tt = "Còn bàn";
                        else tt = "Hết bàn";
                        tv_trangthai.setText(String.format("Trạng thái : %s", tt));
                        tv_ghichu.setText(String.format("Ghi chú : %s", d.getGhichu()));
                        tv_hotenchu.setText(String.format("Họ tên chủ : %s", d.getHotenchu()));
                        String g ="";
                        if(d.getBaigiuxe()== 1) g = "Không có bãi giữ xe";
                        else if(d.getBaigiuxe() == 2) g ="Trong nhà";
                        else g = "Có bãi giữ xe";
                        tv_baigiuxe.setText(String.format("Bãi giữ xe : %s", g));
                    }

                    @Override
                    public void onFailure(Call<DiaDiem> call, Throwable t) {
                        Toast.makeText(ChiTietQuanLyDiaDiem.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
