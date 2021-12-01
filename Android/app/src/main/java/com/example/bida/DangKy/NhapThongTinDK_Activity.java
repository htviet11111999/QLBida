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
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NhapThongTinDK_Activity extends AppCompatActivity {
    ArrayList <TaiKhoan> data = new ArrayList<>();
    Boolean kt = false;
    EditText edt_nhapSDT;
    EditText edt_nhapHoTen;
    EditText edt_nhapDiaChi;
    Button btn_TiepTuc;
    Button btn_Thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nhapthongtin_dangky);

        edt_nhapSDT = (EditText)findViewById(R.id.phone_dangky);
        edt_nhapHoTen = (EditText)findViewById(R.id.hoten_dangky);
        edt_nhapDiaChi = (EditText)findViewById(R.id.diachi_dangky);

        btn_TiepTuc = (Button) findViewById(R.id.btnTiepTuc_dangky);
        btn_Thoat = (Button) findViewById(R.id.btnThoat_dangky);

        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NhapThongTinDK_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_TiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sdt = edt_nhapSDT.getText().toString().trim();
                String hoten = edt_nhapHoTen.getText().toString();
                String diachi = edt_nhapDiaChi.getText().toString();
                //String reg = "^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$";

                if(sdt.length()==0)
                {
                    edt_nhapSDT.requestFocus();
                    edt_nhapSDT.setError("Không được để trống !");

                }
//                else if ( sdt.length()!=0 && sdt.matches(reg) == false) {
//                    edt_nhapSDT.requestFocus();
//                    edt_nhapSDT.setError("Sai định dạng sđt !");
//                }
                else if(hoten.length()==0)
                {
                    edt_nhapHoTen.requestFocus();
                    edt_nhapHoTen.setError("Không được để trống !");
                }else if(diachi.length()==0)
                {
                    edt_nhapDiaChi.requestFocus();
                    edt_nhapDiaChi.setError("Không được để trống !");
                }
                else {
                    ApiService.apiService.layDSTaiKhoan()
                            .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                                @Override
                                public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                                    data = response.body();

                                    for (int i =0;i < data.size(); i++){
//                                        String s = edt_nhapSDT.getText().toString().trim();
//                                        String s1 = s.substring(1);
//                                        String sdt = "+84"+s1;
                                        if (data.get(i).getSdt().equals(sdt) == true){
                                            edt_nhapSDT.requestFocus();
                                            edt_nhapSDT.setError("Số điện thoại đã đăng ký !");
                                            kt = true;
                                            break;
                                        }
                                    }
                                    if (kt == false){
                                        TaiKhoan taiKhoan = gettaikhoan();
                                        ApiService.apiService.taoTaiKhoan(taiKhoan)
                                                .enqueue(new Callback<JsonObject>() {
                                                    @Override
                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                        Toast.makeText(NhapThongTinDK_Activity.this, "Thêm thông tin thành công !", Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(NhapThongTinDK_Activity.this, GuiMaOTP_DangKyActivity.class);
//                                                            String s = edt_nhapSDT.getText().toString().trim();
//                                                            String s1 = s.substring(1);
//                                                            String sdt = "+84"+s1;
                                                        intent.putExtra("number",edt_nhapSDT.getText().toString().trim());
                                                        startActivity(intent);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                                        Toast.makeText(NhapThongTinDK_Activity.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                                @Override
                                public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                                    Toast.makeText(NhapThongTinDK_Activity.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    private TaiKhoan gettaikhoan() {
        TaiKhoan taikhoan = new TaiKhoan();
        //String s = edt_nhapSDT.getText().toString().trim();
        //String s1 = s.substring(1);
       // String sdt = "+84"+s1;
        taikhoan.setSdt(edt_nhapSDT.getText().toString().trim());
        taikhoan.setHoten(edt_nhapHoTen.getText().toString());
        taikhoan.setDiachi(edt_nhapDiaChi.getText().toString());
        taikhoan.setQuyen("KH");
        taikhoan.setMatkhau("");
        taikhoan.setHinhanh("");
        return taikhoan;
    }
}
