package com.llneverland.appoderfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.llneverland.appoderfood.DAO.NhanVienDAO;
import com.llneverland.appoderfood.DAO.QuyenDAO;
import com.llneverland.appoderfood.DTO.NhanVienDTO;
import com.llneverland.appoderfood.DTO.QuyenDTO;
import com.llneverland.appoderfood.Fragment.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows 8.1 on 1/24/2017.
 */
public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener{
    EditText edTenDangNhap, edMatKhau, edNgaySinh, edCmnd;
    RadioGroup rdGioiTinh;
    Button btnDongY, btnThoat;
    TextView txtTieuDeDangKy;
    NhanVienDAO nhanVienDAO;
    RadioButton rdNam,rdNu;
    Spinner spinQuyen;
    int manv =0;
    int landautien=0;
    QuyenDAO quyenDAO;
    List<QuyenDTO> quyenDTOList;
    List<String> dataAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky_layout);

        edTenDangNhap = (EditText) findViewById(R.id.edTenDangNhap);
        edMatKhau= (EditText) findViewById(R.id.edMatKhau);
        edNgaySinh = (EditText) findViewById(R.id.edNgaySinh);
        edCmnd= (EditText) findViewById(R.id.edCmnd);
        rdGioiTinh = (RadioGroup) findViewById(R.id.rdGioiTinh);
        btnDongY = (Button) findViewById(R.id.btnDongY);
        btnThoat = (Button) findViewById(R.id.btnThoat);
        txtTieuDeDangKy = (TextView) findViewById(R.id.txtTieuDeDangKy);
        rdNam = (RadioButton) findViewById(R.id.rdNam);
        rdNu = (RadioButton) findViewById(R.id.rdNu);
        spinQuyen = (Spinner) findViewById(R.id.spinQuyen);

        btnDongY.setOnClickListener(this);
        btnThoat.setOnClickListener(this);
        edNgaySinh.setOnFocusChangeListener(this);

        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);



        manv = getIntent().getIntExtra("manhanvien", 0);
        landautien = getIntent().getIntExtra("landautien",0);

        quyenDTOList = quyenDAO.LayDanhSachQuyen();
        dataAdapter = new ArrayList<String>();

        for (int i=0; i<quyenDTOList.size();i++){
            String tenquyen = quyenDTOList.get(i).getTenQuyen();
            dataAdapter.add(tenquyen);
        }

        if(landautien == 0){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dataAdapter);
            spinQuyen.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else{
            spinQuyen.setVisibility(View.GONE);
        }


        if(manv != 0){
            txtTieuDeDangKy.setText(getResources().getString(R.string.capnhatnhanvien));
            btnDongY.setText("Cập Nhật");
            NhanVienDTO nhanVienDTO = nhanVienDAO.LayDanhSachNhanVienTheoMa(manv);

            edTenDangNhap.setText(nhanVienDTO.getTENDN());
            edMatKhau.setText(nhanVienDTO.getMATKHAU());
            edNgaySinh.setText(nhanVienDTO.getNGAYSINH());
            edCmnd.setText(String.valueOf(nhanVienDTO.getCMND()));

            String gioitinh = nhanVienDTO.getGIOITINH();
            if(gioitinh.equals("Nam")){
                rdNam.setChecked(true);
            }else{
                rdNu.setChecked(true);
            }

        }
    }

    public void DongYDangKy(){
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau= edMatKhau.getText().toString();
        String sGioiTinh ="";
        switch (rdGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }
        String sNgaySinh= edNgaySinh.getText().toString();
        int sCmnd = Integer.parseInt(edCmnd.getText().toString());

        if(sTenDangNhap == null || sTenDangNhap.equals("")){
            Toast.makeText(this,this.getResources().getString(R.string.nhaptendangnhap),Toast.LENGTH_SHORT).show();
        }else if(sMatKhau == null || sMatKhau.equals("")){
            Toast.makeText(this,this.getResources().getString(R.string.nhapmatkhau),Toast.LENGTH_SHORT).show();
        }else{
            NhanVienDTO nhanVienDTO= new NhanVienDTO();
            nhanVienDTO.setTENDN(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setGIOITINH(sGioiTinh);
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setCMND(sCmnd);

            if(landautien != 0){
                // gan quyen mac dinh la admin
                quyenDAO.ThemQuyen("Quản lý");
                quyenDAO.ThemQuyen("Nhân viên");
                nhanVienDTO.setMAQUYEN(1);
            }else{
                //gán quyền bằng quyền mà admin chọn khi tạo nhân viên
                int vitri = spinQuyen.getSelectedItemPosition();
                int maquyen = quyenDTOList.get(vitri).getMaQuyen();
                nhanVienDTO.setMAQUYEN(maquyen);
            }

            long kiemtra = nhanVienDAO.ThemNhanVien(nhanVienDTO);
            if(kiemtra != 0){
                Toast.makeText(this,this.getResources().getString(R.string.themnhanvienthanhcong),Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,this.getResources().getString(R.string.themnhanvienthatbai),Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void SuaNhanVien(){
        String sTenDangNhap = edTenDangNhap.getText().toString();
        String sMatKhau= edMatKhau.getText().toString();
        String sGioiTinh ="";
        switch (rdGioiTinh.getCheckedRadioButtonId()){
            case R.id.rdNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rdNu:
                sGioiTinh = "Nữ";
                break;
        }
        String sNgaySinh= edNgaySinh.getText().toString();
        int sCmnd = Integer.parseInt(edCmnd.getText().toString());
        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        nhanVienDTO.setMANV(manv);
        nhanVienDTO.setTENDN(sTenDangNhap);
        nhanVienDTO.setMATKHAU(sMatKhau);
        nhanVienDTO.setGIOITINH(sGioiTinh);
        nhanVienDTO.setNGAYSINH(sNgaySinh);
        nhanVienDTO.setCMND(sCmnd);
        boolean kiemtra = nhanVienDAO.SuaNhanVien(nhanVienDTO);
        if(kiemtra){
            Toast.makeText(this, getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case  R.id.btnDongY:
                if(manv !=0){
                    // code sua
                    SuaNhanVien();
                }else{
                    //code them
                    DongYDangKy();
                }
                ;break;
            case R.id.btnThoat:
                finish();
                ;break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id){
            case R.id.edNgaySinh:
                if(hasFocus) {
                    DatePickerFragment datePickerFragment= new DatePickerFragment();
                    datePickerFragment.show(getSupportFragmentManager(),"Ngày Sinh");
                    //xuat pop up
                }
                ;break;
        }
    }
}
