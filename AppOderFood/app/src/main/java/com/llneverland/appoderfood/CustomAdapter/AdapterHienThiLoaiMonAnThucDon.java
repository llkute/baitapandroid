package com.llneverland.appoderfood.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.llneverland.appoderfood.DAO.LoaiMonAnDAO;
import com.llneverland.appoderfood.DTO.LoaiMonAnDTO;
import com.llneverland.appoderfood.R;

import java.util.List;

/**
 * Created by Windows 8.1 on 1/30/2017.
 */
public class AdapterHienThiLoaiMonAnThucDon extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiMonAnDTO> listLoaiMonAn;
    ViewHolderHienThiLoaiMonAnThucDon viewHolderHienThiLoaiMonAnThucDon;
    LoaiMonAnDAO loaiMonAnDAO;

    public AdapterHienThiLoaiMonAnThucDon(Context context, int layout, List<LoaiMonAnDTO> listLoaiMonAn){
        this.context = context;
        this.layout = layout;
        this.listLoaiMonAn = listLoaiMonAn;
        loaiMonAnDAO = new LoaiMonAnDAO(context);
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

    public class ViewHolderHienThiLoaiMonAnThucDon{
        TextView txtTenLoaiMonAnThucAn;
        ImageView imHienThiMonAn;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            viewHolderHienThiLoaiMonAnThucDon = new ViewHolderHienThiLoaiMonAnThucDon();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, parent, false);

            viewHolderHienThiLoaiMonAnThucDon.txtTenLoaiMonAnThucAn = (TextView) view.findViewById(R.id.txtTenLoaiMonAnThucDon);
            viewHolderHienThiLoaiMonAnThucDon.imHienThiMonAn = (ImageView) view.findViewById(R.id.imHienThiMonAn);

            view.setTag(viewHolderHienThiLoaiMonAnThucDon);
        }else{
            viewHolderHienThiLoaiMonAnThucDon = (ViewHolderHienThiLoaiMonAnThucDon) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = listLoaiMonAn.get(position);
        int maloai = loaiMonAnDTO.getMaLoai();
        String HinhAnh = loaiMonAnDAO.LayHinhAnhMonAn(maloai);
        Uri uri = Uri.parse(HinhAnh);
//        Bitmap bitmap = MediaStore.Images.Media.getBitmap( ,uri);


        viewHolderHienThiLoaiMonAnThucDon.imHienThiMonAn.setImageURI(uri);
        viewHolderHienThiLoaiMonAnThucDon.txtTenLoaiMonAnThucAn.setText(loaiMonAnDTO.getTenLoai());

        return view;
    }
}
