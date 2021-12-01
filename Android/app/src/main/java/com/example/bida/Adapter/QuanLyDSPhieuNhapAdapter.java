package com.example.bida.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.bida.Model.Kho;
import com.example.bida.Model.PhieuNhap;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class QuanLyDSPhieuNhapAdapter extends ArrayAdapter<PhieuNhap> {
    Context context;
    int layoutResouredId;
    ArrayList<PhieuNhap> data = null;

    public QuanLyDSPhieuNhapAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PhieuNhap> data) {
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
            convertView = layoutInflater.inflate(R.layout.adapter_dsphieunhap_nv, null, true);
        }
        PhieuNhap p = (PhieuNhap) getItem(position);
        ((TextView) convertView.findViewById(R.id.ngaylap_dsphieunhap)).setText(String.format("Ngày lập : %s", p.getNgay()));
        ((TextView) convertView.findViewById(R.id.hotennhanvien_dsphieunhap)).setText(String.format("Họ tên nhân viên lập : %s", p.getTennhanvien()));
        ((TextView) convertView.findViewById(R.id.tienthanhtoan_dsphieunhap)).setText(String.format("Tiền thanh toán : %s", p.getTienthanhtoan()));
        String tt ;
        if (p.getTrangthai() == 0 ) tt ="Chưa thanh toán";
        else tt = "Đã thanh toán";
        ((TextView) convertView.findViewById(R.id.trangthai_dsphieunhap)).setText(String.format("Trạng thái : %s",tt));
        return convertView;
    }

}
