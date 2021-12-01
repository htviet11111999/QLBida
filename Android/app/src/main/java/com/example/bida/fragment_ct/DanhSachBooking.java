package com.example.bida.fragment_ct;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.bida.Adapter.QuanLyDSBookingAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Model.Booking;
import com.example.bida.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachBooking extends ListFragment {
    ArrayList<Booking> b = new ArrayList<>();
    QuanLyDSBookingAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.danhsachbooking_ct,container, false);
        ApiService.apiService.layDSBooking(Home_CT.d.getId())
                .enqueue(new Callback<ArrayList<Booking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Booking>> call, Response<ArrayList<Booking>> response) {
                        b = response.body();
                        adapter = new QuanLyDSBookingAdapter(getActivity(), R.layout.danhsachbooking_ct, b);
                        setListAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<ArrayList<Booking>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        Intent intent = new Intent(v.getContext(), CTB.class);
        intent.putExtra("id",b.get(position).getId());
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }
}
