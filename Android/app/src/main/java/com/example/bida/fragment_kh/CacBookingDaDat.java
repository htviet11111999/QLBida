package com.example.bida.fragment_kh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.bida.Adapter.CacBookingDatCuaKH;
import com.example.bida.Adapter.QuanLyBanBidaAdapter;
import com.example.bida.Adapter.QuanLyDSBookingAdapter;
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
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.example.bida.fragment_ct.Home_CT;
import com.google.android.gms.common.api.Api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CacBookingDaDat extends ListFragment {
    ArrayList<Booking> booking = new ArrayList<>();
    ArrayList<Booking> bookingchuaxacnhan = new ArrayList<>();
    public static Booking b;
    CacBookingDatCuaKH adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cacbookingdadat,container, false);
        ApiService.apiService.layDSBookingToan()
                .enqueue(new Callback<ArrayList<Booking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                        booking = response.body();
                        Calendar calendar = Calendar.getInstance();
                        int ngay = calendar.get(Calendar.DATE);
                        int thang  = calendar.get(Calendar.MONTH) + 1;
                        int nam  = calendar.get(Calendar.YEAR);
                        int gio = 0;
                        int phut = calendar.get(Calendar.MINUTE);
                        int td = calendar.get(Calendar.AM_PM);
                        if(td == 1){
                            gio = calendar.get(Calendar.HOUR) + 12;
                        }
                        else gio =calendar.get(Calendar.HOUR);
                        for (int i = 0; i< booking.size(); i++){
                            if (booking.get(i).getIdkhachhang() == Menu_KH.makhachhang && booking.get(i).getTrangthai()==2){
                                String s=booking.get(i).getNgaychoi();
                                String strArrtmp[]=s.split("/");
                                int ngaydat=Integer.parseInt(strArrtmp[0]);
                                int thangdat=Integer.parseInt(strArrtmp[1]);
                                int namdat=Integer.parseInt(strArrtmp[2]);
                                String ss=booking.get(i).getGiochoi();
                                String strArr[]=ss.split(":");
                                String strArr1[]=strArr[1].split(" ");
                                int phutdat = Integer.parseInt(strArr1[0]);
                                String tdd = strArr1[1];
                                int giodat = 0;
                                if(tdd.equals("PM")==true ){
                                    giodat = Integer.parseInt(strArr[0]) + 12;
                                }
                                else giodat = Integer.parseInt(strArr[0]);
                                int thoigian = Math.abs(gio*60 + phut - giodat*60 - phutdat);
                                if (  (thangdat >= thang && namdat >= nam) || (ngaydat >= ngay && thangdat == thang && namdat >= nam)  || (ngaydat >= ngay && thangdat >= thang && namdat >= nam) || ( ngaydat == ngay && thangdat == thang && namdat == nam && thoigian >=30 ) ){
                                    bookingchuaxacnhan.add(booking.get(i));
                                }

                            }
                        }
                        adapter = new CacBookingDatCuaKH(getActivity(), R.layout.fragment_cacbookingdadat, bookingchuaxacnhan);
                        setListAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {

                    }
                });
        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), ChiTiet_BookingDaDat.class);
        b = bookingchuaxacnhan.get(position);
        intent.putExtra("id",bookingchuaxacnhan.get(position).getId());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }

}
