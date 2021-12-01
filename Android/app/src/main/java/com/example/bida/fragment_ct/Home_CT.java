package com.example.bida.fragment_ct;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.bida.Api.ApiService;
import com.example.bida.MainActivity;
import com.example.bida.Menu_QTV;
import com.example.bida.Model.DiaDiem;
import com.example.bida.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_CT extends Fragment {
    public ArrayList <DiaDiem> data = new ArrayList<>();
    MapView mMapView;
    private GoogleMap googleMap;
    public  static DiaDiem d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ApiService.apiService.layDSDiaDiem()
                .enqueue(new Callback<ArrayList<DiaDiem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<DiaDiem>> call, Response<ArrayList<DiaDiem>> response) {
                        data = response.body();
                        for(int i =0; i< data.size();i++){
                            if (data.get(i).getIdchu() == MainActivity.ma){
                                d = data.get(i);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<DiaDiem>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        View rootView = inflater.inflate(R.layout.fragment_home_ct, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.myMap_ct);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(@NonNull Marker marker) {
                            Intent intent = new Intent(getActivity(), ChiTietDiaDiem.class);
                            intent.putExtra("ma",d.getId());
                            startActivity(intent);
                            return false;
                        }
                    });

                    // For dropping a marker at a point on the Map
                    LatLng sydney = new LatLng(d.getKinhdo(), d.getVido());
                    googleMap.addMarker(new MarkerOptions().position(sydney).title(d.getTen()).snippet(d.getDiachi()));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(20).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
