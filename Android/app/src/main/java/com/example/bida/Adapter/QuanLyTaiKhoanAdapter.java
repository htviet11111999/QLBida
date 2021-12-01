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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class QuanLyTaiKhoanAdapter extends ArrayAdapter<TaiKhoan> implements Filterable {

    Context context;
    int layoutResouredId;
    ArrayList<TaiKhoan> data = null;
    ArrayList<TaiKhoan> arrayList = null;

    public QuanLyTaiKhoanAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TaiKhoan> data) {
        super(context, resource, data);
        this.context = context;
        this.layoutResouredId = resource;
        this.data = data;
        this.arrayList = new ArrayList<TaiKhoan>();
        this.arrayList.addAll(data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_qltk_qtv, null, true);
        }
        TaiKhoan taiKhoan = (TaiKhoan) getItem(position);
        String photoImage = taiKhoan.getHinhanh();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        ImageView imgns =(ImageView)convertView.findViewById(R.id.hinhdaidien_qltk);
        imgns.setImageBitmap(theImage);
        ((TextView) convertView.findViewById(R.id.hoten_qltk)).setText(String.format("Họ và tên : %s", taiKhoan.getHoten()));
        ((TextView) convertView.findViewById(R.id.sdt_qltk)).setText(String.format("Số điện thoại : %s", taiKhoan.getSdt()));
        ((TextView) convertView.findViewById(R.id.diachi_qltk)).setText(String.format("Địa chỉ : %s", taiKhoan.getDiachi()));
        String quyen = "";
        if(taiKhoan.getQuyen().equals("QTV")){
            quyen = "Quản trị viên";
        }
        else if(taiKhoan.getQuyen().equals("KH")){
            quyen = "Khách hàng";
        }else if(taiKhoan.getQuyen().equals("CT")){
            quyen = "Chủ tiệm";
        }
        else if(taiKhoan.getQuyen().equals("NV")){
            quyen = "Nhân viên";
        }
        ((TextView) convertView.findViewById(R.id.quyen_qltk)).setText(String.format("Quyền : %s", quyen));
        return convertView;
    }

    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if(charText.length() == 0){
            data.addAll(arrayList);
        }
        else {
            for(TaiKhoan tk : arrayList){
                if(tk.getHoten().toLowerCase(Locale.getDefault())
                .contains(charText)){
                    data.add(tk);
                }
            }
        }
        notifyDataSetChanged();
    }

}

