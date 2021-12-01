package com.example.bida.fragment_kh;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_KH;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.LichSuCT;
import com.example.bida.Model.LichSuKH;
import com.example.bida.Model.LichSuQTV;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
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

public class SuaTTTK_KH extends AppCompatActivity {
    TaiKhoan tk;
    int id ;
    ImageView img_avatar , img_taihinh;
    EditText edt_hoten, edt_sdt, edt_diachi;
    String mk;
    Button btn_Hoanthanh, btn_Thoat;
    TextView  tv_quyen;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capnhattaikhoan);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        img_avatar = (ImageView) findViewById(R.id.updateavatar_main);
        img_taihinh = (ImageView) findViewById(R.id.update_avatar_main);
        edt_hoten = (EditText) findViewById(R.id.update_hoten_main);
        edt_sdt = (EditText) findViewById(R.id.update_sdt_main);
        edt_diachi = (EditText) findViewById(R.id.update_diachi_main);
        tv_quyen = (TextView) findViewById(R.id.tv_quyen_main);
        btn_Hoanthanh = (Button) findViewById(R.id.updateHoanthanh_main);
        btn_Thoat = (Button) findViewById(R.id.updateThoat_main);

        img_taihinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });


        ApiService.apiService.lay1TaiKhoan(id)
                .enqueue(new Callback<TaiKhoan>() {
                    @Override
                    public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                        tk = response.body();
                        edt_hoten.setText(tk.getHoten());
                        edt_sdt.setText(tk.getSdt());
                        edt_diachi.setText(tk.getDiachi());
                        mk = tk.getMatkhau();
                        String quyen = "";
                        if(tk.getQuyen().equals("QTV")){
                            quyen = "Quản trị viên";
                        }
                        else if(tk.getQuyen().equals("KH")){
                            quyen = "Khách hàng";
                        }else if(tk.getQuyen().equals("CT")){
                            quyen = "Chủ tiệm";
                        }
                        else if(tk.getQuyen().equals("NV")){
                            quyen = "Nhân viên";
                        }
                        tv_quyen.setText(quyen);
                        String photoImage = tk.getHinhanh();
                        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        img_avatar.setImageBitmap(theImage);
                    }

                    @Override
                    public void onFailure(Call<TaiKhoan> call, Throwable t) {
                        Toast.makeText(SuaTTTK_KH.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });

        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuaTTTK_KH.this, Menu_KH.class);
                intent.putExtra("number", Menu_KH.sdt);
                startActivity(intent);
            }
        });
        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edt_hoten.getText().toString().trim();
                String sdt = edt_sdt.getText().toString().trim();
                String diachi = edt_diachi.getText().toString();

                if(hoten.length()==0)
                {
                    edt_hoten.requestFocus();
                    edt_hoten.setError("Không được để trống !");

                }else if(sdt.length()==0)
                {
                    edt_sdt.requestFocus();
                    edt_sdt.setError("Không được để trống !");
                }else if(diachi.length()==0)
                {
                    edt_diachi.requestFocus();
                    edt_diachi.setError("Không được để trống !");
                }
                else{

                    TaiKhoan taiKhoan = getTaiKhoan();
                    ApiService.apiService.capnhatTaiKhoan(id,taiKhoan)
                            .enqueue(new Callback<JsonObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(SuaTTTK_KH.this, "Cập nhật thông tin thành công !", Toast.LENGTH_SHORT).show();
                                    String noidung = "Bạn đã cập nhật thông tin thành công";
                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                    LichSuKH ls = new LichSuKH();
                                    ls.setIdkhachhang(Menu_KH.makhachhang);
                                    ls.setNoidung(noidung);
                                    ls.setThoigian(thoigian);
                                    LichSuKH.ThemLichSu(Menu_KH.makhachhang,ls);
                                    Intent intent = new Intent(SuaTTTK_KH.this, Menu_KH.class);
                                    intent.putExtra("number",Menu_KH.sdt);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(SuaTTTK_KH.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
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
    private TaiKhoan getTaiKhoan() {
        TaiKhoan taikhoan = new TaiKhoan();
        taikhoan.setHoten(edt_hoten.getText().toString().trim());
        taikhoan.setSdt(edt_sdt.getText().toString().trim());
        taikhoan.setDiachi(edt_diachi.getText().toString());
        taikhoan.setMatkhau(mk);
        String quyen = "";
        if(tv_quyen.getText().toString().equals("Quản trị viên")){
            quyen = "QTV";
        }
        else if(tv_quyen.getText().toString().equals("Khách hàng")){
            quyen = "KH";
        }else if(tv_quyen.getText().toString().equals("Chủ tiệm")){
            quyen = "CT";
        }
        else if(tv_quyen.getText().toString().equals("Nhân viên")){
            quyen = "NV";
        }
        taikhoan.setQuyen(quyen);
        Bitmap bitmap = ((BitmapDrawable)img_avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,bas);
        byte[] imBytes=bas.toByteArray();
        String encodedImage = Base64.encodeToString(imBytes, Base64.DEFAULT);
        String result = "data:image/png;base64,"+encodedImage;
        taikhoan.setHinhanh(result);
        return taikhoan;
    }
}
