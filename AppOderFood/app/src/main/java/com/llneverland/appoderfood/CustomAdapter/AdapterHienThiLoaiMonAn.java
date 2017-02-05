package com.llneverland.appoderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.llneverland.appoderfood.DTO.LoaiMonAnDTO;
import com.llneverland.appoderfood.R;

import java.util.List;

/**
 * Created by Windows 8.1 on 1/29/2017.
 */
public class AdapterHienThiLoaiMonAn extends BaseAdapter {
    Context context;
    int layout;
    List<LoaiMonAnDTO> listLoaiMonAn;

    public AdapterHienThiLoaiMonAn(Context context, int layout, List<LoaiMonAnDTO> listLoaiMonAn){
        this.context = context;
        this.layout = layout;
        this.listLoaiMonAn = listLoaiMonAn;
    }

    @Override
    public int getCount() {
        return listLoaiMonAn.size();
    }

    @Override
    public Object getItem(int position) {
        return listLoaiMonAn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listLoaiMonAn.get(position).getMaLoai();
    }

    public class ViewHolderLoaiMonAn{
        TextView txtTenLoai;
    }

    ViewHolderLoaiMonAn viewHolderLoaiMonAn;

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderLoaiMonAn =  new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinloaithucdon_layout, parent, false);

            viewHolderLoaiMonAn.txtTenLoai = (TextView) view.findViewById(R.id.spintxtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        }else {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = listLoaiMonAn.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderLoaiMonAn =  new ViewHolderLoaiMonAn();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinloaithucdon_layout, parent, false);

            viewHolderLoaiMonAn.txtTenLoai = (TextView) view.findViewById(R.id.spintxtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        }else {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = listLoaiMonAn.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }
}
