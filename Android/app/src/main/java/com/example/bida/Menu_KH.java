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
import com.example.bida.ChatBot.ChatMain;
import com.example.bida.Model.TaiKhoan;
import com.example.bida.fragment_kh.CacBookingDaDat;
import com.example.bida.fragment_kh.Home_KH;
import com.example.bida.fragment_kh.LichSu_KH;
import com.example.bida.fragment_kh.ThayDoiMK_KH;
import com.example.bida.fragment_kh.ThongTinTK_KH;
import com.example.bida.fragment_nv.ThongTinTK_NV;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Menu_KH extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<TaiKhoan> data = new ArrayList<>();
    private static final int FRAGMENT_HOME= 0;
    private static final int FRAGMENT_BOOKINGDADAT= 1;
    private static final int FRAGMENT_TTTK= 2;
    private static final int FRAGMENT_TDMK= 3;
    private static final int FRAGMENT_LS= 4;
    public static String sdt;
    public static String tenkhachhang;
    public static int makhachhang;
    private NavigationView mNavidationView;

    private int mCurrentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;
    ImageView img_avatar;
    TextView tv_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_kh);

        Intent intent = getIntent();
        sdt = intent.getStringExtra("number");

        mNavidationView = findViewById(R.id.navigation_view_kh);
        img_avatar = mNavidationView.getHeaderView(0).findViewById(R.id.avatar_dangnhap_kh);
        tv_name = mNavidationView.getHeaderView(0).findViewById(R.id.hoten_dangnhap_kh);

        Toolbar toolbar = findViewById(R.id.toolbar_kh);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout_kh);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        mNavidationView.setNavigationItemSelectedListener(this);

        replaceFragment(new Home_KH());
        mNavidationView.getMenu().findItem(R.id.nav_home_kh).setChecked(true);

        HienThongTin();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home_kh)
        {
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new Home_KH());
                mCurrentFragment = FRAGMENT_HOME;
            }

        }
        else if(id == R.id.nav_booking_dat)
        {
            if(mCurrentFragment != FRAGMENT_BOOKINGDADAT){
                replaceFragment(new CacBookingDaDat());
                mCurrentFragment = FRAGMENT_BOOKINGDADAT;
            }

        }
        else if(id == R.id.nav_thongtintk_kh)
        {
            if(mCurrentFragment != FRAGMENT_TTTK){
                replaceFragment(new ThongTinTK_KH());
                mCurrentFragment = FRAGMENT_TTTK;
            }

        }
        else if(id == R.id.nav_thaydoipass_kh)
        {
            if(mCurrentFragment != FRAGMENT_TDMK){
                replaceFragment(new ThayDoiMK_KH());
                mCurrentFragment = FRAGMENT_TDMK;
            }

        }
        else if(id == R.id.nav_lichsu_kh)
        {
            if(mCurrentFragment != FRAGMENT_LS){
                replaceFragment(new LichSu_KH());
                mCurrentFragment = FRAGMENT_LS;
            }

        }
        else if (id == R.id.nav_exit_kh){
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
        transaction.replace(R.id.content_frame_kh,fragment);
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
                                makhachhang = data.get(i).getId();
                                tenkhachhang = data.get(i).getHoten();
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
                        Toast.makeText(Menu_KH.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
