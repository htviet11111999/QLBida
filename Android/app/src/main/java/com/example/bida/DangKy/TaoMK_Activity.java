package com.example.bida.DangKy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.MainActivity;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.QuenMatKhau.GuiMaOTP_Activity;
import com.example.bida.QuenMatKhau.NhapThongTin_QuenMKActivity;
import com.example.bida.QuenMatKhau.ThayDoiMK_Activity;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaoMK_Activity extends AppCompatActivity {
    ArrayList <TaiKhoan> data = new ArrayList<>();
    TaiKhoan tk = new TaiKhoan();
    int ma;
    String sdt;
    EditText edt_matkhau;
    EditText edt_matkhauXacThuc;
    Button btn_HoanThanh;
    Button btn_Thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taomk_dangky);

        Intent intent = getIntent();
        sdt = intent.getStringExtra("number");

        edt_matkhau = (EditText) findViewById(R.id.inputMK_dk);
        edt_matkhauXacThuc = (EditText) findViewById(R.id.inputMKXacThuc_dk);
        btn_HoanThanh = (Button) findViewById(R.id.btnHoanThanh_DangKy);
        btn_Thoat = (Button) findViewById(R.id.btnThoat_taomk_DangKY);


        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaoMK_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_HoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = edt_matkhau.getText().toString().trim();
                String s1 = edt_matkhauXacThuc.getText().toString().trim();
                if(s.equals(s1)==true){
                    ApiService.apiService.layDSTaiKhoan()
                            .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                                @Override
                                public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                                    data = response.body();
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
                                            taikhoan.setHinhanh("");
                                            taikhoan.setMatkhau(edt_matkhau.getText().toString().trim());
                                            ApiService.apiService.capnhatTaiKhoan(ma,taikhoan)
                                                    .enqueue(new Callback<JsonObject>() {
                                                        @Override
                                                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                            Toast.makeText(TaoMK_Activity.this, "Tạo mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(TaoMK_Activity.this, ChonAvatar_Activity.class);
                                                            intent.putExtra("number",sdt);
                                                            intent.putExtra("matkhau",edt_matkhau.getText().toString().trim());
                                                            startActivity(intent);
                                                        }

                                                        @Override
                                                        public void onFailure(Call<JsonObject> call, Throwable t) {
                                                            Toast.makeText(TaoMK_Activity.this, "Tạo mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                            break;
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                                    Toast.makeText(TaoMK_Activity.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });




                }
                else{
                    edt_matkhauXacThuc.requestFocus();
                    edt_matkhauXacThuc.setError("Không trùng với mật khẩu ở trên !");
                }
            }
        });
    }
}



