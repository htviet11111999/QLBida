package com.example.bida.fragment_ct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.DiaDiem;
import com.example.bida.R;
import com.example.bida.fragment_qtv.ChiTietQuanLyDiaDiem;
import com.example.bida.fragment_qtv.SuaDiaDiem;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDiaDiem extends AppCompatActivity {
    TextView tv_tentiem, tv_diachi, tv_kinhdo, tv_vido, tv_trangthai,tv_ghichu,tv_hotenchu, tv_baigiuxe;
    Button btn_sua,  btn_thoat;
    int ma;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietthongtin_diadiem_ct);

        Intent intent = getIntent();
        ma = intent.getIntExtra("ma", 0);

        tv_tentiem = (TextView) findViewById(R.id.ten_chitietQldd_ct);
        tv_diachi = (TextView) findViewById(R.id.diachi_chitietQldd_ct);
        tv_kinhdo = (TextView) findViewById(R.id.kinhdo_chitietQldd_ct);
        tv_vido = (TextView) findViewById(R.id.vido_chitietQldd_ct);
        tv_trangthai = (TextView) findViewById(R.id.trangthai_chitietQldd_ct);
        tv_ghichu = (TextView) findViewById(R.id.ghichu_chitietQldd_ct);
        tv_hotenchu = (TextView) findViewById(R.id.hotenchu_chitietQldd_ct);
        tv_baigiuxe = (TextView) findViewById(R.id.baigiuxe_chitietQldd_ct);
        btn_sua = (Button) findViewById(R.id.sua_qldd_ct);
        btn_thoat = (Button) findViewById(R.id.thoat_qldd_ct);

        ApiService.apiService.lay1DiaDiem(ma)
                .enqueue(new Callback<DiaDiem>() {
                    @Override
                    public void onResponse(Call<DiaDiem> call, Response<DiaDiem> response) {
                        DiaDiem d = response.body();
                        tv_tentiem.setText(String.format("T??n ti???m : %s", d.getTen()));
                        tv_diachi.setText(String.format("?????a ch??? : %s", d.getDiachi()));
                        tv_kinhdo.setText(String.format("Kinh ????? : %s", d.getKinhdo()));
                        tv_vido.setText(String.format("V?? ????? : %s", d.getVido()));
                        String tt = "";
                        if (d.getTrangthai() == 0) tt = "C??n b??n";
                        else tt = "H???t b??n";
                        tv_trangthai.setText(String.format("Tr???ng th??i : %s", tt));
                        tv_ghichu.setText(String.format("Ghi ch?? : %s", d.getGhichu()));
                        tv_hotenchu.setText(String.format("H??? t??n ch??? : %s", d.getHotenchu()));
                        String g ="";
                        if(d.getBaigiuxe()== 1) g = "Kh??ng c?? b??i gi??? xe";
                        else if(d.getBaigiuxe() == 2) g ="Trong nh??";
                        else g = "C?? b??i gi??? xe";
                        tv_baigiuxe.setText(String.format("B??i gi??? xe : %s", g));
                    }

                    @Override
                    public void onFailure(Call<DiaDiem> call, Throwable t) {
                        Toast.makeText(ChiTietDiaDiem.this, "G???i API th???t b???i !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietDiaDiem.this, Menu_CT.class);
                intent.putExtra("number", Menu_CT.sdt);
                startActivity(intent);
            }
        });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietDiaDiem.this, CapNhatThongTinDiaDiem.class);
                intent.putExtra("ma",ma);
                startActivity(intent);
            }
        });
    }
}
