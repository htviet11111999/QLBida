package com.example.bida.fragment_ct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Adapter.datvatphamAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.Kho;
import com.example.bida.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CTB extends AppCompatActivity {
    private ListView lvDanhSach;
    Button btn_thoat;
    ArrayList<CTBooking> data = new ArrayList<>();
    datvatphamAdapter adapter = null;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ctbooking_ct);
        setControl();
        setEvent();
    }
    public void setControl() {
        lvDanhSach = (ListView)findViewById(R.id.listCTB);
        btn_thoat = (Button) findViewById(R.id.ThoatCTB);
    }
    public void setEvent() {
        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CTB.this, Menu_CT.class);
                intent.putExtra("number",Menu_CT.sdt);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        ApiService.apiService.layDSCTBooking(id)
                .enqueue(new Callback<ArrayList<CTBooking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CTBooking>> call, Response<ArrayList<CTBooking>> response) {
                        data = response.body();
                        adapter = new datvatphamAdapter(CTB.this, R.layout.chitiet_datvatpham_booking_nv, data);
                        lvDanhSach.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<CTBooking>> call, Throwable t) {
                        Toast.makeText(CTB.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
