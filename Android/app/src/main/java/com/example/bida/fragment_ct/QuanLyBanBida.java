package com.example.bida.fragment_ct;

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
import com.example.bida.Adapter.QuanLyTaiKhoanAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.MainActivity;
import com.example.bida.Menu_CT;
import com.example.bida.Model.BanBida;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyBanBida extends ListFragment {
    ArrayList<BanBida> ban = new ArrayList<>();
    QuanLyBanBidaAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlban_ct, container, false);
        SearchView searchView =(SearchView) view.findViewById(R.id.sv_qlb);
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
        Button btn_them = (Button) view.findViewById(R.id.ThemBan);
            ApiService.apiService.layDSBanBida(Home_CT.d.getId())
                .enqueue(new Callback<ArrayList<BanBida>>() {
                    @Override
                    public void onResponse(Call<ArrayList<BanBida>> call, Response<ArrayList<BanBida>> response) {
                        ban = response.body();
                        adapter = new QuanLyBanBidaAdapter(getActivity(), R.layout.fragment_qlban_ct, ban);
                        setListAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<BanBida>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThemBanBida.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), ChiTietQuanLyBan.class);
        intent.putExtra("id",ban.get(position).getId());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
