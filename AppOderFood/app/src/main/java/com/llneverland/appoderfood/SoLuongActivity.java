package com.llneverland.appoderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.llneverland.appoderfood.DAO.GoiMonDAO;
import com.llneverland.appoderfood.DTO.ChiTietGoiMonDTO;

/**
 * Created by Windows 8.1 on 2/1/2017.
 */
public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnDongYSoLuongMonAn;
    EditText edSoLuongMonAn;

    GoiMonDAO goiMonDAO;

    int maban, mamonan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themsoluong_layout);

        btnDongYSoLuongMonAn = (Button) findViewById(R.id.btnDongYSoLuongMonAn);
        edSoLuongMonAn = (EditText) findViewById(R.id.edSoLuongMonAn);

        goiMonDAO = new GoiMonDAO(this);

        Intent intent = getIntent();
        maban = intent.getIntExtra("maban", 0);
        mamonan = intent.getIntExtra("mamonan", 0);
        Log.d("maban123", maban + "");

        btnDongYSoLuongMonAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this,"click!", Toast.LENGTH_SHORT).show();
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban, "false");
        Log.d("magoimon1324", magoimon + "");
        boolean  kiemtramonan = goiMonDAO.KiemTraMonAnDaTonTai(magoimon, mamonan);
        if(kiemtramonan){
            //cap nhat mon an da ton tai
            int soluongcu = goiMonDAO.LaySoLuongMonAnTheoMaGoiMon(magoimon, mamonan);
            int soluongthem = Integer.parseInt(edSoLuongMonAn.getText().toString());
            int soluongmoi = soluongcu + soluongthem;

            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMagoimon(magoimon);
            chiTietGoiMonDTO.setMamonan(mamonan);
            chiTietGoiMonDTO.setSoluong(soluongmoi);
            boolean kiemtracapnhapsoluong = goiMonDAO.CapNhatSoLuong(chiTietGoiMonDTO);
            Log.d("kiemtracapnhapsoluong", kiemtracapnhapsoluong +"");
            if(kiemtracapnhapsoluong){
                Log.d("soluongmonanmoi", chiTietGoiMonDTO.getSoluong()+"");
                Toast.makeText(this, getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
            }

        }else{
            //them moi mon an
            int soluonggoi =  Integer.parseInt(edSoLuongMonAn.getText().toString());
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMagoimon(magoimon);
            chiTietGoiMonDTO.setMamonan(mamonan);
            chiTietGoiMonDTO.setSoluong(soluonggoi);
            boolean kiemtrathemchitiet = goiMonDAO.ThemChiTietGoiMon(chiTietGoiMonDTO);
            if(kiemtrathemchitiet){
                Log.d("soluongmonanmoi", chiTietGoiMonDTO.getSoluong()+"");
                Toast.makeText(this, getResources().getString(R.string.themthanhcong),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
