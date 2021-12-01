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
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class datbanAdapter extends ArrayAdapter<BanBida> {

    Context context;
    int layoutResouredId;
    ArrayList<BanBida> data = null;

    public datbanAdapter(@NonNull Context context, int resource, @NonNull ArrayList<BanBida> data) {
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
            convertView = layoutInflater.inflate(R.layout.adapter_datban_kh, null, true);
        }
        BanBida banBida = (BanBida) getItem(position);
        String photoImage = banBida.getHinhanh();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        ImageView imgns =(ImageView)convertView.findViewById(R.id.hinhbanBida_datban);
        imgns.setImageBitmap(theImage);

        //Còn thêm
        ((TextView) convertView.findViewById(R.id.tenban_datban)).setText(String.format("Tên bàn : %s", banBida.getTenban()));
        ((TextView) convertView.findViewById(R.id.gia_datban)).setText(String.format("Giá theo tiếng : %s", banBida.getGia()));
        ((TextView) convertView.findViewById(R.id.soluong_datban)).setText(String.format("Số lượng : %s", banBida.getSoluong()));
        return convertView;
    }
}
