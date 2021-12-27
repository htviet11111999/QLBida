package com.example.bida.fragment_qtv;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Adapter.ChuTiemSpinnerAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.ChuTiemSpinner;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.LichSuQTV;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemDiaDiem extends AppCompatActivity {
    ArrayList<ChuTiemSpinner> arrayList = new ArrayList<ChuTiemSpinner>();
    ArrayList<Integer> trangthai = new ArrayList<>();
    ArrayList<TaiKhoan> data = new ArrayList<>();
    int machutiem;

    EditText edt_ten, edt_kinhdo, edt_vido, edt_diachi, edt_ghichu;
    Spinner spn_chutiem , spn_trangthai;
    RadioButton rd_khongco, rd_trongnha, rd_cobai;
    TextView tv_chutiem, tv_trangthai;
    Button btn_Hoanthanh, btn_Thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themdiadiem_qldd);

        spn_chutiem = (Spinner) findViewById(R.id.spn_chutiem);
        tv_chutiem =(TextView) findViewById(R.id.tvi_tenct);
        spn_trangthai = (Spinner) findViewById(R.id.spn_tt);
        tv_trangthai =(TextView) findViewById(R.id.tvi_tt);
        edt_ten = (EditText) findViewById(R.id.add_tentiem);
        edt_kinhdo = (EditText) findViewById(R.id.add_kinhdo);
        edt_vido = (EditText) findViewById(R.id.add_vido);
        edt_diachi = (EditText) findViewById(R.id.add_diachi_qldd);
        edt_ghichu = (EditText) findViewById(R.id.add_ghichu);
        rd_khongco = (RadioButton) findViewById(R.id.radio_khongco_add);
        rd_trongnha = (RadioButton) findViewById(R.id.radio_trongnha_add);
        rd_cobai = (RadioButton) findViewById(R.id.radio_cobai_add);
        btn_Hoanthanh = (Button) findViewById(R.id.addHoanthanh_qldd);
        btn_Thoat = (Button) findViewById(R.id.addThoat_qldd);

                ApiService.apiService.layDSTaiKhoan()
                .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                        data = response.body();
                        for(int i =0; i< data.size();i++){
                            if (data.get(i).getQuyen().equals("CT")==true && data.get(i).getSohuutiem() == 0){
                                arrayList.add(new ChuTiemSpinner(data.get(i).getHinhanh(), data.get(i).getId(),data.get(i).getHoten()));
                            }
                        }
                        ChuTiemSpinnerAdapter adapterGV = new ChuTiemSpinnerAdapter(ThemDiaDiem.this, R.layout.dong_chutiem, arrayList);
                        spn_chutiem.setAdapter(adapterGV);
                        spn_chutiem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                tv_chutiem.setText(arrayList.get(position).getHoten());
                                machutiem = arrayList.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                        Toast.makeText(ThemDiaDiem.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });


        trangthai.add(0);
        trangthai.add(1);
        ArrayAdapter adapterTT = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, trangthai);
        adapterTT.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spn_trangthai.setAdapter(adapterTT);
        spn_trangthai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(trangthai.get(position) == 0 ) tv_trangthai.setText("Còn bàn");
                else tv_trangthai.setText("Hết bàn");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemDiaDiem.this, Menu_QTV.class);
                intent.putExtra("number",Menu_QTV.sdt);
                startActivity(intent);
            }
        });

        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentiem = edt_ten.getText().toString().trim();
                String diachi = edt_diachi.getText().toString().trim();
                String kinhdo = edt_kinhdo.getText().toString();
                String vido = edt_vido.getText().toString().trim();

                if(tentiem.length()==0)
                {
                    edt_ten.requestFocus();
                    edt_ten.setError("Không được để trống !");

                }else if(kinhdo.length()==0)
                {
                    edt_kinhdo.requestFocus();
                    edt_kinhdo.setError("Không được để trống !");
                }else if(diachi.length()==0)
                {
                    edt_diachi.requestFocus();
                    edt_diachi.setError("Không được để trống !");
                }else if(vido.length()==0)
                {
                    edt_vido.requestFocus();
                    edt_vido.setError("Không được để trống !");
                }
                else{
                    DiaDiem d = getDiaDiem();
                    ApiService.apiService.taoDiaDiem(d)
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    ApiService.apiService.capnhatSohuu(machutiem)
                                            .enqueue(new Callback<JsonObject>() {
                                                @RequiresApi(api = Build.VERSION_CODES.O)
                                                @Override
                                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                    Toast.makeText(ThemDiaDiem.this, "Thêm địa điểm thành công !", Toast.LENGTH_SHORT).show();
                                                    String noidung = "Bạn đã thêm địa điểm mới "+d.getTen();
                                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                                    LichSuQTV ls = new LichSuQTV();
                                                    ls.setNoidung(noidung);
                                                    ls.setThoigian(thoigian);
                                                    LichSuQTV.ThemLichSu(ls);
                                                    Intent intent = new Intent(ThemDiaDiem.this, Menu_QTV.class);
                                                    intent.putExtra("number",Menu_QTV.sdt);
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                                    Toast.makeText(ThemDiaDiem.this, "Thêm địa điểm thất bại !", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(ThemDiaDiem.this, "Thêm địa điểm thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });


    }
    private DiaDiem getDiaDiem() {
        DiaDiem dd = new DiaDiem();
        dd.setTen(edt_ten.getText().toString());
        dd.setDiachi(edt_diachi.getText().toString());
        dd.setKinhdo( Double.valueOf(edt_kinhdo.getText().toString().trim()));
        dd.setVido( Double.valueOf(edt_vido.getText().toString().trim()));
        dd.setTrangthai( Integer.valueOf(spn_trangthai.getSelectedItem().toString()));
        dd.setIdchu(Integer.valueOf(machutiem));
        dd.setHotenchu(tv_chutiem.getText().toString());
        dd.setGhichu(edt_ghichu.getText().toString());
        if(rd_khongco.isChecked()) dd.setBaigiuxe(1);
        else if(rd_trongnha.isChecked()) dd.setBaigiuxe(2);
        else dd.setBaigiuxe(3);
        return dd;
    }
}
