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
import android.widget.SimpleAdapter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Home extends AppCompatActivity { //메인페이지
    DatabaseWrite helper;
    SQLiteDatabase database;
    String sql;
    Cursor cursor;
    int version=1;
    int hh;
    ListView listView;
    List<HashMap<String,String>> list = new ArrayList<>();
    Button rw, rl, rm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        setTitle("HOME");
        listView = (ListView)findViewById(R.id.list);

        rw=(Button)findViewById(R.id.register_write);
        rl=(Button)findViewById(R.id.register_logout);
        rm=(Button)findViewById(R.id.register_mine);

        rw.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View view) { //글작성 버튼
                Intent intent = new Intent(getApplicationContext(), write.class);
                startActivity(intent);
                finish();
            }
        });

        rl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //로그아웃 버튼
                ((USER)getApplicationContext()).setId(null);
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });
        rm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //마이페이지로 넘어가는 버튼

                Intent intent = new Intent(getApplicationContext(), mypage.class);
                startActivity(intent);
                finish();
            }
        });

        helper = new DatabaseWrite(Home.this, null, version);
        database = helper.getWritableDatabase();
        sql="SELECT * FROM "+ helper.tableName + " ORDER BY NUM DESC";
        cursor=database.rawQuery(sql,null);
        if(cursor.moveToNext()){ //0:num, 1:title, 2:content, 3:writer, 4:cart, 5:hit, 6:quantity, 7:price
            do{
                hh=cursor.getInt(5);
                HashMap<String, String> map = new HashMap<>();
                map.put("title",cursor.getString(1));
                String sprice = Integer.toString(cursor.getInt(7));
                map.put("price",sprice);
                String snum = Integer.toString(cursor.getInt(0));
                map.put("num",snum);
                list.add(map);
            }while(cursor.moveToNext());
        }
        cursor.close();
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                new String[] {"title","price"}, new int[] {android.R.id.text1, android.R.id.text2});
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int bbb = Integer.parseInt(list.get(position).get("num"));
                ((USER)getApplicationContext()).setBno(bbb); //현재 글번호를 저장해둔다
                bbb=((USER)getApplicationContext()).getBno();
                helper = new DatabaseWrite(Home.this, null, version);
                database = helper.getWritableDatabase();
                sql="SELECT * FROM "+ helper.tableName + " where num=" + bbb;
                cursor=database.rawQuery(sql,null);
                if(cursor.moveToNext()){ //0:num, 1:title, 2:content, 3:writer, 4:cart, 5:hit, 6:quantity, 7:price
                    do{
                        hh=cursor.getInt(5);
                    }while(cursor.moveToNext());
                }
                cursor.close();
                int up_hit = hh+1;
                sql="UPDATE "+ helper.tableName + " set hit='" + up_hit+"'"+" where num='" + bbb+"'"; //조회수 업데이트
                database.execSQL(sql);
                Intent intent = new Intent(getApplicationContext(), readView.class);
                startActivity(intent);
                finish();
            }
        });
    }
}