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
import com.example.bida.Model.LichSuCT;
import com.example.bida.Model.LichSuQTV;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class LichSuCTAdapter extends ArrayAdapter<LichSuCT> {

    Context context;
    int layoutResouredId;
    ArrayList<LichSuCT> data = null;

    public LichSuCTAdapter(@NonNull Context context, int resource, @NonNull ArrayList<LichSuCT> data) {
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
            convertView = layoutInflater.inflate(R.layout.adapter_lichsu_ct, null, true);
        }
        LichSuCT lichSuCT = (LichSuCT) getItem(position);

        ((TextView) convertView.findViewById(R.id.noidung_ct)).setText(String.format("Nội dung : %s", lichSuCT.getNoidung()));
        ((TextView) convertView.findViewById(R.id.thoigian_ct)).setText(String.format("Thời gian : %s", lichSuCT.getThoigian()));
        return convertView;
    }

}
