package com.llneverland.appoderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.llneverland.appoderfood.DAO.BanAnDAO;
import com.llneverland.appoderfood.DAO.GoiMonDAO;
import com.llneverland.appoderfood.DTO.BanAnDTO;
import com.llneverland.appoderfood.DTO.GoiMonDTO;
import com.llneverland.appoderfood.Fragment.HienThiThucDonFragment;
import com.llneverland.appoderfood.R;
import com.llneverland.appoderfood.ThanhToanActivity;
import com.llneverland.appoderfood.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Windows 8.1 on 1/27/2017.
 */
public class AdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener{

    Context context;
    int layout;
    List<BanAnDTO> listBanAn;
    BanAnDAO banAnDAO;
    GoiMonDAO goiMonDAO;
    FragmentManager fragmentManager;

    public AdapterHienThiBanAn(Context context, int layout, List<BanAnDTO> listBanAn){
        this.context = context;
        this.layout = layout;
        this.listBanAn = listBanAn;
        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return listBanAn.size();
    }

    @Override
    public Object getItem(int position) {
        return listBanAn.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listBanAn.get(position).getMaBan();
    }



    public class ViewHolderBanAn{
        TextView txtTenBanAn;
        ImageView imgBanAn, imgGoiMon, imgThanhToan, imgAnButton;

    }
    ViewHolderBanAn viewHolderBanAn;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_hienthibanan_layout, parent, false);

            viewHolderBanAn = new ViewHolderBanAn();
            viewHolderBanAn.txtTenBanAn = (TextView) view.findViewById(R.id.txtTenBanAn);
            viewHolderBanAn.imgBanAn = (ImageView) view.findViewById(R.id.imgBanAn);
            viewHolderBanAn.imgGoiMon = (ImageView) view.findViewById(R.id.imgGoiMon);
            viewHolderBanAn.imgThanhToan = (ImageView) view.findViewById(R.id.imgThanhToan);
            viewHolderBanAn.imgAnButton = (ImageView) view.findViewById(R.id.imgAnButton);

            view.setTag(viewHolderBanAn);
        }else{
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }

        if(listBanAn.get(position).isDuocChon()){
            HienThiButton();
        }else{
            AnButton(false);
        }

        BanAnDTO banAnDTO = listBanAn.get(position);
        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());

        String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(banAnDTO.getMaBan());
        if(tinhtrang.equals("true")){
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banantrue);
        }else{
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banan);
        }

        viewHolderBanAn.imgBanAn.setTag(position);

        viewHolderBanAn.imgBanAn.setOnClickListener(this);
        viewHolderBanAn.imgGoiMon.setOnClickListener(this);
        viewHolderBanAn.imgThanhToan.setOnClickListener(this);
        viewHolderBanAn.imgAnButton.setOnClickListener(this);

        return view;
    }

    public void HienThiButton(){
        viewHolderBanAn.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgAnButton.setVisibility(View.VISIBLE);

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.hieuung_hienthi_button_banan);
        viewHolderBanAn.imgGoiMon.startAnimation(animation);
        viewHolderBanAn.imgThanhToan.startAnimation(animation);
        viewHolderBanAn.imgAnButton.startAnimation(animation);
    }

    public void AnButton(boolean hieuung){
        if(hieuung ==true){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.hieuung_anbutton_banan);
            viewHolderBanAn.imgGoiMon.startAnimation(animation);
            viewHolderBanAn.imgThanhToan.startAnimation(animation);
            viewHolderBanAn.imgAnButton.startAnimation(animation);
        }
        viewHolderBanAn.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgAnButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        viewHolderBanAn = (ViewHolderBanAn) ((View)v.getParent()).getTag();
        int vitri1 = (int) viewHolderBanAn.imgBanAn.getTag();
        int maban = listBanAn.get(vitri1).getMaBan();
        switch (id){
            case R.id.imgBanAn:
                int vitri= (int) v.getTag();
                listBanAn.get(vitri).setDuocChon(true);
                Toast.makeText(context, "Nhan" , Toast.LENGTH_SHORT).show();
                HienThiButton();

                break;
            case R.id.imgGoiMon:
                Log.d("vitri", vitri1+"");
                String tinhtrang = banAnDAO.LayTinhTrangBanTheoMa(maban);
                if(tinhtrang.equals("false")){
                    // thuc hien code them goi mon va cap nhat tinh trang ban
                    Log.d("tinhtrang", tinhtrang);
                    Intent LayITrangChu =((TrangChuActivity)context).getIntent();
                    int manhavien= LayITrangChu.getIntExtra("manhanvien",0);
                    Log.d("manhavien", manhavien+"");
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yy");
                    String ngaythanhtoan= simpleDateFormat.format(calendar.getTime());

                    GoiMonDTO goiMonDTO = new GoiMonDTO();
                    goiMonDTO.setMaban(maban);
                    goiMonDTO.setManhanvien(manhavien);
                    goiMonDTO.setNgaygoi(ngaythanhtoan);
                    goiMonDTO.setTinhtrang("false");

                    long kiemtra = goiMonDAO.ThemGoiMon(goiMonDTO);
                    banAnDAO.CapNhatTrangThaiBanTheoMa(maban,"true");
                    if(kiemtra == 0){
                        Toast.makeText(context, context.getResources().getString(R.string.themthatbai),Toast.LENGTH_SHORT).show();
                    }

                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                Bundle bDuLieuThucDon = new Bundle();
                bDuLieuThucDon.putInt("maban", maban);

                hienThiThucDonFragment.setArguments(bDuLieuThucDon);
                transaction.replace(R.id.content, hienThiThucDonFragment).addToBackStack("hienthibanan");
                transaction.commit();
                break;
            case R.id.imgThanhToan:
                Intent iThanhToan = new Intent(context, ThanhToanActivity.class);
                iThanhToan.putExtra("maban",maban);
                context.startActivity(iThanhToan);
                break;
            case R.id.imgAnButton:
                AnButton(true);
                break;
        }
    }
}
