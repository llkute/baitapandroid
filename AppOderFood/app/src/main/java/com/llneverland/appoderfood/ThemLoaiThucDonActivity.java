package com.llneverland.appoderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.llneverland.appoderfood.DAO.LoaiMonAnDAO;

/**
 * Created by Windows 8.1 on 1/28/2017.
 */
public class ThemLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnDongYThemLoaiThucDon;
    EditText edTenLoaiThucDon;
    LoaiMonAnDAO loaiMonAnDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themloaithucdon_layout);

        btnDongYThemLoaiThucDon = (Button) findViewById(R.id.btnDongYThemLoaiThucDon);
        edTenLoaiThucDon= (EditText) findViewById(R.id.edTenLoaiThucDon);

        loaiMonAnDAO = new LoaiMonAnDAO(this);

        btnDongYThemLoaiThucDon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.btnDongYThemLoaiThucDon:
                String sTenLoaiThucDon  = edTenLoaiThucDon.getText().toString();
                if(sTenLoaiThucDon == null || sTenLoaiThucDon.equals("")){
                    Toast.makeText(this,R.string.nhaptenloaithucan,Toast.LENGTH_SHORT).show();
                }else{
                    boolean kiemtra = loaiMonAnDAO.ThemLoaiMonAn(sTenLoaiThucDon);
                    Intent iDuLieu = new Intent();
                    iDuLieu.putExtra("kiemtraloaithucdon",kiemtra);
                    setResult(Activity.RESULT_OK, iDuLieu) ;
                    finish();
                }
                break;
        }
    }
}
