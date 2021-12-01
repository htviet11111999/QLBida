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
import com.example.bida.Model.LichSuNV;
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

public class QuanLyDSPhieuNhap extends ListFragment {
    ArrayList<PhieuNhap> pn = new ArrayList<>();
    ArrayList<NhanVien> nv = new ArrayList<>();
    public static int madiadiem;
    public static int idnhanvientiem;
    public static String hotennhanvien;
    QuanLyDSPhieuNhapAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dsphieunhap_nv,container, false);
        Button btn_them = (Button) view.findViewById(R.id.ThemPN);
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
                                ApiService.apiService.layDSPhieuNhap(madiadiem)
                                        .enqueue(new Callback<ArrayList<PhieuNhap>>() {
                                            @Override
                                            public void onResponse(Call<ArrayList<PhieuNhap>> call, Response<ArrayList<PhieuNhap>> response) {
                                                pn = response.body();
                                                adapter = new QuanLyDSPhieuNhapAdapter(getActivity(), R.layout.fragment_dsphieunhap_nv, pn);
                                                setListAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onFailure(Call<ArrayList<PhieuNhap>> call, Throwable t) {
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
                PhieuNhap p = new PhieuNhap();
                DateFormat df = new SimpleDateFormat("dd/MM/yyy");
                String date = df.format(Calendar.getInstance().getTime());
                p.setNgay(date);
                p.setIdnhanvien(idnhanvientiem);
                p.setTennhanvien(hotennhanvien);
                p.setIddiadiem(madiadiem);
                p.setTienthanhtoan(0);
                p.setTrangthai(0);
                ApiService.apiService.themPhieuNhap(p)
                        .enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                Toast.makeText(getActivity(), "Thêm phiếu nhập thành công !", Toast.LENGTH_SHORT).show();
                                String noidung = "Bạn đã thêm 1 phiếu nhập ";
                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                                String thoigian = df.format(Calendar.getInstance().getTime());
                                LichSuNV ls = new LichSuNV();
                                ls.setIdnhanvien(Menu_NV.manhanvien);
                                ls.setNoidung(noidung);
                                ls.setThoigian(thoigian);
                                LichSuNV.ThemLichSu(Menu_NV.manhanvien,ls);
                                ApiService.apiService.layDSPhieuNhap(madiadiem)
                                        .enqueue(new Callback<ArrayList<PhieuNhap>>() {
                                            @Override
                                            public void onResponse(Call<ArrayList<PhieuNhap>> call, Response<ArrayList<PhieuNhap>> response) {
                                                pn = response.body();
                                                adapter = new QuanLyDSPhieuNhapAdapter(getActivity(), R.layout.fragment_dsphieunhap_nv, pn);
                                                setListAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onFailure(Call<ArrayList<PhieuNhap>> call, Throwable t) {
                                                Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Toast.makeText(getActivity(), "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), ThongTin1PhieuNhap.class);
        intent.putExtra("id",pn.get(position).getId());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
