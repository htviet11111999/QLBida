package com.example.bida.fragment_ct;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.example.bida.Adapter.QuanLyDSBookingAdapter;
import com.example.bida.Adapter.QuanLyKhoAdapter;
import com.example.bida.Api.ApiService;
import com.example.bida.Menu_CT;
import com.example.bida.Model.Booking;
import com.example.bida.Model.Kho;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.Model.VatPhamGanHet;
import com.example.bida.R;
import com.example.bida.fragment_qtv.ChiTietQuanLyTaiKhoan;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeVatPhamSapHet extends Fragment {
    ArrayList<Kho> kho = new ArrayList<>();
    ArrayList<VatPhamGanHet> vpgh = new ArrayList<>();
    ArrayList<PieEntry> sl = new ArrayList<>();
    PieDataSet pieDataSet;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongke_vatphamganhet_ct,container, false);
        PieChart chart = (PieChart) view.findViewById(R.id.TK_vatphamsaphet);

        ApiService.apiService.layDSVatPham(Home_CT.d.getId())
                .enqueue(new Callback<ArrayList<Kho>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Kho>> call, Response<ArrayList<Kho>> response) {
                        kho = response.body();
                        for(int i = 0; i< kho.size(); i++){
                            if (kho.get(i).getSoluong() <= 10){
                                vpgh.add(new VatPhamGanHet(kho.get(i).getTenvatpham(),kho.get(i).getSoluong()));

                            }
                        }
                        for (int i = 0; i<vpgh.size(); i++){

                            sl.add(new PieEntry(vpgh.get(i).getSoluong(),vpgh.get(i).getTenvatpham()));
                        }
                        pieDataSet = new PieDataSet(sl,"Thống kê");
                        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        pieDataSet.setValueTextColor(Color.BLACK);
                        pieDataSet.setValueTextSize(10f);
                        PieData pieData = new PieData(pieDataSet);
                        chart.setData(pieData);
                        chart.getDescription().setEnabled(false);
                        chart.setCenterText("Vật phẩm gần hết");
                        chart.animate();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Kho>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }

}
