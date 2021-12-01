package com.example.bida.fragment_qtv;

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
import com.example.bida.Menu_QTV;
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

public class SuaTaiKhoan extends AppCompatActivity {
    TaiKhoan tk;
    int id ;
    ImageView img_avatar , img_taihinh;
    EditText edt_hoten, edt_sdt, edt_diachi, edt_matkhau;
    Button btn_Hoanthanh, btn_Thoat;
    TextView  tv_quyen;
    int REQUEST_CODE_FOLDER = 456;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capnhatthongtin_qltk);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        img_avatar = (ImageView) findViewById(R.id.updateavatar_qltk);
        img_taihinh = (ImageView) findViewById(R.id.update_avatar_qltk);
        edt_hoten = (EditText) findViewById(R.id.update_hoten);
        edt_sdt = (EditText) findViewById(R.id.update_sdt);
        edt_diachi = (EditText) findViewById(R.id.update_diachi);
        edt_matkhau = (EditText) findViewById(R.id.update_matkhau);
        tv_quyen = (TextView) findViewById(R.id.tv_quyen);
        btn_Hoanthanh = (Button) findViewById(R.id.updateHoanthanh_qltk);
        btn_Thoat = (Button) findViewById(R.id.updateThoat_qltk);

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
                        edt_matkhau.setText(tk.getMatkhau());
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
                        Toast.makeText(SuaTaiKhoan.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });

        btn_Thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuaTaiKhoan.this, Menu_QTV.class);
                intent.putExtra("number",Menu_QTV.sdt);
                startActivity(intent);
            }
        });
        btn_Hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = edt_hoten.getText().toString().trim();
                String sdt = edt_sdt.getText().toString().trim();
                String diachi = edt_diachi.getText().toString();
                String matkhau = edt_matkhau.getText().toString().trim();

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
                }else if(matkhau.length()==0)
                {
                    edt_matkhau.requestFocus();
                    edt_matkhau.setError("Không được để trống !");
                }
                else{

                    TaiKhoan taiKhoan = getTaiKhoan();
                    ApiService.apiService.capnhatTaiKhoan(id,taiKhoan)
                            .enqueue(new Callback<JsonObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(SuaTaiKhoan.this, "Cập nhật thông tin thành công !", Toast.LENGTH_SHORT).show();
                                    String noidung = "Bạn đã cập nhật thông tin của tài khoản "+taiKhoan.getHoten();
                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                    LichSuQTV ls = new LichSuQTV();
                                    ls.setNoidung(noidung);
                                    ls.setThoigian(thoigian);
                                    LichSuQTV.ThemLichSu(ls);
                                    Intent intent = new Intent(SuaTaiKhoan.this, Menu_QTV.class);
                                    intent.putExtra("number",Menu_QTV.sdt);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(SuaTaiKhoan.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
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
        taikhoan.setMatkhau(edt_matkhau.getText().toString().trim());
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
