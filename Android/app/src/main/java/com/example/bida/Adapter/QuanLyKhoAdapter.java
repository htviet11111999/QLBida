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
import com.example.bida.Model.NhanVien;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class QuanLyKhoAdapter extends ArrayAdapter<Kho> {
    Context context;
    int layoutResouredId;
    ArrayList<Kho> data = null;
    ArrayList<Kho> arrayList = null;

    public QuanLyKhoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Kho> data) {
        super(context, resource, data);
        this.context = context;
        this.layoutResouredId = resource;
        this.data = data;
        this.arrayList = new ArrayList<Kho>();
        this.arrayList.addAll(data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_qlkho_nv, null, true);
        }
        Kho k = (Kho) getItem(position);
        String photoImage = k.getHinhanh();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        ImageView imgns =(ImageView)convertView.findViewById(R.id.hinhvatpham);
        imgns.setImageBitmap(theImage);
        ((TextView) convertView.findViewById(R.id.tenvatpham_qlk)).setText(String.format("Tên vật phẩm : %s", k.getTenvatpham()));
        ((TextView) convertView.findViewById(R.id.dongia_qlk)).setText(String.format("Đơn giá : %s", k.getDongia()));
        ((TextView) convertView.findViewById(R.id.soluong_qlk)).setText(String.format("Số lượng : %s", k.getSoluong()));
        return convertView;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if(charText.length() == 0){
            data.addAll(arrayList);
        }
        else {
            for(Kho tk : arrayList){
                if(tk.getTenvatpham().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    data.add(tk);
                }
            }
        }
        notifyDataSetChanged();
    }

}
