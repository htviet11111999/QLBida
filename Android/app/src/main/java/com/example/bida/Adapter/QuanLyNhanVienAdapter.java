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

import com.example.bida.Model.NhanVien;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Locale;

public class QuanLyNhanVienAdapter extends ArrayAdapter<NhanVien> {

    Context context;
    int layoutResouredId;
    ArrayList<NhanVien> data = null;
    ArrayList<NhanVien> arrayList = null;

    public QuanLyNhanVienAdapter(@NonNull Context context, int resource, @NonNull ArrayList<NhanVien> data) {
        super(context, resource, data);
        this.context = context;
        this.layoutResouredId = resource;
        this.data = data;
        this.arrayList = new ArrayList<NhanVien>();
        this.arrayList.addAll(data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.adapter_qlnv_ct, null, true);
        }
        NhanVien nv= (NhanVien) getItem(position);
        String photoImage = nv.getHinhanh();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        ImageView imgns =(ImageView)convertView.findViewById(R.id.hinhnv_qlnv);
        imgns.setImageBitmap(theImage);
        ((TextView) convertView.findViewById(R.id.hoten_qlnv)).setText(String.format("Họ và tên : %s", nv.getHoten()));
        ((TextView) convertView.findViewById(R.id.sdt_qlnv)).setText(String.format("Số điện thoại : %s", nv.getSdt()));
        ((TextView) convertView.findViewById(R.id.diachi_qlnv)).setText(String.format("Địa chỉ : %s", nv.getDiachi()));
        String gt = "";
        if(nv.getGioitinh() == 1 ){
            gt = "Nam";
        }
        else gt = "Nữ";
        ((TextView) convertView.findViewById(R.id.gioitinh_qlnv)).setText(String.format("Giới tính : %s", gt));
        return convertView;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        data.clear();
        if(charText.length() == 0){
            data.addAll(arrayList);
        }
        else {
            for(NhanVien tk : arrayList){
                if(tk.getHoten().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    data.add(tk);
                }
            }
        }
        notifyDataSetChanged();
    }

}
