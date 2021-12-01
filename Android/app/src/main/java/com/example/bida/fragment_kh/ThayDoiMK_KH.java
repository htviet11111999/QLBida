package com.example.bida.fragment_kh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_KH;
import com.example.bida.Menu_NV;
import com.example.bida.Model.LichSuCT;
import com.example.bida.Model.LichSuKH;
import com.example.bida.Model.LichSuNV;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThayDoiMK_KH extends Fragment {
    TaiKhoan tk;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thaydoimatkhau_kh,container, false);
        EditText mkcu = (EditText) view.findViewById(R.id.inputMKmuondoi_kh);
        EditText mkmoi = (EditText) view.findViewById(R.id.inputMK_kh);
        EditText mkmoixacthuc = (EditText) view.findViewById(R.id.inputMKXacThuc_kh);
        Button hoanthanh = (Button) view.findViewById(R.id.btnHoanThanh_kh);

        hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiService.apiService.lay1TaiKhoan(Menu_KH.makhachhang)
                        .enqueue(new Callback<TaiKhoan>() {
                            @Override
                            public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                                tk = response.body();
                                String mk = mkcu.getText().toString().trim();
                                if (mk.equals(tk.getMatkhau())==false) {
                                    mkcu.requestFocus();
                                    mkmoixacthuc.requestFocus();
                                    mkmoi.requestFocus();
                                    mkcu.setError("Sai mật khẩu !");
                                }
                                else{
                                    String mkm = mkmoi.getText().toString().trim();
                                    String mkx = mkmoixacthuc.getText().toString().trim();
                                    if (mkm.length() == 0) {
                                        mkmoi.requestFocus();
                                        mkmoi.setError("Không được để trống !");
                                    } else if (mkx.length() == 0) {
                                        mkmoixacthuc.requestFocus();
                                        mkmoixacthuc.setError("Không được để trống !");
                                    } else if (mkm.equals(mkx) == false) {
                                        mkmoixacthuc.requestFocus();
                                        mkmoixacthuc.setError("Nhập lại đúng mật khẩu mới !");
                                    } else {
                                        tk.setMatkhau(mkm);
                                        ApiService.apiService.capnhatTaiKhoan(Menu_KH.makhachhang, tk)
                                                .enqueue(new Callback<JsonObject>() {
                                                    @RequiresApi(api = Build.VERSION_CODES.O)
                                                    @Override
                                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                        Toast.makeText(getActivity(), "Đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                                                        String noidung = "Bạn đã thay đổi mật khẩu ";
                                                        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                                        String thoigian = df.format(Calendar.getInstance().getTime());
                                                        LichSuKH ls = new LichSuKH();
                                                        ls.setIdkhachhang(Menu_KH.makhachhang);
                                                        ls.setNoidung(noidung);
                                                        ls.setThoigian(thoigian);
                                                        LichSuKH.ThemLichSu(Menu_KH.makhachhang,ls);
                                                    }

                                                    @Override
                                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                                        Toast.makeText(getActivity(), "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                }
                            }
                            @Override
                            public void onFailure(Call<TaiKhoan> call, Throwable t) {
                                Toast.makeText(getActivity(), "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return view;
    }

}
