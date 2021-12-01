package com.example.bida.QuenMatKhau;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.DangKy.ChonAvatar_Activity;
import com.example.bida.DangKy.TaoMK_Activity;
import com.example.bida.MainActivity;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThayDoiMK_Activity extends AppCompatActivity {
    ArrayList<TaiKhoan> data = new ArrayList<>();
    TaiKhoan tk = new TaiKhoan();
    int ma;
    String sdt;
    EditText edt_MkMuondoi , edt_MkXacThuc;
    Button btn_HoanThanh, btn_Thoat;
    Boolean ktt = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thaydoimk);

        edt_MkMuondoi = (EditText) findViewById(R.id.inputMKmuondoi);
        edt_MkXacThuc = (EditText) findViewById(R.id.inputMKXacThuc);
        btn_HoanThanh = (Button) findViewById(R.id.btnHoanThanh);
        btn_Thoat = (Button) findViewById(R.id.btnThoat_thaydoimk);

        Intent intent = getIntent();
        sdt = intent.getStringExtra("number");

        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThayDoiMK_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_HoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edt_MkMuondoi.getText().toString().trim();
                String s1 = edt_MkXacThuc.getText().toString().trim();
                if(s.equals(s1)==false){
                    edt_MkXacThuc.requestFocus();
                    edt_MkXacThuc.setError("Không trùng với mật khẩu ở trên !");
                }
                else{
                    TaiKhoan taik = gettaikhoan();
                    ApiService.apiService.capnhatTaiKhoan(ma,taik)
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(ThayDoiMK_Activity.this, "Thay đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ThayDoiMK_Activity.this, MainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(ThayDoiMK_Activity.this, "Thay đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
    private TaiKhoan gettaikhoan() {
        ApiService.apiService.layDSTaiKhoan()
                .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                        data = response.body();
                        Log.e("SL",""+data.size());
                        for(int i =0; i< data.size();i++)
                        {
                            if(data.get(i).getSdt().equals(sdt) == true){
                                ma = data.get(i).getId();
                                TaiKhoan taikhoan = new TaiKhoan();
                                //String s = edt_nhapSDT.getText().toString().trim();
                                //String s1 = s.substring(1);
                                // String sdt = "+84"+s1;
                                taikhoan.setSdt(data.get(i).getSdt());
                                taikhoan.setHoten(data.get(i).getHoten());
                                taikhoan.setDiachi(data.get(i).getDiachi());
                                taikhoan.setQuyen("KH");
                                taikhoan.setHinhanh(data.get(i).getHinhanh());
                                taikhoan.setMatkhau(edt_MkMuondoi.getText().toString().trim());
                                tk =taikhoan;
                                break;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                        Toast.makeText(ThayDoiMK_Activity.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        return tk;
    }
}

