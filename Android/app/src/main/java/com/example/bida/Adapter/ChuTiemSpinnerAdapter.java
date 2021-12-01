package com.example.bida.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bida.Model.ChuTiemSpinner;
import com.example.bida.R;

import java.io.ByteArrayInputStream;
import java.util.List;


public class ChuTiemSpinnerAdapter extends BaseAdapter {
    Context context;
    int myLayout;
    List<ChuTiemSpinner> arrayList;

    public ChuTiemSpinnerAdapter(Context context, int myLayout, List<ChuTiemSpinner> arrayList) {
        this.context = context;
        this.myLayout = myLayout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout,null);

        TextView txtmachu = (TextView)convertView.findViewById(R.id.magctpinner);
        txtmachu.setText("Mã chủ tiệm: "+arrayList.get(position).getId());

        String photoImage = arrayList.get(position).getHinh();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        ImageView imgns =(ImageView)convertView.findViewById(R.id.hinhctspinner);
        imgns.setImageBitmap(theImage);

        return convertView;
    }
}
