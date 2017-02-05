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

import com.llneverland.appoderfood.DAO.BanAnDAO;

/**
 * Created by Windows 8.1 on 2/2/2017.
 */
public class SuaBanAn extends AppCompatActivity implements View.OnClickListener{
    EditText edSuaTenBanAn;
    Button btnDongYSuaBanAn;
    BanAnDAO banAnDAO;
    int maban =0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suabanan_layout);

        edSuaTenBanAn = (EditText) findViewById(R.id.edSuaTenBanAn);
        btnDongYSuaBanAn = (Button) findViewById(R.id.btnDongYSuaBanAn);

        banAnDAO = new BanAnDAO(this);

        maban = getIntent().getIntExtra("maban",0);

        btnDongYSuaBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String tenban = edSuaTenBanAn.getText().toString();
        if(!tenban.trim().equals("") || tenban.trim() != null){
            boolean kiemtrasua = banAnDAO.CapNhatTenBanTheoMa(maban, tenban);
            Intent intent = new Intent();
            intent.putExtra("kiemtrasua",kiemtrasua);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }else{
            Toast.makeText(this, getResources().getString(R.string.loithemmonan), Toast.LENGTH_SHORT).show();
        }   
    }
}
