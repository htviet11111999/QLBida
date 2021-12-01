package com.example.bida.fragment_kh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.bida.Adapter.QuanLyDSBookingAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_KH;
import com.example.bida.Menu_NV;
import com.example.bida.Model.Booking;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.example.bida.fragment_nv.SuaTTTK_NV;
import com.example.bida.fragment_qtv.ChiTietQuanLyTaiKhoan;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinTK_KH extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongtintaikhoan_cuakhachhang,container, false);
        ImageView img_avatar = (ImageView) view.findViewById(R.id.avatar_qltkkh);
        TextView tv_hoten = (TextView) view.findViewById(R.id.hoten_chitietQltkkh);
        TextView tv_sdt = (TextView) view.findViewById(R.id.sdt_chitietQltkkh);
        TextView tv_diachi = (TextView) view.findViewById(R.id.diachi_chitietQltkkh);
        TextView tv_quyen = (TextView) view.findViewById(R.id.quyen_chitietQltkkh);
        Button btn_sua = (Button) view.findViewById(R.id.sua_qltkkh);
        ApiService.apiService.lay1TaiKhoan(Menu_KH.makhachhang)
                .enqueue(new Callback<TaiKhoan>() {
                    @Override
                    public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                        TaiKhoan tk = response.body();
                        String photoImage = tk.getHinhanh();
                        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                        img_avatar.setImageBitmap(theImage);
                        tv_hoten.setText(String.format("Họ và tên : %s", tk.getHoten()));
                        tv_sdt.setText(String.format("Số điện thoại : %s", tk.getSdt()));
                        tv_diachi.setText(String.format("Địa chỉ : %s", tk.getDiachi()));
                        String quyen = "";
                        if (tk.getQuyen().equals("QTV")) {
                            quyen = "Quản trị viên";
                        } else if (tk.getQuyen().equals("KH")) {
                            quyen = "Khách hàng";
                        } else if (tk.getQuyen().equals("CT")) {
                            quyen = "Chủ tiệm";
                        } else if (tk.getQuyen().equals("NV")) {
                            quyen = "Nhân viên";
                        }
                        tv_quyen.setText(String.format("Quyền : %s", quyen));
                    }

                    @Override
                    public void onFailure(Call<TaiKhoan> call, Throwable t) {
                        Toast.makeText(getActivity(), "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SuaTTTK_KH.class);
                intent.putExtra("id",Menu_KH.makhachhang);
                startActivity(intent);
            }
        });
        return view;
    }

}
