package com.example.bida.fragment_ct;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.LichSuCT;
import com.example.bida.R;
import com.example.bida.fragment_qtv.SuaDiaDiem;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CapNhatThongTinDiaDiem extends AppCompatActivity {
    DiaDiem d;
    int id ;
    EditText edt_ten, edt_kinhdo, edt_vido, edt_diachi, edt_ghichu;
    TextView tv_tenchutiem , tv_trangthai;
    RadioButton rd_khongco, rd_trongnha, rd_cobai;
    Button btn_Hoanthanh, btn_Thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capnhatthongtin_diadiem_ct);

        Intent intent = getIntent();
        id = intent.getIntExtra("ma", 0);

        tv_tenchutiem =(TextView) findViewById(R.id.update_hotenchu_ct);
        tv_trangthai =(TextView) findViewById(R.id.update_trangthai_ct);
        edt_ten = (EditText) findViewById(R.id.update_tentiem_ct);
        edt_kinhdo = (EditText) findViewById(R.id.update_kinhdo_ct);
        edt_vido = (EditText) findViewById(R.id.update_vido_ct);
        edt_diachi = (EditText) findViewById(R.id.update_diachi_qldd_ct);
        edt_ghichu = (EditText) findViewById(R.id.update_ghichu_ct);
        rd_khongco = (RadioButton) findViewById(R.id.radio_khongco_ct);
        rd_trongnha = (RadioButton) findViewById(R.id.radio_trongnha_ct);
        rd_cobai = (RadioButton) findViewById(R.id.radio_cobai_ct);
        btn_Hoanthanh = (Button) findViewById(R.id.updateHoanthanh_qldd_ct);
        btn_Thoat = (Button) findViewById(R.id.updateThoat_qldd_ct);

        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CapNhatThongTinDiaDiem.this, Menu_CT.class);
                intent.putExtra("number",Menu_CT.sdt);
                startActivity(intent);
            }
        });

        ApiService.apiService.lay1DiaDiem(id)
                .enqueue(new Callback<DiaDiem>() {
                    @Override
                    public void onResponse(Call<DiaDiem> call, Response<DiaDiem> response) {
                        d = response.body();
                        edt_ten.setText(d.getTen());
                        tv_tenchutiem.setText(d.getHotenchu());
                        edt_diachi.setText(d.getDiachi());
                        edt_kinhdo.setText( String.valueOf(d.getKinhdo()));
                        edt_vido.setText( String.valueOf(d.getVido()));
                        String s = "";
                        if(d.getTrangthai() == 0) s = "Còn bàn";
                        else s = "Hết bàn";
                        tv_trangthai.setText(s);
                        edt_ghichu.setText(d.getGhichu());
                        if (d.getBaigiuxe() == 1 ) rd_khongco.setChecked(true);
                        else if (d.getBaigiuxe() == 2) rd_trongnha.setChecked(true);
                        else rd_cobai.setChecked(true);
                    }

                    @Override
                    public void onFailure(Call<DiaDiem> call, Throwable t) {
                        Toast.makeText(CapNhatThongTinDiaDiem.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
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
                    DiaDiem di = getDiaDiem();
                    ApiService.apiService.capnhatDiaDiem(id,di)
                            .enqueue(new Callback<JsonObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(CapNhatThongTinDiaDiem.this, "Cập nhật thông tin thành công !", Toast.LENGTH_SHORT).show();
                                    String noidung = "Bạn đã cập nhật thông tin tiệm Bida của bạn ";
                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                    LichSuCT ls = new LichSuCT();
                                    ls.setIdchutiem(Menu_CT.machutiem);
                                    ls.setNoidung(noidung);
                                    ls.setThoigian(thoigian);
                                    LichSuCT.ThemLichSu(Menu_CT.machutiem,ls);
                                    Intent intent = new Intent(CapNhatThongTinDiaDiem.this, Menu_CT.class);
                                    intent.putExtra("number",Menu_CT.sdt);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(CapNhatThongTinDiaDiem.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }
    private DiaDiem getDiaDiem() {
        DiaDiem diaDiem  = new DiaDiem();
        diaDiem.setTen(edt_ten.getText().toString().trim());
        diaDiem.setDiachi(edt_diachi.getText().toString().trim());
        diaDiem.setKinhdo( Double.valueOf(edt_kinhdo.getText().toString().trim()));
        diaDiem.setVido( Double.valueOf(edt_vido.getText().toString().trim()));
        if(tv_trangthai.getText().toString().equals("Còn bàn")==true){
            diaDiem.setTrangthai(0);
        }
        else diaDiem.setTrangthai(1);
        diaDiem.setHotenchu(tv_tenchutiem.getText().toString());
        diaDiem.setGhichu(edt_ghichu.getText().toString());
        diaDiem.setIdchu(d.getIdchu());
        if(rd_khongco.isChecked()) diaDiem.setBaigiuxe(1);
        else if(rd_trongnha.isChecked()) diaDiem.setBaigiuxe(2);
        else diaDiem.setBaigiuxe(3);
        return diaDiem;
    }
}
