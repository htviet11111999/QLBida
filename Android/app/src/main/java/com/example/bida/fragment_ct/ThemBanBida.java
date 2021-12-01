package com.example.bida.fragment_ct;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.MainActivity;
import com.example.bida.Menu_CT;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.LichSuCT;
import com.example.bida.Model.LichSuQTV;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemBanBida extends AppCompatActivity {
    ImageView img_avatar , img_taihinh;
    EditText edt_tenban, edt_gia, edt_soluong;
    Button btn_Hoanthanh, btn_Thoat;
    int REQUEST_CODE_FOLDER = 456;
    int ma;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thembanbida_ct);
        ma = Home_CT.d.getId();

        img_avatar = (ImageView) findViewById(R.id.addhinhBida_qlban);
        img_taihinh = (ImageView) findViewById(R.id.add_hinhbida_qlban);
        edt_tenban = (EditText) findViewById(R.id.add_tenban);
        edt_gia = (EditText) findViewById(R.id.add_gia);
        edt_soluong = (EditText) findViewById(R.id.add_soluong);
        btn_Hoanthanh = (Button) findViewById(R.id.addHoanthanh_qlban);
        btn_Thoat = (Button) findViewById(R.id.addThoat_qlban);

        img_taihinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });

        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemBanBida.this, Menu_CT.class);
                intent.putExtra("number",Menu_CT.sdt);
                startActivity(intent);
            }
        });

        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenban = edt_tenban.getText().toString().trim();
                String giaca = edt_gia.getText().toString().trim();
                String soluong = edt_soluong.getText().toString();

                if(tenban.length()==0)
                {
                    edt_tenban.requestFocus();
                    edt_tenban.setError("Không được để trống !");

                }else if(giaca.length()==0)
                {
                    edt_gia.requestFocus();
                    edt_gia.setError("Không được để trống !");
                }else if(soluong.length()==0)
                {
                    edt_soluong.requestFocus();
                    edt_soluong.setError("Không được để trống !");
                }
                else{
                    BanBida b = new BanBida();
                    b.setTenban(edt_tenban.getText().toString());
                    b.setGia( Integer.valueOf(edt_gia.getText().toString().trim()));
                    b.setSoluong(Integer.valueOf(edt_soluong.getText().toString().trim()));
                    b.setIddiadiem(ma);
                    Bitmap bitmap = ((BitmapDrawable)img_avatar.getDrawable()).getBitmap();
                    ByteArrayOutputStream bas = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,bas);
                    byte[] imBytes=bas.toByteArray();
                    String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
                    String result = "data:image/png;base64,"+encodedImage;
                    b.setHinhanh(result);
                    ApiService.apiService.themBanBida(b)
                            .enqueue(new Callback<JsonObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(ThemBanBida.this, "Thêm bàn bida thành công !", Toast.LENGTH_SHORT).show();
                                    String noidung = "Bạn đã thêm bàn Bida mới "+b.getTenban();
                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                    LichSuCT ls = new LichSuCT();
                                    ls.setIdchutiem(Menu_CT.machutiem);
                                    ls.setNoidung(noidung);
                                    ls.setThoigian(thoigian);
                                    LichSuCT.ThemLichSu(Menu_CT.machutiem,ls);
                                    Intent intent = new Intent(ThemBanBida.this, Menu_CT.class);
                                    intent.putExtra("number",Menu_CT.sdt);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(ThemBanBida.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
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
}
