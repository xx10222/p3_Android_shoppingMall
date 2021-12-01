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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

//사용자의 구매목록을 보여주는 화면 액티비티
public class mypage extends AppCompatActivity {
    DatabaseMy helper;
    SQLiteDatabase database;
    String sql;
    Cursor cursor;
    int version=1;
    int pp;
    String ii, tt;
    ListView listView;
    List<HashMap<String,String>> list = new ArrayList<>();
    Button rl, rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        setTitle("MY PAGE");
        listView = (ListView)findViewById(R.id.list);

        rl=(Button)findViewById(R.id.register_logout);
        rb=(Button)findViewById(R.id.register_back);

        rl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //로그아웃
                ((USER)getApplicationContext()).setId(null); //저장된 아이디 정보를 삭제한다
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });
        rb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { //메인화면으로 돌아가는 버튼

                Intent intent = new Intent(getApplicationContext(), Home.class);
                startActivity(intent);
                finish();
            }
        });

        String user_id = ((USER)getApplicationContext()).getId(); //현재 저장된 사용자의 아이디를 얻어온다
        helper = new DatabaseMy(mypage.this, null, version);
        database = helper.getWritableDatabase();
        sql="SELECT * FROM "+ helper.tableName + " where id='" + user_id+"'"; //사용자의 아이디와 일치하는 내용을 불러온다
        cursor=database.rawQuery(sql,null);
        if(cursor.moveToNext()){ //0:id, 1:title, 2:price
            do{
                ii=cursor.getString(0);
                tt=cursor.getString(1);
                pp=cursor.getInt(2);
                HashMap<String, String> map = new HashMap<>(); //상품 이름과 가격을 저장한다
                map.put("title",tt);
                String sprice = Integer.toString(pp);
                map.put("price",sprice);
                list.add(map);
            }while(cursor.moveToNext());
        }
        cursor.close();
        SimpleAdapter adapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                new String[] {"title","price"}, new int[] {android.R.id.text1, android.R.id.text2}); //상품 이름과 가격을 리스트로 출력한다
        listView.setAdapter(adapter);
    }
}