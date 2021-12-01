package com.example.bida.QuenMatKhau;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.DangKy.NhapThongTinDK_Activity;
import com.example.bida.MainActivity;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhapThongTin_QuenMKActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_CODE_SEND_SMS = 1;
    private static final String LOG_TAG = "AndroidExample";
    ArrayList<TaiKhoan> data = new ArrayList<>();
    Boolean k = false;
    EditText edt_thongtin;
    Button btn_tieptuc;
    Button btn_thoat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapthongtin_quenmk);

        edt_thongtin = (EditText)findViewById(R.id.phone);
        btn_tieptuc = (Button)findViewById(R.id.btnTiepTuc);
        btn_thoat = (Button)findViewById(R.id.btnThoat_nhapthongtin_quenmk);


        btn_tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sodt = edt_thongtin.getText().toString().trim();
                if(sodt.length()==0)
                {
                    edt_thongtin.requestFocus();
                    edt_thongtin.setError("Không được để trống !");
                }
                else
                {
                    ApiService.apiService.layDSTaiKhoan()
                            .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                                @Override
                                public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                                    data = response.body();

                                    for (int i = 0; i< data.size(); i++)
                                    {
                                        if (data.get(i).getSdt().equals(edt_thongtin.getText().toString().trim())==true){
                                            Intent intent = new Intent(NhapThongTin_QuenMKActivity.this, GuiMaOTP_Activity.class);
                        //                    String s = edt_thongtin.getText().toString().trim();
                        //                    String s1 = s.substring(1);
                        //                    String sdt = "+84"+s1;
                                            intent.putExtra("number",edt_thongtin.getText().toString().trim());
                                            startActivity(intent);
                                            k = true;
                                            break;
                                        }

                                    }
                                    if (k == false){
                                        edt_thongtin.requestFocus();
                                        edt_thongtin.setError("Sđt chưa được đăng ký, mời bạn đăng ký !");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                                    Toast.makeText(NhapThongTin_QuenMKActivity.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }



            }
        });

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NhapThongTin_QuenMKActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }


}
