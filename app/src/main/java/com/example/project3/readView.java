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
//상품 상세조회 액티비티
public class readView extends AppCompatActivity {
    DatabaseWrite helper;
    SQLiteDatabase database;
    String sql;
    Cursor cursor;
    int version=1;
    ListView listView;
    int nn;
    String tt;
    String cc;
    String ww;
    int s_hit;
    int qq;
    int pp;
    TextView tnum,ttitle,tcontent,twriter, thit,tquantity,tprice;
    Button rg, rb, rd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readview);
        setTitle("DETAIL");
        listView = (ListView)findViewById(R.id.list);

        rg=(Button)findViewById(R.id.register_get);
        rb=(Button)findViewById(R.id.register_back);
        rd=(Button)findViewById(R.id.register_delete);

        tnum = (TextView)findViewById(R.id.text1);
        ttitle = (TextView)findViewById(R.id.text2);
        tcontent = (TextView)findViewById(R.id.text3);
        twriter = (TextView)findViewById(R.id.text4);
        thit = (TextView)findViewById(R.id.text6);
        tquantity = (TextView)findViewById(R.id.text7);
        tprice = (TextView)findViewById(R.id.text8);

        helper = new DatabaseWrite(readView.this, null, version);
        database = helper.getWritableDatabase();
        int product_num = ((USER)getApplicationContext()).getBno();
        sql="SELECT * FROM "+ helper.tableName + " where num=" + product_num; //현재 글번호와 일치하는 상품의 정보를 얻어온다
        cursor=database.rawQuery(sql,null);

        if(cursor.moveToNext()){ /*
                    num title content writer cart hit quantity price
        */
            do{
                nn=cursor.getInt(0);
                tt=cursor.getString(1);
                cc=cursor.getString(2);
                ww=cursor.getString(3);
                s_hit=cursor.getInt(5);
                qq=cursor.getInt(6);
                pp=cursor.getInt(7);

            }while(cursor.moveToNext());
        }
        cursor.close();
        //위에서 받은 정보 출력하기 위해 저장한다
        tnum.setText(Integer.toString(nn));
        ttitle.setText(tt);
        tcontent.setText(cc);
        twriter.setText(ww);
        thit.setText(Integer.toString(s_hit));
        tquantity.setText(Integer.toString(qq));
        tprice.setText(Integer.toString(pp));

        rg.setOnClickListener(new View.OnClickListener() { //결제페이지?
            //@Override
            public void onClick(View view) { //구매 페이지로 넘어간다
                Intent intent = new Intent(getApplicationContext(), purchase.class);
                startActivity(intent);
                finish();
            }
        });

        rb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //메인화면으로 돌아간다
                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });
        rd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //글 수정 화면으로 넘어간다
                //현재 id와 글의 id(ww)비교
                String now_id = ((USER)getApplicationContext()).getId();
                if(!ww.equals(now_id)) //작성자와 현재 사용자가 다른 경우 글 수정 불가
                {
                    String message ="글을 수정할 수 없습니다";
                    Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                    return;
                }
                    //일치하는 경우 글 수정 화면으로 넘어간다
                    Intent intent = new Intent(getApplicationContext(), updateView.class);
                    startActivity(intent);
                    finish();
            }
        });



    }
}