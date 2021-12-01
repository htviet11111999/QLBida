package com.example.bida.fragment_nv;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bida.Adapter.ChiTietPhieuNhapAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_NV;
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.R;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietPhieuNhap extends AppCompatActivity {
    private ListView lvDanhSach;
    Button btn_them , btn_thoat;
    ArrayList<CTPhieuNhap> data = new ArrayList<>();
    ChiTietPhieuNhapAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_phieunhap_nv);
        setControl();
        setEvent();
    }

    public void setControl() {
        lvDanhSach = (ListView)findViewById(R.id.listCTPN);
        btn_them = (Button) findViewById(R.id.ThemCTPN);
        btn_thoat = (Button) findViewById(R.id.ThoatCTPN);
    }

    public void setEvent() {
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietPhieuNhap.this, ThongTin1PhieuNhap.class);
                intent.putExtra("id", ThongTin1PhieuNhap.id);
                startActivity(intent);
            }
        });
        if (ThongTin1PhieuNhap.phieuNhap.getTrangthai() == 1) btn_them.setVisibility(View.GONE);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietPhieuNhap.this, ThemCTPhieuNhap.class);
                startActivity(intent);
            }
        });
        ApiService.apiService.layDSCTPhieuNhap(ThongTin1PhieuNhap.id)
                .enqueue(new Callback<ArrayList<CTPhieuNhap>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CTPhieuNhap>> call, Response<ArrayList<CTPhieuNhap>> response) {
                        data = response.body();
                        adapter = new ChiTietPhieuNhapAdapter(ChiTietPhieuNhap.this, R.layout.chitiet_phieunhap_nv, data);
                        lvDanhSach.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<CTPhieuNhap>> call, Throwable t) {
                        Toast.makeText(ChiTietPhieuNhap.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}