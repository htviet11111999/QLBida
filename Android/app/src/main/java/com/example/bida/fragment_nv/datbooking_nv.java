package com.example.bida.fragment_nv;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bida.Adapter.ChiTietPhieuNhapAdapter;
import com.example.bida.Adapter.QuanLyBanBidaAdapter;
import com.example.bida.Adapter.datbanAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_KH;
import com.example.bida.Menu_NV;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.Model.LichSuNV;
import com.example.bida.R;
import com.example.bida.fragment_ct.ChiTietQuanLyBan;
import com.example.bida.fragment_ct.Home_CT;
import com.example.bida.fragment_ct.SuaBan;
import com.example.bida.fragment_kh.ChiTietDiaDiem_KH;
import com.example.bida.fragment_kh.DatBan_KH;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class datbooking_nv extends AppCompatActivity {
    private ListView lvDanhSach;
    Button btn_tieptuc,btnDate,btnTime;
    TextView txtDate,txtTime;
    ArrayList<BanBida> data = new ArrayList<>();
    ArrayList<BanBida> databantrong = new ArrayList<>();
    EditText edt_hotenkh,edt_sdt, edt_soluongnguoi;
    datbanAdapter adapter = null;
    public static BanBida ban;
    public static Booking b;
    Calendar cal;
    Date dateFinish;
    Date hourFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datbooking_nv);
        setControl();
        getDefaultInfor();
        addEventFormWidgets();
        setEvent();
    }

    public void setControl() {
        txtDate=(TextView) findViewById(R.id.themBooking_ngaychoi_nv);
        txtTime=(TextView) findViewById(R.id.themBooking_giochoi_nv);
        btnTime = (Button) findViewById(R.id.btntime_nv);
        btnDate = (Button) findViewById(R.id.btndate_nv);
        lvDanhSach = (ListView)findViewById(R.id.listDSbanBida_nv);
        edt_hotenkh = (EditText) findViewById(R.id.datBooking_hotenkh);
        edt_sdt = (EditText) findViewById(R.id.datBooking_sdt);
        edt_soluongnguoi = (EditText) findViewById(R.id.datBooking_soluongnguoi);
        btn_tieptuc = (Button) findViewById(R.id.btn_tieptuc_datban_nv);
        btn_tieptuc.getBackground().setAlpha(0);
        btn_tieptuc.setText("");
    }

    public void setEvent() {
        ApiService.apiService.layDSBanBida(QuanLyDSBooking.madiadiem)
                .enqueue(new Callback<ArrayList<BanBida>>() {
                    @Override
                    public void onResponse(Call<ArrayList<BanBida>> call, Response<ArrayList<BanBida>> response) {
                        data = response.body();
                        for (int i =0 ;i < data.size(); i++){
                            if (data.get(i).getSoluong() > 0){
                                databantrong.add(data.get(i));
                            }
                        }
                        adapter = new datbanAdapter(datbooking_nv.this, R.layout.booking_kh, databantrong);
                        lvDanhSach.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<BanBida>> call, Throwable t) {
                        Toast.makeText(datbooking_nv.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });

        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                btn_tieptuc.getBackground().setAlpha(255);
                ban=databantrong.get(i);
                btn_tieptuc.setText("TIẾP TỤC");
            }
        });
        btn_tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edt_hotenkh.getText().toString();
                String sdt = edt_sdt.getText().toString();
                String soluongnguoi = edt_soluongnguoi.getText().toString();
                if (hoten.length() == 0) {
                    edt_hotenkh.requestFocus();
                    edt_hotenkh.setError("Không được để trống !");
                }
                else if (sdt.length() == 0) {
                    edt_sdt.requestFocus();
                    edt_sdt.setError("Không được để trống !");
                }
                else if (soluongnguoi.length() == 0) {
                    edt_soluongnguoi.requestFocus();
                    edt_soluongnguoi.setError("Không được để trống !");
                } else if (Integer.valueOf(soluongnguoi) < 0) {
                    edt_soluongnguoi.requestFocus();
                    edt_soluongnguoi.setError("Số lượng người không hợp lệ !");
                }
                else {
                    b = new Booking();
                    b.setIdkhachhang(0);
                    b.setTenkhachhang(edt_hotenkh.getText().toString());
                    b.setSdt(edt_sdt.getText().toString());
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String date = df.format(Calendar.getInstance().getTime());
                    b.setNgay(date);
                    SimpleDateFormat dft= null;
                    dft =new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    //đưa lên giao diện
                    b.setGio(dft.format(cal.getTime()));
                    b.setTrangthai(0);
                    b.setIdnhanvien(QuanLyDSBooking.idnhanvientiem);
                    b.setTennhanvien(QuanLyDSBooking.hotennhanvien);
                    b.setTienthanhtoan(0);
                    b.setIddiadiem(QuanLyDSBooking.madiadiem);
                    b.setQrcode("");
                    b.setSlnguoi(Integer.valueOf(edt_soluongnguoi.getText().toString()));
                    b.setIdban(ban.getId());
                    b.setTenban(ban.getTenban());
                    b.setGiaban(ban.getGia());
                    b.setNgaychoi(txtDate.getText().toString());
                    b.setGiochoi(txtTime.getText().toString());
                    ApiService.apiService.themBooking(b)
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    BanBida b = ban;
                                    b.setSoluong(b.getSoluong() - 1);
                                    ApiService.apiService.capnhatBan(QuanLyDSBooking.madiadiem, b.getId(), b)
                                            .enqueue(new Callback<JsonObject>() {
                                                @Override
                                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                    Toast.makeText(datbooking_nv.this, "Cập nhật thông tin thành công !", Toast.LENGTH_SHORT).show();
                                                    String noidung = "Bạn đã thêm 1 booking ";
                                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                                    LichSuNV ls = new LichSuNV();
                                                    ls.setIdnhanvien(Menu_NV.manhanvien);
                                                    ls.setNoidung(noidung);
                                                    ls.setThoigian(thoigian);
                                                    LichSuNV.ThemLichSu(Menu_NV.manhanvien,ls);
                                                    Intent intent = new Intent(datbooking_nv.this, datvatpham_nv.class);
                                                    intent.putExtra("ma", b.getId());
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                                    Toast.makeText(datbooking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(datbooking_nv.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
    public void getDefaultInfor()
    {
        //lấy ngày hiện tại của hệ thống
        cal=Calendar.getInstance();
        SimpleDateFormat dft=null;
        //Định dạng ngày / tháng /năm
        dft=new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate=dft.format(cal.getTime());
        //hiển thị lên giao diện
        txtDate.setText(strDate);
        //Định dạng giờ phút am/pm
        dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
        String strTime=dft.format(cal.getTime());
        //đưa lên giao diện
        txtTime.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft=new SimpleDateFormat("HH:mm",Locale.getDefault());
        txtTime.setTag(dft.format(cal.getTime()));

        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        dateFinish=cal.getTime();
        hourFinish=cal.getTime();
    }
    public void addEventFormWidgets()
    {
        btnDate.setOnClickListener(new MyButton());
        btnTime.setOnClickListener(new MyButton());
    }
    private class MyButton implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btndate_nv:
                    showDatePickerDialog();
                    break;
                case R.id.btntime_nv:
                    showTimePickerDialog();
                    break;
            }
        }

    }
    /**
     * Hàm hiển thị DatePicker dialog
     */
    public void showDatePickerDialog()
    {
        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {
                //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                txtDate.setText(
                        (dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
                //Lưu vết lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish=cal.getTime();

            }
        };
        //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtDate.getText()+"";
        String strArrtmp[]=s.split("/");
        int ngay=Integer.parseInt(strArrtmp[0]);
        int thang=Integer.parseInt(strArrtmp[1])-1;
        int nam=Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic=new DatePickerDialog(
                datbooking_nv.this,
                callback, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }
    /**
     * Hàm hiển thị TimePickerDialog
     */
    public void showTimePickerDialog()
    {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                //Xử lý lưu giờ và AM,PM
                String s=hourOfDay +":"+minute;
                int hourTam=hourOfDay;
                if(hourTam>12)
                    hourTam=hourTam-12;
                txtTime.setText
                        (hourTam +":"+minute +(hourOfDay>12?" PM":" AM"));
                //lưu giờ thực vào tag
                txtTime.setTag(s);
                //lưu vết lại giờ vào hourFinish
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hourFinish=cal.getTime();

            }
        };
        //các lệnh dưới này xử lý ngày giờ trong TimePickerDialog
        //sẽ giống với trên TextView khi mở nó lên
        String s=txtTime.getTag()+"";
        String strArr[]=s.split(":");
        int gio=Integer.parseInt(strArr[0]);
        int phut=Integer.parseInt(strArr[1]);
        TimePickerDialog time=new TimePickerDialog(
                datbooking_nv.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }
}