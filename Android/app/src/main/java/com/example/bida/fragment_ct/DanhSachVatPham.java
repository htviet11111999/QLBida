package com.example.bida.fragment_ct;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import com.example.bida.Adapter.QuanLyKhoAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Model.Kho;
import com.example.bida.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachVatPham extends ListFragment {
    ArrayList<Kho> kho = new ArrayList<>();
    QuanLyKhoAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.danhsachvatpham_ct,container, false);
        SearchView searchView =(SearchView) view.findViewById(R.id.sv_dsvp);
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
        ApiService.apiService.layDSVatPham(Home_CT.d.getId())
                .enqueue(new Callback<ArrayList<Kho>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                        kho = response.body();
                        adapter = new QuanLyKhoAdapter(getActivity(), R.layout.danhsachvatpham_ct, kho);
                        setListAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }

}
