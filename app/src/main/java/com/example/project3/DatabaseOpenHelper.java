package com.example.project3;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//회원들의 정보를 저장하는 데이터베이스
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String tableName = "USER";
    public static final String DBname = "pro3user.db";

    public DatabaseOpenHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
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
    //테이블에는 사용자 이름, 사용자 id, 비밀번호, 사용자의 돈이 순서대로 저장된다
    public void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + tableName + "(name text,id text, pw text, money integer)";

        try {
            db.execSQL(sql);
        }catch (SQLException e){
        }
    }

    public void insertUser(SQLiteDatabase db, String name, String id, String pw){
        Log.i("tag","회원가입 성공시 실행");
        db.beginTransaction();
        try {
            int money;
            //사용자의 정보를 저장할 때 사용자의 돈은 기본값으로 300000원을 설정한다
            String sql = "INSERT INTO " + tableName + "(name,id, pw, money)"
                    + "values('"+ name +"','"+ id +"', '"+pw+"','"+ 300000 +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
}