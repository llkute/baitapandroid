package com.llneverland.appoderfood.DTO;

/**
 * Created by Windows 8.1 on 1/27/2017.
 */
public class BanAnDTO {
    int MaBan;
    String TenBan;
    boolean DuocChon;
    public boolean isDuocChon() {
        return DuocChon;
    }

    public void setDuocChon(boolean duocChon) {
        DuocChon = duocChon;
    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int maBan) {
        MaBan = maBan;
    }

    public String getTenBan() {
        return TenBan;
    }

    public void setTenBan(String tenBan) {
        TenBan = tenBan;
    }
}
