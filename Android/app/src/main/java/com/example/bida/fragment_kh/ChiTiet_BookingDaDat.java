package com.example.bida.fragment_kh;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_KH;
import com.example.bida.Menu_NV;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.Model.LichSuKH;
import com.example.bida.Model.LichSuNV;
import com.example.bida.R;
import com.example.bida.fragment_nv.QuanLyDSPhieuNhap;
import com.example.bida.fragment_nv.ThongTin1PhieuNhap;
import com.example.bida.fragment_nv.chitiet_Booking_nv;
import com.example.bida.fragment_nv.quetqrcode_xacthuc;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTiet_BookingDaDat extends AppCompatActivity {
    ImageView img_avatar;
    public  static  int id;
    TextView tv_kh, tv_ngaylap, tv_tienthanhtoan,tv_ngaychoi, tv_giochoi;
    Button btn_thoat, btn_huy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitiet_booking_dadat_kh);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        img_avatar = (ImageView) findViewById(R.id.avatar_dadat);
        tv_kh = (TextView) findViewById(R.id.tenkh_booking_dadat);
        tv_ngaylap = (TextView) findViewById(R.id.ngaylap_booking_dadat);
        tv_tienthanhtoan = (TextView) findViewById(R.id.tienthanhtoan_booking_dadat);
        tv_ngaychoi = (TextView) findViewById(R.id.ngaychoi_booking_dadat);
        tv_giochoi = (TextView) findViewById(R.id.giochoi_booking_dadat);
        btn_thoat = (Button) findViewById(R.id.thoat_booking_dadat);
        btn_huy = (Button) findViewById(R.id.huylichchoi);

        Booking booking = CacBookingDaDat.b;
        String photoImage = booking.getQrcode();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        img_avatar.setImageBitmap(theImage);
        tv_kh.setText(String.format("Kh??ch h??ng : %s", booking.getTenkhachhang()));
        tv_ngaylap.setText(String.format("Ng??y l???p : %s", booking.getNgay()));
        double vnd = Math.round(booking.getTienthanhtoan() * 10) / 10 ;

        // t???o 1 NumberFormat ????? ?????nh d???ng ti???n t??? theo ti??u chu???n c???a Vi???t Nam
        // ????n v??? ti???n t??? c???a Vi???t Nam l?? ?????ng
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(vnd);
        tv_tienthanhtoan.setText(String.format("Ti???n d???ch v??? : %s",str1 ));
        tv_ngaychoi.setText(String.format("Ng??y ch??i : %s", booking.getNgaychoi()));
        tv_giochoi.setText(String.format("Gi??? b???t ?????u ch??i : %s", booking.getGiochoi()));

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ChiTiet_BookingDaDat.this);
                builder1.setMessage("B???n ch???c ch???n mu???n h???y l???ch ch??i n??y ch??? ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "?????ng ??",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                Booking boo = CacBookingDaDat.b;
                                boo.setGiochoithat("");
                                boo.setTrangthai(4);
                                ApiService.apiService.lay1BanBidatheoID(boo.getIdban())
                                        .enqueue(new Callback<BanBida>() {
                                            @Override
                                            public void onResponse(Call<BanBida> call, Response<BanBida> response) {
                                                BanBida ban = response.body();
                                                ban.setSoluong(ban.getSoluong()+1);
                                                ApiService.apiService.capnhatBantheoID(ban.getId(),ban)
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
                                ApiService.apiService.suaBookingXacthuc(boo.getId(), boo)
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                Toast.makeText(ChiTiet_BookingDaDat.this, "H???y l???ch ch??i th??nh c??ng !", Toast.LENGTH_SHORT).show();
                                                String noidung = "B???n ???? h???y l???ch ch??i th??nh c??ng";
                                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                String thoigian = df.format(Calendar.getInstance().getTime());
                                                LichSuKH ls = new LichSuKH();
                                                ls.setIdkhachhang(Menu_KH.makhachhang);
                                                ls.setNoidung(noidung);
                                                ls.setThoigian(thoigian);
                                                LichSuKH.ThemLichSu(Menu_KH.makhachhang,ls);
                                                Intent intent = new Intent(ChiTiet_BookingDaDat.this, Menu_KH.class);
                                                intent.putExtra("number",Menu_KH.sdt);
                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                            }
                                        });
                            }
                        });

                builder1.setNegativeButton(
                        "Tho??t",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        btn_thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTiet_BookingDaDat.this, Menu_KH.class);
                intent.putExtra("number",Menu_KH.sdt);
                startActivity(intent);
            }
        });

    }
}
