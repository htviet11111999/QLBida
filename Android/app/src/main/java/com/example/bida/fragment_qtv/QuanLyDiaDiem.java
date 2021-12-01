package com.example.bida.fragment_qtv;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.bida.Adapter.QuanLyDiaDiemAdapter;
import com.example.bida.Adapter.QuanLyTaiKhoanAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.R;
import com.google.android.gms.common.api.Api;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLyDiaDiem extends ListFragment {
    ArrayList<DiaDiem> data = new ArrayList<>();
    QuanLyDiaDiemAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ApiService.apiService.layDSDiaDiem()
                .enqueue(new Callback<ArrayList<DiaDiem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<DiaDiem>> call, Response<ArrayList<DiaDiem>> response) {
                        data = response.body();
                        adapter = new QuanLyDiaDiemAdapter(getActivity(), R.layout.fragment_qldd_qtv, data);
                        setListAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<DiaDiem>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        View view = inflater.inflate(R.layout.fragment_qldd_qtv, container, false);
        SearchView searchView =(SearchView) view.findViewById(R.id.sv_qldd);
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
        Button btn_them = (Button) view.findViewById(R.id.ThemDD);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ThemDiaDiem.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), ChiTietQuanLyDiaDiem.class);
        intent.putExtra("id",data.get(position).getId());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
