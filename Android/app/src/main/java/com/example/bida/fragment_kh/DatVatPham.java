package com.example.bida.fragment_kh;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bida.Adapter.ChiTietPhieuNhapAdapter;
import com.example.bida.Adapter.datvatphamAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_KH;
import com.example.bida.Menu_NV;
import com.example.bida.Model.Booking;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.Model.Kho;
import com.example.bida.Model.LichSuKH;
import com.example.bida.Model.LichSuNV;
import com.example.bida.R;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatVatPham extends AppCompatActivity {
    private ListView lvDanhSach;
    Button btn_them, btn_hoanthanh;
    ArrayList<CTBooking> data = new ArrayList<>();
    ArrayList<Kho> kho = new ArrayList<>();
    Kho kh = new Kho();
    datvatphamAdapter adapter = null;
    public static Booking booking = ThemCTBooking_vatpham.booking;
    int idvatpham;
    int ma;
    int iddiadiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dat_vatpham_kh);
        Intent intent = getIntent();
        ma= intent.getIntExtra("ma", 0);

        setControl();
        setEvent();
    }

    public void setControl() {

        lvDanhSach = (ListView)findViewById(R.id.listCTBooking);
        btn_them = (Button) findViewById(R.id.ThemCTBooking);
        btn_hoanthanh = (Button) findViewById(R.id.HoanThanhThemCTBooking);
    }

    public void setEvent() {

        ApiService.apiService.layDSCTBooking(ma)
                .enqueue(new Callback<ArrayList<CTBooking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<CTBooking>> call, Response<ArrayList<CTBooking>> response) {
                        data = response.body();
                        adapter = new datvatphamAdapter(DatVatPham.this, R.layout.dat_vatpham_kh, data);
                        lvDanhSach.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<CTBooking>> call, Throwable t) {
                        Toast.makeText(DatVatPham.this,"G???i API th???t b???i !",Toast.LENGTH_SHORT).show();
                    }
                });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DatVatPham.this, ThemCTBooking_vatpham.class);
                startActivity(intent);
            }
        });
        btn_hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String noidung = String.valueOf(ma);
                QRCodeWriter writer = new QRCodeWriter();
                try {
                    BitMatrix bitMatrix = writer.encode(noidung, BarcodeFormat.QR_CODE, 512, 512);
                    int width = bitMatrix.getWidth();
                    int height = bitMatrix.getHeight();
                    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++) {
                        for (int y = 0; y < height; y++) {
                            bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                        }
                    }
                    Bitmap bitmap = bmp;
                    ByteArrayOutputStream bas = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,bas);
                    byte[] imBytes=bas.toByteArray();
                    String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
                    String result = "data:image/png;base64,"+encodedImage;
                    Booking n = DatBan_KH.boo;
                    n.setQrcode(result);
                    n.setGiochoithat("");
                    Log.e("id booking",""+ma);
                    ApiService.apiService.suaBooking(n.getIddiadiem(), ma, n)
                            .enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(DatVatPham.this,"???? c???p nh???t th??nh c??ng !",Toast.LENGTH_SHORT).show();
                                    String noidung = "B???n ???? ?????t l???ch ch??i th??nh c??ng";
                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                    LichSuKH ls = new LichSuKH();
                                    ls.setIdkhachhang(Menu_KH.makhachhang);
                                    ls.setNoidung(noidung);
                                    ls.setThoigian(thoigian);
                                    LichSuKH.ThemLichSu(Menu_KH.makhachhang,ls);
                                    Intent intent = new Intent(DatVatPham.this, QRCode.class);
                                    startActivity(intent);
                                }
                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {

                                }
                            });
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        lvDanhSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ApiService.apiService.layDSVatPham(ChiTietDiaDiem_KH.id)
                        .enqueue(new Callback<ArrayList<Kho>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                                kho = response.body();
                                for (int i =0 ; i< kho.size(); i++){
                                    if(kho.get(i).getTenvatpham().equals(data.get(pos).getTenvatpham())==true){
                                        kh = kho.get(i);
                                        idvatpham = kho.get(i).getId();
                                        iddiadiem = kho.get(i).getIddiadiem();
                                        break;
                                    }
                                }
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(DatVatPham.this);
                                builder1.setMessage("B???n ch???c ch???n mu???n x??a ch??? ?");
                                builder1.setCancelable(true);
                                builder1.setPositiveButton(
                                        "?????ng ??",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int i) {
                                                kh.setSoluong(kh.getSoluong() + data.get(pos).getSoluong());
                                                ApiService.apiService.capnhatVatPham(iddiadiem,idvatpham,kh)
                                                        .enqueue(new Callback<JsonObject>() {
                                                            @Override
                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                booking.setTienthanhtoan(booking.getTienthanhtoan()-data.get(pos).getDongia()* data.get(pos).getSoluong());
                                                                booking.setGiochoithat("");
                                                                ApiService.apiService.suaBooking(iddiadiem,data.get(pos).getIdbooking(),booking)
                                                                        .enqueue(new Callback<JsonObject>() {
                                                                            @Override
                                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                                ApiService.apiService.xoaCTBooking(data.get(pos).getId())
                                                                                        .enqueue(new Callback<JsonObject>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                                                Toast.makeText(DatVatPham.this, "X??a chi ti???t Booking th??nh c??ng !", Toast.LENGTH_SHORT).show();
                                                                                                ApiService.apiService.layDSCTBooking(ma)
                                                                                                        .enqueue(new Callback<ArrayList<CTBooking>>() {
                                                                                                            @Override
                                                                                                            public void onResponse(Call<ArrayList<CTBooking>> call, Response<ArrayList<CTBooking>> response) {
                                                                                                                data = response.body();
                                                                                                                adapter = new datvatphamAdapter(DatVatPham.this, R.layout.dat_vatpham_kh, data);
                                                                                                                lvDanhSach.setAdapter(adapter);
                                                                                                                adapter.notifyDataSetChanged();
                                                                                                            }
                                                                                                            @Override
                                                                                                            public void onFailure(Call<ArrayList<CTBooking>> call, Throwable t) {
                                                                                                                Toast.makeText(DatVatPham.this,"G???i API th???t b???i !",Toast.LENGTH_SHORT).show();
                                                                                                            }
                                                                                                        });
                                                                                            }

                                                                                            @Override
                                                                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                                                                Toast.makeText(DatVatPham.this, "G???i API th???t b???i !", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        });
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                                                            }
                                                                        });
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
                            @Override
                            public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {
                                Toast.makeText(DatVatPham.this, "G???i API th???t b???i !", Toast.LENGTH_SHORT).show();
                            }
                        });
                return false;
            }
        });
    }
}