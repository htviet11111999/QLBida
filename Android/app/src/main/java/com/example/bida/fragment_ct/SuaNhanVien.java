package com.example.bida.fragment_ct;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.LichSuCT;
import com.example.bida.Model.NhanVien;
import com.example.bida.R;
import com.example.bida.fragment_qtv.SuaTaiKhoan;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaNhanVien extends AppCompatActivity {
    ImageView img_avatar , img_taihinh;
    EditText edt_tennv, edt_sdt, edt_diachi;
    RadioButton rd_nam, rd_nu;
    Button btn_Hoanthanh, btn_Thoat;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capnhatthongtin_nhanvien_ct);

        img_avatar = (ImageView) findViewById(R.id.updatehinh_qlnhanvien);
        img_taihinh = (ImageView) findViewById(R.id.update_hinh_qlnhanvien);
        edt_tennv = (EditText) findViewById(R.id.update_tennhanvien);
        edt_sdt = (EditText) findViewById(R.id.update_sdt);
        edt_diachi = (EditText) findViewById(R.id.update_diachi_qlnv);
        rd_nam = (RadioButton) findViewById(R.id.radio_nam_update);
        rd_nu = (RadioButton) findViewById(R.id.radio_nu_update);
        btn_Hoanthanh = (Button) findViewById(R.id.updateHoanthanh_qlnhanvien);
        btn_Thoat = (Button) findViewById(R.id.updateThoat_qlnhanvien);

        img_taihinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        ApiService.apiService.lay1NhanVien(Home_CT.d.getId(), ChiTietNhanVien.id)
                .enqueue(new Callback<NhanVien>() {
                    @Override
                    public void onResponse(Call<NhanVien> call, Response<NhanVien> response) {
                        NhanVien nv = response.body();
                        edt_tennv.setText(nv.getHoten());
                        edt_sdt.setText(nv.getSdt());
                        edt_diachi.setText(nv.getDiachi());
                        String photoImage = nv.getHinhanh();
                        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        img_avatar.setImageBitmap(theImage);
                        if (nv.getGioitinh() ==1 ) rd_nam.setChecked(true);
                        else rd_nu.setChecked(true);
                    }

                    @Override
                    public void onFailure(Call<NhanVien> call, Throwable t) {
                        Toast.makeText(SuaNhanVien.this, "G???i API th???t b???i !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuaNhanVien.this, Menu_CT.class);
                intent.putExtra("number",Menu_CT.sdt);
                startActivity(intent);
            }
        });
        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tennv = edt_tennv.getText().toString();
                String sdt= edt_sdt.getText().toString().trim();
                String diachi = edt_diachi.getText().toString();

                if(tennv.length()==0)
                {
                    edt_tennv.requestFocus();
                    edt_tennv.setError("Kh??ng ???????c ????? tr???ng !");

                }else if(sdt.length()==0)
                {
                    edt_sdt.requestFocus();
                    edt_sdt.setError("Kh??ng ???????c ????? tr???ng !");
                }else if(diachi.length()==0)
                {
                    edt_diachi.requestFocus();
                    edt_diachi.setError("Kh??ng ???????c ????? tr???ng !");
                }
                else {
                    NhanVien v = getNhanVien();
                    ApiService.apiService.capnhatNhanVien(Home_CT.d.getId(), ChiTietNhanVien.id,v)
                            .enqueue(new Callback<JsonObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(SuaNhanVien.this, "C???p nh???t th??ng tin th??nh c??ng !", Toast.LENGTH_SHORT).show();
                                    String noidung = "B???n ???? c???p nh???t th??ng tin nh??n vi??n "+v.getHoten();
                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                    LichSuCT ls = new LichSuCT();
                                    ls.setIdchutiem(Menu_CT.machutiem);
                                    ls.setNoidung(noidung);
                                    ls.setThoigian(thoigian);
                                    LichSuCT.ThemLichSu(Menu_CT.machutiem,ls);
                                    Intent intent = new Intent(SuaNhanVien.this, Menu_CT.class);
                                    intent.putExtra("number",Menu_CT.sdt);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(SuaNhanVien.this, "G???i API th???t b???i !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                img_avatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private NhanVien getNhanVien (){
        NhanVien n = new NhanVien();
        n.setHoten(edt_tennv.getText().toString());
        n.setSdt(edt_sdt.getText().toString());
        n.setDiachi(edt_diachi.getText().toString());
        Bitmap bitmap = ((BitmapDrawable)img_avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bas);
        byte[] imBytes=bas.toByteArray();
        String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
        String result = "data:image/png;base64,"+encodedImage;
        n.setHinhanh(result);
        if(rd_nam.isChecked()) n.setGioitinh(1);
        else n.setGioitinh(0);
        return n;
    }
}
