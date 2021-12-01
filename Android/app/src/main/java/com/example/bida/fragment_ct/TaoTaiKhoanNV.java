package com.example.bida.fragment_ct;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import com.example.bida.Menu_QTV;
import com.example.bida.Model.LichSuCT;
import com.example.bida.Model.LichSuQTV;
import com.example.bida.Model.NhanVien;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.example.bida.fragment_qtv.ThemTaiKhoan;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaoTaiKhoanNV extends AppCompatActivity {
    ImageView img_avatar;
    TextView tv_hotennv, tv_sdt, tv_diachi;
    EditText edt_matkhau;
    Button btn_hoanthanh, btn_thoat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taotaikhoan_nhanvien_ct);

        img_avatar = (ImageView) findViewById(R.id.addhinhnhanvien_taotk);
        tv_hotennv = (TextView) findViewById(R.id.taotk_tennhanvien);
        tv_sdt = (TextView) findViewById(R.id.taotk_sdt);
        tv_diachi = (TextView) findViewById(R.id.add_diachi_taotk);
        edt_matkhau =(EditText) findViewById(R.id.taotk_matkhau);
        btn_thoat = (Button) findViewById(R.id.addThoat_taotk);
        btn_hoanthanh = (Button) findViewById(R.id.addHoanthanh_taotk);

        NhanVien n = ChiTietNhanVien.nv;
        String photoImage = n.getHinhanh();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        img_avatar.setImageBitmap(theImage);
        tv_hotennv.setText(n.getHoten());
        tv_sdt.setText(n.getSdt());
        tv_diachi.setText(n.getDiachi());

        btn_hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matkhau = edt_matkhau.getText().toString().trim();
                if(matkhau.length()==0)
                {
                    edt_matkhau.requestFocus();
                    edt_matkhau.setError("Không được để trống !");
                }
                else{
                    TaiKhoan taiKhoan = getTaiKhoan();
                    ApiService.apiService.taoTaiKhoan(taiKhoan)
                            .enqueue(new Callback<JsonObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    Toast.makeText(TaoTaiKhoanNV.this, "Thêm tài khoản nhân viên thành công !", Toast.LENGTH_SHORT).show();
                                    String noidung = "Bạn đã thêm tài khoản nhân viên mới "+taiKhoan.getHoten();
                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                    String thoigian = df.format(Calendar.getInstance().getTime());
                                    LichSuCT ls = new LichSuCT();
                                    ls.setIdchutiem(Menu_CT.machutiem);
                                    ls.setNoidung(noidung);
                                    ls.setThoigian(thoigian);
                                    LichSuCT.ThemLichSu(Menu_CT.machutiem,ls);
                                    Intent intent = new Intent(TaoTaiKhoanNV.this, Menu_CT.class);
                                    intent.putExtra("number",Menu_CT.sdt);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(TaoTaiKhoanNV.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

    }
    private TaiKhoan getTaiKhoan() {
        TaiKhoan taikhoan = new TaiKhoan();
        taikhoan.setHoten(tv_hotennv.getText().toString());
        taikhoan.setSdt(tv_sdt.getText().toString().trim());
        taikhoan.setDiachi(tv_diachi.getText().toString());
        taikhoan.setMatkhau(edt_matkhau.getText().toString().trim());
        taikhoan.setQuyen("NV");
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
