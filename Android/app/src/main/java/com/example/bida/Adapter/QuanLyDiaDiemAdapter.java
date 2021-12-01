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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class QuanLyDiaDiemAdapter extends ArrayAdapter<DiaDiem> {
    Context context;
    int layoutResouredId;
    ArrayList<DiaDiem> data = null;
    ArrayList<DiaDiem> arrayList = null;

    public QuanLyDiaDiemAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DiaDiem> data) {
        super(context, resource, data);
        this.context = context;
        this.layoutResouredId = resource;
        this.data = data;
        this.arrayList = new ArrayList<DiaDiem>();
        this.arrayList.addAll(data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_qldd_qtv, null, true);
        }
        DiaDiem diaDiem = (DiaDiem) getItem(position);
        ((TextView) convertView.findViewById(R.id.tentiemm_qldd)).setText(String.format("Tên tiệm : %s", diaDiem.getTen()));
        ((TextView) convertView.findViewById(R.id.diachi_qldd)).setText(String.format("Địa chỉ : %s", diaDiem.getDiachi()));
        ((TextView) convertView.findViewById(R.id.hotenchu_qldd)).setText(String.format("Họ và tên chủ : %s", diaDiem.getHotenchu()));
        return convertView;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if(charText.length() == 0){
            data.addAll(arrayList);
        }
        else {
            for(DiaDiem tk : arrayList){
                if(tk.getTen().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    data.add(tk);
                }
            }
        }
        notifyDataSetChanged();
    }
}
