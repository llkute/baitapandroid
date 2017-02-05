package com.llneverland.appoderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.llneverland.appoderfood.DTO.ThanhToanDTO;
import com.llneverland.appoderfood.R;

import java.util.List;

/**
 * Created by Windows 8.1 on 2/2/2017.
 */
public class AdapterHienThiThanhToan extends BaseAdapter {
    Context context;
    int layout;
    List<ThanhToanDTO> thanhToanDTOList;
    ViewHolderThanhToan viewHolderThanhToan;
    public AdapterHienThiThanhToan(Context context, int layout, List<ThanhToanDTO> thanhToanDTOList){
        this.context = context;
        this.layout = layout;
        this.thanhToanDTOList = thanhToanDTOList;
    }

    @Override
    public int getCount() {
        return thanhToanDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return thanhToanDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolderThanhToan{
        TextView txtTenMonAnThanhToan, txtSoLuongThanhToan, txtGiaTienThanhToan;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view ==null){
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,parent, false);

            viewHolderThanhToan.txtGiaTienThanhToan = (TextView) view.findViewById(R.id.txtGiaTienThanhToan);
            viewHolderThanhToan.txtSoLuongThanhToan = (TextView) view.findViewById(R.id.txtSoLuongThanhToan);
            viewHolderThanhToan.txtTenMonAnThanhToan = (TextView) view.findViewById(R.id.txtTenMonAnThanhToan);

            view.setTag(viewHolderThanhToan);
        }else{
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        ThanhToanDTO thanhToanDTO = thanhToanDTOList.get(position);

        viewHolderThanhToan.txtGiaTienThanhToan.setText(String.valueOf(thanhToanDTO.getGiatien()));
        viewHolderThanhToan.txtTenMonAnThanhToan.setText(thanhToanDTO.getTenmonan());
        viewHolderThanhToan.txtSoLuongThanhToan.setText(String.valueOf(thanhToanDTO.getSoluong()));

        return view;
    }
}
