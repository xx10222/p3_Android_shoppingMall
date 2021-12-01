package com.example.project3;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//사용자의 구매목록을 저장하는 데이터 베이스
public class DatabaseMy extends SQLiteOpenHelper {
    public static final String tableName = "mine";
    public static final String DBname = "pro3mine.db";

    public DatabaseMy(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DBname, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("tag","db 생성, 있으면 안함");
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {

    }
    //테이블에는 사용자의 id, 상품 이름, 상품 가격 순서대로 저장된다.
    public void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + tableName + "(id text,title text, price integer)";
        try {
            db.execSQL(sql);
        }catch (SQLException e){
        }
    }

    public void insertUser(SQLiteDatabase db, String id, String title, int price){
        Log.i("tag","상품 구매시 동작");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + tableName + "(id, title, price)"
                    + "values('"+ id +"', '"+ title +"', '"+ price +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
}
