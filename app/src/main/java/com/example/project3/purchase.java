package com.example.project3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

//결제화면 액티비티
public class purchase extends AppCompatActivity {
    DatabaseWrite helper;
    DatabaseOpenHelper User;
    DatabaseMy Mine;
    SQLiteDatabase database;
    SQLiteDatabase user_db;
    SQLiteDatabase my_db;
    String sql;
    String user_sql;
    Cursor cursor;
    Cursor user_cursor;
    int version=1;
    ListView listView;
    String tt, nn;
    int pp;
    int mm, qq;
    TextView ttitle,tprice,tuserid,tmoney, tquantity, tname;

    List<HashMap<String,String>> list = new ArrayList<>();
    Button rg, rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase);
        setTitle("PURCHASE");
        listView = (ListView)findViewById(R.id.list);

        rg=(Button)findViewById(R.id.register_get);
        rb=(Button)findViewById(R.id.register_back);

        ttitle = (TextView)findViewById(R.id.text2);
        tquantity = (TextView)findViewById(R.id.text7);
        tprice = (TextView)findViewById(R.id.text8);
        tuserid=(TextView)findViewById(R.id.user_id);
        tmoney=(TextView)findViewById(R.id.user_money);
        tname=(TextView)findViewById(R.id.user_name);
        //사용자 테이블에서 사용자 돈이랑 아이디 받아와서 출력하기
        helper = new DatabaseWrite(purchase.this, null, version);
        database = helper.getWritableDatabase();
        final int product_num = ((USER)getApplicationContext()).getBno();
        sql="SELECT * FROM "+ helper.tableName + " where num=" + product_num;
        cursor=database.rawQuery(sql,null);

        if(cursor.moveToNext()){ /*
                    0:num
                    1:title
                    2:content
                    3:writer
                    4:cart
                    5:hit
                    6:quantity
                    7:price
        */
            do{
                //nn=cursor.getInt(0);
                tt=cursor.getString(1);
                pp=cursor.getInt(7);
                qq=cursor.getInt(6);

            }while(cursor.moveToNext());
        }
        cursor.close();
        //위에서 받은 정보 출력
        ttitle.setText(tt);
        tquantity.setText(Integer.toString(qq));
        tprice.setText(Integer.toString(pp));

        final String user_id=((USER)getApplicationContext()).getId();
        tuserid.setText(user_id);

        User = new DatabaseOpenHelper(purchase.this, null, version);
        user_db = User.getWritableDatabase();

        user_sql="SELECT * FROM "+ User.tableName + " where id='" + user_id+"'";
        user_cursor=user_db.rawQuery(user_sql,null);
        if(user_cursor.moveToNext()){ /*
                    0:name
                    1:id
                    2:pw
                    3:money
        */
            do{
                mm=user_cursor.getInt(3);
                nn=user_cursor.getString(0);
                String id = user_cursor.getString(1);

            }while(user_cursor.moveToNext());
        }
        user_cursor.close();
        tmoney.setText(Integer.toString(mm));
        tname.setText(nn);

        rg.setOnClickListener(new View.OnClickListener() { //결제페이지?
            //@Override
            public void onClick(View view) {
                //mm이 돈, pp가 가격
                int up_money=mm-pp; //사용자의 돈에서 상품 가격을 뺀다
                int up_quantity=qq-1; //수량을 1 감소시킨다
                if(up_money<0 || up_quantity<=0) //돈이나 수량이 0보다 작으면 부족하다고 판단하여 구매 거부한다
                {
                    String message ="상품을 구매할 수 없습니다";
                    Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                    return;
                }
                //사용자 돈을 테이블에 업데이트한다
                user_sql="UPDATE "+ User.tableName + " set money='" + up_money+"'"+" where id='" + user_id+"'";
                user_db.execSQL(user_sql);
                //상품 수량을 업데이트한다
                sql="UPDATE "+ helper.tableName + " set quantity='" + up_quantity+"'"+" where num='" + product_num+"'";
                database.execSQL(sql);
                //구매목록 테이블에 추가한다
                Mine = new DatabaseMy(purchase.this, null, version);
                my_db = Mine.getWritableDatabase();
                Mine.insertUser(my_db, user_id, tt, pp);

                String message ="구매완료!";
                Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), Home.class); //메인화면으로 넘어간다
                startActivity(intent);
                finish();
            }
        });

        rb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //상품 상세조회 페이지로 돌아간다
                Intent intent = new Intent(getApplicationContext(), readView.class);
                startActivity(intent);
                finish();
            }
        });

    }
}