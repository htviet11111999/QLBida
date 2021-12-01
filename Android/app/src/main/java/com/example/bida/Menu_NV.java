package com.example.bida;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bida.Api.ApiService;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.fragment_ct.Home_CT;
import com.example.bida.fragment_ct.QuanLyBanBida;
import com.example.bida.fragment_ct.QuanLyNhanVien;
import com.example.bida.fragment_nv.Home_NV;
import com.example.bida.fragment_nv.LichSu_NV;
import com.example.bida.fragment_nv.QuanLyDSBooking;
import com.example.bida.fragment_nv.QuanLyDSPhieuNhap;
import com.example.bida.fragment_nv.QuanLyKho;
import com.example.bida.fragment_nv.ThayDoiMatKhau_NV;
import com.example.bida.fragment_nv.ThongTinTK_NV;
import com.example.bida.fragment_qtv.Home;
import com.example.bida.fragment_qtv.LichSu_QTV;
import com.example.bida.fragment_qtv.QuanLyDiaDiem;
import com.example.bida.fragment_qtv.QuanLyTaiKhoan;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_NV extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<TaiKhoan> data = new ArrayList<>();
    private static final int FRAGMENT_HOME= 0;
    private static final int FRAGMENT_KHO= 1;
    private static final int FRAGMENT_DSPN= 2;
    private static final int FRAGMENT_DSBOOKING= 3;
    private static final int FRAGMENT_TTTK= 4;
    private static final int FRAGMENT_TDMK= 5;
    private static final int FRAGMENT_LS= 6;
    public static String sdt;
    public static int manhanvien;
    private NavigationView mNavidationView;

    private int mCurrentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;
    ImageView img_avatar;
    TextView tv_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_nv);

        Intent intent = getIntent();
        sdt = intent.getStringExtra("number");

        mNavidationView = findViewById(R.id.navigation_view_nv);
        img_avatar = mNavidationView.getHeaderView(0).findViewById(R.id.avatar_dangnhap_nv);
        tv_name = mNavidationView.getHeaderView(0).findViewById(R.id.hoten_dangnhap_nv);

        Toolbar toolbar = findViewById(R.id.toolbar_nv);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout_nv);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        mNavidationView.setNavigationItemSelectedListener(this);

        replaceFragment(new Home_NV());
        mNavidationView.getMenu().findItem(R.id.nav_home_nv).setChecked(true);

        HienThongTin();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home_nv)
        {
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new Home_NV());
                mCurrentFragment = FRAGMENT_HOME;
            }

        }
        else if(id == R.id.nav_vatpham_nv)
        {
            if(mCurrentFragment != FRAGMENT_KHO){
                replaceFragment(new QuanLyKho());
                mCurrentFragment = FRAGMENT_KHO;
            }

        }
        else if(id == R.id.nav_danhsachphieunhap_nv)
        {
            if(mCurrentFragment != FRAGMENT_DSPN){
                replaceFragment(new QuanLyDSPhieuNhap());
                mCurrentFragment = FRAGMENT_DSPN;
            }
        }
        else if(id == R.id.nav_danhsachBooking_nv)
        {
            if(mCurrentFragment != FRAGMENT_DSBOOKING){
                replaceFragment(new QuanLyDSBooking());
                mCurrentFragment = FRAGMENT_DSBOOKING;
            }
        }
        else if(id == R.id.nav_thongtintk_nv)
        {
            if(mCurrentFragment != FRAGMENT_TTTK){
                replaceFragment(new ThongTinTK_NV());
                mCurrentFragment = FRAGMENT_TTTK;
            }
        }
        else if(id == R.id.nav_thaydoipass_nv)
        {
            if(mCurrentFragment != FRAGMENT_TDMK){
                replaceFragment(new ThayDoiMatKhau_NV());
                mCurrentFragment = FRAGMENT_TDMK;
            }
        }
        else if(id == R.id.nav_lichsu_nv)
        {
            if(mCurrentFragment != FRAGMENT_LS){
                replaceFragment(new LichSu_NV());
                mCurrentFragment = FRAGMENT_LS;
            }
        }
        else if (id == R.id.nav_exit_nv){
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame_nv,fragment);
        transaction.commit();
    }

    private void HienThongTin(){
        ApiService.apiService.layDSTaiKhoan()
                .enqueue(new Callback<ArrayList<TaiKhoan>>() {
                    @Override
                    public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                        data = response.body();
                        for(int i =0; i< data.size();i++){
                            if (data.get(i).getSdt().equals(sdt)){
                                manhanvien = data.get(i).getId();
                                tv_name.setText(data.get(i).getHoten());
                                String photoImage = data.get(i).getHinhanh();
                                photoImage = photoImage.substring(photoImage.indexOf(",") + 1);
                                byte[] outImage = Base64.decode(photoImage.getBytes(), Base64.DEFAULT);
                                ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
                                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
                                img_avatar.setImageBitmap(theImage);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {
                        Toast.makeText(Menu_NV.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
