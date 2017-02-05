package com.llneverland.appoderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llneverland.appoderfood.DTO.ChiTietGoiMonDTO;
import com.llneverland.appoderfood.DTO.GoiMonDTO;
import com.llneverland.appoderfood.DTO.ThanhToanDTO;
import com.llneverland.appoderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows 8.1 on 2/1/2017.
 */
public class GoiMonDAO {
    SQLiteDatabase database;

    public GoiMonDAO(Context context){
        CreateDatabase createDatabase= new CreateDatabase(context);
        database = createDatabase.open();
    }

    public long ThemGoiMon(GoiMonDTO goiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_MABAN,goiMonDTO.getMaban());
        contentValues.put(CreateDatabase.TB_GOIMON_MANV,goiMonDTO.getManhanvien());
        contentValues.put(CreateDatabase.TB_GOIMON_NGAYGOI,goiMonDTO.getNgaygoi());
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG,goiMonDTO.getTinhtrang());

        long magoimon = database.insert(CreateDatabase.TB_GOIMON,null,contentValues);
        return magoimon;
    }

    public long LayMaGoiMonTheoMaBan(int maban,String tinhtrang){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_GOIMON + " WHERE " + CreateDatabase.TB_GOIMON_MABAN + " = '" + maban + "' AND "
                + CreateDatabase.TB_GOIMON_TINHTRANG + " = '" + tinhtrang + "'";

        long magoimon = 0;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            magoimon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON));

            cursor.moveToNext();
        }

        return magoimon;
    }

    public boolean KiemTraMonAnDaTonTai(int magoimon, int mamonan){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;

        Cursor cursor = database.rawQuery(truyvan,null);
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }

    public int LaySoLuongMonAnTheoMaGoiMon(int magoimon,int mamonan){
        int soluong = 0;
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN
                + " = " + mamonan + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + magoimon;
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soluong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG));

            cursor.moveToNext();
        }

        return soluong;
    }

    public boolean CapNhatSoLuong(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoluong());

        long kiemtra = database.update(CreateDatabase.TB_CHITIETGOIMON,contentValues,CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMonDTO.getMagoimon()
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + chiTietGoiMonDTO.getMamonan(),null );

        if (kiemtra != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean ThemChiTietGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG,chiTietGoiMonDTO.getSoluong());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAGOIMON, chiTietGoiMonDTO.getMagoimon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAMONAN,chiTietGoiMonDTO.getMamonan());

        long kiemtra = database.insert(CreateDatabase.TB_CHITIETGOIMON, null, contentValues);

        if (kiemtra != 0){
            return true;
        }else{
            return false;
        }
    }

    public List<ThanhToanDTO> LayDanhSachMonAnTheoMaGoiMon(int magoimon){
        String truyvan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " ct," + CreateDatabase.TB_MONAN + " ma WHERE "
                + "ct." + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = ma." + CreateDatabase.TB_MONAN_MAMON
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = '" + magoimon + "'";

        List<ThanhToanDTO> thanhToanDTOs = new ArrayList<ThanhToanDTO>();
        Cursor cursor = database.rawQuery(truyvan,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ThanhToanDTO thanhToanDTO = new ThanhToanDTO();
            thanhToanDTO.setSoluong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
            thanhToanDTO.setGiatien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            thanhToanDTO.setTenmonan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));

            thanhToanDTOs.add(thanhToanDTO);

            cursor.moveToNext();
        }

        return thanhToanDTOs;
    }

    public boolean CapNhatTrangThaiGoiMonTheoMaBan(int maban,String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG,tinhtrang);

        long kiemtra = database.update(CreateDatabase.TB_GOIMON,contentValues,CreateDatabase.TB_GOIMON_MABAN + " = '" + maban + "'",null);
        if(kiemtra != 0){
            return true;
        }else{
            return false;
        }
    }
}