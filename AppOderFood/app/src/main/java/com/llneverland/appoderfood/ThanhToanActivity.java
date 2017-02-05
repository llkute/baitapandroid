package com.llneverland.appoderfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.llneverland.appoderfood.CustomAdapter.AdapterHienThiThanhToan;
import com.llneverland.appoderfood.DAO.BanAnDAO;
import com.llneverland.appoderfood.DAO.GoiMonDAO;
import com.llneverland.appoderfood.DTO.ThanhToanDTO;

import java.util.List;

/**
 * Created by Windows 8.1 on 2/1/2017.
 */
public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener{
    GridView gridViewThanhToan;
    Button btnThanhToan,btnThoat;
    TextView txtTongTien;
    GoiMonDAO goiMonDAO;
    List<ThanhToanDTO> thanhToanDTOList;
    AdapterHienThiThanhToan adapterHienThiThanhToan;
    long tongtien= 0;
    BanAnDAO banAnDAO;
    int maban =0;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        gridViewThanhToan = (GridView) findViewById(R.id.gvThanhToan);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToan);
        btnThoat = (Button) findViewById(R.id.btnThoatThanhToan);

        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);
        fragmentManager = getSupportFragmentManager();

        maban = getIntent().getIntExtra("maban",0);
        if(maban != 0){
            HienThiThanhToan();
            for (int i=0; i < thanhToanDTOList.size() ; i++){
                int soluong = thanhToanDTOList.get(i).getSoluong();
                int giatien = thanhToanDTOList.get(i).getGiatien();

                tongtien += (soluong*giatien); // tongtien = tongtien + (soluong*giatien)
            }

            txtTongTien.setText(getResources().getString(R.string.tongcong) + tongtien);
        }



        btnThanhToan.setOnClickListener(this);
        btnThoat.setOnClickListener(this);

    }

    private void HienThiThanhToan(){
        int magoimon = (int) goiMonDAO.LayMaGoiMonTheoMaBan(maban,"false");

        thanhToanDTOList = goiMonDAO.LayDanhSachMonAnTheoMaGoiMon(magoimon);

        adapterHienThiThanhToan = new AdapterHienThiThanhToan(this, R.layout.custom_layout_thanhtoan,
                thanhToanDTOList);
        gridViewThanhToan.setAdapter(adapterHienThiThanhToan);
        adapterHienThiThanhToan.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThanhToan:
                boolean kiemtracapnhapban =banAnDAO.CapNhatTrangThaiBanTheoMa(maban , "false");
                boolean kiemtracapnhatgoimon = goiMonDAO.CapNhatTrangThaiGoiMonTheoMaBan(maban, "true");
                if(kiemtracapnhapban && kiemtracapnhatgoimon){
                    HienThiThanhToan();
                    Toast.makeText(this, getResources().getString(R.string.thanhtoanthanhcong),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "That bai",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnThoatThanhToan:
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
//                fragmentTransaction.replace(R.id.content, hienThiBanAnFragment);
//                fragmentTransaction.commit();
                finish();
                break;
        }
    }
}
