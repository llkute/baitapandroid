package com.llneverland.appoderfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.llneverland.appoderfood.DAO.NhanVienDAO;

/**
 * Created by Windows 8.1 on 1/25/2017.
 */
public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edTenDangNhap, edMatKhau;
    Button btnDangKy, btnDongY;
    NhanVienDAO nhanVienDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap_layout);

        edTenDangNhap = (EditText) findViewById(R.id.edTenDangNhap);
        edMatKhau = (EditText) findViewById(R.id.edMatKhau);
        btnDongY = (Button) findViewById(R.id.btnDongY);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);

        nhanVienDAO = new NhanVienDAO(this);
        HienThiButtonDongYOrDangKy();

        btnDongY.setOnClickListener(this);
        btnDangKy.setOnClickListener(this);
    }

    public void HienThiButtonDongYOrDangKy(){
        boolean kiemtra= nhanVienDAO.KiemTraNhanVien();
        if(kiemtra){
            btnDongY.setVisibility(View.VISIBLE);
            btnDangKy.setVisibility(View.GONE);
        }else{
            btnDongY.setVisibility(View.GONE);
            btnDangKy.setVisibility(View.VISIBLE);
        }
    }

    public void btnDongY(){
        String sTenDangNhap=edTenDangNhap.getText().toString();
        String sMatKhau=edMatKhau.getText().toString();
        int kiemtra= nhanVienDAO.KiemTraDangNhap(sTenDangNhap,sMatKhau);
        int maquyen = nhanVienDAO.LayQuyenNhanVien(kiemtra);
        if(kiemtra != 0){
            SharedPreferences sharedPreferences =getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("maquyen",maquyen);
            editor.commit();

            Intent iTrangChu= new Intent(DangNhapActivity.this, TrangChuActivity.class);
            iTrangChu.putExtra("tendangnhap",sTenDangNhap);
            iTrangChu.putExtra("manhanvien",kiemtra);
            startActivity(iTrangChu);
            overridePendingTransition(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
        }else{
            Toast.makeText(DangNhapActivity.this, "That bai", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnDangKy(){
        Intent iDangKy= new Intent(DangNhapActivity.this, DangKyActivity.class);
        iDangKy.putExtra("landautien",1);
        startActivity(iDangKy);
    }

    @Override
    protected void onResume() {
        super.onResume();
        HienThiButtonDongYOrDangKy();
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.btnDongY:
                btnDongY();
                break;
            case R.id.btnDangKy:
                btnDangKy();
                break;
        }
    }
}
