package com.llneverland.appoderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.llneverland.appoderfood.DAO.BanAnDAO;

/**
 * Created by Windows 8.1 on 1/26/2017.
 */
public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edTenBanAn;
    Button btnDongY;

    BanAnDAO banAnDAO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thembanan_layout);

        edTenBanAn = (EditText) findViewById(R.id.edTenBanAn);
        btnDongY = (Button) findViewById(R.id.btnDongY);

        banAnDAO = new BanAnDAO(this);
        btnDongY.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String sTenBanAn= edTenBanAn.getText().toString();
        if(sTenBanAn != null || sTenBanAn.equals("")){
            boolean kiemtra = banAnDAO.ThemBanAn(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("ketquathem",kiemtra);
            setResult(Activity.RESULT_OK,intent);
            finish();
        }
    }
}
