package com.example.bida.fragment_qtv;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.ListFragment;

import com.example.bida.Adapter.LichSuQTVAdapter;
import com.example.bida.Adapter.QuanLyDiaDiemAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.DiaDiem;
import com.example.bida.Model.LichSuQTV;
import com.example.bida.R;
import com.google.gson.JsonObject;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LichSu_QTV extends ListFragment {
    ArrayList<LichSuQTV> data = new ArrayList<>();
    LichSuQTVAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ApiService.apiService.layDSLS_QTV()
                .enqueue(new Callback<ArrayList<LichSuQTV>>() {
                    @Override
                    public void onResponse(Call<ArrayList<LichSuQTV>> call, Response<ArrayList<LichSuQTV>> response) {
                        data = response.body();
                        if (data.size() == 0) Toast.makeText(getActivity(),"Hiện không có thông tin lịch sử !",Toast.LENGTH_SHORT).show();
                        adapter = new LichSuQTVAdapter(getActivity(), R.layout.fragment_lichsu_qtv, data);
                        setListAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<LichSuQTV>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        View view = inflater.inflate(R.layout.fragment_lichsu_qtv, container, false);
        Button btn_xoa = (Button) view.findViewById(R.id.xoaLS_qtv);
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            //Có điều kiện để xóa
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Bạn chắc chắn muốn xóa lịch sử chứ ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int i) {

                                ApiService.apiService.xoaLichSu_QTV()
                                        .enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                                Toast.makeText(getActivity(),"Xóa thành công !",Toast.LENGTH_SHORT).show();
                                                ApiService.apiService.layDSLS_QTV()
                                                        .enqueue(new Callback<ArrayList<LichSuQTV>>() {
                                                            @Override
                                                            public void onResponse(Call<ArrayList<LichSuQTV>> call, Response<ArrayList<LichSuQTV>> response) {
                                                                data = response.body();
                                                                if (data.size() == 0) Toast.makeText(getActivity(),"Hiện không có thông tin lịch sử !",Toast.LENGTH_SHORT).show();
                                                                adapter = new LichSuQTVAdapter(getActivity(), R.layout.fragment_lichsu_qtv, data);
                                                                setListAdapter(adapter);
                                                                adapter.notifyDataSetChanged();
                                                            }

                                                            @Override
                                                            public void onFailure(Call<ArrayList<LichSuQTV>> call, Throwable t) {
                                                                Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
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
        return view;
    }

}
