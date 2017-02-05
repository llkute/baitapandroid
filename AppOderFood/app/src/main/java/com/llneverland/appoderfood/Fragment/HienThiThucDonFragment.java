package com.llneverland.appoderfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.llneverland.appoderfood.CustomAdapter.AdapterHienThiLoaiMonAnThucDon;
import com.llneverland.appoderfood.DAO.LoaiMonAnDAO;
import com.llneverland.appoderfood.DTO.LoaiMonAnDTO;
import com.llneverland.appoderfood.R;
import com.llneverland.appoderfood.ThemThucDonActivity;
import com.llneverland.appoderfood.TrangChuActivity;

import java.util.List;

/**
 * Created by Windows 8.1 on 1/27/2017.
 */
public class HienThiThucDonFragment extends Fragment {

    GridView gvHienThiThucDon;
    List<LoaiMonAnDTO> loaiMonAnDTOs;
    LoaiMonAnDAO loaiMonAnDAO;
    AdapterHienThiLoaiMonAnThucDon adapterHienThiLoaiMonAnThucDon;
    FragmentManager fragmentManager;
    int maban;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hienthithucdon_layout,container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thucdon);
        setHasOptionsMenu(true);

        gvHienThiThucDon = (GridView) view.findViewById(R.id.gvHienThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonAnDAO =new LoaiMonAnDAO(getActivity());

        loaiMonAnDTOs = loaiMonAnDAO.DanhSachCacLoaiMonAn();
        adapterHienThiLoaiMonAnThucDon = new AdapterHienThiLoaiMonAnThucDon(getActivity(),R.layout.custom_hienthimonan_layout, loaiMonAnDTOs);
        gvHienThiThucDon.setAdapter(adapterHienThiLoaiMonAnThucDon);
        adapterHienThiLoaiMonAnThucDon.notifyDataSetChanged();

        Bundle bDuLieuThucDon = getArguments();
        if(bDuLieuThucDon != null){
            maban = bDuLieuThucDon.getInt("maban",0);
            Log.d("MabanThucDon",maban+"" );
        }

        gvHienThiThucDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int maloai = loaiMonAnDTOs.get(position).getMaLoai();

                HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("maloai", maloai);
                bundle.putInt("maban", maban);

                hienThiDanhSachMonAnFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content, hienThiDanhSachMonAnFragment).addToBackStack("hienthilai");
                fragmentTransaction.commit();

            }
        });

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthibanan", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itThemBanAn = menu.add(1, R.id.itThemThucDon, 1, R.string.themthucdon);
        itThemBanAn.setIcon(R.drawable.logodangnhap);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        switch (id){
            case R.id.itThemThucDon:
                Intent iThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                startActivity(iThemThucDon);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
