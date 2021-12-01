package com.example.bida.fragment_kh;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bida.Adapter.datbanAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_KH;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.R;
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

public class DatBan_KH extends AppCompatActivity {
    private ListView lvDanhSach;
    Button btn_tieptuc,btnDate,btnTime;
    TextView txtDate,txtTime;
    ArrayList<BanBida> data = new ArrayList<>();
    ArrayList<BanBida> databantrong = new ArrayList<>();
    ArrayList<Booking> datlich = new ArrayList<>();
    ArrayList<Booking> bookingtoan = new ArrayList<>();
    EditText edt_soluongnguoi;
    datbanAdapter adapter = null;
    public static BanBida ban;
    public static Booking boo;
    public static int idbooking_main;
    Calendar cal;
    Date dateFinish;
    Date hourFinish;
    int ngaydat;
    int thangdat;
    int namdat;
    int giodat;
    int phutdat;
    int kt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_kh);
        setControl();
        getDefaultInfor();
        addEventFormWidgets();
        setEvent();
    }

    public void setControl() {
        lvDanhSach = (ListView)findViewById(R.id.listDSbanBida);
        edt_soluongnguoi = (EditText) findViewById(R.id.themBooking_soluongnguoi);
        txtDate=(TextView) findViewById(R.id.themBooking_ngaychoi);
        txtTime=(TextView) findViewById(R.id.themBooking_giochoi);
        btnTime = (Button) findViewById(R.id.btntime);
        btnDate = (Button) findViewById(R.id.btndate);
        btn_tieptuc = (Button) findViewById(R.id.btn_tieptuc_datban);
        btn_tieptuc.getBackground().setAlpha(0);
        btn_tieptuc.setText("");
    }

    public void setEvent() {
        ApiService.apiService.layDSBanBida(ChiTietDiaDiem_KH.id)
                .enqueue(new Callback<ArrayList<BanBida>>() {
                    @Override
                    public void onResponse(Call<ArrayList<BanBida>> call, Response<ArrayList<BanBida>> response) {
                        data = response.body();
                        for (int i =0 ;i < data.size(); i++){
                            if (data.get(i).getSoluong() > 0){
                                databantrong.add(data.get(i));
                            }
                        }
                        adapter = new datbanAdapter(DatBan_KH.this, R.layout.booking_kh, databantrong);
                        lvDanhSach.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<BanBida>> call, Throwable t) {
                        Toast.makeText(DatBan_KH.this,"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
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
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(Calendar.DATE);
                int thang  = calendar.get(Calendar.MONTH) + 1;
                int nam  = calendar.get(Calendar.YEAR);
                int gio = calendar.get(Calendar.HOUR);
                int phut = calendar.get(Calendar.MINUTE);
                String s=txtDate.getText()+"";
                String strArrtmp[]=s.split("/");
                ngaydat=Integer.parseInt(strArrtmp[0]);
                thangdat=Integer.parseInt(strArrtmp[1]);
                namdat=Integer.parseInt(strArrtmp[2]);

                String ss=txtTime.getTag()+"";
                String strArr[]=ss.split(":");
                giodat=Integer.parseInt(strArr[0]);
                phutdat=Integer.parseInt(strArr[1]);

                String soluongnguoi = edt_soluongnguoi.getText().toString();
                if (soluongnguoi.length() == 0) {
                    edt_soluongnguoi.requestFocus();
                    edt_soluongnguoi.setError("Không được để trống !");
                } else if (Integer.valueOf(soluongnguoi) < 0) {
                    edt_soluongnguoi.requestFocus();
                    edt_soluongnguoi.setError("Số lượng người không hợp lệ !");
                }else if(ngaydat < ngay || thangdat < thang || namdat < nam){
                    txtDate.requestFocus();
                    txtDate.setError("");
                    Toast.makeText(DatBan_KH.this, "Không được chọn ngày trong quá khứ !", Toast.LENGTH_SHORT).show();
                }
                else if ((ngaydat <= ngay && thangdat == thang && namdat == nam) && (giodat == gio && phutdat < phut || giodat < gio)){
                    txtTime.requestFocus();
                    txtTime.setError("");
                    Toast.makeText(DatBan_KH.this, "Không được chọn giờ trong quá khứ !", Toast.LENGTH_SHORT).show();
                }
                else {
                    ApiService.apiService.layDSBookingToan()
                            .enqueue(new Callback<ArrayList<Booking>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                                    bookingtoan = response.body();
                                    String strArrtmp1[]=txtDate.getText().toString().split("/");
                                    int ngaychoi=Integer.parseInt(strArrtmp1[0]);
                                    int thangchoi=Integer.parseInt(strArrtmp1[1]);
                                    int namchoi=Integer.parseInt(strArrtmp1[2]);

                                    String s1 = txtTime.getText().toString()+"";
                                    String strArr1[]=s1.split(":");
                                    String sau1=(strArr1[1]);
                                    String ss1[]= sau1.split(" ");
                                    int phutchoi = Integer.parseInt(ss1[0]);
                                    String tdd1 = ss1[1];
                                    int giochoi = 0;
                                    if(tdd1.equals("PM")==true ){
                                        giochoi = Integer.parseInt(strArr1[0]) + 12;
                                    }
                                    else giochoi = Integer.parseInt(strArr1[0]);
                                    for(int i =0; i< bookingtoan.size(); i++){
                                        String strArrtmp[]=bookingtoan.get(i).getNgaychoi().split("/");
                                        int ngay=Integer.parseInt(strArrtmp[0]);
                                        int thang=Integer.parseInt(strArrtmp[1]);
                                        int nam=Integer.parseInt(strArrtmp[2]);

                                        String s = bookingtoan.get(i).getGiochoi()+"";
                                        String strArr[]=s.split(":");
                                        String sau=(strArr[1]);
                                        String ss[]= sau.split(" ");
                                        int phut = Integer.parseInt(ss[0]);
                                        String tdd = ss[1];
                                        int gio = 0;
                                        if(tdd.equals("PM")==true ){
                                            gio = Integer.parseInt(strArr[0]) + 12;
                                        }
                                        else gio = Integer.parseInt(strArr[0]);
                                        if( bookingtoan.get(i).getSdt().equals(Menu_KH.sdt) && bookingtoan.get(i).getTrangthai()==2 && ngaychoi==ngay && thangchoi == thang && namchoi == nam && Math.abs(gio*60+phut - giochoi*60+phutchoi) <=30){
                                            Toast.makeText(DatBan_KH.this, "Bạn đã đặt lịch chơi trong thời điểm này rồi, vui lòng chơi theo lịch đã đặt trước hoặc hủy lịch đã đặt để đặt booking mới !", Toast.LENGTH_SHORT).show();
                                            kt =0;
                                            break;
                                        }
                                        else{
                                            kt = 1;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                                }
                            });
                      if(kt == 1){
                          boo = new Booking();
                    boo.setIdkhachhang(Menu_KH.makhachhang);
                    boo.setTenkhachhang(Menu_KH.tenkhachhang);
                    boo.setSdt(Menu_KH.sdt);
                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    String date = df.format(Calendar.getInstance().getTime());
                    boo.setNgay(date);
                    SimpleDateFormat dft= null;
                    dft =new SimpleDateFormat("hh:mm a",Locale.getDefault());
                    boo.setGio(dft.format(Calendar.getInstance().getTime()));
                    boo.setTrangthai(2);
                    boo.setIdnhanvien(0);
                    boo.setTennhanvien("");
                    boo.setTienthanhtoan(0);
                    boo.setIddiadiem(ChiTietDiaDiem_KH.id);
                    boo.setQrcode("");
                    boo.setSlnguoi(Integer.valueOf(edt_soluongnguoi.getText().toString()));
                    boo.setIdban(ban.getId());
                    boo.setTenban(ban.getTenban());
                    boo.setGiaban(ban.getGia());
                    boo.setNgaychoi(txtDate.getText().toString());
                    boo.setGiochoi(txtTime.getText().toString());
                    ApiService.apiService.themBooking(boo)
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    BanBida b = ban;
                                    b.setSoluong(b.getSoluong() - 1);
                                    ApiService.apiService.capnhatBan(ChiTietDiaDiem_KH.id, b.getId(), b)
                                            .enqueue(new Callback<JsonObject>() {
                                                @Override
                                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                    Toast.makeText(DatBan_KH.this, "Cập nhật thông tin thành công !", Toast.LENGTH_SHORT).show();
                                                    ApiService.apiService.layDSBooking(ChiTietDiaDiem_KH.id)
                                                            .enqueue(new Callback<ArrayList<Booking>>() {
                                                                @Override
                                                                public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                                                                    datlich = response.body();
                                                                    for (int i = 0; i < datlich.size(); i++) {
                                                                        if ((datlich.get(i).getSdt().equals(Menu_KH.sdt)) && (datlich.get(i).getTrangthai() == 2)) {
                                                                            idbooking_main = datlich.get(i).getId();
                                                                            kt = 0;
                                                                            Intent intent = new Intent(DatBan_KH.this, DatVatPham.class);
                                                                            intent.putExtra("ma",idbooking_main);
                                                                            startActivity(intent);
                                                                        }
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                                                                }
                                                            });
                                                }

                                                @Override
                                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                                    Toast.makeText(DatBan_KH.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(DatBan_KH.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });

                      }

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
        btnDate.setOnClickListener(new MyButtonEvent());
        btnTime.setOnClickListener(new MyButtonEvent());
    }
    private class MyButtonEvent implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btndate:
                    showDatePickerDialog();
                    break;
                case R.id.btntime:
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
                DatBan_KH.this,
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
                DatBan_KH.this,
                callback, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }
}