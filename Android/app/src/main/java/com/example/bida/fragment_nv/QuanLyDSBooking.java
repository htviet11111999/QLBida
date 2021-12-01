package com.example.bida.fragment_nv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.bida.Adapter.QuanLyBanBidaAdapter;
import com.example.bida.Adapter.QuanLyDSBookingAdapter;
import com.example.bida.Adapter.QuanLyDSPhieuNhapAdapter;
import com.example.bida.Adapter.QuanLyDiaDiemAdapter;
import com.example.bida.Adapter.QuanLyKhoAdapter;
import com.example.bida.Adapter.QuanLyTaiKhoanAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.MainActivity;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_KH;
import com.example.bida.Menu_NV;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.Booking;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.Kho;
import com.example.bida.Model.NhanVien;
import com.example.bida.Model.PhieuNhap;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.example.bida.fragment_ct.Home_CT;
import com.example.bida.fragment_kh.ChiTietDiaDiem_KH;
import com.google.android.gms.common.api.Api;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyDSBooking extends ListFragment {
    ArrayList<Booking> b = new ArrayList<>();
    ArrayList<NhanVien> nv = new ArrayList<>();
    public static int madiadiem;
    public static int idnhanvientiem;
    public static String hotennhanvien;
    QuanLyDSBookingAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_nv,container, false);
        Button btn_them = (Button) view.findViewById(R.id.ThemBooking);
        ApiService.apiService.layDSNVToan()
                .enqueue(new Callback<ArrayList<NhanVien>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NhanVien>> call, Response<ArrayList<NhanVien>> response) {
                        nv = response.body();
                        for (int i =0; i< nv.size(); i++){
                            if (nv.get(i).getSdt().equals(Menu_NV.sdt)==true){
                                madiadiem = nv.get(i).getIddiadiem();
                                idnhanvientiem = nv.get(i).getId();
                                hotennhanvien = nv.get(i).getHoten();
                                ApiService.apiService.layDSBooking(madiadiem)
                                        .enqueue(new Callback<ArrayList<Booking>>() {
                                            @Override
                                            public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                                                b = response.body();
                                                adapter = new QuanLyDSBookingAdapter(getActivity(), R.layout.fragment_booking_nv, b);
                                                setListAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                            }
                                            @Override
                                            public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {
                                                Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                break;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ArrayList<NhanVien>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), datbooking_nv.class);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), chitiet_Booking_nv.class);
        intent.putExtra("id",b.get(position).getId());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
