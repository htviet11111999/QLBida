package com.example.bida;

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
import com.example.bida.DangKy.NhapThongTinDK_Activity;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.QuenMatKhau.GuiMaOTP_Activity;
import com.example.bida.QuenMatKhau.NhapThongTin_QuenMKActivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ArrayList<TaiKhoan> data = new ArrayList<>();
    ArrayList<Booking> bo = new ArrayList<>();
    public String quyen;
    public int sohuutiem;
    public static int ma;
    public String sodienthoai;
    EditText edt_tendangnhap;
    EditText  edt_matkhau;
    Button btn_dangnhap;
    TextView tv_quenmk;
    TextView tv_dangky;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getListUser();

        edt_tendangnhap = (EditText)findViewById(R.id.inputTenDangNhap);
        edt_matkhau = (EditText)findViewById(R.id.inputMatKhau);
        btn_dangnhap = (Button) findViewById(R.id.btnLogin);
        tv_quenmk = (TextView)  findViewById(R.id.txtQuenMK);
        tv_dangky = (TextView)  findViewById(R.id.DangKy);

        tv_quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NhapThongTin_QuenMKActivity.class);
                startActivity(intent);
            }
        });

        tv_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NhapThongTinDK_Activity.class);
                startActivity(intent);
            }
        });
        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sdt = edt_tendangnhap.getText().toString().trim();
                String matkhau = edt_matkhau.getText().toString().trim();
                ApiService.apiService.layDSBookingToan()
                        .enqueue(new Callback<ArrayList<Booking>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                                bo = response.body();
                                Calendar calendar = Calendar.getInstance();
                                int ngayhientai = calendar.get(Calendar.DATE);
                                int thanghientai  = calendar.get(Calendar.MONTH) + 1;
                                int namhientai  = calendar.get(Calendar.YEAR);
                                int giohientai = 0;
                                int phuthientai = calendar.get(Calendar.MINUTE);
                                int td = calendar.get(Calendar.AM_PM);
                                if(td == 1){
                                    giohientai = calendar.get(Calendar.HOUR) + 12;
                                }
                                else giohientai =calendar.get(Calendar.HOUR);
                                for (int i = 0; i< bo.size(); i++){
                                    String strArrtmp[]=bo.get(i).getNgaychoi().split("/");
                                    int ngay=Integer.parseInt(strArrtmp[0]);
                                    int thang=Integer.parseInt(strArrtmp[1]);
                                    int nam=Integer.parseInt(strArrtmp[2]);

                                    String s = bo.get(i).getGiochoi()+"";
                                    String strArr[]=s.split(":");
                                    String sau=(strArr[1]);
                                    String ss[]= sau.split(" ");
                                    int phut = Integer.parseInt(ss[0]);
                                    String tdd = ss[1];
                                    Log.e("TDD",""+tdd);
                                    int gio = 0;
                                    if(tdd.equals("PM")==true ){
                                        gio = Integer.parseInt(strArr[0]) + 12;
                                    }
                                    else gio = Integer.parseInt(strArr[0]);
                                    if(ngay < ngayhientai && thang <= thanghientai && nam <= namhientai && Math.abs(gio*60+phut - giohientai*60+phuthientai) > 30 && bo.get(i).getTrangthai() != 4){
                                        Booking book = bo.get(i);
                                        ApiService.apiService.lay1BanBida(book.getIddiadiem(),book.getIdban())
                                                .enqueue(new Callback<BanBida>() {
                                                    @Override
                                                    public void onResponse(Call<BanBida> call, Response<BanBida> response) {
                                                        BanBida ban = response.body();
                                                        ban.setSoluong(ban.getSoluong()+ 1);
                                                        ApiService.apiService.capnhatBan(book.getIddiadiem(),book.getIdban(), ban)
                                                                .enqueue(new Callback<JsonObject>() {
                                                                    @Override
                                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<JsonObject> call, Throwable t) {

                                                                    }
                                                                });
                                                    }

                                                    @Override
                                                    public void onFailure(Call<BanBida> call, Throwable t) {

                                                    }
                                                });

                                        book.setTrangthai(4);
                                        ApiService.apiService.suaBookingXacthuc(book.getId(),book)
                                                .enqueue(new Callback<JsonObject>() {
                                                    @Override
                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                        Toast.makeText(MainActivity.this,"Tải dữ liệu thành công !",Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                                        Toast.makeText(MainActivity.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                        break;
                                    }

                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                            }
                        });

                if(sdt.length()==0)
                {
                    edt_tendangnhap.requestFocus();
                    edt_tendangnhap.setError("Không được để trống !");

                }else if(matkhau.length()==0)
                {
                    edt_matkhau.requestFocus();
                    edt_matkhau.setError("Không được để trống !");
                }
                else
                    ClickLogin();
            }
        });
    }

    private void ClickLogin(){
        String sdt = edt_tendangnhap.getText().toString().trim();
        String matkhau = edt_matkhau.getText().toString().trim();



        if (data == null || data.isEmpty()){
            return;
        }
        boolean isHaveUser = false;
        for(TaiKhoan tk : data){
            if (sdt.equals(tk.getSdt()) && matkhau.equals(tk.getMatkhau())){
                isHaveUser = true;
                break;
            }
        }
        if (isHaveUser){
            //vao login

            for (int i =0; i< data.size(); i++){
                if (data.get(i).getSdt().equals(sdt)==true){
                    quyen = data.get(i).getQuyen();
                    sodienthoai = data.get(i).getSdt();
                    sohuutiem = data.get(i).getSohuutiem();
                    break;
                }
            }
            if(quyen.equals("QTV")==true){
                Toast.makeText(MainActivity.this,"Đăng nhập thành công !",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Menu_QTV.class);
                intent.putExtra("number",sodienthoai);
                startActivity(intent);
            }
            if(quyen.equals("CT")==true && sohuutiem == 1){
                Toast.makeText(MainActivity.this,"Đăng nhập thành công !",Toast.LENGTH_SHORT).show();
                for(int i =0; i< data.size(); i++){
                    if( data.get(i).getSdt().equals(sodienthoai)){
                        ma = data.get(i).getId();
                        break;
                    }
                }
                Intent intent = new Intent(MainActivity.this, Menu_CT.class);
                intent.putExtra("number",sodienthoai);
                startActivity(intent);
            }
            if(quyen.equals("NV")==true){
                Toast.makeText(MainActivity.this,"Đăng nhập thành công !",Toast.LENGTH_SHORT).show();
                for(int i =0; i< data.size(); i++){
                    if( data.get(i).getSdt().equals(sodienthoai)){
                        ma = data.get(i).getId();
                        break;
                    }
                }
                Intent intent = new Intent(MainActivity.this, Menu_NV.class);
                intent.putExtra("number",sodienthoai);
                startActivity(intent);
            }
            if(quyen.equals("KH")==true){
                Toast.makeText(MainActivity.this,"Đăng nhập thành công !",Toast.LENGTH_SHORT).show();
                for(int i =0; i< data.size(); i++){
                    if( data.get(i).getSdt().equals(sodienthoai)){
                        ma = data.get(i).getId();
                        break;
                    }
                }
                Intent intent = new Intent(MainActivity.this, Menu_KH.class);
                intent.putExtra("number",sodienthoai);
                startActivity(intent);
            }

        }else Toast.makeText(MainActivity.this,"Đăng nhập thất bại, vui lòng kiểm tra lại !",Toast.LENGTH_SHORT).show();
    }

    private  void getListUser(){
        ApiService.apiService.layDSTaiKhoan()
                .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                        data = response.body();

                    }

                    @Override
                    public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
