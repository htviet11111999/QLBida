package com.example.bida.fragment_kh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bida.Menu_CT;
import com.example.bida.Menu_KH;
import com.example.bida.R;
import com.example.bida.fragment_nv.SuaVatPham;

import java.io.ByteArrayInputStream;

public class QRCode extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hoanthanh_booking_online_kh);

        String photoImage = DatBan_KH.boo.getQrcode();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        ((ImageView) findViewById(R.id.qrcode)).setImageBitmap(theImage);
        ((Button) findViewById(R.id.thoat_hoanthanhdatbooking)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRCode.this, Menu_KH.class);
                intent.putExtra("number",Menu_KH.sdt);
                startActivity(intent);
            }
        });

    }
}
