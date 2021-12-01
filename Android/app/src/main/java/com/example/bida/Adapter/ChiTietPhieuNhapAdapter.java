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

public class ChiTietPhieuNhapAdapter extends ArrayAdapter<CTPhieuNhap> {
    Context context;
    ArrayList<Kho> kho = new ArrayList<>();
    int layoutResouredId;
    int idvatpham;
    int iddiadiem;
    ChiTietPhieuNhapAdapter adapter = null;
    Kho kh = new Kho();
    ArrayList<CTPhieuNhap> data = null;


    public ChiTietPhieuNhapAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CTPhieuNhap> data) {
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
            convertView = layoutInflater.inflate(R.layout.adapter_chitiet_phieunhap_nv, null, true);
        }
        CTPhieuNhap ct = (CTPhieuNhap) getItem(position);
        String photoImage = ct.getHinhanh();
        photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
        byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        ImageView imgns =(ImageView)convertView.findViewById(R.id.hinhctpn);
        imgns.setImageBitmap(theImage);
        ((TextView) convertView.findViewById(R.id.tenvatpham_ctphieunhap)).setText(String.format("Tên vật phẩm : %s", ct.getTenvatpham()));
        ((TextView) convertView.findViewById(R.id.soluong_ctphieunhap)).setText(String.format("Số lượng : %s", ct.getSoluong()));
        ((TextView) convertView.findViewById(R.id.dongia_ctphieunhap)).setText(String.format("Đơn giá : %s", ct.getDongia()));
        Button btn_xoa = (Button) convertView.findViewById(R.id.btn_xoaCT);
        if(ThongTin1PhieuNhap.phieuNhap.getTrangthai()==1) btn_xoa.setVisibility(View.GONE);
        ApiService.apiService.layDSVatPham(QuanLyDSPhieuNhap.madiadiem)
                .enqueue(new Callback<ArrayList<Kho>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                        kho = response.body();
                        for (int i =0 ; i< kho.size(); i++){
                            if(kho.get(i).getTenvatpham().equals(ct.getTenvatpham())==true){
                                kh = kho.get(i);
                                idvatpham = kho.get(i).getId();
                                iddiadiem = kho.get(i).getIddiadiem();
                                break;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {
                        Toast.makeText(getContext(), "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setMessage("Bạn chắc chắn muốn xóa chứ ?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {
                                kh.setSoluong(kh.getSoluong() - ct.getSoluong());
                                ApiService.apiService.capnhatVatPham(iddiadiem,idvatpham,kh)
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                ThongTin1PhieuNhap.phieuNhap.setTienthanhtoan(ThongTin1PhieuNhap.phieuNhap.getTienthanhtoan()-ct.getDongia()* ct.getSoluong());
                                                ApiService.apiService.suaPhieuNhap(QuanLyDSPhieuNhap.madiadiem,ThongTin1PhieuNhap.phieuNhap.getId(),ThongTin1PhieuNhap.phieuNhap)
                                                        .enqueue(new Callback<JsonObject>() {
                                                            @Override
                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                ApiService.apiService.xoaCTPhieuNhap(ct.getId())
                                                                        .enqueue(new Callback<JsonObject>() {
                                                                            @Override
                                                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                                                Toast.makeText(getContext(), "Xóa chi tiết phiếu nhập thành công !", Toast.LENGTH_SHORT).show();
                                                                                Intent intent = new Intent(getContext(), ChiTietPhieuNhap.class);
                                                                                context.startActivity(intent);
                                                                            }

                                                                            @Override
                                                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                                                Toast.makeText(getContext(), "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }

                                                            @Override
                                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                                            }
                                                        });
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                            }
                                        });
                            }
                        });

                builder1.setNegativeButton(
                        "Thoát",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        return convertView;
    }

}
