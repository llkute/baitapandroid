package com.llneverland.appoderfood.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.llneverland.appoderfood.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.llneverland.appoderfood.DAO.MonAnDAO;
import com.llneverland.appoderfood.DTO.MonAnDTO;
import com.llneverland.appoderfood.R;
import com.llneverland.appoderfood.SoLuongActivity;
import com.llneverland.appoderfood.TrangChuActivity;

import java.util.List;

/**
 * Created by Windows 8.1 on 1/30/2017.
 */
public class HienThiDanhSachMonAnFragment extends Fragment {
    GridView gridView;
    List<MonAnDTO> monAnDTOs;
    MonAnDAO monAnDAO;
    AdapterHienThiDanhSachMonAn adapterHienThiDanhSachMonAn;
    int maban;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hienthithucdon_layout, container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.danhsachmonan);
        gridView = (GridView) view.findViewById(R.id.gvHienThiThucDon);
        monAnDAO = new MonAnDAO(getActivity());

        Bundle bundle = getArguments();
        if(bundle != null){
            int maloai = bundle.getInt("maloai");
            maban = bundle.getInt("maban");


            monAnDTOs = monAnDAO.DanhSachMonAn(maloai);

            adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(),
                    R.layout.custom_hienthidanhsachmonan_layout, monAnDTOs);
            gridView.setAdapter(adapterHienThiDanhSachMonAn);
            adapterHienThiDanhSachMonAn.notifyDataSetChanged();

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(maban != 0){
                        Intent iSoLuong= new Intent(getActivity(), SoLuongActivity.class);
                        iSoLuong.putExtra("maban",maban);
                        iSoLuong.putExtra("mamonan", monAnDTOs.get(position).getMaMonAn());
                        startActivity(iSoLuong);
                    }
                }
            });
        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthilai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });


        return view;
    }
}
