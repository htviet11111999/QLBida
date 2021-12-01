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
import com.example.bida.fragment_ct.DanhSachBooking;
import com.example.bida.fragment_ct.DanhSachPhieuNhap;
import com.example.bida.fragment_ct.DanhSachVatPham;
import com.example.bida.fragment_ct.Home_CT;
import com.example.bida.fragment_ct.LichSu_CT;
import com.example.bida.fragment_ct.QuanLyBanBida;
import com.example.bida.fragment_ct.QuanLyNhanVien;
import com.example.bida.fragment_ct.ThayDoiMK;
import com.example.bida.fragment_ct.ThongKeVatPhamSapHet;
import com.example.bida.fragment_ct.ThongTinTK;
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

public class Menu_CT extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<TaiKhoan> data = new ArrayList<>();
    private static final int FRAGMENT_HOME= 0;
    private static final int FRAGMENT_QLBAN= 1;
    private static final int FRAGMENT_QLNV= 2;
    private static final int FRAGMENT_DSVP= 3;
    private static final int FRAGMENT_DSPN= 4;
    private static final int FRAGMENT_DSB= 5;
    private static final int FRAGMENT_TK= 6;
    private static final int FRAGMENT_TDMK= 7;
    private static final int FRAGMENT_TK_VPGH= 8;
    private static final int FRAGMENT_LS= 9;
    public static String sdt;
    public static int machutiem;
    private NavigationView mNavidationView;

    private int mCurrentFragment = FRAGMENT_HOME;
    private DrawerLayout mDrawerLayout;
    ImageView img_avatar;
    TextView tv_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ct);

        Intent intent = getIntent();
        sdt = intent.getStringExtra("number");

        mNavidationView = findViewById(R.id.navigation_view_ct);
        img_avatar = mNavidationView.getHeaderView(0).findViewById(R.id.avatar_dangnhap_ct);
        tv_name = mNavidationView.getHeaderView(0).findViewById(R.id.hoten_dangnhap_ct);

        Toolbar toolbar = findViewById(R.id.toolbar_ct);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout_ct);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        mNavidationView.setNavigationItemSelectedListener(this);

        replaceFragment(new Home_CT());
        mNavidationView.getMenu().findItem(R.id.nav_home_ct).setChecked(true);

        HienThongTin();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home_ct)
        {
            if(mCurrentFragment != FRAGMENT_HOME){
                replaceFragment(new Home_CT());
                mCurrentFragment = FRAGMENT_HOME;
            }

        }else if(id == R.id.nav_qlbanBida_ct)
        {
            if(mCurrentFragment != FRAGMENT_QLBAN){
                replaceFragment(new QuanLyBanBida());
                mCurrentFragment = FRAGMENT_QLBAN;
            }

        }
        else if(id == R.id.nav_qlnhanvien_ct)
        {
            if(mCurrentFragment != FRAGMENT_QLNV){
                replaceFragment(new QuanLyNhanVien());
                mCurrentFragment = FRAGMENT_QLNV;
            }

        }
        else if(id == R.id.nav_danhsachvatpham)
        {
            if(mCurrentFragment != FRAGMENT_DSVP){
                replaceFragment(new DanhSachVatPham());
                mCurrentFragment = FRAGMENT_DSVP;
            }

        }
        else if(id == R.id.nav_danhsachphieunhap)
        {
            if(mCurrentFragment != FRAGMENT_DSPN){
                replaceFragment(new DanhSachPhieuNhap());
                mCurrentFragment = FRAGMENT_DSPN;
            }

        }
        else if(id == R.id.nav_danhsachBooking)
        {
            if(mCurrentFragment != FRAGMENT_DSB){
                replaceFragment(new DanhSachBooking());
                mCurrentFragment = FRAGMENT_DSB;
            }

        }
        else if(id == R.id.nav_thongtintk)
        {
            if(mCurrentFragment != FRAGMENT_TK){
                replaceFragment(new ThongTinTK());
                mCurrentFragment = FRAGMENT_TK;
            }

        }
        else if(id == R.id.nav_thaydoipass)
        {
            if(mCurrentFragment != FRAGMENT_TDMK){
                replaceFragment(new ThayDoiMK());
                mCurrentFragment = FRAGMENT_TDMK;
            }

        }
        else if(id == R.id.nav_thongkeSapHet)
        {
            if(mCurrentFragment != FRAGMENT_TK_VPGH){
                replaceFragment(new ThongKeVatPhamSapHet());
                mCurrentFragment = FRAGMENT_TK_VPGH;
            }

        }
        else if(id == R.id.nav_lichsu_ct)
        {
            if(mCurrentFragment != FRAGMENT_LS){
                replaceFragment(new LichSu_CT());
                mCurrentFragment = FRAGMENT_LS;
            }

        }
        else if (id == R.id.nav_exit_ct){
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
        transaction.replace(R.id.content_frame_ct,fragment);
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
                                machutiem = data.get(i).getId();
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
                        Toast.makeText(Menu_CT.this, "Gọi API thất bại !", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
