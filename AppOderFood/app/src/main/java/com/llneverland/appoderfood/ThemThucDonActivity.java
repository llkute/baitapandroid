package com.llneverland.appoderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.llneverland.appoderfood.CustomAdapter.AdapterHienThiLoaiMonAn;
import com.llneverland.appoderfood.DAO.LoaiMonAnDAO;
import com.llneverland.appoderfood.DAO.MonAnDAO;
import com.llneverland.appoderfood.DTO.LoaiMonAnDTO;
import com.llneverland.appoderfood.DTO.MonAnDTO;

import java.util.List;

/**
 * Created by Windows 8.1 on 1/27/2017.
 */
public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {
    public static int REQUEST_CODE_THEMLOAITHUCDON = 113;
    public static int REQUEST_CODE_MOHINH = 123;
    ImageButton imThemLoaiThucDon;
    Spinner spinLoaiMonAn;
    ImageView imHinhThucDon;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    EditText edThemTenMonAn, edThemGiaTien;
    List<LoaiMonAnDTO> listLoaiMonAn;
    AdapterHienThiLoaiMonAn adapterHienThiLoaiMonAn;
    LoaiMonAnDAO loaiMonAnDAO;
    String sDuongDanHinh;

    MonAnDAO monAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themthucdon_layout);

        imThemLoaiThucDon = (ImageButton) findViewById(R.id.imThemLoaiThucDon);
        spinLoaiMonAn = (Spinner) findViewById(R.id.spinLoaiMonAn);
        imHinhThucDon = (ImageView) findViewById(R.id.imHinhThucDon);
        btnDongYThemMonAn = (Button) findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = (Button) findViewById(R.id.btnThoatThemMonAn);
        edThemTenMonAn = (EditText) findViewById(R.id.edThemTenMonAn);
        edThemGiaTien = (EditText) findViewById(R.id.edThemGiaTien);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        imThemLoaiThucDon.setOnClickListener(this);
        imHinhThucDon.setOnClickListener(this);
        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);

        HienThiLoaiThucDon();
    }

    public void HienThiLoaiThucDon(){
        listLoaiMonAn = loaiMonAnDAO.DanhSachCacLoaiMonAn();
        adapterHienThiLoaiMonAn = new AdapterHienThiLoaiMonAn(this, R.layout.custom_spinloaithucdon_layout, listLoaiMonAn);
        spinLoaiMonAn.setAdapter(adapterHienThiLoaiMonAn);
        adapterHienThiLoaiMonAn.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imThemLoaiThucDon:
                Intent iThemLoaiThucDon = new Intent(ThemThucDonActivity.this, ThemLoaiThucDonActivity.class);
                startActivityForResult(iThemLoaiThucDon, REQUEST_CODE_THEMLOAITHUCDON);
                break;
            case R.id.imHinhThucDon:
                Intent iMoHinh =new Intent();
                iMoHinh.setType("image/*");
                iMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iMoHinh,"Chọn Hình Ảnh"), REQUEST_CODE_MOHINH);
                break;
            case R.id.btnDongYThemMonAn:
                int vitri = spinLoaiMonAn.getSelectedItemPosition();
                int MaLoai= listLoaiMonAn.get(vitri).getMaLoai();
                String tenmonan = edThemTenMonAn.getText().toString();
                String giatien= edThemGiaTien.getText().toString();

                if(tenmonan !=null && giatien!= null && !tenmonan.equals("") && !giatien.equals("")){
                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giatien);
                    monAnDTO.setHinhAnh(sDuongDanHinh);
                    monAnDTO.setTenMonAn(tenmonan);
                    monAnDTO.setMaLoai(MaLoai);
                    boolean kiemtra = monAnDAO.ThemMonAn(monAnDTO);
                    if(kiemtra){
                        Toast.makeText(this,"Them Thanh Cong",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"Them That Bai",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,getResources().getString(R.string.loithemmonan),Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnThoatThemMonAn:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_THEMLOAITHUCDON){
            if(resultCode == Activity.RESULT_OK){
                Intent dulieu =  data;
                boolean kiemtra = dulieu.getBooleanExtra("kiemtraloaithucdon",false);
                if(kiemtra){
                    HienThiLoaiThucDon();
                    Toast.makeText(this,"Them Thanh Cong",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Them That Bai",Toast.LENGTH_SHORT).show();
                }
            }
        }else if(requestCode == REQUEST_CODE_MOHINH){
            if(resultCode == Activity.RESULT_OK){
                sDuongDanHinh = data.getData().toString();
                imHinhThucDon.setImageURI(data.getData());
            }
        }
    }
}
