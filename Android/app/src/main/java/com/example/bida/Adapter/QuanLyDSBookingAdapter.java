package com.example.bida.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.Model.Kho;
import com.example.bida.Model.PhieuNhap;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class QuanLyDSBookingAdapter extends ArrayAdapter<Booking> {
    Context context;
    int layoutResouredId;
    ArrayList<Booking> data = null;

    public QuanLyDSBookingAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Booking> data) {
        super(context, resource, data);
        this.context = context;
        this.layoutResouredId = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_dsbooking_nv, null, true);
        }
        Booking p = (Booking) getItem(position);
        ((TextView) convertView.findViewById(R.id.tenkhachhang_dsbooking)).setText(String.format("Khách hàng: %s", p.getTenkhachhang()));
        ((TextView) convertView.findViewById(R.id.sdt_dsbooking)).setText(String.format("Số điện thoại: %s", p.getSdt()));
        ((TextView) convertView.findViewById(R.id.ngay_dsbooking)).setText(String.format("Ngày: %s", p.getNgay()));
        ((TextView) convertView.findViewById(R.id.gio_dsbooking)).setText(String.format("Giờ: %s", p.getGio()));
        ((TextView) convertView.findViewById(R.id.hotennhanvien_dsbooking)).setText(String.format("Nhân viên lập: %s", p.getTennhanvien()));
        ((TextView) convertView.findViewById(R.id.tienthanhtoan_dsbooking)).setText(String.format("Tiền thanh toán: %s", p.getTienthanhtoan()));
        String tt ;
        if (p.getTrangthai() == 0 ) {
            tt = "Chưa thanh toán";
            ((TextView) convertView.findViewById(R.id.trangthai_dsbooking)).setTextColor(Color.parseColor("#D80C0C"));
        }
        else if (p.getTrangthai() == 2) {
            tt ="Chưa xác nhận";
            ((TextView) convertView.findViewById(R.id.trangthai_dsbooking)).setTextColor(Color.parseColor("#F3DB0B"));
        }
        else if (p.getTrangthai() == 3) {
            tt = "Đang chơi...";
            ((TextView) convertView.findViewById(R.id.trangthai_dsbooking)).setTextColor(Color.parseColor("#10DF19"));
        }
        else if (p.getTrangthai() == 4) {
            tt = "Đã hủy";
            ((TextView) convertView.findViewById(R.id.trangthai_dsbooking)).setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            tt = "Đã thanh toán";
            ((TextView) convertView.findViewById(R.id.trangthai_dsbooking)).setTextColor(Color.parseColor("#FFBB86FC"));
        }
        ((TextView) convertView.findViewById(R.id.trangthai_dsbooking)).setText(String.format("Trạng thái: %s",tt));
        return convertView;
    }

}
