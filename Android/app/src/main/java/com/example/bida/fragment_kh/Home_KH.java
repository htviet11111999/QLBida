package com.example.bida.fragment_kh;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bida.Api.ApiService;
import com.example.bida.ChatBot.ChatMain;
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
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_KH extends Fragment {
    public ArrayList <DiaDiem> data = new ArrayList<>();
    MapView mMapView;
    SearchView searchView;
    private GoogleMap googleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ApiService.apiService.layDSDiaDiem()
                .enqueue(new Callback<ArrayList<DiaDiem>>() {
                    @Override
                    public void onResponse(Call<ArrayList<DiaDiem>> call, Response<ArrayList<DiaDiem>> response) {
                        data = response.body();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<DiaDiem>> call, Throwable t) {
                        Toast.makeText(getActivity(),"Gọi API thất bại !",Toast.LENGTH_SHORT).show();
                    }
                });
        View rootView = inflater.inflate(R.layout.fragment_home_kh, container, false);
        Button btn_chat = (Button) rootView.findViewById(R.id.Chat);
        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatMain.class);
                startActivity(intent);
            }
        });
        searchView =(SearchView) rootView.findViewById(R.id.sv_location_kh);
        mMapView = (MapView) rootView.findViewById(R.id.myMap_kh);
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
                for(int i=0 ;i<data.size();i++){
                    googleMap = mMap;
                    // For dropping a marker at a point on the Map
                    LatLng sydney = new LatLng(data.get(i).getKinhdo(), data.get(i).getVido());
                    googleMap.addMarker(new MarkerOptions().position(sydney).title(data.get(i).getTen()).snippet(data.get(i).getDiachi()));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(@NonNull Marker marker) {
                            for(int v =0; v<data.size();v++){
                                if (marker.getPosition().latitude == data.get(v).getKinhdo() && marker.getPosition().longitude == data.get(v).getVido()){
                                    Intent intent = new Intent(getActivity(), ChiTietDiaDiem_KH.class);
                                    intent.putExtra("id",data.get(v).getId());
                                    startActivity(intent);
                                    break;
                                }
                            }
                            return false;
                        }
                    });
                }

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchView.getQuery().toString();
                for(int i =0 ; i< data.size(); i++){
                    if(location.equals(data.get(i).getTen())){
                        LatLng sydney = new LatLng(data.get(i).getKinhdo(), data.get(i).getVido());
                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(20).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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
