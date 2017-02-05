package com.llneverland.appoderfood.Fragment;

import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import com.llneverland.appoderfood.CustomAdapter.AdapterHienThiNhanVien;
import com.llneverland.appoderfood.DAO.NhanVienDAO;
import com.llneverland.appoderfood.DTO.NhanVienDTO;
import com.llneverland.appoderfood.DangKyActivity;
import com.llneverland.appoderfood.R;
import com.llneverland.appoderfood.TrangChuActivity;

import java.util.List;

/**
 * Created by Windows 8.1 on 2/2/2017.
 */
public class HienThiNhanVienFragment extends Fragment{
    ListView listViewNhanVien;
    AdapterHienThiNhanVien adapterHienThiNhanVien;
    NhanVienDAO nhanVienDAO;
    List<NhanVienDTO> nhanVienDTOList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthinhanvien, container, false);
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.nhanvien);
        setHasOptionsMenu(true);

        listViewNhanVien = (ListView) view.findViewById(R.id.listViewNhanVien);

        nhanVienDAO = new NhanVienDAO(getActivity());
        HienThiNhanVien();

        registerForContextMenu(listViewNhanVien);
        return view;
    }
    private void HienThiNhanVien(){
        nhanVienDTOList = nhanVienDAO.DanhSachNhanVien();
        adapterHienThiNhanVien = new AdapterHienThiNhanVien(getActivity(), R.layout.custom_layout_hienthinhanvien,
                nhanVienDTOList);
        listViewNhanVien.setAdapter(adapterHienThiNhanVien);
        adapterHienThiNhanVien.notifyDataSetChanged();
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id= item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int manhanvien = nhanVienDTOList.get(vitri).getMANV();
        switch (id){
            case R.id.itSua:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                iDangKy.putExtra("manhanvien", manhanvien);
                startActivity(iDangKy);
                break;
            case R.id.itXoa:
                boolean kiemtra = nhanVienDAO.XoaNhanVien(manhanvien);
                if(kiemtra){
                    HienThiNhanVien();
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
        MenuItem itThemBanAn = menu.add(1, R.id.itThemNhanVien, 1, R.string.themnhanvien);
        itThemBanAn.setIcon(R.drawable.nhanvien);
        itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case  R.id.itThemNhanVien:
                Intent iDangKy = new Intent(getActivity(), DangKyActivity.class);
                startActivity(iDangKy);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
