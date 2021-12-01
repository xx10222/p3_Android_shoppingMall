package com.example.project3;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//작성한 글을 저장하는 데이터베이스
public class DatabaseWrite extends SQLiteOpenHelper {
    public static final String tableName = "PRODUCT";
    public static final String DBname = "pro3prod.db";

    public DatabaseWrite(Context context, SQLiteDatabase.CursorFactory factory, int version) {
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
    //테이블에는 글번호, 상품명, 상품 내용, 작성자, 장바구니 여부, 조회수, 상품 수량, 상품 가격을 순서대로 저장한다.
    //이 때 글번호는 글이 추가될 때마다 자동으로 1씩 증가하도록 설정한다
    public void createTable(SQLiteDatabase db){
        String sql = "CREATE TABLE " + tableName + "(num integer primary key AUTOINCREMENT, " +
                "title text, content text, writer text, cart integer, hit integer," +
                " quantity integer, price integer)";
        //String sql = "CREATE TABLE " + tableName + "(num integer primary key AUTOINCREMENT,title text,content text, writer text, cart integer, hit integer,quantity integer, price integer)";

        try {
            db.execSQL(sql);
        }catch (SQLException e){
        }
    }

    public void insertUser(SQLiteDatabase db, String title, String content, String writer, int price,
                           int quantity){
        Log.i("tag","글 작성 성공시 실행");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + tableName + "(writer, title, content, quantity," +
                    "price, cart, hit)" + "values('"+ writer +"', '"+ title
                    +"','"+ content+"','"+ quantity +"','"+ price+"','"+ 0 +"','"+ 0 +"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
}
