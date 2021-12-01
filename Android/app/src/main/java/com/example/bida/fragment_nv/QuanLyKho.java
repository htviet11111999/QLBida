package com.example.bida.fragment_nv;

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

import com.example.bida.Adapter.QuanLyBanBidaAdapter;
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
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.example.bida.fragment_ct.Home_CT;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyKho extends ListFragment {
    ArrayList<Kho> kho = new ArrayList<>();
    ArrayList<NhanVien> nv = new ArrayList<>();
    public static int madiadiem;
    QuanLyKhoAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vatpham_nv,container, false);
        SearchView searchView =(SearchView) view.findViewById(R.id.sv_qlvp);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filter(s.trim());
                return false;
            }
        });
        Button btn_them = (Button) view.findViewById(R.id.ThemVP);
        ApiService.apiService.layDSNVToan()
                .enqueue(new Callback<ArrayList<NhanVien>>() {
                    @Override
                    public void onResponse(Call<ArrayList<NhanVien>> call, Response<ArrayList<NhanVien>> response) {
                        nv = response.body();
                        for (int i =0; i< nv.size(); i++){
                            if (nv.get(i).getSdt().equals(Menu_NV.sdt)==true){
                                madiadiem = nv.get(i).getIddiadiem();
                                ApiService.apiService.layDSVatPham(madiadiem)
                                        .enqueue(new Callback<ArrayList<Kho>>() {
                                            @Override
                                            public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                                                kho = response.body();
                                                adapter = new QuanLyKhoAdapter(getActivity(), R.layout.fragment_vatpham_nv, kho);
                                                setListAdapter(adapter);
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {
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
                Intent intent = new Intent(view.getContext(), ThemVatPham.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), ChiTietVatPham.class);
        intent.putExtra("id",kho.get(position).getId());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
