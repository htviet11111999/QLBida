package com.example.bida.fragment_ct;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bida.Adapter.AdapterCTPN;
import com.example.bida.Adapter.ChiTietPhieuNhapAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_NV;
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.R;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CTPN extends AppCompatActivity {
    private ListView lvDanhSach;
    Button  btn_thoat;
    ArrayList<CTPhieuNhap> data = new ArrayList<>();
    AdapterCTPN adapter = null;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctphieunhap_ct);
        setControl();
        setEvent();
    }

    public void setControl() {
        lvDanhSach = (ListView)findViewById(R.id.CT);
        btn_thoat = (Button) findViewById(R.id.ThoatCT);
    }

    public void setEvent() {
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CTPN.this, Menu_CT.class);
                intent.putExtra("number",Menu_CT.sdt);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        ApiService.apiService.layDSCTPhieuNhap(id)
                .enqueue(new Callback<ArrayList<CTPhieuNhap>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CTPhieuNhap>> call, Response<ArrayList<CTPhieuNhap>> response) {
                        data = response.body();
                        adapter = new AdapterCTPN(CTPN.this, R.layout.ctphieunhap_ct, data);
                        lvDanhSach.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<CTPhieuNhap>> call, Throwable t) {
                        Toast.makeText(CTPN.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}