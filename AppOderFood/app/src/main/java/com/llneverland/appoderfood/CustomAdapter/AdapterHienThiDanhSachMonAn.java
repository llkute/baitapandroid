package com.llneverland.appoderfood.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.llneverland.appoderfood.DTO.MonAnDTO;
import com.llneverland.appoderfood.R;

import java.util.List;

/**
 * Created by Windows 8.1 on 1/30/2017.
 */
public class AdapterHienThiDanhSachMonAn extends BaseAdapter {
    Context context;
    int layout;
    List<MonAnDTO> monAnDTOList;
    ViewHolderDanhSachMonAn viewHolderDanhSachMonAn;

    public AdapterHienThiDanhSachMonAn(Context context, int layout, List<MonAnDTO> monAnDTOList){
        this.context = context;
        this.layout = layout;
        this.monAnDTOList = monAnDTOList;
    }

    @Override
    public int getCount() {
        return monAnDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return monAnDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return monAnDTOList.get(position).getMaMonAn();
    }

    public class ViewHolderDanhSachMonAn{
        ImageView imHinhMonAn;
        TextView txtTenMonAnThucDon,txtGiaMonAn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderDanhSachMonAn = new ViewHolderDanhSachMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderDanhSachMonAn.imHinhMonAn = (ImageView) view.findViewById(R.id.imHienThiMonAn);
            viewHolderDanhSachMonAn.txtTenMonAnThucDon = (TextView) view.findViewById(R.id.txtTenMonAnThucDon);
            viewHolderDanhSachMonAn.txtGiaMonAn = (TextView) view.findViewById(R.id.txtGiaMonAn);

            view.setTag(viewHolderDanhSachMonAn);
        }else{
            viewHolderDanhSachMonAn = (ViewHolderDanhSachMonAn) view.getTag();
        }

        MonAnDTO monAnDTO = monAnDTOList.get(position);
        String HinhAnh = monAnDTO.getHinhAnh().toString();

        if(HinhAnh == null || HinhAnh.equals("")){
            viewHolderDanhSachMonAn.imHinhMonAn.setImageResource(R.drawable.backgroundheader);
        }else{
            Uri uri = Uri.parse(HinhAnh);
            viewHolderDanhSachMonAn.imHinhMonAn.setImageURI(uri);
        }
        viewHolderDanhSachMonAn.txtTenMonAnThucDon.setText(monAnDTO.getTenMonAn());
        viewHolderDanhSachMonAn.txtGiaMonAn.setText(monAnDTO.getGiaTien());
        return view;
    }
}
