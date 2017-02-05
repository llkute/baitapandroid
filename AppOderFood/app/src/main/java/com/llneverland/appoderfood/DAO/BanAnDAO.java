package com.llneverland.appoderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.llneverland.appoderfood.DTO.BanAnDTO;
import com.llneverland.appoderfood.Database.CreateDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Windows 8.1 on 1/26/2017.
 */
public class BanAnDAO {
    SQLiteDatabase database;

    public BanAnDAO(Context context){
        CreateDatabase createDatabase= new CreateDatabase(context);
        database = createDatabase.open();
    }

    public boolean ThemBanAn(String tenbanan){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN,tenbanan);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, "false");

        long kiemtra = database.insert(CreateDatabase.TB_BANAN,null,contentValues);
        if(kiemtra != 0){
            return true;
        }else{
            return false;
        }
    }

    public List<BanAnDTO> LayTatCaBanAn(){
        List<BanAnDTO> listBanAn = new ArrayList<BanAnDTO>();
        String sTruyVan = "SELECT * FROM "+CreateDatabase.TB_BANAN;
        Cursor cursor = database.rawQuery(sTruyVan,null);
        cursor.moveToFirst();
        while ((!cursor.isAfterLast())){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));

            listBanAn.add(banAnDTO);
            cursor.moveToNext();
        }
        return listBanAn;
    }

    public String LayTinhTrangBanTheoMa(int maban){
        String tinhtrang ="";
        String sTruyVan = "SELECT * FROM "+CreateDatabase.TB_BANAN +" WHERE "
                +CreateDatabase.TB_BANAN_MABAN + " = '"+maban+"'";
        Cursor cursor= database.rawQuery(sTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhtrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TINHTRANG));
            cursor.moveToNext();
        }

        return tinhtrang;
    }

    public boolean CapNhatTrangThaiBanTheoMa(int maban, String tinhtrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, tinhtrang);

        long kiemtra = database.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN +" = "+ "'"+maban+"'", null);
        if(kiemtra !=0){
            return true;
        }else{
            return false;
        }
    }

    public boolean XoaBanAn(int maban){
        long kiemtraxoa= database.delete(CreateDatabase.TB_BANAN,CreateDatabase.TB_BANAN_MABAN + " = '"+ maban +"'",null);
        if(kiemtraxoa != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean CapNhatTenBanTheoMa(int maban, String tenban){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenban);

        long kiemtra = database.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN +" = "+ "'"+maban+"'", null);
        if(kiemtra !=0){
            return true;
        }else{
            return false;
        }
    }
}
