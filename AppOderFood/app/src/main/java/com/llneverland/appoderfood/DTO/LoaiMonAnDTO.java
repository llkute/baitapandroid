package com.llneverland.appoderfood.DTO;

/**
 * Created by Windows 8.1 on 1/29/2017.
 */
public class LoaiMonAnDTO {
    int MaLoai;
    String TenLoai;
    String HinhAnh;

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }

    public int getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(int maLoai) {
        MaLoai = maLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

}
