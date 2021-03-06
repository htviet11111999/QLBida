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
import com.example.bida.Model.CTPhieuNhap;
import com.example.bida.Model.Kho;
import com.example.bida.R;
import com.example.bida.fragment_nv.ChiTietPhieuNhap;
import com.example.bida.fragment_nv.QuanLyDSPhieuNhap;
import com.example.bida.fragment_nv.ThemCTPhieuNhap;
import com.example.bida.fragment_nv.ThongTin1PhieuNhap;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterCTPN extends ArrayAdapter<CTPhieuNhap> {
    Context context;
    int layoutResouredId;
    ChiTietPhieuNhapAdapter adapter = null;
    ArrayList<CTPhieuNhap> data = null;


    public AdapterCTPN(@NonNull Context context, int resource, @NonNull ArrayList<CTPhieuNhap> data) {
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
            convertView = layoutInflater.inflate(R.layout.adapter_ctpn_ct, null, true);
        }
        CTPhieuNhap ct = (CTPhieuNhap) getItem(position);
        String photoImage = ct.getHinhanh();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        ImageView imgns =(ImageView)convertView.findViewById(R.id.hinhctpnhap);
        imgns.setImageBitmap(theImage);
        ((TextView) convertView.findViewById(R.id.tenvatpham_ctpn)).setText(String.format("T??n v???t ph???m : %s", ct.getTenvatpham()));
        ((TextView) convertView.findViewById(R.id.soluong_ctpn)).setText(String.format("S??? l?????ng : %s", ct.getSoluong()));
        ((TextView) convertView.findViewById(R.id.dongia_ctpn)).setText(String.format("????n gi?? : %s", ct.getDongia()));
        return convertView;
    }

}
