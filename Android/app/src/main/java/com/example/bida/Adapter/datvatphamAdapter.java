package com.example.bida.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.bida.Api.ApiService;
import com.example.bida.Menu_NV;
import com.example.bida.Model.Booking;
import com.example.bida.Model.CTBooking;
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.Model.Kho;
import com.example.bida.R;
import com.example.bida.fragment_kh.ChiTietDiaDiem_KH;
import com.example.bida.fragment_kh.DatBan_KH;
import com.example.bida.fragment_kh.DatVatPham;
import com.example.bida.fragment_nv.ChiTietPhieuNhap;
import com.example.bida.fragment_nv.QuanLyDSBooking;
import com.example.bida.fragment_nv.QuanLyDSPhieuNhap;
import com.example.bida.fragment_nv.ThemCTPhieuNhap;
import com.example.bida.fragment_nv.ThongTin1PhieuNhap;
import com.example.bida.fragment_nv.chitiet_Booking_nv;
import com.example.bida.fragment_nv.datbooking_nv;
import com.example.bida.fragment_nv.datvatpham_nv;
import com.example.bida.fragment_nv.them_ctbooking_nv;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class datvatphamAdapter extends ArrayAdapter<CTBooking> {
    Context context;
    int layoutResouredId;
    ArrayList<CTBooking> data = null;

    public datvatphamAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CTBooking> data) {
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
            convertView = layoutInflater.inflate(R.layout.dong_ctbooking_kh, null, true);
        }
        ImageView imgns =(ImageView)convertView.findViewById(R.id.hinhctBooking);
        CTBooking ct = (CTBooking) getItem(position);
            String photoImage = ct.getHinhanh();
            photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
            byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            imgns.setImageBitmap(theImage);
        ((TextView) convertView.findViewById(R.id.tenvatpham_ctBooking)).setText(String.format("Tên vật phẩm : %s", ct.getTenvatpham()));
        ((TextView) convertView.findViewById(R.id.soluong_ctBooking)).setText(String.format("Số lượng : %s", ct.getSoluong()));
        ((TextView) convertView.findViewById(R.id.dongia_ctBooking)).setText(String.format("Đơn giá : %s", ct.getDongia()));

        return convertView;
    }

}
