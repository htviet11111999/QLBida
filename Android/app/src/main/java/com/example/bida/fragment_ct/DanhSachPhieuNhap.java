package com.example.bida.fragment_ct;

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
import com.example.bida.Adapter.QuanLyDSPhieuNhapAdapter;
import com.example.bida.Adapter.QuanLyDiaDiemAdapter;
import com.example.bida.Adapter.QuanLyKhoAdapter;
import com.example.bida.Adapter.QuanLyTaiKhoanAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.MainActivity;
import com.example.bida.Menu_CT;
import com.example.bida.Menu_NV;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.Kho;
import com.example.bida.Model.NhanVien;
import com.example.bida.Model.PhieuNhap;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.example.bida.fragment_ct.Home_CT;
import com.google.android.gms.common.api.Api;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachPhieuNhap extends ListFragment {
    ArrayList<PhieuNhap> pn = new ArrayList<>();
    public static int madiadiem;
    QuanLyDSPhieuNhapAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.danhsachphieunhap_ct,container, false);
        ApiService.apiService.layDSPhieuNhap(Home_CT.d.getId())
                .enqueue(new Callback<ArrayList<PhieuNhap>>() {
                    @Override
                    public void onResponse(Call<ArrayList<PhieuNhap>> call, Response<ArrayList<PhieuNhap>> response) {
                        pn = response.body();
                        adapter = new QuanLyDSPhieuNhapAdapter(getActivity(), R.layout.danhsachphieunhap_ct, pn);
                        setListAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PhieuNhap>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), CTPN.class);
        intent.putExtra("id",pn.get(position).getId());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
