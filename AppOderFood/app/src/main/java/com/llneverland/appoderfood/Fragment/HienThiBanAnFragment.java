package com.llneverland.appoderfood.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.llneverland.appoderfood.CustomAdapter.AdapterHienThiBanAn;
import com.llneverland.appoderfood.DAO.BanAnDAO;
import com.llneverland.appoderfood.DTO.BanAnDTO;
import com.llneverland.appoderfood.R;
import com.llneverland.appoderfood.SuaBanAn;
import com.llneverland.appoderfood.ThemBanAnActivity;
import com.llneverland.appoderfood.TrangChuActivity;

import java.util.List;

/**
 * Created by Windows 8.1 on 1/26/2017.
 */
public class HienThiBanAnFragment extends Fragment {

    public static int REQUEST_CODE_THEM = 111;
    public static int REQUEST_CODE_SUA = 16;
    GridView gvHienThiBanAn;
    List<BanAnDTO> listBanAn;
    BanAnDAO banAnDAO;
    AdapterHienThiBanAn adapterHienThiBanAn;
    SharedPreferences sharedPreferences;
    int maquyen;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hienthibanan_layout, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.banan);
        gvHienThiBanAn = (GridView) view.findViewById(R.id.gvHienThiBanAn);
        banAnDAO = new BanAnDAO(getActivity());

        sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        maquyen = sharedPreferences.getInt("maquyen",0);
        HienThiBanAn();

        if(maquyen ==1){
            registerForContextMenu(gvHienThiBanAn);
        }

        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int maban = listBanAn.get(vitri).getMaBan();
        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaBanAn.class);
                intent.putExtra("maban", maban);
                startActivityForResult(intent,REQUEST_CODE_SUA);
                break;
            case R.id.itXoa:
                boolean kiemtra = banAnDAO.XoaBanAn(maban);
                if(kiemtra){
                    HienThiBanAn();
                    Toast.makeText(getActivity(), getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(maquyen ==1){
            MenuItem itThemBanAn = menu.add(1, R.id.itThemBanAn, 1, R.string.thembanan);
            itThemBanAn.setIcon(R.drawable.thembanan);
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemBanAn:
                Intent iThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(iThemBanAn,REQUEST_CODE_THEM);
                getActivity().overridePendingTransition(R.anim.hieuung_activity_vao, R.anim.hieuung_activity_ra);
                break;
        }
        return true;
    }

    public void HienThiBanAn(){
        listBanAn = banAnDAO.LayTatCaBanAn();

        adapterHienThiBanAn= new AdapterHienThiBanAn(getActivity(),R.layout.custom_hienthibanan_layout, listBanAn);
        gvHienThiBanAn.setAdapter(adapterHienThiBanAn);
        adapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_THEM){
            if(resultCode == Activity.RESULT_OK){
                Intent intent = data;
                boolean kiemtra = intent.getBooleanExtra("ketquathem",false);
                if(kiemtra){
                    HienThiBanAn();
                    Toast.makeText(getActivity(), "Thanh cong them", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "That bai them", Toast.LENGTH_SHORT).show();
                }
            }
        }else if(requestCode == REQUEST_CODE_SUA){
            if(resultCode == Activity.RESULT_OK){
                boolean kiemtrasua = data.getBooleanExtra("kiemtrasua", false);
                if(kiemtrasua){
                    HienThiBanAn();
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.suathanhcong), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
